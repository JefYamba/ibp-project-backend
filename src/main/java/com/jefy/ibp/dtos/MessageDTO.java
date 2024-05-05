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
    private AppUser sender;
    private AppUser receiver;

    public static MessageDTO fromEntity(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .content(message.getContent())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .createdAt(message.getCreatedAt())
                .build();
    }

    public static Message toEntity(MessageDTO messageDTO) {
        return Message.builder()
                .id(messageDTO.getId())
                .content(messageDTO.getContent())
                .sender(messageDTO.getSender())
                .receiver(messageDTO.getReceiver())
                .createdAt(messageDTO.getCreatedAt())
                .build();
    }
}
