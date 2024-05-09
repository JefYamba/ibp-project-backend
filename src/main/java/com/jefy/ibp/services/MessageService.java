package com.jefy.ibp.services;

import com.jefy.ibp.dtos.MessageResponse;
import com.jefy.ibp.dtos.MessageRequest;
import org.springframework.data.domain.Page;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface MessageService {

    Page<MessageResponse> getAll(int page, int size);
    Page<MessageResponse> getAllBySender(Long senderId, int page, int size);
    Page<MessageResponse> getAllByReceiver(Long receiverId, int page, int size);
    Page<MessageResponse> getAllForAdmins(int page, int size);
    MessageResponse getById(Long id);
    MessageResponse create(MessageRequest messageRequest);
    MessageResponse update(MessageRequest messageRequest);
    void delete(Long id);
}
