package com.mgmt.user.config;

import com.mgmt.user.filter.RequestIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		return new FilterRegistrationBean(new RequestIdFilter());
	}
}
