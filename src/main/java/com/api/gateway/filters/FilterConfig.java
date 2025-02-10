package com.api.gateway.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }
    @Bean
    public JwtAuthGatewayFilter jwtAuthGatewayFilter() {
        return new JwtAuthGatewayFilter();
    }
    @Bean
    public RateLimitFilter rateLimitFilter() {
        return new RateLimitFilter();
    }
}
