package com.capa.infrafix.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.capa.infrafix.Dummy;
import com.capa.infrafix.R;

import java.io.IOException;

public class FormPickImageActivity extends AppCompatActivity {

    private ViewModelPickImage viewModel;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FormPickImageActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(ViewModelPickImage.class);

        setContentView(R.layout.activity_form_pick_image);

        this.viewModel.setupPermissions(this);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // you will get result here in result.data
                Uri selectedImageUri = result.getData().getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImageUri);
                    Dummy.getInstance().setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                this.viewModel.makeRequest(this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}