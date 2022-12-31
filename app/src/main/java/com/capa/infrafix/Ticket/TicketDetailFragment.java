package com.capa.infrafix.Ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.capa.infrafix.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class TicketDetailFragment extends Fragment implements OnMapReadyCallback {

    private ImageView imageView;
    private TextView title, date, description;
    private MapView mapView;
    private ViewModelTicket viewModel;
    private TicketDetailFragmentArgs args;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_detail, container, false);
        this.mapView = (MapView) view.findViewById(R.id.mapViewTicketDetails);
        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.cacheViews(view);
        this.viewModel = new ViewModelProvider(this).get(ViewModelTicket.class);
        this.args = TicketDetailFragmentArgs.fromBundle(getArguments());

        this.viewModel.getTicketById(args.getTicketId()).observe(getViewLifecycleOwner(), ticket -> {
            this.imageView.setImageBitmap(this.viewModel.StringToBitmap(ticket.getPictureTicket()));
            this.title.setText(ticket.getSubject());
            this.date.setText(ticket.getDate());
            this.description.setText(ticket.getDescription());
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.viewModel.getTicketById(args.getTicketId()).observe(getViewLifecycleOwner(), ticket -> {
            LatLng latLng = new LatLng(ticket.getLat(), ticket.getLng());
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Position"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        });

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
        this.imageView = view.findViewById(R.id.imageViewTicketDetails);
        this.title = view.findViewById(R.id.textViewTicketDetailsTitle);
        this.date = view.findViewById(R.id.textViewTicketDetailsDate);
        this.description = view.findViewById(R.id.textViewTicketDetailsDescription);

    }
}