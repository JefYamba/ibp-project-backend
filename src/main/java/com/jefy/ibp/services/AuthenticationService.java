package com.jefy.ibp.services;

import com.jefy.ibp.dtos.AuthentificationResponse;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
public interface AuthenticationService {

    AuthentificationResponse authenticate(String username, String password) throws AuthenticationException;
}
