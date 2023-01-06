package com.capa.infrafix.Ticket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capa.infrafix.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketDetailAdapter extends RecyclerView.Adapter<TicketDetailAdapter.ViewHolder> {
    private List<String> images;

    public TicketDetailAdapter(){
        images = new ArrayList<>();
    }

    @NonNull
    @Override
    public TicketDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_details_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketDetailAdapter.ViewHolder holder, int position) {

        String imageFileName = this.images.get(position);
        File file = new File(holder.view.getContext().getApplicationContext().getFilesDir(), imageFileName);
        Glide.with(holder.view.getContext()).load(file).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.images.size();
    }

    public void updateList(List<String> imageNames){
        this.images = imageNames;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageRowTicketDetail);
            this.view = itemView;
        }
    }
}
