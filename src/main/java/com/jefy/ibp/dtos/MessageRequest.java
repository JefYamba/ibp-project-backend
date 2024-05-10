package com.jefy.ibp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class MessageRequest {
    private Long id;
    @NotBlank(message = "Message content must be filled in")
    private String content;

    @NotNull(message = "Sender id cannot be null")
    @Min(value = 1, message = "Sender id cannot be less than 1")
    private Long senderId;
    private Long receiverId;

}
