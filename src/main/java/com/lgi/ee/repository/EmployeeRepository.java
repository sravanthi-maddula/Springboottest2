package com.lgi.ee.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.lgi.ee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * The employees repository details.
 *
 * @author Kalpana Pochareddy
 */
@Repository
public class EmployeeRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;


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
}
