package com.jefy.ibp.validators;

import com.jefy.ibp.dtos.BookRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public class BookValidator {
    public static Map<String, String> validateBook(BookRequest bookRequest) {
        Map<String, String> errors = new HashMap<>();

        if (bookRequest.getTitle() == null || bookRequest.getTitle().isBlank()) {
            errors.put("title", "Title is required");
        }
        if (bookRequest.getAuthor() == null || bookRequest.getAuthor().isBlank()) {
            errors.put("author", "Author is required");
        }
        if (bookRequest.getGenre() == null || bookRequest.getGenre().isBlank()) {
            errors.put("genre", "Genre is required");
        }
        return errors;
    }

}
