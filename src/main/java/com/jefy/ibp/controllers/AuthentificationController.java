package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AuthentificationResponse;
import com.jefy.ibp.dtos.Constants;
import com.jefy.ibp.dtos.AuthentificationRequest;
import com.jefy.ibp.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.AUTHENTIFICATION_URL)
public class AuthentificationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    @Operation(
            summary = "Authenticate",
            description = "Tries to authenticate a user",
            responses =  {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized / Can't authenticate", responseCode = "401"),
            }
    )
    public ResponseEntity<AuthentificationResponse> login(@RequestBody AuthentificationRequest body){
        return ResponseEntity.status(OK).body(authenticationService.authenticate(body.getUsername(), body.getPassword()));
    }
}
