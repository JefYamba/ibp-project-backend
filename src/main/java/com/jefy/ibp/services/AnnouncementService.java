package com.jefy.ibp.services;

import com.jefy.ibp.dtos.AnnouncementDTO;
import com.jefy.ibp.dtos.AnnouncementRequestDTO;

import java.util.List;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface AnnouncementService {
    List<AnnouncementDTO> getAll();
    AnnouncementDTO getById(Long id);
    AnnouncementDTO create(AnnouncementRequestDTO announcementRequestDTO);
    AnnouncementDTO update(AnnouncementRequestDTO announcementRequestDTO);
    void delete(Long id);
}
