package com.jefy.ibp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthentificationRequest {

    @Email(
            message = "Email must be like well formated (eg: example@text.com)",
            regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    )
    @NotBlank(message = "Email must be filled in")
    private String username;
    @NotBlank(message = "Email must be filled in")
    private String password;
}
