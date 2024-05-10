package com.jefy.ibp.services.impl;

import com.jefy.ibp.dtos.MessageResponse;
import com.jefy.ibp.dtos.MessageRequest;
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
    public Page<MessageResponse> getAll(int page, int size) {
        return messageRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                .map(MessageResponse::fromEntity);
    }

    @Override
    public Page<MessageResponse> getAllBySender(Long senderId, int page, int size) {

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), senderId) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");

        if (senderId != null && appUserRepository.existsById(senderId)) {
            return messageRepository.findBySenderId(senderId, PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                    .map(MessageResponse::fromEntity);
        }
        throw new IllegalArgumentException("Invalid UserResponse");
    }

    @Override
    public Page<MessageResponse> getAllByReceiver(Long receiverId, int page, int size) {

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), receiverId) && loggedUser.getRole() != Role.ADMIN )
            throw new OperationNotAuthorizedException("this operation is not allowed");


        if (receiverId != null && appUserRepository.existsById(receiverId)) {
            return messageRepository.findByReceiverId(receiverId, PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                    .map(MessageResponse::fromEntity);
        }
        throw new IllegalArgumentException("Invalid id");
    }

    @Override
    public Page<MessageResponse> getAllForAdmins(int page, int size) {
        return messageRepository.findMessageByReceiverNull(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"createdAt")))
                .map(MessageResponse::fromEntity);
    }

    @Override
    public MessageResponse getById(Long id) {
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

        return MessageResponse.fromEntity(message);
    }

    @Override
    public MessageResponse create(MessageRequest messageRequest) {
        if (messageRequest == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), messageRequest.getSenderId())) {
            throw new OperationNotAuthorizedException("this operation is not allowed");
        }


        AppUser sender = appUserRepository.findById(messageRequest.getSenderId()).orElseThrow(
                () -> new RecordNotFoundException("Can't find sender with id: " + messageRequest.getSenderId())
        );

        AppUser receiver = appUserRepository.findById(messageRequest.getReceiverId()).orElse(null);

        return MessageResponse.fromEntity(
                messageRepository.save(Message.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .content(messageRequest.getContent())
                        .createdAt(Instant.now())
                        .build()
                )
        );
    }

    @Override
    public MessageResponse update(MessageRequest messageRequest) {
        if (messageRequest == null || messageRequest.getId() == null) {
            throw new IllegalArgumentException("message and message's id are required");
        }

        AppUser loggedUser = appUserRepository.getAppUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Objects.equals(loggedUser.getId(), messageRequest.getSenderId())) {
            throw new OperationNotAuthorizedException("this operation is not allowed");
        }

        Message message = messageRepository.findById(messageRequest.getId()).orElseThrow(
                () -> new RecordNotFoundException("Can't find announcement with id: " + messageRequest.getId())
        );

        message.setContent(messageRequest.getContent());

        return MessageResponse.fromEntity(
                messageRepository.save(message)
        );
    }

    @Override
    public void delete(Long id){
        messageRepository.deleteById(id);
    }
}
