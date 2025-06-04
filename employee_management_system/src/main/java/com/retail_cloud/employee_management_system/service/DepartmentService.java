package com.retail_cloud.employee_management_system.service;

import com.retail_cloud.employee_management_system.dto.DepartmentRequestDTO;
import com.retail_cloud.employee_management_system.dto.DepartmentResponseDTO;

public interface DepartmentService {

	DepartmentResponseDTO createDepartment(DepartmentRequestDTO request);

	DepartmentResponseDTO updateDepartment(DepartmentRequestDTO request, Long id);

	Boolean deleteDepartment(Long id);

}
