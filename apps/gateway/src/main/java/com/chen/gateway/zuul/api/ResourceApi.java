package com.chen.gateway.zuul.api;

import com.chen.authority.dto.auth.ResourceQueryDTO;
import com.chen.authority.entity.auth.Resource;
import com.chen.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@FeignClient(name = "${pinda.feign.authority-server:pd-auth-server}",
//        fallback = ResourceApiFallback.class)

//在运行时，Spring 会将 ${pinda.feign.authority-server:auth-server} 替换为 pinda.feign.authority-server 配置属性的实际值。如果未定义该属性，它将使用默认值“auth-server”
@FeignClient(name = "${feign.authority-server:auth-server}",
        fallback = ResourceApiFallback.class)
public interface ResourceApi {
    //获取所有需要鉴权的资源
    @GetMapping("/resource/list")
    public R<List> list();

    //查询当前登录用户拥有的资源权限
    @GetMapping("/resource")
    public R<List<Resource>> visible(ResourceQueryDTO resource);
}
