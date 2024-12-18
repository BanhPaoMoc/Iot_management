package com.example.iot_management.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.iot_management.R;
import com.example.iot_management.models.Room;

public class Loading extends AppCompatActivity {

    private ProgressBar progressBar;
    private String currentUserId, roomId;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);

        progressBar = findViewById(R.id.progressBar);


        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("roomId")) {
                roomId = intent.getStringExtra("roomId");
            } else {
                Log.e("RoomDetail", "roomId không được truyền qua Intent");
            }

            if (intent.hasExtra("currentUserId")) {
                currentUserId = intent.getStringExtra("currentUserId");
            } else {
                Log.e("RoomDetail", "currentUserId không được truyền qua Intent");
            }

            if (intent.hasExtra("room")) {
                room = (Room) intent.getSerializableExtra("room"); // Nếu cần đối tượng Room
            }

            // Log kiểm tra dữ liệu
            Log.d("RoomDetail", "roomId: " + roomId);
            Log.d("RoomDetail", "currentUserId: " + currentUserId);
        } else {
            Log.e("RoomDetail", "Intent không chứa dữ liệu");
        }


        showLoading();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Loading.this, RoomDetail.class);
                intent.putExtra("roomId", roomId);          // Truyền roomId
                intent.putExtra("room", room);                   // Truyền đối tượng Room (nếu cần)
                intent.putExtra("currentUserId", currentUserId); // Truyền currentUserId
                startActivity(intent);
                hideLoading();
            }
        }, 1500);
    }
    // Hiển thị ProgressBar
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    // Ẩn ProgressBar
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}