package com.jefy.ibp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
public class ChangePasswordRequest {
    @NotBlank(message = "Old password must be filled in")
    private String oldPassword;

    @Length(min = 4, message = "New password must contain at least 4 characters")
    @NotBlank(message = "New password must be filled in")
    private String newPassword;

    @NotBlank(message = "Confirm new password must be filled in")
    private String confirmPassword;
}
