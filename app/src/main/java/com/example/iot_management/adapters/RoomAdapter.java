//package com.example.iot_management.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.iot_management.Fragment.RoomDetailFragment;
//import com.example.iot_management.R;
//import com.example.iot_management.models.Room;
//
//import java.util.List;
//
//public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
//
//    private Context context; // Thêm context
//    private List<Room> roomList;
//
//    public RoomAdapter(Context context, List<Room> roomList) {
//        this.context = context;
//        this.roomList = roomList;
//    }
//
//    @NonNull
//    @Override
//    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
//        return new RoomViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
//        Room room = roomList.get(position);
//        holder.tvRoomName.setText(room.getName());
//
//        // Ví dụ: Xử lý sự kiện click (mở màn hình chi tiết phòng)
//        holder.itemView.setOnClickListener(v -> {
//            Toast.makeText(context, "Clicked on: " + room.getName(), Toast.LENGTH_SHORT).show();
//            // Chuyển sang Activity khác
//             Intent intent = new Intent(context, RoomDetailFragment.class);
//             intent.putExtra("roomId", room.getId());
//             context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return roomList.size();
//    }
//
//    public static class RoomViewHolder extends RecyclerView.ViewHolder {
//        TextView tvRoomName;
//        public RoomViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvRoomName = itemView.findViewById(R.id.tvRoomName);
//        }
//    }
//}


package com.example.iot_management.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.Activity.RoomDetail;

import com.example.iot_management.R;
import com.example.iot_management.models.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList;
    private Context context;

    public RoomAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomName.setText(room.getName());

        // Xử lý sự kiện click vào item để chuyển tới RoomDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetail.class);
            intent.putExtra("roomId", room.getId());          // Truyền roomId
            intent.putExtra("room", room);                   // Truyền đối tượng Room (nếu cần)
            intent.putExtra("currentUserId", room.getUserId()); // Truyền currentUserId
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
        }
    }
}
