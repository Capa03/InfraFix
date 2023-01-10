package com.capa.infrafix.localdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.capa.infrafix.Ticket.Ticket;

import java.util.List;

@Dao
public interface TicketDAO {

    @Query("SELECT * FROM Ticket")
    LiveData<List<Ticket>> getAllTicket();

    @Query("SELECT * FROM Ticket WHERE id = :id")
    LiveData<Ticket> getTicket(int id);

    @Query("DELETE FROM Ticket")
    void clearTable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTicket(Ticket ticket);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createTickets(List<Ticket> ticket);

    @Delete
    void deleteTicket(Ticket ticket);

}
