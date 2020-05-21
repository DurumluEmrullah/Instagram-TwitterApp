package com.durumluemrullah.mobilappproje.Classes;

import com.google.firebase.firestore.FieldValue;

public class Message {
    private String lobiId;
    private String message;
    private String sender;
    private FieldValue fieldValue;



    public Message(String lobiId, String message, String sender,FieldValue fieldValue) {
        this.lobiId = lobiId;
        this.message = message;
        this.sender = sender;
        this.fieldValue=fieldValue;
    }

    public FieldValue getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getLobiId() {
        return lobiId;
    }

    public void setLobiId(String lobiId) {
        this.lobiId = lobiId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
