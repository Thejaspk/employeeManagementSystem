package com.retail_cloud.employee_management_system.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.retail_cloud.employee_management_system.dto.DepartmentRequestDTO;
import com.retail_cloud.employee_management_system.dto.DepartmentResponseDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeResponseDTO;
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
			department.setHeadId(new Employee());
			department.getHeadId().setId(request.getHeadId());
			departmentRepository.save(department);
			
			BeanUtils.copyProperties(department, response);
			
			response.setHeadId(department.getHeadId().getId());
		}
		return response;

	}

	@Override
	public DepartmentResponseDTO updateDepartment(DepartmentRequestDTO request, Long id) {

		if (request != null && id != null) {
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
				response.setHeadId(department.getHeadId().getId());
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

	@Override
	public Page<DepartmentResponseDTO> getAllDepartment(Pageable pageable) {
		
		Page<Department> departments = departmentRepository.findAll(pageable);
		
		Page<DepartmentResponseDTO> responsePage=departments.map(department->{
			DepartmentResponseDTO dto = new DepartmentResponseDTO();
			if(department.getCreatedDate()!=null) {
			dto.setCreatedDate(department.getCreatedDate());
			}
			if(department.getDepartmentName()!=null) {
			dto.setDepartmentName(department.getDepartmentName());
			}
			if(department.getHeadId()!=null) {
			dto.setHeadId(department.getHeadId().getId());
			}
			if(department.getId() != null) {
				dto.setId(department.getId());
			}
			return dto;
		});
		
		return responsePage;
	}

	@Override
	public DepartmentResponseDTO getDepartmentById(Long id, String expand) {
		
		Optional<Department> departmentOptional = departmentRepository.findById(id);
		Department department = new Department();
		DepartmentResponseDTO response = new DepartmentResponseDTO();
		List<EmployeeResponseDTO> employeeResponseDTOList = null;

		
		if(departmentOptional.isPresent()) {
			if(departmentOptional.get().getCreatedDate() != null) {
			response.setCreatedDate(departmentOptional.get().getCreatedDate());
			}
			if(departmentOptional.get().getDepartmentName() != null && !departmentOptional.get().getDepartmentName().equals("") ) {
				response.setDepartmentName(departmentOptional.get().getDepartmentName());
				}
			if(departmentOptional.get().getHeadId()!= null) {
				response.setHeadId(departmentOptional.get().getHeadId().getId());
				
			}
			if(departmentOptional.get().getId() != null) {
				response.setId(departmentOptional.get().getId());
			}
			 
			if(expand.equalsIgnoreCase("employee")) {
				
				List<Employee> employeesList = departmentOptional.get().getListOfEmployees();
				
				if(employeesList != null && !employeesList.isEmpty()) {
					
				 employeeResponseDTOList  =	employeesList.stream().map(employee-> {
						EmployeeResponseDTO empDTO = new EmployeeResponseDTO();
						empDTO.setId(employee.getId());
						empDTO.setAddress(employee.getAddress());
						empDTO.setBonusPercentage(employee.getBonusPercentage());
						empDTO.setDateOfBirth(employee.getDateOfBirth());
						empDTO.setJoiningDate(employee.getJoiningDate());
						empDTO.setName(employee.getName());
						if(employee.getReportingManager()!=null) {
							empDTO.setReportingManagerId(employee.getReportingManager().getId());
						}
						if(employee.getDepartment()!=null) {
							empDTO.setDepartmentId(employee.getDepartment().getId());
						}
						empDTO.setRole(employee.getRole());
						empDTO.setSalary(employee.getSalary());
						return empDTO;
					}).toList();
				}
			}
			
			response.setEmployees(employeeResponseDTOList);
			
		}
		return response;
	}



}
