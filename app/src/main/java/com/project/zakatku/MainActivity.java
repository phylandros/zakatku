package com.project.zakatku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.project.zakatku.DashboardFragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_DASHBOARD = R.id.menu_dashboard;
    public static final int MENU_PROFILE = R.id.menu_profile;

    BottomNavigationView bottomNavigationView;
    Fragment dashboardFragment, profileFragment, activeFragment;
    DashboardFragment dashboardFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        dashboardFragment = new DashboardFragment();
        profileFragment = new ProfileFragment();
        activeFragment = dashboardFragment;

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,profileFragment, "2").hide(profileFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, dashboardFragment, "1").commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == MENU_DASHBOARD) {
                    switchToDashboard();
                    return true;
                } else if (itemId == MENU_PROFILE) {
                    switchToProfile();
                    return true;
                }

                return false;
            }
        });



    }

    public void switchToDashboard() {
//        getSupportFragmentManager().beginTransaction().hide(activeFragment).show(dashboardFragment).commit();
//        activeFragment = dashboardFragment;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (dashboardFragment == null) {
            dashboardFragment = new DashboardFragment();
            transaction.add(R.id.fragment_container, dashboardFragment, "1");
        }
        transaction.hide(activeFragment);
        transaction.show(dashboardFragment);
        activeFragment = dashboardFragment;
        transaction.addToBackStack(null);
        transaction.commit();
    }



    public void switchToProfile() {
        getSupportFragmentManager().beginTransaction().hide(activeFragment).show(profileFragment).commit();
        activeFragment = profileFragment;
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof DashboardFragment) {
            Toast.makeText(MainActivity.this, "Tekan tombol logout untuk kembali ke login",Toast.LENGTH_SHORT).show();
            // Jika DashboardFragment sedang ditampilkan, mencegah "back"
            // Tampilkan dialog atau lakukan tindakan lain jika diperlukan
        } else {
            // Jika fragment lain sedang aktif, izinkan "back" seperti biasa
            super.onBackPressed();
        }
    }

}