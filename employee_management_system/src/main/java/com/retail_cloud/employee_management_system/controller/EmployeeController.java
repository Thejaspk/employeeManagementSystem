package com.retail_cloud.employee_management_system.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.retail_cloud.employee_management_system.dto.EmployeeLookUpDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeRequestDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeResponseDTO;
import com.retail_cloud.employee_management_system.service.EmployeeService;

@RestController
@RequestMapping(value = "employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PutMapping(value = "updateEmployeeDepartment")
	public ResponseEntity<String> updateEmployeeDepartment(@RequestParam(required=true)  Long id,
			@RequestParam(required=true) Long departmentId) {

		Boolean response = employeeService.updateEmployeeDepartment(id, departmentId);
		if (!response) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not present with given id");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Department updated for employee id :" + id);

		}

	}

	@GetMapping(value = "getAllEmployees")
	public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<EmployeeResponseDTO> responseList = employeeService.getAllEmployees(PageRequest.of(page, size));
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@PostMapping(value = "createEmployee")
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO request) {

	    EmployeeResponseDTO response = employeeService.createEmployee(request);

	    if (response.getId() != null) {
	        // Build Location URI for the new employee
	        URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(response.getId())
	                .toUri();

	        return ResponseEntity
	                .created(location)   // 201 Created + Location header
	                .body(response);     // Return the created employee data
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }
	}


	@PutMapping(value = "updateEmployee/{id}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody EmployeeRequestDTO request,
			@PathVariable Long id) {

		EmployeeResponseDTO response = employeeService.updateEmployee(request, id);
		if (response != null)
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping(value = "employeeLookUp")
	public ResponseEntity<Page<EmployeeLookUpDTO>> employeeLookUp(
			@RequestParam(required = false, defaultValue = "false") Boolean lookup,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		
		Page<EmployeeLookUpDTO> response = employeeService.getEmployeeLookUp(lookup, PageRequest.of(page, size));
		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

}
