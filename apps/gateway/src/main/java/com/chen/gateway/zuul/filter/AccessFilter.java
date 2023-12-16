package com.chen.gateway.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.chen.authority.dto.auth.ResourceQueryDTO;
import com.chen.authority.entity.auth.Resource;
import com.chen.common.constant.CacheKey;
import com.chen.context.BaseContextConstants;
import com.chen.exception.code.ExceptionCode;
import com.chen.gateway.zuul.api.ResourceApi;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权处理过滤器
 */
@Component
@Slf4j
public class AccessFilter extends BaseFilter {
    @Autowired
    private ResourceApi resourceApi;
    @Autowired
    private CacheChannel cacheChannel;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    //鉴权处理逻辑
    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        log.warn("request url:{}", request.getRequestURI());
        //第1步：判断当前请求uri是否需要忽略
        if(isIgnoreToken()){
            //当前请求需要忽略，直接放行
            log.warn("当前请求需要忽略，直接放行");
            return null;
        }
//        if(true){
//            return null;
//        }
        //第2步：获取当前请求的请求方式和uri，拼接成GET/user/page这种形式，称为权限标识符
//        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String method = request.getMethod();//GET POST PUT
        String uri = request.getRequestURI();
        uri = StrUtil.subSuf(uri,zuulPrefix.length());
        uri = StrUtil.subSuf(uri,uri.indexOf("/",1));
        String permission = method + uri;//GET/user/page
        log.warn("METHOD/URL:{}", permission);

        //第3步：从缓存中获取所有需要进行鉴权的资源(同样是由资源表的method字段值+url字段值拼接成)，如果没有获取到则通过Feign调用权限服务获取并放入缓存中
        CacheObject cacheObject = cacheChannel.get(CacheKey.RESOURCE, CacheKey.RESOURCE_NEED_TO_CHECK);
        List<String> list = (List<String>) cacheObject.getValue();
        if(list == null){
            //缓存中没有相应数据
            list = resourceApi.list().getData();
            //放入缓存中
            if(list != null && list.size() > 0){
                cacheChannel.set(CacheKey.RESOURCE,CacheKey.RESOURCE_NEED_TO_CHECK,list);
            }
        }

        //第4步：判断这些资源是否包含当前请求的权限标识符，如果不包含当前请求的权限标识符，则返回未经授权错误提示
        long count = list.stream().filter((resource) -> {
            return permission.startsWith(resource);
        }).count();

        if(count == 0){
            //当前请求是一个未知请求，直接返回未授权异常信息
            log.warn("当前请求是一个未知请求，直接返回未授权异常信息");
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),ExceptionCode.UNAUTHORIZED.getCode(),401);
            return null;
        }

        //第5步：如果包含当前的权限标识符，则从zuul header中取出用户id，根据用户id取出缓存中的用户拥有的权限，如果没有取到则通过Feign调用权限服务获取并放入缓存，判断用户拥有的权限是否包含当前请求的权限标识符
        String userId = RequestContext.getCurrentContext().getZuulRequestHeaders().get(BaseContextConstants.JWT_KEY_USER_ID);

        log.warn("debug:cacheChannel{}", cacheChannel);
        log.warn("debug:cacheChannel.get(CacheKey.USER_RESOURCE, userId){}", cacheChannel.get("user_resource", "3"));
        log.warn("debug: USER_RESOURCE:{}  userId:{}", CacheKey.USER_RESOURCE, userId);
        log.warn("debug:cacheChannel.get(CacheKey.USER_RESOURCE, userId).getValue{}", cacheChannel.get(CacheKey.USER_RESOURCE, userId).getValue());
        CacheObject cache = cacheChannel.get(CacheKey.USER_RESOURCE, userId);

        if(cache == null){
            log.warn("cache == null，直接返回未授权异常信息");
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),ExceptionCode.UNAUTHORIZED.getCode(),401);
            return null;
        }

        log.warn("cache!=null");
        List<String> visibleResource = (List<String>) cache.getValue();
        if(visibleResource == null){
            //缓存中不存在，需要通过接口远程调用权限服务来获取
            ResourceQueryDTO resourceQueryDTO = ResourceQueryDTO.builder().userId(new Long(userId)).build();

//            log.warn("resourceApi:{}", resourceApi);
//            log.warn("resourceQueryDTO:{}", resourceQueryDTO.toString());
//            log.warn("resourceApi.visible(resourceQueryDTO):{}", resourceApi.visible(resourceQueryDTO));
//            log.warn("resourceApi.visible(resourceQueryDTO).getData():{}", resourceApi.visible(resourceQueryDTO).getData());


            List<Resource> resourceList = resourceApi.visible(resourceQueryDTO).getData();
            log.warn("通过feign获取到资源权限表：{}", resourceList.toString());
            if(resourceList != null && resourceList.size() > 0){
                visibleResource = resourceList.stream().map((resource -> {
                    return resource.getMethod() + resource.getUrl();
                })).collect(Collectors.toList());
                //将当前用户拥有的权限载入缓存
                cacheChannel.set(CacheKey.USER_RESOURCE,userId,visibleResource);
            }else{
                errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),ExceptionCode.UNAUTHORIZED.getCode(),401);
                return null;
            }
        }

        //第6步：如果用户拥有的权限包含当前请求的权限标识符则说明当前用户拥有权限，直接放行
        log.warn("第6步：如果用户拥有的权限包含当前请求的权限标识符则说明当前用户拥有权限，直接放行");
        count = visibleResource.stream().filter((resource) -> {
            return permission.startsWith(resource);
        }).count();

        if(count > 0){
            //当前用户拥有访问权限，直接放行
            log.warn("当前用户拥有访问权限，直接放行");
            return null;
        }else{
            log.warn("第7步：如果用户拥有的权限不包含当前请求的权限标识符则说明当前用户没有权限，返回未经授权错误提示");
            //第7步：如果用户拥有的权限不包含当前请求的权限标识符则说明当前用户没有权限，返回未经授权错误提示
            errorResponse(ExceptionCode.UNAUTHORIZED.getMsg(),ExceptionCode.UNAUTHORIZED.getCode(),403);
            return null;
        }
    }
}
