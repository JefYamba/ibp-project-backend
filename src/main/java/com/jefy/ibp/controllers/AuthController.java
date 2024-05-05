package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.Constants;
import com.jefy.ibp.dtos.LoginRequest;
import com.jefy.ibp.dtos.LoginResponseDTO;
import com.jefy.ibp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest body){
        return ResponseEntity.ok(authService.login(body.getUsername(), body.getPassword()));
    }
}
