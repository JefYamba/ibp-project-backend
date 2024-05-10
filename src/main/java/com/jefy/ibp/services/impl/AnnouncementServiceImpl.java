package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.AnnouncementResponse;
import com.jefy.ibp.dtos.AnnouncementRequest;
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
    public Page<AnnouncementResponse> getAll(int page, int size) {
        return announcementRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                .map(AnnouncementResponse::fromEntity);
    }

    @Override
    public AnnouncementResponse getById(Long id) {
        return announcementRepository.findById(id).map(AnnouncementResponse::fromEntity).orElseThrow(
                () -> new RecordNotFoundException("Can't find announcement with id: " + id)
        );
    }

    @Override
    public AnnouncementResponse create(AnnouncementRequest announcementRequest) {

        if (announcementRequest == null) {
            throw new IllegalArgumentException("Can't create announcement without author id");
        }

        return AnnouncementResponse.fromEntity(
                announcementRepository.save(Announcement.builder()
                        .content(announcementRequest.getContent())
                        .createdAt(Instant.now())
                        .build())
        );
    }

    @Override
    public AnnouncementResponse update(AnnouncementRequest announcementRequest) {
        if (announcementRequest == null || announcementRequest.getId() == null) {
            throw new IllegalArgumentException("Can't update this announcement without id");
        }


        Announcement announcement = announcementRepository.findById(announcementRequest.getId()).orElseThrow(
                () -> new RecordNotFoundException("Can't find announcement with id: " + announcementRequest.getId())
        );

        announcement.setContent(announcementRequest.getContent());

        return AnnouncementResponse.fromEntity(
                announcementRepository.save(announcement)
        );
    }

    @Override
    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }
}
