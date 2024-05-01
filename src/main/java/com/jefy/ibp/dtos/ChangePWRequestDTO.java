package com.jefy.ibp.dtos;

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
public class ChangePWRequestDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
