package com.project.zakatku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class InputZakatActivity extends AppCompatActivity {

    private DataZakat dataZakat;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button btnPembayaran, btnToDashboard;
    Spinner spJiwa,spPembayaran;
    TextView tvNomBayar;
    CheckBox cbBpk, cbIbu;
    EditText etNumPhone, etNoKKInput, etNamaInput;
    String Harga_tunai = "16000";
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
        etNamaInput = findViewById(R.id.namaInput);
        etNoKKInput = findViewById(R.id.noKKInput);

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
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cbIbu.setChecked(false);
                }
            }
        });

        cbIbu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    cbBpk.setChecked(false);
                }
            }
        });




        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNamaInput.getText().toString();
                String noKK = etNoKKInput.getText().toString();
                String jumlahJiwa = spJiwa.getSelectedItem().toString();
                String nomBayar = tvNomBayar.getText().toString();
                String NomPhone = etNumPhone.getText().toString();


                if (nama.isEmpty()||noKK.isEmpty()||jumlahJiwa.isEmpty()||nomBayar.isEmpty()){
                    Toast.makeText(InputZakatActivity.this, "Isi Field yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    database = FirebaseDatabase.getInstance("https://zakatku-35bff-default-rtdb.asia-southeast1.firebasedatabase.app");
                    reference = database.getReference("pembayaran");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String dataZakatId = String.valueOf(System.currentTimeMillis());
                            String Username = getIntent().getStringExtra("userId");

                            DataZakat dataZakat = new DataZakat(Username, jumlahJiwa, nomBayar);
                            reference.child(dataZakatId).setValue(dataZakat);
                            Toast.makeText(InputZakatActivity.this, "Data Berhasil di Input", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(InputZakatActivity.this, NiatActivity.class);
                            String Email = getIntent().getStringExtra("email");

                            intent.putExtra("KODE_PEMBAYARAN", dataZakatId); // Mengirim kode pembayaran
                            intent.putExtra("NAMA", nama);
                            intent.putExtra("JUMLAH_JIWA", jumlahJiwa);
                            intent.putExtra("EMAIL", Email);
                            intent.putExtra("NUM_PHONE", NomPhone);
                            intent.putExtra("HARGA_TUNAI", Harga_tunai);
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