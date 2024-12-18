package com.example.iot_management.adapters;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.models.Led;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LedAdapter extends RecyclerView.Adapter<LedAdapter.LedViewHolder> {
    private List<Led> ledList;

    public LedAdapter(List<Led> ledList) {
        this.ledList = ledList;
    }

    @NonNull
    @Override
    public LedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_led, parent, false);
        return new LedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LedViewHolder holder, int position) {
        Led led = ledList.get(position);
        holder.tvLedId.setText("LedID: " + led.getDeviceId());
        holder.tvLedState.setText("State: " + (led.isState() ? "On" : "Off"));
        // Xử lý sự kiện bật/tắt LED
        holder.btnLedControl.setOnClickListener(v -> {
            boolean newState = !led.isState(); // Đổi trạng thái LED
            led.setState(newState);           // Cập nhật trạng thái trong danh sách

            // Cập nhật trạng thái LED lên Firebase
            updateLedStateInFirebase(led, holder);



            // Cập nhật giao diện
            holder.tvLedState.setText("State: " + (newState ? "On" : "Off"));
            holder.btnLedControl.setText(newState ? "Turn Off" : "Turn On");
        });
    }



    private void updateLedStateInFirebase(Led led, LedViewHolder holder) {
        DatabaseReference ledRef = FirebaseDatabase.getInstance()
                .getReference("Devices")
                .child("Leds")
                .child(led.getDeviceId());


        ledRef.child("state").setValue(led.isState())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(holder.itemView.getContext(),
                            "LED state updated to " + (led.isState() ? "On" : "Off"),
                            Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi
                    Log.e("Firebase", "Error updating LED state: ", e);
                });
    }

    @Override
    public int getItemCount() {
        return ledList.size();
    }

    public static class LedViewHolder extends RecyclerView.ViewHolder {
        TextView tvLedState, tvLedId;
        Button btnLedControl;

        public LedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLedId = itemView.findViewById(R.id.tvLedId);
            tvLedState = itemView.findViewById(R.id.tvLedState);
            btnLedControl = itemView.findViewById(R.id.btnLedControl);
        }
    }
}
