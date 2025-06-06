package com.retail_cloud.employee_management_system.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "employee" , schema = "employee_management_system")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id ; 
	@Column(name="name")
	private String name; 
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	@Column(name="salary")
	private BigDecimal salary;
	@Column(name="address")
	private String address;
	@Column(name="role")
	private String role;
	@Column(name="joining_date")
	private Date joiningDate;
	@Column(name="bonus_percentage")
	private BigDecimal bonusPercentage;
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	@ManyToOne
	@JoinColumn(name="reporting_manager_id")
	private Employee reportingManager;
	
}
