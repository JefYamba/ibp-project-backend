package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.AnnouncementDTO;
import com.jefy.ibp.dtos.AnnouncementRequestDTO;
import com.jefy.ibp.entities.Announcement;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.repositories.AnnouncementRepository;
import com.jefy.ibp.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    @Override
    public Page<AnnouncementDTO> getAll(int page, int size) {
        return announcementRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                .map(AnnouncementDTO::fromEntity);
    }

    @Override
    public AnnouncementDTO getById(Long id) {
        return announcementRepository.findById(id).map(AnnouncementDTO::fromEntity).orElseThrow(
                () -> new RecordNotFoundException("Can't find announcement with id: " + id)
        );
    }

    @Override
    public AnnouncementDTO create(AnnouncementRequestDTO announcementRequestDTO) {

        if (announcementRequestDTO == null) {
            throw new IllegalArgumentException("Can't create announcement without author id");
        }

        if (announcementRequestDTO.getContent() == null || announcementRequestDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Can't create announcement without content");
        }
        return AnnouncementDTO.fromEntity(
                announcementRepository.save(Announcement.builder()
                        .content(announcementRequestDTO.getContent())
                        .createdAt(Instant.now())
                        .build())
        );
    }

    @Override
    public AnnouncementDTO update(AnnouncementRequestDTO announcementRequestDTO) {
        if (announcementRequestDTO == null || announcementRequestDTO.getId() == null) {
            throw new IllegalArgumentException("Can't update this announcement without id");
        }

        if (announcementRequestDTO.getContent() == null || announcementRequestDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Can't create announcement without content");
        }

        Announcement announcement = announcementRepository.findById(announcementRequestDTO.getId()).orElseThrow(
                () -> new RecordNotFoundException("Can't find announcement with id: " + announcementRequestDTO.getId())
        );

        announcement.setContent(announcementRequestDTO.getContent());

        return AnnouncementDTO.fromEntity(
                announcementRepository.save(announcement)
        );
    }

    @Override
    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }
}
