package com.capa.infrafix.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.capa.infrafix.Form.FormFragment;
import com.capa.infrafix.R;
import com.capa.infrafix.SuccessFragment;
import com.capa.infrafix.Ticket.TicketDetailFragment;
import com.capa.infrafix.Ticket.TicketFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements TicketDetailFragment.MainActivityNavBar, FormFragment.MainActivityNavBar, TicketFragment.MainActivityNavBar, SuccessFragment.MainActivityNavBar {


    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private ViewModelMain viewModel;
    private BottomNavigationView bottomNavigationView;

    private ActivityResultLauncher<Intent> captureImageLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(ViewModelMain.class);
        this.bottomMenu();
        this.viewModel.setupPermissions(this);

        captureImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                try {
                    Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                    String filename = UUID.randomUUID().toString();
                    try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                        fos.flush();
                    }
                    viewModel.reportImage(filename);
                } catch (Exception e) {

                }
            }
        });

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // you will get result here in result.data
                Uri selectedImageUri = result.getData().getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImageUri);

                    String filename = UUID.randomUUID().toString();
                    try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                        fos.flush();
                    }
                    viewModel.reportImage(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void bottomMenu(){

        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(this.navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);

        bottomNavigationView = findViewById(R.id.bottomNavBar);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(this.navController, this.appBarConfiguration)
                || super.onSupportNavigateUp();
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

    @Override
    public void hideNavBar() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void showNavBar() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    public void captureImage() {
        captureImageLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }
}