package com.capa.infrafix.localdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.capa.infrafix.model.Ticket;

@Database(entities = {Ticket.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract TicketDAO getTicketDAO();

    public static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        "TicketDB").build();
            }
        }
        INSTANCE.getOpenHelper().getWritableDatabase();
        return INSTANCE;
    }
}
