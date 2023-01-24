package com.capa.infrafix.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ViewModelMain extends AndroidViewModel {


    private Context context;

    public ViewModelMain(@NonNull Application application) {
        super(application);
        this.context = getApplication().getApplicationContext();
    }

    public void setupPermissions(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        for (int i = 0; i< permissions.length; i++) {
            int per = ContextCompat.checkSelfPermission(context, permissions[i]);
            if (per != PackageManager.PERMISSION_GRANTED) {
                    makeRequest(activity);
            }
        }
    }


    public void makeRequest(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(activity, permissions, 101);
    }

}
