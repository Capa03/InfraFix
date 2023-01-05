package com.capa.infrafix.Form;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.capa.infrafix.R;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormViewHolder> {
    private List<BitmapImage> bitmaps;

    public FormAdapter(List<BitmapImage> bitmap){
        this.bitmaps = bitmap;
    }

    @NonNull
    @Override
    public FormAdapter.FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row_from,parent,false);
        return new FormViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormAdapter.FormViewHolder holder, int position) {
        BitmapImage bitmapImage = this.bitmaps.get(position);
        holder.setImageView(bitmapImage.getBitmap());
    }

    @Override
    public int getItemCount() {

        return this.bitmaps.size();
    }

    public void updateList(List<BitmapImage> bitmaps){
        this.bitmaps = bitmaps;
        notifyDataSetChanged();
    }

    public class FormViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;


        public FormViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageRowFrom);
        }

        public void setImageView(Bitmap bitmap){
            this.imageView.setImageBitmap(bitmap);
        }
    }
}
