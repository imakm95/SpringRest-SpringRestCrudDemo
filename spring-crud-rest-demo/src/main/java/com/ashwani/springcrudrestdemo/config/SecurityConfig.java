package com.ashwani.springcrudrestdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
/*This configuration class adds three sample users. Currently we're using in-memory authentication to keep things simple. 
 * But you could easily use database storage with encrypted passwords. We covered db storage in previous videos. */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserBuilder users = User.withDefaultPasswordEncoder();
		auth.inMemoryAuthentication().withUser(users.username("ASHWANI").password("ASHWANI_").roles("EMPLOYEE"))  //for GET only
													.withUser(users.username("ANJANI").password("ANJANI_").roles("EMPLOYEE","MANAGER")) //FOR GET,PUT,POST,PATCH
													.withUser(users.username("NARENDRA").password("NARENDRA_").roles("EMPLOYEE","ADMIN")); //FOR GET,PUT,POST,DELETE
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* This only allows access to the given endpoints based on the role. 
		The use of “**” makes sure to secure endpoints if user enters additional information at the end of the URL. */	
		
		http
		.authorizeRequests()
		//.antMatchers("/api/customers/**").authenticated()  //only for authentication without roles
		.antMatchers(HttpMethod.GET, "/api/customers").hasRole("EMPLOYEE") // for authentication based on roles
		.antMatchers(HttpMethod.GET, "/api/customers/**").hasRole("EMPLOYEE") 
		.antMatchers(HttpMethod.PUT, "/api/customers").hasAnyRole("MANAGER","ADMIN") 
		.antMatchers(HttpMethod.PUT, "/api/customers/**").hasAnyRole("MANAGER","ADMIN") 
		.antMatchers(HttpMethod.POST, "/api/customers").hasAnyRole("MANAGER","ADMIN") 
		.antMatchers(HttpMethod.POST, "/api/customers/**").hasAnyRole("MANAGER","ADMIN") 
		.antMatchers(HttpMethod.PATCH, "/api/customers").hasAnyRole("MANAGER","ADMIN") 
		.antMatchers(HttpMethod.PATCH, "/api/customers/**").hasAnyRole("MANAGER","ADMIN") 
		.antMatchers(HttpMethod.DELETE, "/api/customers").hasRole("ADMIN") 
		.antMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN") 
		.and()
		.httpBasic()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	//Role Access Failure gives 403 Forbidden Error & Auth Access Failure gives 401 Unauthorized Error
	
	// Why disable CSRF?
			/* Spring Security 5 has CSRF enabled by default. You would need to send over CSRF tokens.
			However, CSRF generally does not apply for REST APIs. CSRF protection is a request that could be processed by a browser 
			by normal users.
			If you are only creating a REST service that is used by non-browser clients, you will likely want to disable CSRF protection.*/
			

	// Why disable sessions?
			/* For our application, we would like avoid the use of cookies for sesson tracking. This should force the REST client
			to enter user name and password for each request. However, this is not always the case depending on the REST client / browser 
			you are using. Your mileage will vary here (for example, this doesn't work in Eclipse embedded browser).*/
}
