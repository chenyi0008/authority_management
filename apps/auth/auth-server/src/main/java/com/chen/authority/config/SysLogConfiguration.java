package com.chen.authority.config;

import com.chen.authority.biz.service.common.OptLogService;
import com.chen.log.entity.OptLogDTO;
import com.chen.log.event.SysLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.function.Consumer;

/**
 * 系统操作日志配置类
 */
@Configuration
@EnableAsync
public class SysLogConfiguration {
    //创建日志记录监听器对象
    @Bean
    public SysLogListener sysLogListener(OptLogService optLogService){
        Consumer<OptLogDTO> consumer = (optLog) -> optLogService.save(optLog);
        return new SysLogListener(consumer);
    }
}