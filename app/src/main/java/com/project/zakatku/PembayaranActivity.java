package com.project.zakatku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PembayaranActivity extends AppCompatActivity {

    DatabaseReference databaseReference; // Referensi ke Firebase Database
    TextView kodePembayaranTextView, nominalPembayaranTextView, vakode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        nominalPembayaranTextView = findViewById(R.id.nominalpem);
        vakode = findViewById(R.id.vapem);
        ImageView copyVAIcon = findViewById(R.id.copyva);
        Button btnBack = findViewById(R.id.backDashboard);
        LinearLayout bankLayout = findViewById(R.id.bank_layout);

        String[] vaDana = {"88810","89508","3901","8881","8100", "8000009", "8059", "8528"};
        String[] bankDana = {"BRI","Mandiri","BCA","BNI","Panin","BTPN","CIMB Niaga","Lainnya"};

        Spinner bankSpinner = findViewById(R.id.bankVa);
        ArrayAdapter<String> bankAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bankDana);
        bankSpinner.setAdapter(bankAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PembayaranActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedBank = bankDana[position];
                if ("BRI".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                } else if ("Mandiri".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                } else if ("BCA".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                } else if ("BNI".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                } else if ("Panin".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                } else if ("BTPN".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                } else if ("CIMB Niaga".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                } else if ("Lainnya".equals(selectedBank)) {
                    vakode.setText(vaDana[position] + "082290000259");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Implementasi jika tidak ada yang dipilih
            }
        });


        copyVAIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView vakode = findViewById(R.id.vapem);
                String vaNumber = vakode.getText().toString();

                // Salin nomor VA ke clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Nomor VA", vaNumber);
                clipboard.setPrimaryClip(clip);

                // Tampilkan pesan bahwa nomor VA telah disalin
                Toast.makeText(PembayaranActivity.this, "Nomor VA disalin", Toast.LENGTH_SHORT).show();
            }
        });

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
                        String metodePembayaran = dataSnapshot.child("metodePembayaran").getValue(String.class);


                        if ("Link Aja".equals(metodePembayaran)){
                            vakode.setText("082290000259");
                            bankLayout.setVisibility(View.GONE);
                        }

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
