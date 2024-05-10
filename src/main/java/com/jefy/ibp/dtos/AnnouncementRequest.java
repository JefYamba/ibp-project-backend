package com.jefy.ibp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementRequest {
    private Long id;
    @NotBlank(message = "Message content must be filled in")
    private String content;

}
