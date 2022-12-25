package com.capa.infrafix.Ticket;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capa.infrafix.R;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

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
        //holder.setImageView(ticket.getPictureTicket());
        holder.textView.setText(ticket.getSubject());
    }

    @Override
    public int getItemCount() {
        return this.ticketList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageViewTicketImage);
            this.textView = itemView.findViewById(R.id.textViewTicketSubject);
        }

        public void setImageView(Bitmap imageView){
            this.imageView.setImageBitmap(imageView);
        }

        public void setTextView(String text){
            this.textView.setText(text);
        }
    }

    public interface TicketAdapterListener{
        void onTicketClicked(int position, Ticket ticket);
        void onTicketLongClicked(int position, Ticket ticket);
    }
}
