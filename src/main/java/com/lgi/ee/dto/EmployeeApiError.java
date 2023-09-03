package com.lgi.ee.dto;

import lombok.Builder;
import lombok.Data;

/**
 * This class is used for employee api error
 *
 * @author Kalpana Pochareddy
 */
@Data
@Builder
public class EmployeeApiError {
    private Integer errorCode;
    private String errorMessage;
}
