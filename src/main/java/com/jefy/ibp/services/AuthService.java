package com.jefy.ibp.services;

import com.jefy.ibp.dtos.LoginResponseDTO;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public interface AuthService {

    LoginResponseDTO login(String username, String password) throws AuthenticationException;
}
