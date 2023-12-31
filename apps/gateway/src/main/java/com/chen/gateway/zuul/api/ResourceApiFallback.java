package com.chen.gateway.zuul.api;

import com.chen.authority.dto.auth.ResourceQueryDTO;
import com.chen.authority.entity.auth.Resource;
import com.chen.base.R;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * 资源API熔断
 */
@Component
public class ResourceApiFallback implements ResourceApi {
    @Override
    public R<List> list() {
        return null;
    }

    @Override
    public R<List<Resource>> visible(ResourceQueryDTO resource) {
        return null;
    }
}
