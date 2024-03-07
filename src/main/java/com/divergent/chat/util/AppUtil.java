package com.divergent.chat.util;

import com.divergent.chat.domain.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Message fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Message.class);
    }

}
