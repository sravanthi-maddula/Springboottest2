package com.lgi.ee.controller;

import com.lgi.ee.entity.Employee;
import com.lgi.ee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller used to retrieve, add and delete employee details
 *
 * @author Kalpana Pochareddy
 */

@CrossOrigin(origins = "http://18.169.165.162:4200", allowedHeaders = "*")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") String employeeId) {
        return employeeService.fetchEmployee(employeeId);
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") String employeeId) {
        return  employeeService.deleteEmployee(employeeId);
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable("id") String employeeId, @RequestBody Employee employee) {
        return employeeService.updateEmployee(employeeId,employee);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.fetchAllEmployee();
    }
}
