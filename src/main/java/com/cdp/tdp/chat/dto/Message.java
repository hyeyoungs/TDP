package com.cdp.tdp.chat.dto;

public class Message {
    private String sender;
    private String giver;
    private String messageContent;

    public String getSender() {
        return sender;
    }

    public String getGiver() {
        return giver;
    }
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String sender, String giver, String messageContent) {
        this.messageContent = messageContent;
        this.sender = sender;
        this.giver = giver;
    }
}
