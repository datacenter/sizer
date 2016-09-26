/**
 * 
 */
package com.cisco.acisizer.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Mahesh
 *
 */

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class CustomConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Override the configuration of spring security to disable authentication
	 * for the static UI components.
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/index*","/profiler/**/","/app/**", "/scripts/**", "/static_jsons/**", "/samples/**", "/styles/**", "/images/**",
						"/modules/**", "/bower_components/**", "/", "/about", "/login", "/Config/AuthConfig",
						"/views/**", "/acisizer/**","/api-docs",
						"/api-docs/default/app-controller","/api-docs/default/bd-controller","/api-docs/default/epg-controller",
						"/api-docs/default/project-controller","/api-docs/default/tenant-controller","/api-docs/default/vrf-controller",
						"/api-docs/default/l-3-out-controller","/api-docs/default/contract-controller","/api-docs/default/generic-controller",
						"/api-docs/default/project-controller-debug","/api-docs/default/shared-resource-controller",
						"/api-docs/default/device-controller"," /api-docs/default/rack-controller",
						"/api-docs/default/room-controller","/api-docs/default/row-controller","/api-docs/default/rack-controller",
						"/api-docs/default/server-controller","/api-docs/default/device-type-controller","/api-docs/default/switch-controller",
						"/api-docs/default/policy-controller",
						"/restapi*","/lib/*","/fonts/*","/lang/*","/swagger*")
				.permitAll().anyRequest().authenticated();

		http.httpBasic().disable();
		http.csrf().disable();
		// Following code was added to cache the page. The css/JS script is accessing content in another page which is not downloaded resulting in improper rendering of the page. This showsup when hosted on a remote server. Comment out the code below to disable caching. Now we have external IP for server where it can be reproduced and arrive at proper fix of waiting till all files are downloaded rather than depending on the cache.
		//
		 // http.headers().cacheControl().disable();
	}
}
