package com.jefy.ibp.exceptions;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 08/05/2024
 */
public class OperationNotAuthorizedException extends RuntimeException{
    public OperationNotAuthorizedException(String message) {
        super(message);
    }
}
