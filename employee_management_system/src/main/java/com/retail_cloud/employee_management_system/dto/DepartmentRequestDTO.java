package com.retail_cloud.employee_management_system.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DepartmentRequestDTO {

	
	private String departmentName;
	private Long headId;
	private Date createdDate;
}
