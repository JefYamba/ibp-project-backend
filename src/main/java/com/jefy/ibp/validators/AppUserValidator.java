package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.UserRequest;

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
    public static Map<String, String> validateUser(UserRequest userRequest) {
        Map<String, String> errors = new HashMap<>();

        if (userRequest.getFirstName() == null || userRequest.getFirstName().isBlank()) {
            errors.put("firstName", "First name is required");
        }
        if (userRequest.getLastName() == null || userRequest.getLastName().isBlank()) {
            errors.put("lastName", "Last name is required");
        }
        if (userRequest.getEmail() == null || userRequest.getEmail().isBlank() || !isValidEmail(userRequest.getEmail())) {
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
