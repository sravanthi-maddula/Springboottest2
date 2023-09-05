package com.lgi.ee.service;

import com.lgi.ee.entity.Employee;
import com.lgi.ee.exception.EmployeeNotFoundException;
import com.lgi.ee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class contains Employee service implementation details.
 *
 * @author Kalpana Pochareddy
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee fetchEmployee(String employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        if (Optional.ofNullable(employee).isPresent()) {
            return employee;
        }
        throw new EmployeeNotFoundException("Employee not found with ID " + employeeId);
    }

    @Override
    public String deleteEmployee(String employeeId) {
        Employee employee = fetchEmployee(employeeId);
        return employeeRepository.delete(employee);
    }

    @Override
    public Employee updateEmployee(String employeeId, Employee employee) {
        return employeeRepository.update(employeeId, employee);
    }

    @Override
    public List<Employee> fetchAllEmployee() {
        return employeeRepository.getAllEmployees();
    }
}
