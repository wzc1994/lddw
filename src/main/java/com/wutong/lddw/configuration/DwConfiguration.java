package com.wutong.lddw.configuration;

import com.wutong.lddw.context.WebFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DwConfiguration {

    @Bean
    WebFilter paramFilter(){
        return new WebFilter();
    }

    @Bean("ParamFilter")
    public FilterRegistrationBean<WebFilter> registerFilter1(WebFilter webFilter) {
        FilterRegistrationBean<WebFilter> registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(webFilter);
        registrationBean.addUrlPatterns("/api/*"); //url拦截
        registrationBean.setOrder(1);
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

}
