package com.mypractice.api.client.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mypractice.api.client.dto.DepartmentDto;

@Controller
public class ApiController {

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
}
