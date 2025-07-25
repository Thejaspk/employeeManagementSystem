package com.retail_cloud.employee_management_system.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.retail_cloud.employee_management_system.dto.DepartmentRequestDTO;
import com.retail_cloud.employee_management_system.dto.DepartmentResponseDTO;
import com.retail_cloud.employee_management_system.exception.CustomResourceNotFoundException;
import com.retail_cloud.employee_management_system.service.DepartmentService;

@RestController
@RequestMapping(value = "department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@PostMapping(value = "createDepartment")
	public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentRequestDTO request) {

	    DepartmentResponseDTO response = departmentService.createDepartment(request);

	    if (response.getId() != null) {
	        // Build the URI for getDepartmentById
	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath()       // base URL: http://localhost:8080
	                .path("/getDepartmentById")     // endpoint
	                .queryParam("id", response.getId())
	                .queryParam("expand", true)     // if expand=true is needed
	                .build()
	                .toUri();

	        return ResponseEntity
	                .created(location)   // HTTP 201 + Location header
	                .body(response);     // response body
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }
	}


	@PutMapping("updateDepartment/{id}")
	public ResponseEntity<DepartmentResponseDTO> updateDepartment(@RequestBody DepartmentRequestDTO request,
			@PathVariable Long id) {

		DepartmentResponseDTO response = departmentService.updateDepartment(request, id);
		if (response != null)
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@DeleteMapping(value = "deleteDepartment/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {

		Boolean response = departmentService.deleteDepartment(id);
		if (response == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else if (!response) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Employees are assigned to this department. So it cannot be deleted!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Department Deleted : " + id);

		}

	}

	@GetMapping(value = "getAllDepartments")
	public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartments(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<DepartmentResponseDTO> responseList = departmentService.getAllDepartment(PageRequest.of(page, size));
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@GetMapping(value = "getDepartmentById")
	public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@RequestParam(required=true) Long id,
			@RequestParam(defaultValue = "") String expand) {

		DepartmentResponseDTO dto = departmentService.getDepartmentById(id, expand);
		if (dto == null) {
			throw new CustomResourceNotFoundException("Department Not Found with the given id : "+ id);
		}
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

}
