package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.AppUserRequestDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public class AppUserValidator {
    public static Map<String, String> validateUser(AppUserRequestDTO appUserRequestDTO) {
        Map<String, String> errors = new HashMap<>();

        if (appUserRequestDTO.getFirstName() == null || appUserRequestDTO.getFirstName().isBlank()) {
            errors.put("firstName", "First name is required");
        }
        if (appUserRequestDTO.getLastName() == null || appUserRequestDTO.getLastName().isBlank()) {
            errors.put("lastName", "Last name is required");
        }
        if (appUserRequestDTO.getEmail() == null || appUserRequestDTO.getEmail().isBlank() || !isValidEmail(appUserRequestDTO.getEmail())) {
            errors.put("email", "Email is required or is not a valid");
        }
        return errors;
    }

    private static boolean isValidEmail(String text) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        return isValid(text,regex);
    }
    private static boolean isValid(String text, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

}
