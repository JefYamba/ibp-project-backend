package com.jefy.ibp.services;

import com.jefy.ibp.dtos.AnnouncementResponse;
import com.jefy.ibp.dtos.AnnouncementRequest;
import org.springframework.data.domain.Page;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface AnnouncementService {
    Page<AnnouncementResponse> getAll(int page, int size);
    AnnouncementResponse getById(Long id);
    AnnouncementResponse create(AnnouncementRequest announcementRequest);
    AnnouncementResponse update(AnnouncementRequest announcementRequest);
    void delete(Long id);
}
