package com.capa.infrafix.Ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capa.infrafix.R;
import com.capa.infrafix.model.Ticket;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder>  {

    private List<Ticket> ticketList;
    private TicketAdapterListener listener;

    public TicketAdapter(TicketAdapterListener listener){
        this.ticketList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public TicketAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ticket,parent,false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.TicketViewHolder holder, int position) {
        Ticket ticket = this.ticketList.get(position);
        if(ticket.getPictureTicket().size() > 0) {
            String imageFileName = ticket.getPictureTicket().get(0);
            File file = new File(holder.root.getContext().getApplicationContext().getFilesDir(), imageFileName);
            Glide.with(holder.root.getContext()).load(file).into(holder.imageView);
        }

        if (ticket.getSubject().length() > 15) {
            holder.textViewSubject.setText(ticket.getSubject().substring(0,14)+ "...");
        }else {
            holder.textViewSubject.setText(ticket.getSubject());
        }

        holder.textViewData.setText(ticket.getDate());
        holder.root.setOnClickListener(view -> {
            listener.onTicketClicked(position, ticket);
        });

        holder.root.setOnLongClickListener(view -> {
            listener.onTicketLongClicked(position,ticket);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return this.ticketList.size();
    }

    public void updateList(List<Ticket> ticketList){
        this.ticketList = ticketList;
        notifyDataSetChanged();
    }



    public class TicketViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textViewSubject, textViewData;
        private View root;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageViewTicketImage);
            this.textViewSubject = itemView.findViewById(R.id.textViewTicketSubject);
            this.textViewData = itemView.findViewById(R.id.textViewTicketData);
            this.root = itemView;
        }

    }

    public interface TicketAdapterListener{
        void onTicketClicked(int position, Ticket ticket);
        void onTicketLongClicked(int position, Ticket ticket);
    }
}
