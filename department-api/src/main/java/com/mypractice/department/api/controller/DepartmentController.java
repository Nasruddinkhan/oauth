package com.mypractice.department.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypractice.department.api.dto.DepartmentDto;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@PreAuthorize("hasAuthority('ROLE_developer')")
	@GetMapping("/all")
	public List<DepartmentDto> findAllDepartments() {
		return Arrays.asList(DepartmentDto.builder().departmentId("IT-DPT").departmentName("IT-DEPARTMENT").build(), 
				DepartmentDto.builder().departmentId("MGR-DPT").departmentName("MANAGER-DEPARTMENT").build());
	}
}
