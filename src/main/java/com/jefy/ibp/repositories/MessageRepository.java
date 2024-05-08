package com.jefy.ibp.repositories;

import com.jefy.ibp.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findBySenderId(Long senderId, PageRequest pageRequest);
    Page<Message> findByReceiverId(Long receiverId, PageRequest pageRequest);
    Page<Message> findMessageByReceiverNull(PageRequest pageRequest);
}
