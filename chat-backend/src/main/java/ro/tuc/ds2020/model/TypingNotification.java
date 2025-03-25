package ro.tuc.ds2020.model; // Adjust the package name as per your project structure

public class TypingNotification {

    private String sender;    // Username of the person who is typing
    private boolean isTyping; // Flag to indicate if the user is typing or has stopped

    // Constructor
    public TypingNotification(String sender, boolean isTyping) {
        this.sender = sender;
        this.isTyping = isTyping;
    }

    // Default constructor (needed for Jackson)
    public TypingNotification() {
    }

    // Getters and setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean isTyping) {
        this.isTyping = isTyping;
    }
}
