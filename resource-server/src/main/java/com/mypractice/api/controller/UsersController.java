package com.mypractice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypractice.api.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	Environment env;

	@GetMapping("/status/check")
	public String status() {
		return "Working on port: " + env.getProperty("local.server.port");
	}

	// @PreAuthorize("#id == #jwt.subject")
	@PreAuthorize("hasAuthority('ROLE_developer') or #id == #jwt.subject")
	// @PreAuthorize("hasRole('developer')")
	// @Secured("ROLE_user")
	@DeleteMapping("/{id}")
	public String deleteUserById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		return "delete user by : " + id + " and jwt subject is " + jwt.getSubject();
	}

	@PostAuthorize("hasAuthority('ROLE_developer') or returnObject.userId == #jwt.subject")
	@GetMapping("/{id}")
	public UserDto getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		return new UserDto("Nasruddin", "Khan", "1d891fc9-746b-4de1-8c5a-5827687b579");
	}
} 
