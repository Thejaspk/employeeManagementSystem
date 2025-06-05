package com.retail_cloud.employee_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail_cloud.employee_management_system.dto.DepartmentRequestDTO;
import com.retail_cloud.employee_management_system.dto.DepartmentResponseDTO;
import com.retail_cloud.employee_management_system.service.DepartmentService;

@RestController
@RequestMapping(value = "department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@PostMapping(value = "createDepartment")
	public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentRequestDTO request) {

		DepartmentResponseDTO response = departmentService.createDepartment(request);
		if (response.getId() != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PutMapping(value = "updateDepartment")
	public ResponseEntity<DepartmentResponseDTO> updateDepartment(@RequestBody DepartmentRequestDTO request,
			@RequestParam("id") Long id) {

		DepartmentResponseDTO response = departmentService.updateDepartment(request, id);
		if (response != null)
			return ResponseEntity.status(HttpStatus.OK).body(response);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@DeleteMapping(value = "deleteDepartment")
	public ResponseEntity<String> deleteDepartment(@RequestParam("id") Long id) {

		Boolean response = departmentService.deleteDepartment(id);
		if (response == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else if (!response) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Employees are assigned to this department. So it cannot be deleted!");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Department Deleted :" + id);

		}

	}

	@GetMapping(value = "getAllDepartment")
	public ResponseEntity<Page<DepartmentResponseDTO>> getAllDepartment(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<DepartmentResponseDTO> responseList = departmentService.getAllDepartment(PageRequest.of(page, size));
		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@GetMapping(value = "getDepartmentId")
	public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@RequestParam Long id,
			@RequestParam(value = "expand", required = false) String expand) {

		DepartmentResponseDTO dto = departmentService.getDepartmentById(id, expand);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

}
