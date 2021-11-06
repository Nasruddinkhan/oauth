package com.mypractice.employee.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String email;
	
}
