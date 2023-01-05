package com.capa.infrafix.Form;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capa.infrafix.Activity.FormCaptureImageActivity;
import com.capa.infrafix.Activity.FormPickImageActivity;
import com.capa.infrafix.Dummy;
import com.capa.infrafix.R;
import com.capa.infrafix.Ticket.Ticket;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FormFragment extends Fragment {

    private GoogleMap mMap;
    private MapView mapView;
    private ViewModelForm viewModelForm;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private Button send;
    private EditText description, date, ticketTitle;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private List<Address> addresses;
    private MainActivityNavBar mainActivityNavBar;
    private FormAdapter adapter;
    private List<BitmapImage> bitmapImage = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.do_you_want_leave_us);

                builder.setPositiveButton(R.string.yes, (dialog, which) -> getActivity().finish());
                builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        this.mapView = (MapView) view.findViewById(R.id.mapView);
        this.mapView.onCreate(savedInstanceState);

        this.mapView.getMapAsync(googleMap -> {
            mMap = googleMap;


            for (String permission : REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    //Get location
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                LatLng loc = new LatLng(this.addresses.get(0).getLatitude(), this.addresses.get(0).getLongitude());
                                mMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModelForm = new ViewModelProvider(this).get(ViewModelForm.class);
        this.cacheViews(view);
        if (!this.viewModelForm.isLocationPermissionGranted()) {
            this.viewModelForm.requestPermission(getActivity());
        }



        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewImageForm);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        this.adapter = new FormAdapter(this.bitmapImage);
        recyclerView.setAdapter(adapter);

        Button openCamera = view.findViewById(R.id.imageButtonCamera);
        openCamera.setOnClickListener(view1 -> {
            FormCaptureImageActivity.startActivity(getContext());
        });

        Button openGallery = view.findViewById(R.id.imageButtonGallary);
        openGallery.setOnClickListener(view13 -> {
            FormPickImageActivity.startActivity(getContext());
        });

        send.setOnClickListener(view12 -> {
            Boolean somethingWrong = false;
            String titleTicket = getResources().getString(R.string.TitleEmpty);
            String dateTicket = getResources().getString(R.string.DateEmpty);
            String descriptionTicket = getResources().getString(R.string.DescriptionEmpty);
            String bitmap = "";
            String descriptionValue = this.description.getText().toString();
            String dateValue = this.date.getText().toString();
            String titleValue = this.ticketTitle.getText().toString();

            if (Dummy.getInstance().getBitmap() != null) {
                bitmap = this.viewModelForm.BitmapToString(Dummy.getInstance().getBitmap());
            }

            if(descriptionValue.isEmpty()){
                this.description.setError(descriptionTicket);
                somethingWrong = true;
            }
            if(dateValue.isEmpty()){
                this.date.setError(dateTicket);
                somethingWrong = true;
            }
            if(titleValue.isEmpty()){
                this.ticketTitle.setError(titleTicket);
                somethingWrong = true;
            }

            if(Dummy.getInstance().getBitmap() == null){
                Toast.makeText(getContext(),R.string.ImageEmpty,Toast.LENGTH_SHORT).show();
                somethingWrong = true;
            }

            Ticket ticket = new Ticket(0, titleValue, descriptionValue, dateValue, bitmap, this.addresses.get(0).getLatitude(), this.addresses.get(0).getLongitude());
            if(!somethingWrong) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.do_you_want_leave_us);

                builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    this.viewModelForm.createTicket(ticket);
                    this.description.setText("");
                    this.date.setText("");
                    this.ticketTitle.setText("");

                    NavDirections action = FormFragmentDirections.actionFormFragmentToSuccessFragment();
                    Navigation.findNavController(view).navigate(action);
                });

                builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        this.mainActivityNavBar.showNavBar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivityNavBar)
            this.mainActivityNavBar = (MainActivityNavBar) context;
    }

    public interface MainActivityNavBar {
        void hideNavBar();
        void showNavBar();
    }


    @Override
    public void onStart() {
        super.onStart();
        this.mapView.onStart();
       // this.imageView.setVisibility(View.GONE);

       if (Dummy.getInstance().getBitmap() != null) {
           // this.imageView.setVisibility(View.VISIBLE);
           // imageView.setImageBitmap(Dummy.getInstance().getBitmap());
           bitmapImage.add(new BitmapImage(Dummy.getInstance().getBitmap()));
        }
        this.adapter.updateList(bitmapImage);
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

    private void cacheViews(View view) {
        //this.imageView = view.findViewById(R.id.imageViewCapturedToSend);
        this.description = view.findViewById(R.id.editTextDescription);
        this.date = view.findViewById(R.id.editTextDate);
        this.send = view.findViewById(R.id.buttonSend);
        this.ticketTitle = view.findViewById(R.id.editTextTitle);
    }
}