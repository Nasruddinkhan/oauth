package com.mypractice.department.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import com.mypractice.department.api.converter.KeycloakRoleConverter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter  {

	private final KeycloakRoleConverter keyCloackConverter;

	@Autowired
	public WebSecurity(KeycloakRoleConverter keyCloackConverter) {
		super();
		this.keyCloackConverter = keyCloackConverter;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(keyCloackConverter);
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/department/all").hasRole("developer")
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
