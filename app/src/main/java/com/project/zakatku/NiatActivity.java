package com.project.zakatku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NiatActivity extends AppCompatActivity {

    Button btnLanjutPembayaran, btnBackInputZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niat);

        btnLanjutPembayaran = findViewById(R.id.lanjutPembayaran);
        btnBackInputZakat = findViewById(R.id.backDashboard);


        btnBackInputZakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NiatActivity.this, InputZakatActivity.class);
                startActivity(intent);
            }
        });

        btnLanjutPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NiatActivity.this, PembayaranActivity.class);
                intent.putExtra("KODE_PEMBAYARAN", getIntent().getStringExtra("KODE_PEMBAYARAN"));
                startActivity(intent);

            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}