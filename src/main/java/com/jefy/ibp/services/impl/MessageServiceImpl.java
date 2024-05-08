package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.MessageDTO;
import com.jefy.ibp.dtos.MessageRequestDTO;
import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.entities.Message;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.repositories.MessageRepository;
import com.jefy.ibp.services.MessageService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public Page<MessageDTO> getAll(int page, int size) {
        return messageRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                .map(MessageDTO::fromEntity);
    }

    @Override
    public Page<MessageDTO> getAllBySender(Long senderId, int page, int size) {
        if (senderId != null && appUserRepository.existsById(senderId)) {
            return messageRepository.findBySenderId(senderId, PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                    .map(MessageDTO::fromEntity);
        }
        throw new IllegalArgumentException("Invalid AppUserDTO");
    }

    @Override
    public Page<MessageDTO> getAllByReceiver(Long receiverId, int page, int size) {
        if (receiverId != null && appUserRepository.existsById(receiverId)) {
            return messageRepository.findByReceiverId(receiverId, PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                    .map(MessageDTO::fromEntity);
        }
        throw new IllegalArgumentException("Invalid id");
    }

    @Override
    public Page<MessageDTO> getAllForAdmins(int page, int size) {
        return messageRepository.findMessageByReceiverNull(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                .map(MessageDTO::fromEntity);
    }

    @Override
    public MessageDTO getById(Long id) {
        return messageRepository.findById(id).map(MessageDTO::fromEntity).orElseThrow(
                () -> new RecordNotFoundException("Can't find announcement with id: " + id)
        );
    }

    @Override
    public MessageDTO create(MessageRequestDTO messageRequestDTO) {
        if (messageRequestDTO == null || messageRequestDTO.getSenderId() == null) {
            throw new IllegalArgumentException("Can't create announcement without sender");
        }

        if (messageRequestDTO.getContent() == null || messageRequestDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Can't create announcement without content");
        }

        AppUser sender = appUserRepository.findById(messageRequestDTO.getSenderId()).orElseThrow(
                () -> new RecordNotFoundException("Can't find sender with id: " + messageRequestDTO.getSenderId())
        );

        AppUser receiver = appUserRepository.findById(messageRequestDTO.getReceiverId()).orElse(null);

        return MessageDTO.fromEntity(
                messageRepository.save(Message.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .content(messageRequestDTO.getContent())
                        .createdAt(Instant.now())
                        .build()
                )
        );
    }

    @Override
    public MessageDTO update(MessageRequestDTO messageRequestDTO)  throws Exception  {
        if (messageRequestDTO == null || messageRequestDTO.getId() == null) {
            throw new IllegalArgumentException("Can't update this announcement without id");
        }

        if (messageRequestDTO.getContent() == null || messageRequestDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Can't create announcement without content");
        }

        Message message = messageRepository.findById(messageRequestDTO.getId()).orElseThrow(
                () -> new RecordNotFoundException("Can't find announcement with id: " + messageRequestDTO.getId())
        );

        message.setContent(messageRequestDTO.getContent());

        return MessageDTO.fromEntity(
                messageRepository.save(message)
        );
    }

    @Override
    public void delete(Long id) {
        if (!messageRepository.existsById(id)){
            throw new RecordNotFoundException("Can't find message with id: " + id);
        }
        messageRepository.deleteById(id);
    }
}
