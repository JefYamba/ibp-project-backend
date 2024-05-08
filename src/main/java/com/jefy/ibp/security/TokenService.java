package com.jefy.ibp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 21/04/2024
 */
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtEncoder jwtEncoder;

    public String generateJwt(Authentication auth) {
        Instant now = Instant.now();

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Long id = ((AppUserDetails)auth.getPrincipal()).getAppUser().getId();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName())
                .claim("id", id)
                .claim("roles", scope)
                .build();


        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
