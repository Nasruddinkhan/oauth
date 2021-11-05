package com.mypractice.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import com.mypractice.api.converter.KeycloakRoleConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private final KeycloakRoleConverter keyCloackConverter;

	@Autowired
	public WebSecurity(KeycloakRoleConverter keyCloackConverter) {
		super();
		this.keyCloackConverter = keyCloackConverter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.debug("Using default configure(HttpSecurity). "
				+ "If subclassed this will potentially override subclass configure(HttpSecurity).");
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(keyCloackConverter);
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/status/check").hasRole("developer")
				// .hasAnyRole("") for used multiple role
				// .hasAuthority("SCOPE_profile")
				.anyRequest()
				.authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt()
				.jwtAuthenticationConverter(jwtAuthenticationConverter);

	}


}
