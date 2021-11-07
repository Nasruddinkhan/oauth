package com.mypractice.api.client.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class DepartmentDto {
	private String departmentId;
	private String departmentName;
	private String port;
}

