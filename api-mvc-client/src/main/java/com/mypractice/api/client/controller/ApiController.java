package com.mypractice.api.client.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.mypractice.api.client.dto.DepartmentDto;

@Controller
public class ApiController {

	@Autowired
	OAuth2AuthorizedClientService  oAuth2AuthorizedClientService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	WebClient webClient;
	
	@GetMapping("/department")
	public String findAllDepartments(Model model, @AuthenticationPrincipal OidcUser oidcUser) {

		System.out.println("oidcUser = " + oidcUser);
		OidcIdToken idToken = oidcUser.getIdToken();
		String idTokenValue = idToken.getTokenValue();
		System.out.println("idTokenValue = " + idTokenValue);

		List<DepartmentDto> department = Arrays.asList(
				DepartmentDto.builder().departmentId("IT-DPT").departmentName("IT-DEPARTMENT").port("").build(),
				DepartmentDto.builder().departmentId("MGR-DPT").departmentName("MANAGER-DEPARTMENT").port("").build());
		model.addAttribute("department", department);
		return "department";
	}

	@GetMapping("/department/jwtAccessToken")
	public String findAllDepartmentsJwtAccessToken(Model model, @AuthenticationPrincipal OidcUser oidcUser) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(auth2AuthenticationToken.getAuthorizedClientRegistrationId(), auth2AuthenticationToken.getName());
	    String jwtAccessTOken =	authorizedClient.getAccessToken().getTokenValue();
	    System.out.println("jwtAccessTOken "+jwtAccessTOken);
		List<DepartmentDto> department = Arrays.asList(
				DepartmentDto.builder().departmentId("IT-DPT").departmentName("IT-DEPARTMENT").port("").build(),
				DepartmentDto.builder().departmentId("MGR-DPT").departmentName("MANAGER-DEPARTMENT").port("").build());
		model.addAttribute("department", department);
		return "department";
	}
	@GetMapping("/department/restTemplate")
	public String findAllDepartmentsRestTemplate(Model model, @AuthenticationPrincipal OidcUser oidcUser) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(auth2AuthenticationToken.getAuthorizedClientRegistrationId(), auth2AuthenticationToken.getName());
	    String jwtAccessToken =	authorizedClient.getAccessToken().getTokenValue();
	    System.out.println("jwtAccessTOken "+jwtAccessToken);
		String url = "http://localhost:1082/department/all";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+jwtAccessToken);
		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<List<DepartmentDto>> department = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<DepartmentDto>>() {} );
		model.addAttribute("department", department.getBody());
		return "department";
	}
	
	@GetMapping("/department/webClient")
	public String getAlbums(Model model, 
			@AuthenticationPrincipal OidcUser principal) {
		
		String url = "http://localhost:1082/department/all";
		List<DepartmentDto> department = webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<DepartmentDto>>(){})
				.block();
        model.addAttribute("department", department);
		return "department";
	}
}
