package com.lgi.ee.service;

import com.lgi.ee.entity.Department;
import com.lgi.ee.entity.Employee;
import com.lgi.ee.exception.EmployeeNotFoundException;
import com.lgi.ee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setup() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }
    @Test
    void addEmployee_testWhenEmployeeAdded() {
        Employee employee = buildEmployee();
        when(employeeRepository.save(any())).thenReturn(employee);
        Employee savedEmp = employeeService.addEmployee(employee);
        assertEquals("Kalpana", savedEmp.getFirstName());
    }

    @Test
    void fetchEmployee_testWhenGivenEmployeeIdFound() {
        when(employeeRepository.getEmployeeById("12345")).thenReturn(buildEmployee());
        Employee employee = employeeService.fetchEmployee("12345");
        assertEquals("RD", employee.getDepartment().getDepartmentCode());
    }

    @Test
    void fetchEmployee_testWhenGivenEmployeeIdNotFound() {
        when(employeeRepository.getEmployeeById("12345")).thenThrow(new EmployeeNotFoundException("employee not found"));
        EmployeeNotFoundException thrown = assertThrows(EmployeeNotFoundException.class, () -> employeeService.fetchEmployee("12345"));
        assertEquals("employee not found", thrown.getMessage());
    }

    @Test
    void deleteEmployee_testWhenGivenEmployeeIdFound() {
        when(employeeRepository.getEmployeeById("12345")).thenReturn(buildEmployee());
        when(employeeRepository.delete(any())).thenReturn("deleted successfully");
        String message = employeeService.deleteEmployee("12345");
        assertEquals("deleted successfully", message);
    }

    @Test
    void deleteEmployee_testWhenGivenEmployeeIdNotFound() {
        when(employeeRepository.getEmployeeById("12345")).thenThrow(new EmployeeNotFoundException("employee not found"));
        EmployeeNotFoundException thrown = assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee("12345"));
        assertEquals("employee not found", thrown.getMessage());
    }

    private Employee buildEmployee() {
        Employee employee = new Employee();
        Department department = new Department("R&D", "RD");
        employee.setDepartment(department);
        employee.setEmployeeId("12345");
        employee.setEmail("test@unit.com");
        employee.setFirstName("Kalpana");
        employee.setLastName("Pochareddy");
        return employee;
    }
}
