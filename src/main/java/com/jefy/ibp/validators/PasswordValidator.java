package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.ChangePasswordRequest;
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

    public   Map<String, String> validatePassword(ChangePasswordRequest changePasswordRequest, AppUser appUser) {
        Map<String, String> errors = new HashMap<>();

        if (changePasswordRequest.getOldPassword() == null || changePasswordRequest.getOldPassword().isBlank()) {
            errors.put("oldPassword", "Old password is empty");
        } else if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),appUser.getPassword())) {
            errors.put("oldPassword", "Old password is incorrect");
        }
        if (changePasswordRequest.getNewPassword() == null || changePasswordRequest.getNewPassword().isBlank() ||
                changePasswordRequest.getNewPassword().length() < 4)
        {
            errors.put("newPassword", "New password must be at least 4 characters");
        }
        if (changePasswordRequest.getConfirmPassword() == null || changePasswordRequest.getConfirmPassword().isBlank() ||
                !changePasswordRequest.getConfirmPassword().equals(changePasswordRequest.getNewPassword()))
        {
            errors.put("confirmPassword", "Passwords do not match");
        }
        return errors;
    }
}

