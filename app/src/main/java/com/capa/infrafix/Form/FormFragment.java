package com.capa.infrafix.Form;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.capa.infrafix.Activity.CameraActivity;
import com.capa.infrafix.Dummy;
import com.capa.infrafix.R;
import com.capa.infrafix.Ticket.Ticket;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class FormFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLastLocation;
    private MapView mapView;
    private List<Address> listGeoCode;
    private ViewModelForm viewModelForm;
    private ImageView imageView;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private Button send;
    private EditText description , date,ticketTitle;
    double lat , lng = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        this.mapView = (MapView) view.findViewById(R.id.mapView);
        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModelForm = new ViewModelProvider(this).get(ViewModelForm.class);
        this.cacheViews(view);


        if(this.viewModelForm.isLocationPermissionGranted()){
            try{
                this.listGeoCode = new Geocoder(getContext()).getFromLocationName("Lisbon",1);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            this.viewModelForm.requestPermission(getActivity());
        }

        ImageButton openCamera = view.findViewById(R.id.imageButtonCamera);
        openCamera.setOnClickListener(view1 -> {
            CameraActivity.startActivity(getContext());
        });

        send.setOnClickListener(view12 -> {
            String descriptionValue = this.description.getText().toString();
            String dateValue = this.date.getText().toString();
            String titleValue = this.ticketTitle.getText().toString();
            Ticket ticket = new Ticket(0,titleValue,descriptionValue,dateValue,"",this.lat,this.lng);
            this.viewModelForm.createTicket(ticket);
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        if(Dummy.getInstance().getBitmap() != null){
            imageView.setImageBitmap(Dummy.getInstance().getBitmap());
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Location location = null;


        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
                location = mMap.getMyLocation();
            }
        }

        if(location != null){

            this.lat = location.getLatitude();
            this.lng = location.getLongitude();

            LatLng loc = new LatLng(this.lat,this.lng);
            mMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }

    private void cacheViews(View view){
        this.imageView = view.findViewById(R.id.imageViewCapturedToSend);
        this.description = view.findViewById(R.id.editTextDescription);
        this.date = view.findViewById(R.id.editTextDate);
        this.send = view.findViewById(R.id.buttonSend);
        this.ticketTitle = view.findViewById(R.id.editTextTitle);
    }
}