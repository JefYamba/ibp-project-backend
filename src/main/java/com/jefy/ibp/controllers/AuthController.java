package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.Constants;
import com.jefy.ibp.dtos.LoginRequest;
import com.jefy.ibp.dtos.ResponseDTO;
import com.jefy.ibp.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;

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


    @PostMapping("/login")
    @Operation(
            summary = "Authenticate",
            description = "Tries to authenticate a user",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Can't authenticate", responseCode = "401"),
            }
    )
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequest body){

        return ResponseEntity.ok(ResponseDTO.builder()
                .timeStamp(LocalDateTime.now())
                .status(ACCEPTED)
                .statusCode(ACCEPTED.value())
                .message("Authenticated successfully")
                .data(of("auth", authService.login(body.getUsername(), body.getPassword())))
                .build()
        );
    }
}
