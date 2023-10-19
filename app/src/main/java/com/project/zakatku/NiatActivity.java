package com.project.zakatku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NiatActivity extends AppCompatActivity {

    Button btnLanjutPembayaran, btnBackDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niat);

        btnLanjutPembayaran = findViewById(R.id.lanjutPembayaran);
        btnBackDashboard = findViewById(R.id.backDashboard);


        btnBackDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new DashboardFragment());
                transaction.commit();
            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}