package com.dawnop.p5home.config;

import com.dawnop.p5home.filter.CrossDomainFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {
    @Bean
    public FilterRegistrationBean<CrossDomainFilter> crossDomainFilter() {
        FilterRegistrationBean<CrossDomainFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CrossDomainFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
