package com.capa.infrafix.Form;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.capa.infrafix.Ticket.Ticket;
import com.capa.infrafix.localdatabase.TicketDAO;

public class ViewModelForm extends AndroidViewModel {

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private final int LOCATION_PERMISSION_CODE = 101;
    private Context context;
    private TicketDAO ticketDAO;

    public ViewModelForm(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }


     boolean isLocationPermissionGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this.context, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;

    }

     void requestPermission(Activity activity){
        for (String required_permission : REQUIRED_PERMISSIONS) {
            ActivityCompat.requestPermissions(activity, new String[]{required_permission}, LOCATION_PERMISSION_CODE);
        }
    }



     public void setupPermissions(Activity activity){
        int permission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.CAMERA);

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest(activity);
        }
    }

     public void makeRequest(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(activity, permissions, 101);
    }

    public void createTicket(Ticket ticket){
        new Thread(()-> this.ticketDAO.createTicket(new Ticket(ticket.getTicketId(),ticket.getSubject(), ticket.getDescription(), ticket.getDate(), ticket.getPictureTicket(), ticket.getLat(), ticket.getLng()))).start();
    }
}