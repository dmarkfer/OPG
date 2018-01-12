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
    private int idDriver, idBuyer;
    private long idAdvert, idAdvertTransport;



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
}
