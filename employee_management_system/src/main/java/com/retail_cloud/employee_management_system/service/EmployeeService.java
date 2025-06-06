package com.retail_cloud.employee_management_system.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.retail_cloud.employee_management_system.dto.EmployeeLookUpDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeRequestDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeResponseDTO;

public interface EmployeeService {

	Boolean updateEmployeeDepartment(Long id, Long departmentId);

	Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable);

	EmployeeResponseDTO createEmployee(EmployeeRequestDTO request);

	EmployeeResponseDTO updateEmployee(EmployeeRequestDTO request, Long id);

	Page<EmployeeLookUpDTO> getEmployeeLookUp( Boolean lookup, Pageable pageable);

}
