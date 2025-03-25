package ro.tuc.ds2020.model;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String sessionId; // Add this field


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    // Constructors
    public ChatMessage() {
    }

    public ChatMessage(MessageType type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
    }

    // Getters
    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    // Setters
    public void setType(MessageType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // Getter and Setter for sessionId
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

