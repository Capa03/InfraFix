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

public class ViewModelPickImage extends AndroidViewModel {
    private Context context;
    public ViewModelPickImage(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public void setupPermissions(Activity activity) {

        int permission = ContextCompat.checkSelfPermission(this.context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permission1 = ContextCompat.checkSelfPermission(this.context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED && permission1 != PackageManager.PERMISSION_GRANTED ) {
            this.makeRequest(activity);
        }
    }


    public void makeRequest(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(activity, permissions, 101);
    }
}
