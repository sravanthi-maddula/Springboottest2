package com.lgi.ee.exception;

/**
 * The Employee class contains the EmployeeNotFoundException.
 *
 * @author Kalpana Pochareddy
 */
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
