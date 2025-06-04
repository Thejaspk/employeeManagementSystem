package com.retail_cloud.employee_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.retail_cloud.employee_management_system.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT count(e) FROM Employee e WHERE e.departmentId.id = ?1")
	Long findByDepartmentId(Long id);

}
