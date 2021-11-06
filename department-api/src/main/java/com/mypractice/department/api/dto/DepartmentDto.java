package com.mypractice.department.api.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class DepartmentDto {
	private String departmentId;
	private String departmentName;
}
