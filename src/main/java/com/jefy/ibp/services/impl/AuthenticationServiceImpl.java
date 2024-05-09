package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.AuthentificationResponse;
import com.jefy.ibp.security.TokenService;
import com.jefy.ibp.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public AuthentificationResponse authenticate(String email, String password) throws AuthenticationException  {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        String token = tokenService.generateJwt(auth);

        return new AuthentificationResponse(token);
    }
}
