package com.zhu.study.seckill.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zhu.study.seckill.jwt.AuthInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**");   
    }
    
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
}