package com.mypractice.api.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

	@GetMapping("/jwt")
	public Map<String, Object> getJwtPrincipal(@AuthenticationPrincipal Jwt jwt){
		return Collections.singletonMap("principle", jwt);
	}

}