package com.example.firechat.model;

public class FireMessage {
    private String Text;
    private String Name;
    private String ImageURL;
    private String sender;
    private String recipient;
    private boolean isMine;

    public FireMessage() {

    }

    public FireMessage(String text, String name, String imageURL, String sender, String recipient, boolean isMine) {
        Text = text;
        Name = name;
        ImageURL = imageURL;
        this.sender = sender;
        this.recipient = recipient;
        this.isMine = isMine;
    }

    public String getText() {
        return Text;
    }

    public String getName() {
        return Name;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
