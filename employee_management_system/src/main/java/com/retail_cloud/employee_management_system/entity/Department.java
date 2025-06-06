package com.retail_cloud.employee_management_system.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "department" , schema = "employee_management_system")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id ;
	@Column(name = "department_name")
	private String departmentName;
	@Column(name = "created_date")
	private Date createdDate;
	@OneToOne
	@JoinColumn(name = "department_head_id")
	private Employee headId;
	
	@OneToMany(mappedBy = "department" , fetch = FetchType.LAZY)
	private List<Employee> listOfEmployees ; 
	

}
