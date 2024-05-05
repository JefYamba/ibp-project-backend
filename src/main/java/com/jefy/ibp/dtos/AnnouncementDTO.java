package com.jefy.ibp.dtos;

import com.jefy.ibp.entities.Announcement;
import com.jefy.ibp.entities.AppUser;
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
public class AnnouncementDTO {
    private Long id;
    private String content;
    private Instant createdAt;
    private AppUser author;


    public static AnnouncementDTO fromEntity(Announcement announcement) {
        return AnnouncementDTO.builder()
                .id(announcement.getId())
                .content(announcement.getContent())
                .author(announcement.getAuthor())
                .createdAt(announcement.getCreatedAt())
                .build();
    }

    public static Announcement toEntity(AnnouncementDTO announcementDTO) {
        return Announcement.builder()
                .id(announcementDTO.getId())
                .content(announcementDTO.getContent())
                .author(announcementDTO.getAuthor())
                .createdAt(announcementDTO.getCreatedAt())
                .build();
    }
}
