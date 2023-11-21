package com.project.zakatku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class InputZakatActivity extends AppCompatActivity {

    private DataZakat dataZakat;
    FirebaseDatabase database;
    DatabaseReference reference;

    Button btnPembayaran, btnToDashboard;
    Spinner spJiwa,spPembayaran;
    TextView tvNomBayar;
    CheckBox cbBpk, cbIbu;
    EditText etNumPhone, etNoKKInput, etNamaInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_zakat);

        dataZakat = new DataZakat();

        btnPembayaran = findViewById(R.id.btnPembayaran);
        btnToDashboard = findViewById(R.id.btnToDashboard);
        spJiwa = findViewById(R.id.spJumJiwa);
        tvNomBayar = findViewById(R.id.nomBayar);
        cbBpk = findViewById(R.id.Bapak);
        cbIbu = findViewById(R.id.Ibu);
        etNumPhone = findViewById(R.id.numphone);
        spPembayaran = findViewById(R.id.pembayarn);
        etNamaInput = findViewById(R.id.namaInput);
        etNoKKInput = findViewById(R.id.noKKInput);


        String[] metodePembayaran = getResources().getStringArray(R.array.pem_list);
        int[] imageResIds = {R.drawable.baseline_assured_workload_24,R.drawable.dana_img, R.drawable.linkaja_img}; // Ganti dengan referensi gambar yang sesuai
        CustomSpinnerAdapter adapter2 = new CustomSpinnerAdapter(this, R.layout.costum_dropdown, Arrays.asList(metodePembayaran), imageResIds);
        spPembayaran.setAdapter(adapter2);



        String[] jumJiw = getResources().getStringArray(R.array.jumlah_jiwa);
        CustomSpinnerAdapterJiwa adapter = new CustomSpinnerAdapterJiwa(this, R.layout.costum_dropdown, jumJiw);
        spJiwa.setAdapter(adapter);
        spJiwa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String select = jumJiw[i];

                // Cek apakah nilai yang dipilih adalah angka atau "--Pilih Jiwa--"
                if (!select.equals("--Pilih Jiwa--")) {
                    int jumlahJiwa = Integer.parseInt(select);
                    String harga = String.valueOf(jumlahJiwa * 16000);
                    tvNomBayar.setText(harga);
                } else {
                    // Jika "--Pilih Jiwa--" dipilih, atur teks kosong atau nilai lainnya
                    tvNomBayar.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Implementasi jika tidak ada yang dipilih
            }
        });



        cbBpk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbBpk.isChecked()) {
                    dataZakat.setStatusBapak(true);
                    dataZakat.setStatusIbu(false);
                } else {
                    dataZakat.setStatusBapak(false);
                    dataZakat.setStatusIbu(true);
                }
            }
        });

        cbIbu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbIbu.isChecked()) {
                    dataZakat.setStatusIbu(true);
                    dataZakat.setStatusBapak(false);
                } else {
                    dataZakat.setStatusIbu(false);
                    dataZakat.setStatusBapak(true);
                }
            }
        });


        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNamaInput.getText().toString();
                String noKK = etNoKKInput.getText().toString();
                String metodePembayaran = spPembayaran.getSelectedItem().toString();
                String jumlahJiwa = spJiwa.getSelectedItem().toString();
                String nomBayar = tvNomBayar.getText().toString();
                String statusPembayaran = "Belum dibayar";

                if (nama.isEmpty()||noKK.isEmpty()||metodePembayaran.isEmpty()||jumlahJiwa.isEmpty()||nomBayar.isEmpty()){
                    Toast.makeText(InputZakatActivity.this, "Isi Field yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    database = FirebaseDatabase.getInstance("https://zakatku-35bff-default-rtdb.asia-southeast1.firebasedatabase.app");
                    reference = database.getReference("pembayaran");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long childCount = dataSnapshot.getChildrenCount();
                            String dataZakatId = String.valueOf(childCount + 1);

                            DataZakat dataZakat = new DataZakat(nama, noKK, metodePembayaran, jumlahJiwa, nomBayar, cbBpk.isChecked(), cbIbu.isChecked(), statusPembayaran);
                            reference.child(dataZakatId).setValue(dataZakat);
                            Toast.makeText(InputZakatActivity.this, "Data Berhasil di Input", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InputZakatActivity.this, NiatActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle kesalahan jika diperlukan
                        }
                    });
                }



            }
        });

        btnToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputZakatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}