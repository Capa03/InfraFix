package com.capa.infrafix.Form;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;

import com.capa.infrafix.model.Ticket;
import com.capa.infrafix.repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewModelForm extends AndroidViewModel {

    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private Context context;
    private List<String> imageFileNames = new ArrayList<>();
    private final TicketRepository ticketRepository;
    private boolean stateImage;

    public ViewModelForm(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.ticketRepository = new TicketRepository(application.getApplicationContext());
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
        int LOCATION_PERMISSION_CODE = 101;
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


