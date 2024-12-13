
package com.example.iot_management.Activity;

import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class RoomDetail extends AppCompatActivity {
    private RecyclerView rvDhts,rvGasLevels, rvLeds;
    private EditText edtDeviceId;
    private Button btnCheckDevice;
    private TextView tvRoomNameDetails;

    private Room room;
    private DatabaseReference databaseReference, dhtsReference, gasLevelsReference, ledsReference;
    private List<Device> deviceList;
    private DeviceAdapter deviceAdapter;
    private DhtAdapter dhtAdapter;
    private GasLevelAdapter gasLevelAdapter;
    private LedAdapter ledAdapter;
    private ArrayList<Dht> dhtList;
    private ArrayList<GasLevel> gasLevelList;
    private ArrayList<Led> ledList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);


        try {

            if (getIntent() != null && getIntent().hasExtra("room")) {
                room = (Room) getIntent().getSerializableExtra("room");
                Toast.makeText(this, "Room" + room, Toast.LENGTH_SHORT).show();
                if (room != null) {
                    Log.d("RoomDetail", "Room data: " + room.getName());
                } else {
                    Log.e("RoomDetail", "Room data is null");
                    Toast.makeText(this, "Không nhận được dữ liệu phòng", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng Activity nếu không có dữ liệu
                }
            } else {
                Log.e("RoomDetail", "Intent không chứa dữ liệu phòng");
                Toast.makeText(this, "Không nhận được dữ liệu phòng", Toast.LENGTH_SHORT).show();
                finish();
            }


//            try {
//                // Firebase references
//                if (room != null) {
//                    databaseReference = FirebaseDatabase.getInstance()
//                            .getReference("Users")
//                            .child(room.getUserId())
//                            .child("rooms")
//                            .child(room.getId());
////                            .child("devices");
//                } else {
//                    Log.e("RoomDetail", "room is null");
//                }
//            } catch (Exception e) {
//                Log.e("RoomDetail", "Error " + e);
//            }


            dhtsReference = FirebaseDatabase.getInstance().getReference("Devices").child("Dht");
            gasLevelsReference = FirebaseDatabase.getInstance().getReference("Devices").child("GasLevel");
            ledsReference = FirebaseDatabase.getInstance().getReference("Devices").child("Leds");


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

            rvDhts.setLayoutManager(new LinearLayoutManager(this));
            rvGasLevels.setLayoutManager(new LinearLayoutManager(this));
            rvLeds.setLayoutManager(new LinearLayoutManager(this));

            dhtList = new ArrayList<>();
            gasLevelList = new ArrayList<>();
            ledList = new ArrayList<>();

            dhtAdapter = new DhtAdapter(dhtList);
            gasLevelAdapter = new GasLevelAdapter(gasLevelList);
            ledAdapter = new LedAdapter(ledList);

            tvRoomNameDetails.setText(room.getName());

            deviceList = new ArrayList<>();
            deviceAdapter = new DeviceAdapter(deviceList);

            // Button click listener for adding a device
            btnCheckDevice.setOnClickListener(v -> {
                String deviceId = edtDeviceId.getText().toString().trim();
                if (!deviceId.isEmpty()) {
                    checkDeviceExistence(deviceId);
                } else {
                    Toast.makeText(RoomDetail.this, "Vui lòng nhập ID thiết bị", Toast.LENGTH_SHORT).show();
                }
            });
            // Load devices data
            loadRoomDevices();
            loadDhts();
            loadGasLevels();
            loadLeds();
        } catch (DatabaseException dbEx) {
            Log.e("DatabaseError", "Error parsing data: " + dbEx.getMessage());
        } catch (Exception ex) {
            Log.e("GeneralError", "Unexpected error: " + ex.getMessage());
        }


    }

    private void checkDeviceExistence(String deviceId) {
        // Kiểm tra trong bảng Dht
        dhtsReference.child(deviceId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Dht dht = snapshot.getValue(Dht.class);
                    if (dht != null) {
                        dhtList.add(dht);
                        dhtAdapter.notifyDataSetChanged();
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

    private void loadRoomDevices() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deviceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Device device = snapshot.getValue(Device.class);
                    if (device != null) {
                        deviceList.add(device);
                    }
                }
                deviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RoomDetail.this, "Lỗi khi tải dữ liệu thiết bị", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadDhts() {
        dhtsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dhtList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dht dht = snapshot.getValue(Dht.class);
                    if (dht != null) {
                        dhtList.add(dht);
                    }
                }
                dhtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomDetail.this, "Lỗi khi tải dữ liệu DHT", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGasLevels() {
        gasLevelsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gasLevelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    GasLevel gasLevel = snapshot.getValue(GasLevel.class);
                    if (gasLevel != null) {
                        gasLevelList.add(gasLevel);
                    }
                }
                gasLevelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomDetail.this, "Lỗi khi tải dữ liệu Gas Level", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadLeds() {
        ledsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ledList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Led led = snapshot.getValue(Led.class);
                    if (led != null) {
                        ledList.add(led);
                    }
                }
                ledAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomDetail.this, "Lỗi khi tải dữ liệu LED", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
