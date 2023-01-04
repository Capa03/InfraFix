package com.capa.infrafix.Ticket;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ticket {

    @PrimaryKey(autoGenerate = true)
    private long ticketId;
    private String subject;
    private String description;
    private String date;
    private String pictureTicket;
    private double lat;
    private double lng;


    public Ticket(long ticketId, String subject, String description, String date, String pictureTicket, double lat, double lng) {
        this.ticketId = ticketId;
        this.subject = subject;
        this.description = description;
        this.date = date;
        this.pictureTicket = pictureTicket;
        this.lat = lat;
        this.lng = lng;
    }



    public long getTicketId() {
        return ticketId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPictureTicket() {
        return pictureTicket;
    }

    public void setPictureTicket(String pictureTicket) {
        this.pictureTicket = pictureTicket;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
