package com.jefy.ibp.exceptions;

import lombok.Getter;

import java.util.Set;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Getter
public class EntityNotValidException extends RuntimeException {
    private final Set<String> errors;
    public EntityNotValidException(Set<String> errors) {
        super("Entity not valid");
        this.errors = errors;
    }
}
