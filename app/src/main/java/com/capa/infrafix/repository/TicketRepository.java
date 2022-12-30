package com.capa.infrafix.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.capa.infrafix.Ticket.Ticket;
import com.capa.infrafix.localdatabase.AppDatabase;
import com.capa.infrafix.localdatabase.TicketDAO;
import com.capa.infrafix.remote.TicketService;

import java.net.ContentHandler;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketRepository {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Executor executor = Executors.newSingleThreadExecutor();

    private TicketDAO ticketDAO;
    private TicketService ticketService;

    public TicketRepository(Context context){
        this.ticketDAO = AppDatabase.getInstance(context).getTicketDAO();
        this.ticketService = retrofit.create(TicketService.class);
    }


    public LiveData<List<Ticket>> getTicketList(){
        return this.ticketDAO.getAllTicket();
    }

    public LiveData<Ticket> getTicketById(int ticketID){
        return this.ticketDAO.getTicket(ticketID);
    }

    public void refreshTicket(){
        this.ticketService.getTicketList().enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> ticketDAO.createTickets(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
