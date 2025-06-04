package com.retail_cloud.employee_management_system.dto;

import java.util.Date;

import com.retail_cloud.employee_management_system.entity.Employee;

import lombok.Data;

@Data
public class DepartmentResponseDTO {
	private Long id ;
	private String departmentName;
	private Date createdDate;
	private Long headId;
}
