package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.ChangePasswordRequest;
import com.jefy.ibp.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@AllArgsConstructor
@Service
public class PasswordValidator {
    private  final PasswordEncoder passwordEncoder;

    public   Set<String> validatePassword(ChangePasswordRequest changePasswordRequest, AppUser appUser) {
        Set<String> errors = new HashSet<>();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),appUser.getPassword())) {
            errors.add("Old password is incorrect");
        }

        if (changePasswordRequest.getConfirmPassword() == null || changePasswordRequest.getConfirmPassword().isBlank() ||
                !changePasswordRequest.getConfirmPassword().equals(changePasswordRequest.getNewPassword()))
        {
            errors.add("Passwords do not match");
        }
        return errors;
    }
}

