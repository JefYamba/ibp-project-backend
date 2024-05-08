package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.MessageDTO;
import com.jefy.ibp.dtos.MessageRequestDTO;
import com.jefy.ibp.entities.AppUser;
import com.jefy.ibp.entities.Message;
import com.jefy.ibp.enums.Role;
import com.jefy.ibp.exceptions.OperationNotAuthorizedException;
import com.jefy.ibp.exceptions.RecordNotFoundException;
import com.jefy.ibp.repositories.AppUserRepository;
import com.jefy.ibp.repositories.MessageRepository;
import com.jefy.ibp.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

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

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), senderId) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");

        if (senderId != null && appUserRepository.existsById(senderId)) {
            return messageRepository.findBySenderId(senderId, PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                    .map(MessageDTO::fromEntity);
        }
        throw new IllegalArgumentException("Invalid AppUserDTO");
    }

    @Override
    public Page<MessageDTO> getAllByReceiver(Long receiverId, int page, int size) {

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), receiverId) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");


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
        Message message = messageRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Can't find message with id: " + id)
        );

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (
                !Objects.equals(loggedUser.getId(), message.getSender().getId()) &&
                !Objects.equals(loggedUser.getId(), message.getReceiver().getId()) &&
                loggedUser.getRole() != Role.ADMIN
        ) {
            throw new OperationNotAuthorizedException("this operation is not allowed");
        }

        return MessageDTO.fromEntity(message);
    }

    @Override
    public MessageDTO create(MessageRequestDTO messageRequestDTO) {
        if (messageRequestDTO == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), messageRequestDTO.getSenderId())) {
            throw new OperationNotAuthorizedException("this operation is not allowed");
        }

        if (messageRequestDTO.getContent() == null || messageRequestDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Can't create message without content");
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
    public MessageDTO update(MessageRequestDTO messageRequestDTO) {
        if (messageRequestDTO == null || messageRequestDTO.getId() == null) {
            throw new IllegalArgumentException("message and message's id are required");
        }

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), messageRequestDTO.getSenderId())) {
            throw new OperationNotAuthorizedException("this operation is not allowed");
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
    public void delete(Long id){
        messageRepository.deleteById(id);
    }
}
