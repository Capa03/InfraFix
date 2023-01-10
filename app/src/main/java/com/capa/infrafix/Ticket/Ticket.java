package com.capa.infrafix.Ticket;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Ticket {

    @PrimaryKey(autoGenerate = false)
    private int id;
    private String subject;
    private String description;
    private String date;
    private List<String> pictureTicket;
    private double lat;
    private double lng;

    public int getId() {
        return id;
    }

    public Ticket(int id, String subject, String description, String date, List<String> pictureTicket, double lat, double lng) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.date = date;
        this.pictureTicket = pictureTicket;
        this.lat = lat;
        this.lng = lng;
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

    public List<String> getPictureTicket() {
        return pictureTicket;
    }

    public void setPictureTicket(List<String> pictureTicket) {
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
