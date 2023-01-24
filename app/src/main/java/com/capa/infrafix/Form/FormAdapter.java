package com.capa.infrafix.Form;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capa.infrafix.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormViewHolder> {

    private List<String> images = new ArrayList<>();;

    private boolean trashState = false;
    private FormEventListener listener;
    public FormAdapter(FormEventListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FormAdapter.FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row_delete, parent, false);
        return new FormViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormAdapter.FormViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String imageFileName = this.images.get(position);
        File file = new File(holder.view.getContext().getApplicationContext().getFilesDir(), imageFileName);
        Glide.with(holder.view.getContext()).load(file).into(holder.imageView);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setTrashState(!getTrashState());
                return false;
            }
        });

        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             listener.onDeleteClicked(imageFileName);
            }
        });


        if(!trashState){
            holder.trash.setVisibility(View.GONE);
        }else{
            holder.trash.setVisibility(View.VISIBLE);
        }
    }

    public boolean getTrashState() {
        return trashState;
    }

    public void setTrashState(boolean trashState) {
        this.trashState = trashState;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.images.size();
    }

    public void updateList(List<String> imageNames) {
        this.images = imageNames;
        notifyDataSetChanged();
    }

    public class FormViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private ImageButton trash;
        private View view;

        public FormViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.imageView = itemView.findViewById(R.id.imageRowFromDelete);
            this.trash = itemView.findViewById(R.id.imageButtonDeleteImage);

        }

    }

    public interface FormEventListener {
        void onDeleteClicked(String fileName);
    }
}
