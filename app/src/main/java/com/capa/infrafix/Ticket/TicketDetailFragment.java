package com.capa.infrafix.Ticket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capa.infrafix.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class TicketDetailFragment extends Fragment implements OnMapReadyCallback {


    private TextView title, date, description;
    private MapView mapView;
    private ViewModelTicket viewModel;
    private TicketDetailFragmentArgs args;
    private MainActivityNavBar mainActivityNavBar;
    private View view;
    private TicketDetailAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.cacheViews(view);
        this.viewModel = new ViewModelProvider(this).get(ViewModelTicket.class);;
        this.args = TicketDetailFragmentArgs.fromBundle(getArguments());
        this.view = view;

        String titleTicket = getResources().getString(R.string.title_ticket);
        String dateTicket = getResources().getString(R.string.date);
        String descriptionTicket = getResources().getString(R.string.description);

        this.viewModel.getTicketById(args.getTicketId()).observe(getViewLifecycleOwner(), ticket -> {
            this.title.setText(titleTicket + " : "+ ticket.getSubject());
            this.date.setText(dateTicket + " : "+ ticket.getDate());
            this.description.setText(descriptionTicket + " : "+ ticket.getDescription());
            this.adapter.updateList(ticket.getPictureTicket());
        });

        this.mainActivityNavBar.hideNavBar();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTicketDetail);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        this.adapter = new TicketDetailAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this.adapter);

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
        this.title = view.findViewById(R.id.textViewTicketDetailsTitle);
        this.date = view.findViewById(R.id.textViewTicketDetailsDate);
        this.description = view.findViewById(R.id.textViewTicketDetailsDescription);

    }
}