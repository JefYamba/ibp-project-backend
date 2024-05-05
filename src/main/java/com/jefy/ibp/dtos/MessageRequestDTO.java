package com.jefy.ibp.dtos;

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
public class MessageRequestDTO {
    private Long id;
    private String content;
    private Long senderId;
    private Long receiverId;

}
