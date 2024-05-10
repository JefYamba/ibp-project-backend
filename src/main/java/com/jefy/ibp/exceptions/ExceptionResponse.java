package com.jefy.ibp.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Set;


/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 10/05/2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private LocalDateTime timeStamp;
    private Integer statusCode;
    private HttpStatus status;
    private String error;
    private Set<String> formErrors;
}
