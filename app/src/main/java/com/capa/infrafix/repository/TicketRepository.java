package com.capa.infrafix.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.capa.infrafix.model.Ticket;
import com.capa.infrafix.localdatabase.AppDatabase;
import com.capa.infrafix.localdatabase.TicketDAO;
import com.capa.infrafix.model.TicketOutDTO;
import com.capa.infrafix.remote.TicketService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketRepository {
    private final String URL_HOME = "http://192.168.1.73:5011/api/";
    private final String URL_ANDROID = "http://10.0.2.2:5011/api/";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL_HOME)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Executor executor = Executors.newSingleThreadExecutor();

    private TicketDAO ticketDAO;
    private TicketService ticketService;

    public TicketRepository(Context context) {
        this.ticketDAO = AppDatabase.getInstance(context).getTicketDAO();
        this.ticketService = retrofit.create(TicketService.class);
    }

    public LiveData<List<Ticket>> getTicketList() {
        return this.ticketDAO.getAllTicket();
    }

    public LiveData<Ticket> getTicketById(int ticketID) {
        return this.ticketDAO.getTicket(ticketID);
    }

    public void createTicketApi(Ticket ticket) {
        TicketOutDTO ticketOut = new TicketOutDTO(ticket.getSubject(), ticket.getDescription()
                , ticket.getDate(), ticket.getPictureTicket(), ticket.getLat(), ticket.getLng());
        executor.execute(() -> ticketDAO.createTicket(ticket));


        this.ticketService.createTicket(ticketOut).enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> {
                        refreshTicket();
                    });
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                t.printStackTrace();
                System.out.print(t.getMessage());
            }
        });
    }

    public void refreshTicket() {
        this.ticketService.getTicketList().enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> {
                        ticketDAO.clearTable();
                        for (Ticket ticket : response.body()) {
                            ticketDAO.createTicket(ticket);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void deleteTicket(int ticketId) {
        this.ticketService.deleteTicket(ticketId).enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.isSuccessful()) {
                    executor.execute(() -> ticketDAO.deleteTicket(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
