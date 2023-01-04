package com.capa.infrafix.Activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements TicketDetailFragment.MainActivityNavBar, FormFragment.MainActivityNavBar, TicketFragment.MainActivityNavBar, SuccessFragment.MainActivityNavBar {


    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private ViewModelMain viewModel;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(this).get(ViewModelMain.class);
        this.bottomMenu();
        this.viewModel.setupPermissions(this);
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
}