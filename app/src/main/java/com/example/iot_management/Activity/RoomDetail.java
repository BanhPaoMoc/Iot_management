//package com.example.iot_management.Activity;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.iot_management.R;
//import com.example.iot_management.adapters.DeviceAdapter;
//import com.example.iot_management.models.Device;
//import com.example.iot_management.models.Room;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RoomDetail extends AppCompatActivity {
//    private RecyclerView rvDevices;
//    private EditText edtDeviceId;
//    private Button btnAddDevice;
//    private TextView tvRoomNameDetails;
//
//    private Room room;
//    private DatabaseReference databaseReference, devicesReference;
//    private List<Device> deviceList;
//    private DeviceAdapter deviceAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_room_detail);
//
//        if (getIntent() != null && getIntent().hasExtra("room")) {
//            room = (Room) getIntent().getSerializableExtra("room");
//        }
//
//        databaseReference = FirebaseDatabase.getInstance()
//                .getReference("Users")
//                .child(room.getUserId())
//                .child("rooms")
//                .child(room.getId())
//                .child("devices");
//
//        devicesReference = FirebaseDatabase.getInstance().getReference("Devices");
//
//        rvDevices = findViewById(R.id.rvDevices);
//        edtDeviceId = findViewById(R.id.edtDeviceId);
//        btnAddDevice = findViewById(R.id.btnAddDevice);
//        tvRoomNameDetails = findViewById(R.id.tvroomNameDetails);
//
//        tvRoomNameDetails.setText(room.getName());
//
//        rvDevices.setLayoutManager(new LinearLayoutManager(this));
//        deviceList = new ArrayList<>();
//        deviceAdapter = new DeviceAdapter(deviceList);
//        rvDevices.setAdapter(deviceAdapter);
//
//        btnAddDevice.setOnClickListener(v -> {
//            String deviceId = edtDeviceId.getText().toString();
//            if (!deviceId.isEmpty()) {
//                checkDeviceExistence(deviceId);
//            } else {
//                Toast.makeText(RoomDetail.this, "Vui lòng nhập ID thiết bị", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnAddDevice.setOnClickListener(v -> {
//            String deviceId = edtDeviceId.getText().toString();
//            if (!deviceId.isEmpty()) {
//                checkDeviceExistence(deviceId);
//            } else {
//                Toast.makeText(RoomDetail.this, "Vui lòng nhập ID thiết bị", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Lắng nghe thay đổi từ Firebase để cập nhật danh sách thiết bị trong phòng
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                deviceList.clear();  // Xóa danh sách cũ
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Device device = snapshot.getValue(Device.class);
//                    deviceList.add(device);
//                }
//                deviceAdapter.notifyDataSetChanged();  // Cập nhật lại UI
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(RoomDetail.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Kiểm tra sự tồn tại của thiết bị trong database
//    private void checkDeviceExistence(String deviceId) {
//        devicesReference.child(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Nếu thiết bị đã tồn tại, lấy thông tin và thêm vào phòng
//                    Device existingDevice = dataSnapshot.getValue(Device.class);
//                    addDeviceToRoom(existingDevice);
//                } else {
//                    // Nếu thiết bị không tồn tại
//                    Toast.makeText(RoomDetail.this, "Thiết bị không tồn tại trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(RoomDetail.this, "Lỗi khi kiểm tra thiết bị", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Thêm thiết bị vào phòng người dùng
//    private void addDeviceToRoom(Device existingDevice) {
//        String deviceId = existingDevice.getId();  // ID thiết bị
//        databaseReference.child(deviceId).setValue(existingDevice)  // Thêm thiết bị vào phòng
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(RoomDetail.this, "Thêm thiết bị thành công", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(RoomDetail.this, "Lỗi khi thêm thiết bị", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}


package com.example.iot_management.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.adapters.DeviceAdapter;
import com.example.iot_management.models.Device;
import com.example.iot_management.models.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomDetail extends AppCompatActivity {
    private RecyclerView rvDevices;
    private EditText edtDeviceId;
    private Button btnAddDevice;
    private TextView tvRoomNameDetails;

    private Room room;
    private DatabaseReference databaseReference, devicesReference;
    private List<Device> deviceList;
    private DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        // Retrieve room data from Intent
        if (getIntent() != null && getIntent().hasExtra("room")) {
            room = (Room) getIntent().getSerializableExtra("room");
        }

        // Firebase references
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(room.getUserId())
                .child("rooms")
                .child(room.getId())
                .child("devices");

        devicesReference = FirebaseDatabase.getInstance().getReference("Devices");

        // Initialize UI elements
        rvDevices = findViewById(R.id.rvDevices);
        edtDeviceId = findViewById(R.id.edtDeviceId);
        btnAddDevice = findViewById(R.id.btnAddDevice);
        tvRoomNameDetails = findViewById(R.id.tvroomNameDetails);

        tvRoomNameDetails.setText(room.getName());

        rvDevices.setLayoutManager(new LinearLayoutManager(this));
        deviceList = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(deviceList);
        rvDevices.setAdapter(deviceAdapter);

        // Button click listener for adding a device
        btnAddDevice.setOnClickListener(v -> {
            String deviceId = edtDeviceId.getText().toString();
            if (!deviceId.isEmpty()) {
                checkDeviceExistence(deviceId); // Check if the device exists
            } else {
                Toast.makeText(RoomDetail.this, "Vui lòng nhập ID thiết bị", Toast.LENGTH_SHORT).show();
            }
        });

        // Listen for changes in the room's devices data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deviceList.clear();  // Clear the old list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Device device = snapshot.getValue(Device.class);
                    deviceList.add(device);
                }
                deviceAdapter.notifyDataSetChanged();  // Update the UI with new device list
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RoomDetail.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check if the device exists in the Devices reference in Firebase
    private void checkDeviceExistence(String deviceId) {
        devicesReference.child(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If the device exists, retrieve its data and add it to the room
                    Device existingDevice = dataSnapshot.getValue(Device.class);
                    addDeviceToRoom(existingDevice); // Add device to the room
                } else {
                    // If the device doesn't exist in the database
                    Toast.makeText(RoomDetail.this, "Thiết bị không tồn tại trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RoomDetail.this, "Lỗi khi kiểm tra thiết bị", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Add the device to the user's room
    private void addDeviceToRoom(Device existingDevice) {
        String deviceId = existingDevice.getId();  // Get the device ID
        databaseReference.child(deviceId).setValue(existingDevice)  // Add the device to the room
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RoomDetail.this, "Thêm thiết bị thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RoomDetail.this, "Lỗi khi thêm thiết bị", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
