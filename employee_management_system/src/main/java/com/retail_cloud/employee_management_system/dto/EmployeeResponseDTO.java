package com.retail_cloud.employee_management_system.dto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private Date dateOfBirth;
    private BigDecimal salary;
    private String address;
    private String role;
    private Date joiningDate;
    private BigDecimal bonusPercentage;
    private Long departmentId;
    private Long reportingManagerId;
}
