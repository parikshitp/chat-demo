package com.divergent.chat.service;

import com.divergent.chat.domain.Message;
import com.divergent.chat.repository.MessageRepository;
import com.divergent.chat.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageStoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageStoreService.class);
    @Autowired
    private MessageRepository messageRepository;

    public void saveMessage(String message) {
        try {
            Message value = AppUtil.fromJson(message);
            value.setCreatedDate(LocalDateTime.now());
            messageRepository.save(value);
        }
        catch (Exception e) {
            LOGGER.error("Message save failed. Exception occured : ", e);
        }

    }

}
