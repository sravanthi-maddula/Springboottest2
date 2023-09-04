package com.lgi.ee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgi.ee.entity.Department;
import com.lgi.ee.entity.Employee;
import com.lgi.ee.exception.EmployeeNotFoundException;
import com.lgi.ee.exception.ExceptionControllerAdvice;
import com.lgi.ee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        EmployeeController employeeController = new EmployeeController(employeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                        .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Test
    void getEmployee_testWhenGivenEmployeeIdFound() throws Exception {
        when(employeeService.fetchEmployee("12345")).thenReturn(buildEmployee());
        mockMvc.perform(get("/employee/{id}", "12345"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firstName").value("Kalpana"))
                .andExpect(jsonPath("$.department.departmentCode").value("RD"));
    }

    @Test
    void getEmployee_testWhenGivenEmployeeIdIsNotFound() throws Exception {
        when(employeeService.fetchEmployee("12346")).thenThrow(new EmployeeNotFoundException("employee not found"));
        mockMvc.perform(get("/employee/{id}", "12346"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEmployee_testWhenGivenEmployeeIdFound() throws Exception {
        when(employeeService.deleteEmployee("12345")).thenReturn("employee deleted successfully");
        mockMvc.perform(delete("/employee/{id}", "12345"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployee_testWhenGivenEmployeeIdIsNotFound() throws Exception {
        when(employeeService.deleteEmployee("12346")).thenThrow(new EmployeeNotFoundException("employee not found"));
        mockMvc.perform(delete("/employee/{id}", "12346"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveEmployee_testWhenGivenEmployeeIdFound() throws Exception {
        when(employeeService.addEmployee(any())).thenReturn(buildEmployee());
        String json = asJsonString(buildEmployee());
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Kalpana"))
                .andExpect(jsonPath("$.department.departmentCode").value("RD"));
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

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
