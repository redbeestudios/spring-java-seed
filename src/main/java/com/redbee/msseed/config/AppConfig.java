package com.redbee.msseed.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class AppConfig implements WebMvcConfigurer {

    private final TraceSleuthInterceptor traceSleuthInterceptor;

    public AppConfig(TraceSleuthInterceptor traceSleuthInterceptor) {
        this.traceSleuthInterceptor = traceSleuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.traceSleuthInterceptor);
    }


}
