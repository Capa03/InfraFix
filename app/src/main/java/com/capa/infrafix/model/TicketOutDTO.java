package com.capa.infrafix.model;

import java.util.List;

public class TicketOutDTO {
    private String subject;
    private String description;
    private String date;
    private List<String> pictureTicket;
    private double lat;
    private double lng;

    public TicketOutDTO(String subject, String description, String date, List<String> pictureTicket, double lat, double lng) {
        this.subject = subject;
        this.description = description;
        this.date = date;
        this.pictureTicket = pictureTicket;
        this.lat = lat;
        this.lng = lng;
    }
}
