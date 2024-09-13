package com.kodilla.ecommercee.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class ApiKeyFilterConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilter() {
        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiKeyFilter());

        // add filter to our endpoints
        registrationBean.addUrlPatterns("/v1/products/modify/*", "/v1/groups/modify/*","/v1/orders/modify/*","/v1/users/modify/*");

        return registrationBean;
    }
}