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

        CustomSpinnerAdapter adapter2 = new CustomSpinnerAdapter(this, R.layout.custom_spinner_item, Arrays.asList(metodePembayaran), imageResIds);
        spPembayaran.setAdapter(adapter2);



        String[] jumJiw = getResources().getStringArray(R.array.jumlah_jiwa);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jumlah_jiwa, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJiwa.setAdapter(adapter);
        spJiwa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String select = jumJiw[i];
                String harga;

                switch (select) {
                    case "1":
                        harga = String.valueOf(1*16000);
                        break;
                    case "2":
                        harga = String.valueOf(2*16000);
                        break;
                    case "3":
                        harga = String.valueOf(3*16000);
                        break;
                    case "4":
                        harga = String.valueOf(4*16000);
                        break;
                    case "5":
                        harga = String.valueOf(5*16000);
                        break;
                    case "6":
                        harga = String.valueOf(6*16000);
                        break;
                    case "7":
                        harga = String.valueOf(7*16000);
                        break;
                    case "8":
                        harga = String.valueOf(8*16000);
                        break;
                    case "9":
                        harga = String.valueOf(9*16000);
                        break;
                    case "10":
                        harga = String.valueOf(10*16000);
                        break;
                    default:
                        harga = "0"; // Pilihan default jika tidak ada yang cocok
                        break;
                }

                tvNomBayar.setText(harga);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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