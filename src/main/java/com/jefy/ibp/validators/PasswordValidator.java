package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.ChangePWRequestDTO;
import com.jefy.ibp.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@AllArgsConstructor
@Service
public class PasswordValidator {
    private  final PasswordEncoder passwordEncoder;

    public   Map<String, String> validatePassword(ChangePWRequestDTO changePWRequestDTO, AppUser appUser) {
        Map<String, String> errors = new HashMap<>();

        if (changePWRequestDTO.getOldPassword() == null || changePWRequestDTO.getOldPassword().isBlank()) {
            errors.put("oldPassword", "Old password is required");
        } else if (!appUser.getPassword().equals(passwordEncoder.encode(changePWRequestDTO.getNewPassword()))) {
            errors.put("oldPassword", "Old password is incorrect");
        }
        if (changePWRequestDTO.getNewPassword() == null || changePWRequestDTO.getNewPassword().isBlank() ||
                changePWRequestDTO.getNewPassword().length() < 4)
        {
            errors.put("newPassword", "New password must be at least 4 characters");
        }
        if (changePWRequestDTO.getConfirmPassword() == null || changePWRequestDTO.getConfirmPassword().isBlank() ||
                !changePWRequestDTO.getConfirmPassword().equals(changePWRequestDTO.getNewPassword()))
        {
            errors.put("confirmPassword", "Passwords do not match");
        }
        return errors;
    }
}

