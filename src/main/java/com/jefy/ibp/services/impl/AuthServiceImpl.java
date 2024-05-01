package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.AppUserDTO;
import com.jefy.ibp.dtos.LoginResponseDTO;
import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.security.TokenService;
import com.jefy.ibp.services.AuthService;
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
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public LoginResponseDTO login(String email, String password) throws AuthenticationException  {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        String token = tokenService.generateJwt(auth);

        return new LoginResponseDTO(token, AppUserDTO.fromEntity((appUserRepository.findByEmail(email).orElse(new AppUser()))));
    }
}
