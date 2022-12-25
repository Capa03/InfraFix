package com.capa.infrafix.Camera;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capa.infrafix.Activity.CameraActivity;
import com.capa.infrafix.Dummy;
import com.capa.infrafix.R;
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
    private MapView mapView;
    private List<Address> listGeoCode;
    private ViewModelCamera viewModelCamera;
    private ImageView imageView;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
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
        this.viewModelCamera = new ViewModelProvider(this).get(ViewModelCamera.class);
        this.cacheViews(view);


        if(this.viewModelCamera.isLocationPermissionGranted()){
            try{
                this.listGeoCode = new Geocoder(getContext()).getFromLocationName("Lisboa",1);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            this.viewModelCamera.requestPermission(getActivity());
        }

        ImageButton openCamera = view.findViewById(R.id.imageButtonCamera);
        openCamera.setOnClickListener(view1 -> {
            CameraActivity.startActivity(getContext());
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
            }
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
    }
}