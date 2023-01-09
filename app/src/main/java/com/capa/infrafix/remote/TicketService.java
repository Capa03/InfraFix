package com.capa.infrafix.remote;

import com.capa.infrafix.Ticket.Ticket;
import com.capa.infrafix.model.TicketOutDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TicketService {

    @GET("Ticket")
    Call<List<Ticket>> getTicketList();

    @POST("Ticket")
    Call<Ticket> createTicket(@Body TicketOutDTO newTicket);

    @PUT("tickets/{ticketId}")
    Call<Ticket> updateTicket(@Body Ticket updatedTicket, @Path("ticketId") int ticketId);

    @DELETE("Ticket/{ticketId}")
    Call<Ticket> deleteTicket(@Path("ticketId") int ticketId);
}
