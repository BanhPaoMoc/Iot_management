package com.example.iot_management.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.Fragment.HomeFragment;
import com.example.iot_management.Fragment.NotificationFragment;
import com.example.iot_management.Fragment.SettingFragment;
import com.example.iot_management.Fragment.UserFragment;
import com.example.iot_management.R;
//import com.example.iot_management.adapters.RoomAdapter;
import com.example.iot_management.models.Room;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton btnAdd;
    private RecyclerView rvRooms;

    private DatabaseReference databaseReference;
    private String currentUserId;

    private List<Room> roomList;
//    private RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAdd = findViewById(R.id.btn_add);

        // Kiểm tra người dùng đã đăng nhập chưa
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // Nếu người dùng chưa đăng nhập, chuyển hướng tới màn hình đăng nhập
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);
            finish(); // Đóng MainActivity để không quay lại được
            return; // Dừng tiếp tục thực thi code còn lại
        }

        // Nếu người dùng đã đăng nhập, lấy thông tin user ID
        currentUserId = user.getUid();

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

        // Initialize the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(currentUserId)
                .child("rooms");

        // Set up the Floating Action Button click listener
        btnAdd.setOnClickListener(v -> showAddRoomDialog());
    }
    private void showAddRoomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_add_room, null);
        builder.setView(dialogView);

        EditText edtRoomName = dialogView.findViewById(R.id.edtRoomName);
        Button btnAddRoom = dialogView.findViewById(R.id.btnAddRoom);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();

        // Set the event for adding the room
        btnAddRoom.setOnClickListener(v -> {
            String roomName = edtRoomName.getText().toString().trim();
            if (TextUtils.isEmpty(roomName)) {
                edtRoomName.setError("Vui lòng nhập tên phòng");
                return;
            }

            // Add the room to Firebase
            addRoomToFirebase(roomName, dialog);
        });

        // Set the event for cancelling the dialog
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void addRoomToFirebase(String roomName, AlertDialog dialog) {
        // Generate a new room ID in Firebase
        String roomId = databaseReference.push().getKey();
        if (roomId != null) {
            // Create a new Room object
            Room room = new Room(roomId, roomName, currentUserId, new HashMap<>());
            // Save the room to Firebase
            databaseReference.child(roomId).setValue(room)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Thêm phòng thành công",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Lỗi khi thêm phòng: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this,
                    "Không thể tạo ID cho phòng",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
