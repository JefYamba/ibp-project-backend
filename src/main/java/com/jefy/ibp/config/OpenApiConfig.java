package com.jefy.ibp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "JefYamba",
                        email = "joph.e.f.yamba@gmail.com",
                        url = "https://github.com/JefYamba"
                ),
                description = "OpenApi documentation for Internet Based Programming Project",
                title = "Based Programming Project REST API - JefYamba",
                version = "1"
        ),
        servers = {
                @Server(
                        description = "Local Env",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Authentification",
    scheme = "bearer",
    type =  SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
