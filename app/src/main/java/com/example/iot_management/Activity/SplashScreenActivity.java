package com.example.iot_management.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_management.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                // Kiểm tra trạng thái đăng nhập
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                Intent intent;
                if (!isLoggedIn) {
                    // Nếu đã đăng nhập, chuyển đến MainActivity
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                } else {
                    // Nếu chưa đăng nhập, chuyển đến LoginActivity
                    intent = new Intent(SplashScreenActivity.this, loginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
