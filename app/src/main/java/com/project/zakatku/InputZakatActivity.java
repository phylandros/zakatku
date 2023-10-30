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

import java.util.ArrayList;

public class InputZakatActivity extends AppCompatActivity {

    Button btnPembayaran, btnToDashboard;
    Spinner spJiwa,spPembayaran;
    TextView tvNomBayar;
    CheckBox cbBpk, cbIbu;
    EditText etNumPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_zakat);

        btnPembayaran = findViewById(R.id.btnPembayaran);
        btnToDashboard = findViewById(R.id.btnToDashboard);
        spJiwa = findViewById(R.id.spJumJiwa);
        tvNomBayar = findViewById(R.id.nomBayar);
        cbBpk = findViewById(R.id.Bapak);
        cbIbu = findViewById(R.id.Ibu);
        etNumPhone = findViewById(R.id.numphone);
        spPembayaran = findViewById(R.id.pembayarn);

        String jumJiw[] = getResources().getStringArray(R.array.jumlah_jiwa);
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
                if (cbBpk.isChecked()){
                    String value = "Bapak";
                    cbIbu.setChecked(false);
                } else {
                    cbIbu.setChecked(true);
                }

            }
        });

        cbIbu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cbIbu.isChecked()){
                    String value = "Ibu";
                    cbBpk.setChecked(false);
                } else {
                    cbBpk.setChecked(true);
                }
            }
        });


        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputZakatActivity.this, NiatActivity.class);
                startActivity(intent);
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
    public class Item {
        private String text;
        private int imageResource;

        public Item(String text, int imageResource) {
            this.text = text;
            this.imageResource = imageResource;
        }

        public String getText() {
            return text;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}