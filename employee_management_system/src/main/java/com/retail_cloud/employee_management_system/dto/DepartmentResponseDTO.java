package com.retail_cloud.employee_management_system.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DepartmentResponseDTO {
	private Long id ;
	private String departmentName;
	private Date createdDate;
	private Long headId;
    private List<EmployeeResponseDTO> employees;

}
