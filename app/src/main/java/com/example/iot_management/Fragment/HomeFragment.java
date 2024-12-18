package com.example.iot_management.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.adapters.RoomAdapter;
import com.example.iot_management.models.Device;
import com.example.iot_management.models.Room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private RecyclerView rvRooms;
    private DatabaseReference databaseReference;
    private List<Room> roomList;
    private RoomAdapter roomAdapter;
    private String currentUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ RecyclerView
        rvRooms = view.findViewById(R.id.rvRooms);
        rvRooms.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách phòng và adapter
        roomList = new ArrayList<>();
        roomAdapter = new RoomAdapter(roomList, getContext()); // Đảm bảo bạn đã triển khai RoomAdapter
        rvRooms.setAdapter(roomAdapter); // Gắn adapter vào RecyclerView

        // Lấy userId hiện tại từ Firebase Auth
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(getContext(), "User: " + currentUserId, Toast.LENGTH_SHORT).show();

        // Tham chiếu Firebase Database tới danh sách các phòng
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(currentUserId)
                .child("rooms");

        // Load danh sách phòng từ Firebase
        loadRooms(currentUserId);

        return view;
    }


    private void loadRooms(String userId) {
        DatabaseReference roomsReference = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(userId)
                .child("rooms");

        roomsReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                roomList.clear(); // Xóa danh sách cũ trước khi thêm dữ liệu mới
                DataSnapshot snapshot = task.getResult();
                if (snapshot != null && snapshot.exists()) {
                    Log.d("LoadRooms", "Number of rooms: " + snapshot.getChildrenCount()); // Log số phòng
                    for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                        try {
                            // Chuyển đổi dữ liệu từ Firebase thành đối tượng Room
                            Room room = roomSnapshot.getValue(Room.class);
                            if (room != null) {
                                roomList.add(room);
                            }
                        } catch (Exception e) {
                            Log.e("LoadRooms", "Error parsing room data", e);
                        }
                    }
                    // Cập nhật giao diện RecyclerView
                    roomAdapter.notifyDataSetChanged();
                } else {
                    Log.d("LoadRooms", "No rooms found for user: " + userId);
                }
            } else {
                Log.e("LoadRooms", "Error loading rooms: ", task.getException());
                Toast.makeText(getContext(), "Lỗi khi tải danh sách phòng: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
