package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.Constants;
import com.jefy.ibp.dtos.LoginRequest;
import com.jefy.ibp.dtos.LoginResponseDTO;
import com.jefy.ibp.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.AUTHENTIFICATION_URL)
public class AuthController {

    private final AuthService authService;

    @Operation(
            description = "get the authentification and the token for the user",
            summary = "authenticate",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad request / Invalid parameter", responseCode = "400")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body){
        try {
            return ResponseEntity.ok(authService.login(body.getUsername(), body.getPassword()));
        } catch (AuthenticationException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", e.getMessage());

            return ResponseEntity.badRequest().body(errors);
        }
    }
}
