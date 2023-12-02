package com.project.zakatku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private String[] mData;
    private String userEmail;
    private String Username;

    public RecyclerViewAdapter(String[] data, String userEmail, String Username) {
        mData = data;
        this.userEmail = userEmail;
        this.Username = Username;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mData[position]);
        // Mengatur gambar berdasarkan konten di sini (sesuaikan dengan kebutuhan Anda)
        if (mData[position].equals("Zaku-Tunai")) {
            holder.imageView.setImageResource(R.drawable.cashpayment);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Lakukan aksi ketika CardView "Zaku-Tunai" diklik
                    Intent intent = new Intent(view.getContext(), InputZakatActivity.class);
                    intent.putExtra("email", userEmail);
                    intent.putExtra("userId", Username);
                    view.getContext().startActivity(intent);
                }
            });
        } else if (mData[position].equals("Zaku-Beras")) {
            holder.imageView.setImageResource(R.drawable.rice);
        } else if (mData[position].equals("Zaku-Verif")) {
            holder.imageView.setImageResource(R.drawable.verif);
        } else if (mData[position].equals("Zaku-Akun")) {
            holder.imageView.setImageResource(R.drawable.akun);
        }
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            imageView = itemView.findViewById(R.id.imageview);
        }
    }
}
