package com.jefy.ibp.repositories;

import com.jefy.ibp.entities.Message;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 05/05/2024
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdOrderByCreatedAtDesc(Long senderId);
    List<Message> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);
    List<Message> findMessageByReceiverNullOrderByCreatedAtDesc();
}
