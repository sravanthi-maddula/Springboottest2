package com.lgi.ee.service;

import com.lgi.ee.entity.Employee;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    Employee fetchEmployee(String employeeId);
    String deleteEmployee(String employeeId);
    Employee updateEmployee(String employeeId, Employee employee);
}
