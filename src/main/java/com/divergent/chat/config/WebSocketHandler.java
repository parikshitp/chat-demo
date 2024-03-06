package com.divergent.chat.config;

import com.divergent.chat.domain.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        for (WebSocketSession webSocketSession : sessions) {
            if (!session.equals(webSocketSession)) {
                Message value = fromJson(message.getPayload());
                webSocketSession.sendMessage(new TextMessage(toJson(value)));
            }
        }
    }

    public static Message fromJson(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Message.class);
    }

    public static String toJson(Message message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }
}
