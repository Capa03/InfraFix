package com.capa.infrafix.Ticket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capa.infrafix.R;

public class TicketFragment extends Fragment {

    private TicketAdapter adapter;
    private ViewModelTicket viewModelTicket;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ticket, container, false);
        navController = NavHostFragment.findNavController(TicketFragment.this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewModelTicket = new ViewModelProvider(this).get(ViewModelTicket.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTicket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new TicketAdapter(new TicketAdapter.TicketAdapterListener() {
            @Override
            public void onTicketClicked(int position, Ticket ticket) {
                NavDirections action = TicketFragmentDirections.actionTicketFragmentToTicketDetailFragment5((int) ticket.getTicketId());
                Navigation.findNavController(view).navigate(action);
            }

            @Override
            public void onTicketLongClicked(int position, Ticket ticket) {

            }
        });

        recyclerView.setAdapter(this.adapter);
        this.viewModelTicket.getTickets().observe(getViewLifecycleOwner(), tickets -> {
            this.adapter.updateList(tickets);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        this.viewModelTicket.refreshTicket();
    }
}