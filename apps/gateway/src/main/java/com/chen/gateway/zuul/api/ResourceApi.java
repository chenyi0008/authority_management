package com.chen.gateway.zuul.api;

import com.chen.authority.dto.auth.ResourceQueryDTO;
import com.chen.authority.entity.auth.Resource;
import com.chen.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


@FeignClient(name = "${feign.authority-server:auth-server}",
        fallback = ResourceApiFallback.class)
public interface ResourceApi {
    //获取所有需要鉴权的资源
    @GetMapping("/resource/list")
    public R<List> list();

    //查询当前登录用户拥有的资源权限
    @GetMapping("/resource")
    public R<List<Resource>> visible(@SpringQueryMap ResourceQueryDTO resource);
}
