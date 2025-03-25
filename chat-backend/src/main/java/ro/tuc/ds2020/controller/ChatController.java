package ro.tuc.ds2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.tuc.ds2020.model.ChatMessage;
import ro.tuc.ds2020.service.WebSocketSessionService;
import ro.tuc.ds2020.model.TypingNotification;

@Controller
public class ChatController {

    private final WebSocketSessionService webSocketSessionService;

    @Autowired
    public ChatController(WebSocketSessionService webSocketSessionService) {
        this.webSocketSessionService = webSocketSessionService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        // Logic for sending a message
        System.out.println(chatMessage.getContent());
        return chatMessage;
    }

    @MessageMapping("/typing")
    @SendTo("/topic/public")
    public TypingNotification handleTyping(TypingNotification typingNotification) {
        typingNotification.setTyping(true);
        return typingNotification;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Set session ID in the chat message
        String sessionId = headerAccessor.getSessionId();
        chatMessage.setSessionId(sessionId);

        // Handle new connection in the service
        webSocketSessionService.handleNewConnection(sessionId);

        // Broadcast that a new user has joined to all clients
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        chatMessage.setContent(chatMessage.getSender() + " joined the chat");
        return chatMessage;
    }

    // Optional: Additional methods for other functionalities (like handling disconnects) can be added here
}
