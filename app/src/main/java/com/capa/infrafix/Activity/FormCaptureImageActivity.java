package com.capa.infrafix.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.capa.infrafix.Dummy;
import com.capa.infrafix.Form.ViewModelForm;
import com.capa.infrafix.R;

public class FormCaptureImageActivity extends AppCompatActivity {

    private ViewModelForm viewModelForm;
    private Activity activity = this;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FormCaptureImageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.viewModelForm = new ViewModelProvider(this).get(ViewModelForm.class);
        this.viewModelForm.setupPermissions(this.activity);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // you will get result here in result.data
                Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                Dummy.getInstance().setBitmap(imageBitmap);
                finish();
            }
        });
        startForResult.launch(intent);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Check the permissions again", Toast.LENGTH_SHORT).show();
                this.viewModelForm.makeRequest(this.activity);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}