package com.retail_cloud.employee_management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.retail_cloud.employee_management_system.dto.DepartmentRequestDTO;
import com.retail_cloud.employee_management_system.dto.DepartmentResponseDTO;

public interface DepartmentService {

	DepartmentResponseDTO createDepartment(DepartmentRequestDTO request);

	DepartmentResponseDTO updateDepartment(DepartmentRequestDTO request, Long id);

	Boolean deleteDepartment(Long id);


	Page<DepartmentResponseDTO> getAllDepartment(Pageable pageable);

	DepartmentResponseDTO getDepartmentById(Long id, String expand);

}
