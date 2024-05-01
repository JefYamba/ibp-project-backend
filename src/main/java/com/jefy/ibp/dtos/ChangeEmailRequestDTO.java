package com.jefy.ibp.dtos;

import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeEmailRequestDTO {
    private Long userId;
    private String email;
}
