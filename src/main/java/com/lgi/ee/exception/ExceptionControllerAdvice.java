package com.lgi.ee.exception;

import com.lgi.ee.dto.EmployeeApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Employee class contains the Api exception details.
 *
 * @author Kalpana Pochareddy
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EmployeeApiError handleException(Exception exception) {
        return EmployeeApiError.builder().errorMessage(exception.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value()).build();
    }
}
