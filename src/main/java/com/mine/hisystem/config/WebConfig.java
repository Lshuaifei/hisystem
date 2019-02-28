package com.mine.hisystem.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig{

	@Bean    // 配置siteMesh3
	public FilterRegistrationBean siteMeshFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		WebSiteMeshFilter siteMeshFilter = new WebSiteMeshFilter();
		filterRegistrationBean.setFilter(siteMeshFilter);
		return filterRegistrationBean;
	}

}
