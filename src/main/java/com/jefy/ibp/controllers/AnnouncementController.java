package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AnnouncementDTO;
import com.jefy.ibp.dtos.AnnouncementRequestDTO;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jefy.ibp.dtos.Constants.ANNOUNCEMENTS_URL;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(ANNOUNCEMENTS_URL)
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncements() {
        return ResponseEntity.ok(announcementService.getAll());
    }

    @GetMapping("/{announcement_id}")
    public ResponseEntity<AnnouncementDTO> get(@PathVariable("announcement_id") Long announcementId) {
        return ResponseEntity.ok(announcementService.getById(announcementId));
    }

    @PostMapping
    public ResponseEntity<AnnouncementDTO> register(@RequestBody AnnouncementRequestDTO announcementRequestDTO) {
        return ResponseEntity.ok(announcementService.create(announcementRequestDTO));
    }

    @PutMapping
    public ResponseEntity<AnnouncementDTO> update(@RequestBody AnnouncementRequestDTO announcementRequestDTO) {
        try {
            return ResponseEntity.ok(announcementService.update(announcementRequestDTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{announcement_id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable("announcement_id") Long announcementId) {
        Map<String,String> response = new HashMap<>();
        try {
            announcementService.delete(announcementId);
            response.put("response", "announcement deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RecordNotFoundException e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
