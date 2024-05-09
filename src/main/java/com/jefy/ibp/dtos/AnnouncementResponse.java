package com.jefy.ibp.dtos;

import com.jefy.ibp.entities.Announcement;
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
public class AnnouncementResponse {
    private Long id;
    private String content;
    private Instant createdAt;

    public static AnnouncementResponse fromEntity(Announcement announcement) {
        if (announcement == null || announcement.getId() == null) {
            return null;
        }
        return AnnouncementResponse.builder()
                .id(announcement.getId())
                .content(announcement.getContent())
                .createdAt(announcement.getCreatedAt())
                .build();
    }
}
