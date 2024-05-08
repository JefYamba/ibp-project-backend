package com.jefy.ibp.services;

import com.jefy.ibp.dtos.AnnouncementDTO;
import com.jefy.ibp.dtos.AnnouncementRequestDTO;
import org.springframework.data.domain.Page;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface AnnouncementService {
    Page<AnnouncementDTO> getAll(int page, int size);
    AnnouncementDTO getById(Long id);
    AnnouncementDTO create(AnnouncementRequestDTO announcementRequestDTO);
    AnnouncementDTO update(AnnouncementRequestDTO announcementRequestDTO);
    void delete(Long id);
}
