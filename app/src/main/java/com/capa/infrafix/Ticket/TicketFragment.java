package com.capa.infrafix.Ticket;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
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

import com.capa.infrafix.R;
import com.capa.infrafix.model.Ticket;

public class TicketFragment extends Fragment {

    private TicketAdapter adapter;
    private ViewModelTicket viewModelTicket;
    private NavController navController;
    private MainActivityNavBar mainActivityNavBar;
    private View view;

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
        View root = inflater.inflate(R.layout.fragment_ticket, container, false);
        this.navController = NavHostFragment.findNavController(TicketFragment.this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewModelTicket = new ViewModelProvider(this).get(ViewModelTicket.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTicket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.view = view;
        this.adapter = new TicketAdapter(new TicketAdapter.TicketAdapterListener() {
            @Override
            public void onTicketClicked(int position, Ticket ticket) {
                NavDirections action = TicketFragmentDirections.actionTicketFragmentToTicketDetailFragment5(ticket.getId());
                Navigation.findNavController(view).navigate(action);
            }

            @Override
            public void onTicketLongClicked(int position, Ticket ticket) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.delete);

                builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    viewModelTicket.deleteTicket(ticket);
                    viewModelTicket.deleteTicketApi(ticket.getId());
                });
                builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        recyclerView.setAdapter(this.adapter);

        this.viewModelTicket.getTickets().observe(getViewLifecycleOwner(), tickets -> {
            this.adapter.updateList(tickets);
        });

        this.mainActivityNavBar.showNavBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.viewModelTicket.refreshTicket();
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
}