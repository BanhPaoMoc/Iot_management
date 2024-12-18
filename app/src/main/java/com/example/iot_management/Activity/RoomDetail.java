
package com.example.iot_management.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.adapters.DeviceAdapter;
import com.example.iot_management.adapters.DhtAdapter;
import com.example.iot_management.adapters.GasLevelAdapter;
import com.example.iot_management.adapters.LedAdapter;
import com.example.iot_management.models.Device;
import com.example.iot_management.models.Dht;
import com.example.iot_management.models.GasLevel;
import com.example.iot_management.models.Led;
import com.example.iot_management.models.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomDetail extends AppCompatActivity {
    private RecyclerView rvDhts,rvGasLevels, rvLeds;
    private EditText edtDeviceId;
    private Button btnCheckDevice, btnUpdateDevices;
    private TextView tvRoomNameDetails;

    private Room room;
    private DatabaseReference databaseReference, dhtsReference, gasLevelsReference, ledsReference, ledsReferenceUser, dhtsReferenceUser, gasLevelsReferenceUser;
    private List<Device> deviceList;
    private DeviceAdapter deviceAdapter;
    private DhtAdapter dhtAdapter;
    private GasLevelAdapter gasLevelAdapter;
    private LedAdapter ledAdapter;
    private ArrayList<String> devicesIdinRoom;
    private ArrayList<Dht> dhtList;
    private ArrayList<GasLevel> gasLevelList;
    private ArrayList<Led> ledList;

    // Khai báo các danh sách
    List<String> dhtsReferenceUserList = new ArrayList<>();
    List<String> gasLevelsReferenceUserList = new ArrayList<>();
    List<String> ledsReferenceUserList = new ArrayList<>();

    private String currentUserId, roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);


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

        if (roomId == null || roomId.isEmpty() || currentUserId == null || currentUserId.isEmpty()) {
            Toast.makeText(this, "Không nhận được thông tin cần thiết", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity nếu thiếu dữ liệu
        }



        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(currentUserId)
                .child("rooms");

        try {

            if (getIntent() != null && getIntent().hasExtra("room")) {
                room = (Room) getIntent().getSerializableExtra("room");
                if (room != null) {
                    Log.d("RoomDetail4", "Room data: " + room.getName());
                    Toast.makeText(this, "Room: " + room.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("RoomDetail5", "Room data is null");
                    Toast.makeText(this, "Không nhận được dữ liệu phòng", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng Activity nếu không có dữ liệu
                }
            } else {
                Log.e("RoomDetail6", "Intent không chứa dữ liệu phòng");
                Toast.makeText(this, "Không nhận được dữ liệu phòng", Toast.LENGTH_SHORT).show();
                finish();
            }


            dhtsReference = FirebaseDatabase.getInstance().getReference("Devices")
                    .child("Dht");
            gasLevelsReference = FirebaseDatabase.getInstance().getReference("Devices")
                    .child("GasLevel");
            ledsReference = FirebaseDatabase.getInstance().getReference("Devices")
                    .child("Leds");


            dhtsReferenceUser = FirebaseDatabase.getInstance().getReference("Users")
                    .child(currentUserId)
                    .child("rooms")
                    .child(roomId)
                    .child("devices")
                    .child("DHT");
            gasLevelsReferenceUser = FirebaseDatabase.getInstance().getReference("Users")
                    .child(currentUserId)
                    .child("rooms")
                    .child(roomId)
                    .child("devices")
                    .child("GasLevel");
            ledsReferenceUser = FirebaseDatabase.getInstance().getReference("Users")
                    .child(currentUserId)
                    .child("rooms")
                    .child(roomId)
                    .child("devices")
                    .child("LED");


            // Initialize UI elements
            edtDeviceId = findViewById(R.id.edtDeviceId);
            tvRoomNameDetails = findViewById(R.id.tvroomNameDetails);
            // Initialize UI elements

            rvDhts = findViewById(R.id.rvDhts);
            rvGasLevels = findViewById(R.id.rvGasLevels);
            rvLeds = findViewById(R.id.rvLeds);
            edtDeviceId = findViewById(R.id.edtDeviceId);
            btnCheckDevice = findViewById(R.id.btnCheckDevice);
            tvRoomNameDetails = findViewById(R.id.tvroomNameDetails);


// Khởi tạo danh sách
            ledList = new ArrayList<>();
            ledAdapter = new LedAdapter(ledList);

            // Khởi tạo danh sách và adapter
            dhtList = new ArrayList<>();
            dhtAdapter = new DhtAdapter(dhtList);

            // Khởi tạo danh sách và adapter
            gasLevelList = new ArrayList<>();
            gasLevelAdapter = new GasLevelAdapter(gasLevelList);

            rvDhts.setLayoutManager(new LinearLayoutManager(this));
            rvGasLevels.setLayoutManager(new LinearLayoutManager(this));
            rvLeds.setLayoutManager(new LinearLayoutManager(this));

            rvDhts.setAdapter(dhtAdapter);
            rvGasLevels.setAdapter(gasLevelAdapter);
            rvLeds.setAdapter(ledAdapter);

            tvRoomNameDetails.setText(room.getName());

            deviceList = new ArrayList<>();
            deviceAdapter = new DeviceAdapter(deviceList);

            // Button click listener for adding a device
            btnCheckDevice.setOnClickListener(v -> {
                String deviceId = edtDeviceId.getText().toString().trim();
                if (!deviceId.isEmpty()) {
                    checkDeviceExistence(roomId,deviceId);
                } else {
                    Toast.makeText(RoomDetail.this, "Vui lòng nhập ID thiết bị", Toast.LENGTH_SHORT).show();
                }
            });


// Lấy dữ liệu từ Firebase
            loadDhtData();
            loadGasLevelData();
            loadLedData();


            btnUpdateDevices = findViewById(R.id.btnUpdateDevices);

            btnUpdateDevices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUserDevices();
                }
            });
        } catch (DatabaseException dbEx) {
            Log.e("DatabaseError", "Error parsing data: " + dbEx.getMessage());
        } catch (Exception ex) {
            Log.e("GeneralError", "Unexpected error: " + ex.getMessage());
        }
    }

    // Cập nhật danh sách thiết bị
    public void updateUserDevices() {

        Intent intent = new Intent(RoomDetail.this, Loading.class);
        intent.putExtra("roomId", roomId);          // Truyền roomId
        intent.putExtra("room", room);                   // Truyền đối tượng Room (nếu cần)
        intent.putExtra("currentUserId", currentUserId); // Truyền currentUserId
        startActivity(intent);

        DatabaseReference userDevicesRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(currentUserId)
                .child("rooms")
                .child(roomId)
                .child("devices");

        // Lấy dữ liệu từ các bảng thiết bị: DHT, GasLevel, LED
        userDevicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Kiểm tra nếu có dữ liệu trong "devices"
                if (snapshot.exists()) {
                    // Duyệt qua từng loại thiết bị trong bảng devices
                    for (DataSnapshot deviceTypeSnapshot : snapshot.getChildren()) {
                        // Lấy tên loại thiết bị (DHT, GasLevel, LED)
                        String deviceType = deviceTypeSnapshot.getKey();
                        // Duyệt qua từng deviceId của loại thiết bị đó
                        for (DataSnapshot deviceIdSnapshot : deviceTypeSnapshot.getChildren()) {
                            String deviceId = deviceIdSnapshot.getKey(); // Lấy deviceId

                            // Thêm deviceId vào danh sách tương ứng
                            switch (deviceType) {
                                case "DHT":
                                    dhtsReferenceUserList.add(deviceId);
                                    break;
                                case "GasLevel":
                                    gasLevelsReferenceUserList.add(deviceId);
                                    break;
                                case "LED":
                                    ledsReferenceUserList.add(deviceId);
                                    break;
                            }
                        }
                    }

                    // Sau khi đã lấy và lưu tất cả deviceId vào các danh sách, có thể gọi hàm checkDeviceExistence với từng deviceId
                    for (String deviceId : dhtsReferenceUserList) {
                        checkDeviceExistence(roomId, deviceId);
                    }
                    for (String deviceId : gasLevelsReferenceUserList) {
                        checkDeviceExistence(roomId, deviceId);
                    }
                    for (String deviceId : ledsReferenceUserList) {
                        checkDeviceExistence(roomId, deviceId);
                    }
                } else {
                    Toast.makeText(RoomDetail.this, "Không có thiết bị nào trong phòng này.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomDetail.this, "Lỗi khi lấy thiết bị từ Users.", Toast.LENGTH_SHORT).show();
            }
        });

        refreshRecyclerViews();

    }



    private void checkDeviceExistence(String roomId, String deviceId) {
        // Kiểm tra trong bảng Dht
        dhtsReference.child(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Dht dht = snapshot.getValue(Dht.class);
                    if (dht != null) {
                        dhtList.add(dht);
                        dhtAdapter.notifyDataSetChanged();
                        addDeviceToRoom(roomId, deviceId, "DHT", dht, null, null);
                        Toast.makeText(RoomDetail.this, "Thiết bị DHT đã được thêm", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu không tìm thấy trong Dht, kiểm tra GasLevel
                    gasLevelsReference.child(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                GasLevel gasLevel = snapshot.getValue(GasLevel.class);
                                if (gasLevel != null) {
                                    gasLevelList.add(gasLevel);
                                    gasLevelAdapter.notifyDataSetChanged();
                                    addDeviceToRoom(roomId, deviceId, "GasLevel", null, null, gasLevel);
                                    Toast.makeText(RoomDetail.this, "Thiết bị Gas Level đã được thêm", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Nếu không tìm thấy trong GasLevel, kiểm tra Leds
                                ledsReference.child(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            Led led = snapshot.getValue(Led.class);
                                            if (led != null) {
                                                ledList.add(led);
                                                ledAdapter.notifyDataSetChanged();
                                                addDeviceToRoom(roomId, deviceId, "LED", null, led, null);
                                                Log.d("LEDHNE", "onDataChange: " + ledList);
                                                Toast.makeText(RoomDetail.this, "Thiết bị LED đã được thêm", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Không tìm thấy trong bất kỳ bảng nào
                                            Toast.makeText(RoomDetail.this, "Thiết bị không tồn tại trong bất kỳ bảng nào", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(RoomDetail.this, "Lỗi kiểm tra thiết bị trong bảng Leds", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RoomDetail.this, "Lỗi kiểm tra thiết bị trong bảng Gas Levels", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomDetail.this, "Lỗi kiểm tra thiết bị trong bảng DHT", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addDeviceToRoom(String roomId, String deviceId, String deviceType, Dht dht, Led led, GasLevel gasLevel ) {

        DatabaseReference roomDevicesRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(currentUserId) // ID User, có thể thay đổi theo người dùng đăng nhập
                .child("rooms")
                .child(roomId)
                .child("devices").child(deviceType)
                .child(deviceId); // Tạo nhánh thiết bị


        // Dữ liệu của thiết bị
        Map<String, Object> deviceData = new HashMap<>();
        deviceData.put("deviceId", deviceId);
        deviceData.put("deviceType", deviceType);

        if(deviceType == "LED") {
            deviceData.put("state", led.isState());
        } else if (deviceType == "DHT") {
            deviceData.put("humidity", dht.getHumidity());
            deviceData.put("temperature", dht.getTemperature());

        } else if (deviceType == "GasLevel" ) {
            deviceData.put("gas_sensor", gasLevel.getGas_sensor());
        }

        // Thêm thiết bị vào nhánh Firebase
        roomDevicesRef.setValue(deviceData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FIREBASE", "Thiết bị đã được thêm vào phòng thành công.");
                })
                .addOnFailureListener(e -> {
                    Log.e("FIREBASE", "Lỗi khi thêm thiết bị vào phòng.", e);
                });

    }

    private void refreshRecyclerViews() {
        // Làm mới các danh sách thiết bị trong RecyclerView
        dhtAdapter.notifyDataSetChanged();
        gasLevelAdapter.notifyDataSetChanged();
        ledAdapter.notifyDataSetChanged();
    }

    private void loadDhtData() {
        dhtsReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dhtList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dht dht = dataSnapshot.getValue(Dht.class);
                    if (dht != null) {
                        dhtList.add(dht);
                    }
                }
                dhtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load DHT data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGasLevelData() {
        gasLevelsReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gasLevelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GasLevel gasLevel = dataSnapshot.getValue(GasLevel.class);
                    if (gasLevel != null) {
                        gasLevelList.add(gasLevel);
                    }
                }
                gasLevelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Gas Level data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLedData() {
        ledsReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ledList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Led led = dataSnapshot.getValue(Led.class);
                    if (led != null) {
                        ledList.add(led);
                    }
                }
                ledAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to load LED data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
