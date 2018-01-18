package com.opp.fangla.terznica.data.entities;


import android.support.annotation.NonNull;

import java.util.Date;

public class Message implements Comparable<Message>{

    //>sendMessage(idRazgovora, idPosiljatelja, vrijeme, poruka) -> (success)

    private  int idChat, idSender, messageId;
    private Date date;
    private String message;


    public Message(int idChat, int idSender, Date date, String message, int messageId) {
        this.idChat = idChat;
        this.idSender = idSender;
        this.date = date;
        this.message = message;
        this.messageId = messageId;
    }

    public  Message (){}

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Override
    public int compareTo(@NonNull Message message) {
        return date.compareTo(message.date);
    }
}
