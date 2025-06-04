package com.retail_cloud.employee_management_system.service.implementation;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail_cloud.employee_management_system.dto.DepartmentRequestDTO;
import com.retail_cloud.employee_management_system.dto.DepartmentResponseDTO;
import com.retail_cloud.employee_management_system.entity.Department;
import com.retail_cloud.employee_management_system.entity.Employee;
import com.retail_cloud.employee_management_system.repository.DepartmentRepository;
import com.retail_cloud.employee_management_system.repository.EmployeeRepository;
import com.retail_cloud.employee_management_system.service.DepartmentService;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

	DepartmentRepository departmentRepository;

	public DepartmentServiceImplementation(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public DepartmentResponseDTO createDepartment(DepartmentRequestDTO request) {
		Department department = new Department();
		DepartmentResponseDTO response = new DepartmentResponseDTO();
		if (request != null) {
			BeanUtils.copyProperties(request, department);
			departmentRepository.save(department);
			BeanUtils.copyProperties(department, response);
		}
		return response;

	}

	@Override
	public DepartmentResponseDTO updateDepartment(DepartmentRequestDTO request, Long id) {

		if (request != null) {
			Department department = new Department();
			DepartmentResponseDTO response = new DepartmentResponseDTO();
			Optional<Department> departmentOptional = departmentRepository.findById(id);

			if (departmentOptional.isPresent()) {
				department = departmentOptional.get();
				if (request.getDepartmentName() != null && !request.getDepartmentName().equals("")) {
					department.setDepartmentName(request.getDepartmentName());
				}
				if (request.getCreatedDate() != null) {
					department.setCreatedDate(request.getCreatedDate());
				}
				if (request.getHeadId() != null) {
					department.setHeadId(new Employee());
					department.getHeadId().setId(request.getHeadId());
				}
				departmentRepository.save(department);
				BeanUtils.copyProperties(department, response);
			}
			return response;
		}
		return null;
	}

	@Override
	public Boolean deleteDepartment(Long id) {
		Department department = new Department();
		Optional<Department> departmentOptional = departmentRepository.findById(id);
		
		if(departmentOptional.isPresent()) {
			Long count = employeeRepository.findByDepartmentId(id);
			if(count>0) {
				return false;
			}
			else {
				departmentRepository.deleteById(id);
				return true;
			}
		}
		return null;
		
	}

}
