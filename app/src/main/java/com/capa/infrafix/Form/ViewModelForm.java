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

import com.capa.infrafix.Ticket.Ticket;
import com.capa.infrafix.localdatabase.AppDatabase;
import com.capa.infrafix.localdatabase.TicketDAO;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewModelForm extends AndroidViewModel {

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private final int LOCATION_PERMISSION_CODE = 101;
    private Context context;
    private TicketDAO ticketDAO;
    private List<String> imageFileNames = new ArrayList<>();
    public ViewModelForm(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.ticketDAO = AppDatabase.getInstance(application.getApplicationContext()).getTicketDAO();
    }



    public void setupPermissions(Activity activity) {
        int permission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest(activity);
        }
    }

    public void makeRequest(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(activity, permissions, 101);
    }

    public boolean createTicket(Ticket ticket) {
        new Thread(() -> {
            try {
                ticketDAO.createTicket(ticket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        return true;
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


