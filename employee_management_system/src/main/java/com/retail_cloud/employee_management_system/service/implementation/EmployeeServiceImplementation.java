package com.retail_cloud.employee_management_system.service.implementation;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.retail_cloud.employee_management_system.dto.EmployeeLookUpDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeRequestDTO;
import com.retail_cloud.employee_management_system.dto.EmployeeResponseDTO;
import com.retail_cloud.employee_management_system.entity.Department;
import com.retail_cloud.employee_management_system.entity.Employee;
import com.retail_cloud.employee_management_system.repository.EmployeeRepository;
import com.retail_cloud.employee_management_system.service.EmployeeService;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Boolean updateEmployeeDepartment(Long id, Long departmentId) {

		Optional<Employee> optionalEmployee = employeeRepository.findById(id);

		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			employee.setDepartment(new Department());
			employee.getDepartment().setId(departmentId);
			employeeRepository.save(employee);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
		Page<Employee> employeesPaginatedList = employeeRepository.findAll(pageable);

		Page<EmployeeResponseDTO> responsePage = employeesPaginatedList.map(employee -> {
			EmployeeResponseDTO dto = new EmployeeResponseDTO();
			if (employee != null) {
				dto.setId(employee.getId());
				dto.setName(employee.getName());
				dto.setDateOfBirth(employee.getDateOfBirth());
				dto.setSalary(employee.getSalary());
				dto.setAddress(employee.getAddress());
				dto.setRole(employee.getRole());
				dto.setJoiningDate(employee.getJoiningDate());
				dto.setBonusPercentage(employee.getBonusPercentage());

				if (employee.getDepartment() != null) {
					dto.setDepartmentId(employee.getDepartment().getId());
				} else {
					dto.setDepartmentId(null);
				}

				if (employee.getReportingManager() != null) {
					dto.setReportingManagerId(employee.getReportingManager().getId());
				} else {
					dto.setReportingManagerId(null);
				}
			}
			return dto;
		});

		return responsePage;
	}

	@Override
	public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request) {
		EmployeeResponseDTO response = new EmployeeResponseDTO();
		Employee employee = new Employee();
		BeanUtils.copyProperties(request, employee);
		if(request.getReportingManagerId()!= null) {
		employee.setReportingManager(new Employee());
		employee.getReportingManager().setId(request.getReportingManagerId());
		}
		if(request.getDepartmentId()!=null) {
		employee.setDepartment(new Department());
		employee.getDepartment().setId(request.getDepartmentId());
		}
		employeeRepository.save(employee);
		BeanUtils.copyProperties(employee, response);
		if(request.getReportingManagerId()!= null) {
			response.setReportingManagerId(employee.getReportingManager().getId());
		}
		if(request.getDepartmentId()!=null) {
			response.setDepartmentId(employee.getDepartment().getId());
		}

		return response;
	}

	@Override
	public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO request, Long id) {

		if (request != null && id != null) {
			Employee employee = new Employee();
			EmployeeResponseDTO response = new EmployeeResponseDTO();
			Optional<Employee> employeeOptional = employeeRepository.findById(id);

			if (employeeOptional.isPresent()) {
				employee = employeeOptional.get();
				if (request.getAddress() != null && !request.getAddress().equals("")) {
					employee.setAddress(request.getAddress());
				}
				if (request.getBonusPercentage() != null) {
					employee.setBonusPercentage(request.getBonusPercentage());
				}
				if (request.getDateOfBirth() != null) {
					employee.setDateOfBirth(request.getDateOfBirth());
				}
				if (request.getDepartmentId() != null) {
					employee.setDepartment(new Department());
					employee.getDepartment().setId(request.getDepartmentId());
				}
				if (request.getJoiningDate() != null) {
					employee.setJoiningDate(request.getJoiningDate());
				}
				if (request.getName() != null && !request.getName().equals("")) {
					employee.setName(request.getName());
				}
				if (request.getReportingManagerId() != null) {
					employee.setReportingManager(new Employee());
					employee.getReportingManager().setId(request.getReportingManagerId());
				}
				if (request.getRole() != null && !request.getRole().equals("")) {
					employee.setRole(request.getRole());
				}
				if (request.getSalary() != null) {
					employee.setSalary(request.getSalary());
				}
				employeeRepository.save(employee);
				BeanUtils.copyProperties(employee, response);
				
				if(employee.getDepartment() != null) {
					response.setDepartmentId(employee.getDepartment().getId());
				}
				if(employee.getReportingManager() != null) {
					response.setReportingManagerId(employee.getReportingManager().getId());
				}
			}
			return response;
		}
		return null;
	}

	@Override
	public Page<EmployeeLookUpDTO> getEmployeeLookUp(Boolean lookup, Pageable pageable) {

		if (lookup) {
			
			Page<Employee> employeesPaginatedList = employeeRepository.findAll(pageable);

			Page<EmployeeLookUpDTO> responsePage = employeesPaginatedList.map(emp -> {
				EmployeeLookUpDTO dto = new EmployeeLookUpDTO();
				if (emp != null) {
					if (emp.getId() != null) {
						dto.setId(emp.getId());
					}
					if (emp.getName() != null && !emp.getName().equals("")) {
						dto.setName(emp.getName());
					}
				}
				return dto;
			});
			return responsePage;
			
		}
		return null;
	}

}
