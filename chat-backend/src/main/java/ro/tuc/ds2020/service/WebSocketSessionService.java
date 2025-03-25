package ro.tuc.ds2020.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionService {

    // A thread-safe set to keep track of session IDs
    private ConcurrentHashMap<String, Boolean> activeSessions = new ConcurrentHashMap<>();

    public void handleNewConnection(String sessionId) {
        activeSessions.put(sessionId, true);
        // Add any additional logic you want to execute when a new connection is established
        System.out.println("New WebSocket connection established. Session ID: " + sessionId);
    }

    public void handleDisconnection(String sessionId) {
        activeSessions.remove(sessionId);
        // Add any additional logic you want to execute when a connection is terminated
        System.out.println("WebSocket connection terminated. Session ID: " + sessionId);
    }

    // You can add more methods to interact with the activeSessions map or other logic as needed
}
