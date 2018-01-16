package com.opp.fangla.terznica.data.entities;

public class Conversation {

    //idPrijevoznika, idKupca, idOglasa, idOglasaPrijevoza

    public Conversation(int idDriver, int idBuyer, long idAdvert, long idAdvertTransport) {
        this.idDriver = idDriver;
        this.idBuyer = idBuyer;
        this.idAdvert = idAdvert;
        this.idAdvertTransport = idAdvertTransport;
    }

    public Conversation () {

    }

    /**
     * U idBuyer uvijek ide drugi sudionik razgovora
     */
    private int idDriver, idBuyer;
    private long idAdvert, idAdvertTransport, idConversation;
    private Message firstMessage;
    private boolean finished;


    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public int getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(int idBuyer) {
        this.idBuyer = idBuyer;
    }

    public long getIdAdvert() {
        return idAdvert;
    }

    public void setIdAdvert(long idAdvert) {
        this.idAdvert = idAdvert;
    }

    public long getIdAdvertTransport() {
        return idAdvertTransport;
    }

    public void setIdAdvertTransport(long idAdvertTransport) {
        this.idAdvertTransport = idAdvertTransport;
    }

    public Message getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(Message firstMessage) {
        this.firstMessage = firstMessage;
    }

    public long getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(long idConversation) {
        this.idConversation = idConversation;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
