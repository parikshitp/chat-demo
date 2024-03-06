package com.divergent.chat.controller;

import com.divergent.chat.domain.Message;
import com.divergent.chat.dto.ChatMessageDTO;
import com.divergent.chat.dto.MessageDTO;
import com.divergent.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
//@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired(required = true)
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/api/messages/{senderName}/{receiverName}")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(@PathVariable String senderName, @PathVariable String receiverName) {
        List<Message> messages = messageService.getMessagesBetweenUsers(senderName, receiverName);
        return ResponseEntity.ok(messages);
    }

//    @PostMapping("/api/message/sendMessage")
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/messages")
//    @MessageMapping("/chat")
//    public void handleMessage(ChatMessageDTO messageDto) {
////        Message savedMessage = messageService.sendMessage(messageDto);
//        messageService.sendMessageToClient("", messageDto);
//    }

//    @MessageMapping("/chat")
//    public void handleReceivedMessage(ChatMessageDTO message) {
//        messagingTemplate.convertAndSend("/topic/chat", message);
//    }

    @PutMapping("/api/messages/{messageId}/read")
    public ResponseEntity<?> markMessageAsRead(@PathVariable Long messageId) {
        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok("Message marked as read successfully.");
    }
}

