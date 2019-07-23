package com.example.pjmall.backend.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableAuthorizationServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// super.configure(http);
		// 지원 서버 접근 권한 설정
		http
				.authorizeRequests()
				.antMatchers("/hello*")
				.access("#oauth2.hasScope('MALL_USER')");
		
		
//		http
//		.authorizeRequests().antMatchers("/hello2").access("#oauth2.hasScope('hello2')");

	}
}