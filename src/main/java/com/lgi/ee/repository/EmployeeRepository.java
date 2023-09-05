package com.lgi.ee.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.lgi.ee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * The employees repository details.
 *
 * @author Kalpana Pochareddy
 */
@Repository
public class EmployeeRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public EmployeeRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Employee save(Employee employee) {
        dynamoDBMapper.save(employee);
        return employee;
    }

    public Employee getEmployeeById(String employeeId) {
        return dynamoDBMapper.load(Employee.class, employeeId);
    }

    public String delete(Employee employee) {
        dynamoDBMapper.delete(employee);
        return "Employee Deleted!";
    }

    public Employee update(String employeeId, Employee employee) {
        dynamoDBMapper.save(employee,
                new DynamoDBSaveExpression()
        .withExpectedEntry("employeeId",
                new ExpectedAttributeValue(
                        new AttributeValue().withS(employeeId)
                )));
        return employee;
    }

    public List<Employee> getAllEmployees() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return new ArrayList<>(dynamoDBMapper.scan(Employee.class, scanExpression));
    }
}
