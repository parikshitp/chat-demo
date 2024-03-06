package com.divergent.chat.repository;

import com.divergent.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderNameAndReceiverNameOrderByCreatedDate(String senderName, String receiverName);
}
