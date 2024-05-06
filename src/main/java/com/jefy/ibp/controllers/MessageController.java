package com.jefy.ibp.controllers;

import com.jefy.ibp.dtos.MessageDTO;
import com.jefy.ibp.dtos.MessageRequestDTO;
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


    @GetMapping("/admins")
    public ResponseEntity<List<MessageDTO>> getAllMessagesForAdmins() {
        return ResponseEntity.ok(messageService.getAllForAdmins());
    }


    @GetMapping("/sender/{sender_id}")
    public ResponseEntity<List<MessageDTO>> getAllMessagesForSender(@PathVariable("sender_id") Long senderId) {
        return ResponseEntity.ok(messageService.getAllBySender(senderId));
    }

    @GetMapping("/receiver/{receiver_id}")
    public ResponseEntity<List<MessageDTO>> getAllMessagesForReceiver(@PathVariable("receiver_id") Long receiverId) {
        return ResponseEntity.ok(messageService.getAllByReceiver(receiverId));
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
        try {
            return ResponseEntity.ok(messageService.update(messageRequestDTO));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{message_id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable("message_id") Long messageId) {
        Map<String,String> response = new HashMap<>();
        try {
            messageService.delete(messageId);
            response.put("response", "message deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("errors",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
