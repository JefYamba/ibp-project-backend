package com.jefy.ibp.dtos;

import com.jefy.ibp.entities.Message;
import lombok.*;

import java.time.Instant;

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
public class MessageResponse {
    private Long id;
    private String content;
    private Instant createdAt;
    private UserResponse sender;
    private UserResponse receiver;

    public static MessageResponse fromEntity(Message message) {
        if(message == null || message.getId() == null){
            return null;
        }

        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .sender(UserResponse.fromEntity(message.getSender()))
                .receiver(UserResponse.fromEntity(message.getReceiver()))
                .createdAt(message.getCreatedAt())
                .build();
    }
}
