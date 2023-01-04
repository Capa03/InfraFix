package com.capa.infrafix.Ticket;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.capa.infrafix.localdatabase.AppDatabase;
import com.capa.infrafix.localdatabase.TicketDAO;
import com.capa.infrafix.repository.TicketRepository;

import java.util.List;

public class ViewModelTicket extends AndroidViewModel {
    private TicketRepository ticketRepository;
    private TicketDAO ticketDAO;
    public ViewModelTicket(@NonNull Application application) {
        super(application);
        this.ticketRepository = new TicketRepository(application.getApplicationContext());
        this.ticketDAO = AppDatabase.getInstance(application.getApplicationContext()).getTicketDAO();
    }

    public LiveData<List<Ticket>> getTickets(){
        return this.ticketRepository.getTicketList();
    }

    public LiveData<Ticket> getTicketById(int ticketId){
        return this.ticketRepository.getTicketById(ticketId);
    }

    public void refreshTicket(){
        this.ticketRepository.refreshTicket();
    }

    public void deleteTicket(Ticket ticket){
        new Thread(() -> ticketDAO.deleteTicket(ticket)).start();
    }

    public Bitmap StringToBitmap(String encodedString){
        try{
            byte[] encodedByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte,0, encodedByte.length);
            return bitmap;
        }catch (Exception exception){
            exception.getMessage();
            return  null;
        }
    }
}
