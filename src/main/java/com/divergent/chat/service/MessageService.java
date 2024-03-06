package com.divergent.chat.service;

import com.divergent.chat.domain.Message;
import com.divergent.chat.dto.ChatMessageDTO;
import com.divergent.chat.dto.MessageDTO;
import com.divergent.chat.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired(required = true)
    private SimpMessagingTemplate messagingTemplate;

    public List<Message> getMessagesBetweenUsers(String senderName, String receiverName) {
        return messageRepository.findBySenderNameAndReceiverNameOrderByCreatedDate(senderName, receiverName);
    }

    public Message sendMessage(MessageDTO messageDto) {
        Message message = new Message();
        message.setSenderName(messageDto.getSenderName());
        message.setReceiverName(messageDto.getReceiverName());
        message.setMessage(messageDto.getMessage());
        message.setRead(false);
        message.setCreatedDate(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);

        // Send WebSocket message to receiver
//        messagingTemplate.convertAndSendToUser(messageDto.getReceiverName(), "/queue/messages", savedMessage);
        return savedMessage;
    }

    public void markMessageAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + messageId));
        message.setRead(true);
        message.setUpdatedDate(LocalDateTime.now());
        messageRepository.save(message);
    }

    public void sendMessageToClient(String destination, ChatMessageDTO payload) {
        System.out.println("sendMessageToClient : "+ destination + " "+ payload);
        messagingTemplate.convertAndSend(destination, payload);
    }

}

