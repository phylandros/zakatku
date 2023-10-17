package com.project.zakatku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_DASHBOARD = R.id.menu_dashboard;
    public static final int MENU_PROFILE = R.id.menu_profile;

    BottomNavigationView bottomNavigationView;
    Fragment dashboardFragment, profileFragment, activeFragment;

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

                if (itemId == R.id.menu_dashboard) {
                    getSupportFragmentManager().beginTransaction().hide(activeFragment).show(dashboardFragment).commit();
                    activeFragment = dashboardFragment;
                    return true;
                } else if (itemId == R.id.menu_profile) {
                    getSupportFragmentManager().beginTransaction().hide(activeFragment).show(profileFragment).commit();
                    activeFragment = profileFragment;
                    return true;

                }

                return false;
            }
        });



    }
}