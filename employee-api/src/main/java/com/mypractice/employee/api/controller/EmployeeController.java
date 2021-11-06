package com.mypractice.employee.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mypractice.employee.api.dto.EmployeeDto;

@RequestMapping("/employee")
@RestController
public class EmployeeController {
	@Autowired
	Environment env;
	
	@PreAuthorize("hasAuthority('ROLE_developer')")
	@RequestMapping("/all")
	public List<EmployeeDto> getAllEmployee() {
		return Arrays.asList(
				EmployeeDto.builder().employeeId(101).firstName("Nasruddin")
				.lastName("Khan")
				.email("Nasruddinkhan44@gmail.com")
				.build(), 
				EmployeeDto.builder().employeeId(102).firstName("Sufiya")
				.lastName("Khan")
				.email("Sufiyakhan@gmail.com")
				.build()
				);
	}
}
