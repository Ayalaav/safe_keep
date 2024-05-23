package com.safe_keep.services;

public class Message
{
    private String senderId;
    private String receiverId;
    private String message;

    public Message()
    {

    }

    public Message(String senderId, String receiverId, String message)
    {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    // Getters and setters
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

}