package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.AppUserDTO;
import com.jefy.ibp.dtos.ChangePWRequestDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public class PasswordValidator {
    public static Map<String, String> validatePassword(ChangePWRequestDTO changePWRequestDTO) {
        Map<String, String> errors = new HashMap<>();

        if (changePWRequestDTO.getOldPassword() == null || changePWRequestDTO.getOldPassword().isBlank()) {
            errors.put("oldPassword", "Old password is required");
        }
        if (changePWRequestDTO.getNewPassword() == null || changePWRequestDTO.getNewPassword().isBlank() ||
                changePWRequestDTO.getNewPassword().length() < 8)
        {
            errors.put("newPassword", "New password must be at least 8 characters");
        }
        if (changePWRequestDTO.getConfirmPassword() == null || changePWRequestDTO.getConfirmPassword().isBlank() ||
                !changePWRequestDTO.getConfirmPassword().equals(changePWRequestDTO.getNewPassword()))
        {
            errors.put("confirmPassword", "Passwords do not match");
        }
        return errors;
    }
}

