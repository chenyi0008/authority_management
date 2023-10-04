package com.chen.config;

import com.chen.SnowFlakeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SnowFlakeConfig {

    @Bean
    SnowFlakeUtil snowFlakeUtil(){
        return new SnowFlakeUtil(1, 1);
    }

}
