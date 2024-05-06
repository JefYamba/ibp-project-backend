package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.AnnouncementDTO;
import com.jefy.ibp.dtos.AnnouncementRequestDTO;
import com.jefy.ibp.dtos.MessageDTO;
import com.jefy.ibp.dtos.MessageRequestDTO;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jefy.ibp.dtos.Constants.MESSAGES_URL;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 06/05/2024
 */
@RestController
@RequestMapping(MESSAGES_URL)
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAll());
    }

    @GetMapping("/{message_id}")
    public ResponseEntity<MessageDTO> get(@PathVariable("message_id") Long messageId) {
        return ResponseEntity.ok(messageService.getById(messageId));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> register(@RequestBody MessageRequestDTO messageRequestDTO) {
        return ResponseEntity.ok(messageService.create(messageRequestDTO));
    }

    @PutMapping
    public ResponseEntity<MessageDTO> update(@RequestBody MessageRequestDTO messageRequestDTO) {
        return ResponseEntity.ok(messageService.update(messageRequestDTO));
    }

    @DeleteMapping("/{message_id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable("message_id") Long messageId) {
        Map<String,String> response = new HashMap<>();
        try {
            response.put("response", "message deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
