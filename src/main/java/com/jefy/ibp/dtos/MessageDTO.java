package com.jefy.ibp.dtos;

import com.jefy.ibp.entities.AppUser;
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
public class MessageDTO {
    private Long id;
    private String content;
    private Instant createdAt;
    private AppUserDTO sender;
    private AppUserDTO receiver;

    public static MessageDTO fromEntity(Message message) {
        if(message == null || message.getId() == null){
            return null;
        }

        return MessageDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .sender(AppUserDTO.fromEntity(message.getSender()))
                .receiver(AppUserDTO.fromEntity(message.getReceiver()))
                .createdAt(message.getCreatedAt())
                .build();
    }
}
