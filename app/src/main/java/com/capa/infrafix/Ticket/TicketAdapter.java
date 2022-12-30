package com.capa.infrafix.Ticket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.capa.infrafix.Form.ViewModelForm;
import com.capa.infrafix.R;

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
        holder.setImageView(ticket.getPictureTicket());
        holder.textView.setText(ticket.getSubject());
        holder.root.setOnClickListener(view -> {
            listener.onTicketClicked(position, ticket);
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
        private TextView textView;
        private View root;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageViewTicketImage);
            this.textView = itemView.findViewById(R.id.textViewTicketSubject);
            this.root = itemView;
        }

        public void setImageView(String encodedString){
            try{
                byte[] encodedByte = Base64.decode(encodedString,Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte,0, encodedByte.length);
                this.imageView.setImageBitmap(bitmap);
            }catch (Exception exception){
                exception.getMessage();
            }

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
