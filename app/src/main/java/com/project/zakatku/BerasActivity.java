package com.project.zakatku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BerasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BerasAdapter berasAdapter;
    private List<BerasModel> berasList = new ArrayList<>(); // Inisialisasi array kosong
    String nama = "", email = "", username = "", password = "", notel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beras);

        recyclerView = findViewById(R.id.recycler_view);
        Intent intent = getIntent();
        if (intent != null) {
            String loggedInUsername = intent.getStringExtra("userId");
            // Gunakan username yang diterima di sini
        }
        // Mengisi array berasList dengan data beras (contoh)
        berasList.add(new BerasModel("Beras A", "10000", R.drawable.beras1));
        berasList.add(new BerasModel("Beras B", "12000", R.drawable.beras2));
        berasList.add(new BerasModel("Beras C", "14000", R.drawable.beras3));
        berasList.add(new BerasModel("Beras D", "15000", R.drawable.beras4));
        berasList.add(new BerasModel("Beras E", "16000", R.drawable.beras5));
        berasList.add(new BerasModel("Beras F", "18000", R.drawable.beras6));
        // ... Tambahkan data beras lainnya ke dalam list

        // Set up RecyclerView dengan GridLayoutManager
        berasAdapter = new BerasAdapter(berasList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2); // Dua kolom per baris
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(berasAdapter);

        berasAdapter.setOnItemClickListener(new BerasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Tangani klik pada item di sini
                showBerasDetailsDialog(berasList.get(position)); // Panggil metode untuk menampilkan modal
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://zakatku-35bff-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference dataUser = database.getReference("users");
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("userId", ""); // Ganti dengan key yang benar


        dataUser.orderByChild("username").equalTo(loggedInUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        nama = snapshot.child("nama").getValue(String.class);
                        email = snapshot.child("email").getValue(String.class);
                        username = snapshot.child("username").getValue(String.class);
                        password = snapshot.child("password").getValue(String.class);
                        notel = snapshot.child("notel").getValue(String.class);
                    }
                } else {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btnToDashboard = findViewById(R.id.btnToDashboard);
        btnToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerasActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



    // Metode untuk menampilkan AlertDialog dengan detail beras
    private void showBerasDetailsDialog(BerasModel berasModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(berasModel.getNamaBeras());

        View view = getLayoutInflater().inflate(R.layout.dialog_input_jumlah, null);
        EditText editTextJumlahBeras = view.findViewById(R.id.editTextJumlahBeras);

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String jumlahBeras = editTextJumlahBeras.getText().toString();
                        Intent intent = new Intent(BerasActivity.this, NiatActivity.class);
                        intent.putExtra("KODE_PEMBAYARAN", String.valueOf(System.currentTimeMillis()));
                        intent.putExtra("NAMA", berasModel.getNamaBeras());
                        intent.putExtra("HARGA_TUNAI",berasModel.getHargaBeras());
                        intent.putExtra("JUMLAH_JIWA", jumlahBeras);
                        intent.putExtra("EMAIL", email); // Menggunakan variabel dari Firebase
                        intent.putExtra("NUM_PHONE", notel); // Menggunakan variabel dari Firebase
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}

