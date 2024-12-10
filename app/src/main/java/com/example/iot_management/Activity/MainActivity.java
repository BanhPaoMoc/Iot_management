package com.example.iot_management.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.iot_management.Fragment.HomeFragment;
import com.example.iot_management.Fragment.NotificationFragment;
import com.example.iot_management.Fragment.SettingFragment;
import com.example.iot_management.Fragment.UserFragment;
import com.example.iot_management.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAdd = findViewById(R.id.btn_add);

        // Set Home Fragment as default
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        // Sự kiện click cho Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.notification) {
                    selectedFragment = new NotificationFragment();
                } else if (itemId == R.id.setting) {
                    selectedFragment = new SettingFragment();
                } else if (itemId == R.id.profile) {
                    selectedFragment = new UserFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
                }
                return true;
            }
        });

        // Sự kiện click cho Floating Action Button
        btnAdd.setOnClickListener(v -> Toast.makeText(MainActivity.this, "FAB Clicked", Toast.LENGTH_SHORT).show());
    }
}
