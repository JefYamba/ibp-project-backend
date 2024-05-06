package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.AppUserRequestDTO;
import com.jefy.ibp.dtos.BookRequestDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public class BookValidator {
    public static Map<String, String> validateBook(BookRequestDTO bookRequestDTO) {
        Map<String, String> errors = new HashMap<>();

        if (bookRequestDTO.getTitle() == null || bookRequestDTO.getTitle().isBlank()) {
            errors.put("title", "Title is required");
        }
        if (bookRequestDTO.getAuthor() == null || bookRequestDTO.getAuthor().isBlank()) {
            errors.put("author", "Author is required");
        }
        if (bookRequestDTO.getGenre() == null || bookRequestDTO.getGenre().isBlank()) {
            errors.put("genre", "Genre is required");
        }
        return errors;
    }

}
