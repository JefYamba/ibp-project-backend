package com.jefy.ibp.services;

import com.jefy.ibp.dtos.AnnouncementDTO;
import com.jefy.ibp.dtos.AnnouncementRequestDTO;
import com.jefy.ibp.dtos.BookDTO;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface AnnouncementService {
    Page<AnnouncementDTO> getAll(int page, int size);
    AnnouncementDTO getById(Long id);
    AnnouncementDTO create(AnnouncementRequestDTO announcementRequestDTO);
    AnnouncementDTO update(AnnouncementRequestDTO announcementRequestDTO) throws Exception;
    void delete(Long id) throws RecordNotFoundException;
}
