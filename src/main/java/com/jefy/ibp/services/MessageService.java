package com.jefy.ibp.services;

import com.jefy.ibp.dtos.MessageDTO;
import com.jefy.ibp.dtos.MessageRequestDTO;

import java.util.List;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
public interface MessageService {

    List<MessageDTO> getAll();
    List<MessageDTO> getAllBySender(Long senderId);
    List<MessageDTO> getAllByReceiver(Long receiverId);
    List<MessageDTO> getAllForAdmins();
    MessageDTO getById(Long id);
    MessageDTO create(MessageRequestDTO messageRequestDTO);
    MessageDTO update(MessageRequestDTO messageRequestDTO) throws Exception;
    void delete(Long id);
}
