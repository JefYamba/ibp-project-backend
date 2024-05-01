package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.AppUserDTO;
import com.jefy.ibp.entities.AppUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public class AppUserValidator {
    public static Map<String, String> validateUser(AppUserDTO appUserDTO) {
        Map<String, String> errors = new HashMap<>();

        if (appUserDTO.getFirstName() == null || appUserDTO.getFirstName().isBlank()) {
            errors.put("firstName", "First name is required");
        }
        if (appUserDTO.getLastName() == null || appUserDTO.getLastName().isBlank()) {
            errors.put("lastName", "Last name is required");
        }
        if (appUserDTO.getEmail() == null || appUserDTO.getEmail().isBlank()) {
            errors.put("email", "Email is required");
        }
        return errors;
    }
}
