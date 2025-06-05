package com.retail_cloud.employee_management_system.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail_cloud.employee_management_system.dto.DepartmentRequestDTO;
import com.retail_cloud.employee_management_system.dto.DepartmentResponseDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeLookUpDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeRequestDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeResponseDTO;
import com.retail_cloud.employee_management_system.service.EmployeeService;

@RestController
public class EmployeeController {

	EmployeeService employeeService;

	@PutMapping(value = "updateEmployeeDepartment")
	public ResponseEntity<String> updateEmployeeDepartment(@RequestParam("id") Long id,
			@RequestParam Long departmentId) {

		Boolean response = employeeService.updateEmployeeDepartment(id, departmentId);
		if (!response) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employees are not present with given id");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Department Updated with employee id :" + id);

		}

	}

	@GetMapping(value = "getAllEmployee")
	public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployee(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<EmployeeResponseDTO> responseList = employeeService.getAllEmployee(PageRequest.of(page, size));
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@PostMapping(value = "createEmployee")
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO request) {
		EmployeeResponseDTO response = employeeService.createEmployee(request);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PutMapping(value = "updateEmployee")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody EmployeeRequestDTO request,
			@RequestParam("id") Long id) {

		EmployeeResponseDTO response = employeeService.updateEmployee(request, id);
		if (response != null)
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping(value="employeeLookUp")
	public ResponseEntity<List<EmployeeLookUpDTO>> employeeLookUp(
			@RequestParam(required = false, defaultValue = "false") Boolean lookup) {
		List<EmployeeLookUpDTO> response =employeeService.getEmployeeLookUp(lookup);
		if(response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

}
