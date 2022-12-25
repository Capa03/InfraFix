package com.capa.infrafix.remote;

import com.capa.infrafix.Ticket.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TicketService {

    @GET("tickets")
    Call<List<Ticket>> getTicketList();

    @GET("tickets/{ticketId}")
    Call<Ticket> getTicketById(@Path("ticketId") int id);

    @GET("tickets")
    Call<List<Ticket>> getTicketsByValue(@Query("value") String value);

    @POST("tickets")
    Call<Ticket> createTicket(@Body Ticket newTicket);

    @PUT("tickets/{ticketId}")
    Call<Ticket> updateTicket(@Body Ticket updatedTicket, @Path("ticketId") int ticketId);

    @DELETE("tickets/{ticketId}")
    Call<Object> deleteTicket(@Path("ticketId") int ticketId);
}
