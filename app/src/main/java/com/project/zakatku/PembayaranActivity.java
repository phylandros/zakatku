package com.project.zakatku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PembayaranActivity extends AppCompatActivity {

    DatabaseReference databaseReference; // Referensi ke Firebase Database
    TextView kodePembayaranTextView, nominalPembayaranTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        kodePembayaranTextView = findViewById(R.id.nokode);
        nominalPembayaranTextView = findViewById(R.id.nominalpem);

        DatabaseReference reference = FirebaseDatabase.getInstance("https://zakatku-35bff-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("pembayaran");

        // Mengambil kode pembayaran dari intent
        String kodePembayaran = getIntent().getStringExtra("KODE_PEMBAYARAN");

        if (kodePembayaran != null) {
            reference.child(kodePembayaran).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Ambil nilai kode pembayaran dan nominal dari Firebase
                        String nominalPembayaran = dataSnapshot.child("nomBayar").getValue(String.class);

                        kodePembayaranTextView.setText(kodePembayaran);
                        nominalPembayaranTextView.setText("Rp " + nominalPembayaran);
                    } else {
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle kesalahan jika diperlukan
                }
            });
        }
    }
}
