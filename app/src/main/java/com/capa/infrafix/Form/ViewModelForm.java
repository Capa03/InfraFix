package com.capa.infrafix.Form;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.capa.infrafix.Ticket.Ticket;
import com.capa.infrafix.localdatabase.AppDatabase;
import com.capa.infrafix.localdatabase.TicketDAO;
import com.capa.infrafix.model.TicketOutDTO;
import com.capa.infrafix.repository.TicketRepository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewModelForm extends AndroidViewModel {

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private final int LOCATION_PERMISSION_CODE = 101;
    private Context context;
    private TicketDAO ticketDAO;
    private List<String> imageFileNames = new ArrayList<>();
    private TicketRepository ticketRepository;
    public ViewModelForm(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.ticketDAO = AppDatabase.getInstance(application.getApplicationContext()).getTicketDAO();
        this.ticketRepository = new TicketRepository(application.getApplicationContext());
    }

    public LiveData<List<Ticket>> getTickets(){
        return this.ticketRepository.getTicketList();
    }


    public void createTicketApi(Ticket ticket){
        this.ticketRepository.createTicketApi(ticket);
    }



    public boolean isLocationPermissionGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public void requestPermission(Activity activity){
        for (String required_permission : REQUIRED_PERMISSIONS) {
            ActivityCompat.requestPermissions(activity, new String[]{required_permission}, LOCATION_PERMISSION_CODE);
        }
    }

    public List<String> getImageFileNames() {
        return imageFileNames;
    }

    public void addImageFileName(String fileName){
        imageFileNames.add(fileName);
    }
}


