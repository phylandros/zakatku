package com.project.zakatku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BerasAdapter extends RecyclerView.Adapter<BerasAdapter.ViewHolder> {

    private List<BerasModel> berasList;
    private OnItemClickListener onItemClickListener;

    public BerasAdapter(List<BerasModel> berasList) {
        this.berasList = berasList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beras, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BerasModel beras = berasList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        // Set data to views in ViewHolder
        holder.textViewNama.setText(beras.getNamaBeras());
        holder.textViewHarga.setText(beras.getHargaBeras());
        holder.imageView.setImageResource(beras.getGambarBeras());
    }

    @Override
    public int getItemCount() {
        return berasList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNama;
        TextView textViewHarga;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.nama_beras);
            textViewHarga = itemView.findViewById(R.id.harga_beras);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
}


