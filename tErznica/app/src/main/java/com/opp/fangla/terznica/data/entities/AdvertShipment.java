package com.opp.fangla.terznica.data.entities;


import java.sql.Date;

public class AdvertShipment {

    //(idOglasa, idKupca, polaziste, odrediste, vrijeme) -> (idOglasaPrijevoza)

    private Integer advertId,shipmentAdvertId, creatorId;
    private String startLocation, endLocation;
    private Date  date;
    private Advert advert;



    public Integer getAdvertId() {
        return advertId;
    }

    public void setAdvertId(Integer advertId) {
        this.advertId = advertId;
    }

    public Integer getShipmentAdvertId() {
        return shipmentAdvertId;
    }

    public void setShipmentAdvertId(Integer shipmentAdvertId) {
        this.shipmentAdvertId = shipmentAdvertId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }
}
