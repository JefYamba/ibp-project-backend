package com.jefy.ibp.services;

import com.jefy.ibp.dtos.MessageDTO;
import com.jefy.ibp.dtos.MessageRequestDTO;
import org.springframework.data.domain.Page;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface MessageService {

    Page<MessageDTO> getAll(int page, int size);
    Page<MessageDTO> getAllBySender(Long senderId, int page, int size);
    Page<MessageDTO> getAllByReceiver(Long receiverId, int page, int size);
    Page<MessageDTO> getAllForAdmins(int page, int size);
    MessageDTO getById(Long id);
    MessageDTO create(MessageRequestDTO messageRequestDTO);
    MessageDTO update(MessageRequestDTO messageRequestDTO);
    void delete(Long id);
}
