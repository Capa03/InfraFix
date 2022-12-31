package com.capa.infrafix.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.capa.infrafix.Dummy;
import com.capa.infrafix.R;

import java.io.IOException;

public class FormPickImageActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1000;
    private ImageView mImageView;
    private String id = "";


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FormPickImageActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mImageView = findViewById(R.id.imageViewCapturedToSend);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //Permissao nao garantida
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //Mostrar popup runTime da Permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                // Permissao aceite
                pickImageFromGallery();
            }
        } else {
            // Se o System OS Esta a baixo do Lollipop
            pickImageFromGallery();
        }
        setContentView(R.layout.activity_form_pick_image);
    }


    private void pickImageFromGallery() {
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission e garantida
                    pickImageFromGallery();
                } else {
                    //Permission e rejeitada
                    Toast.makeText(this, "Permissao rejeitada!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}