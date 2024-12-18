package com.example.iot_management.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.models.Led;

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
    }

    @Override
    public int getItemCount() {
        return ledList.size();
    }

    public static class LedViewHolder extends RecyclerView.ViewHolder {
        TextView tvLedState, tvLedId;

        public LedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLedId = itemView.findViewById(R.id.tvLedId);
            tvLedState = itemView.findViewById(R.id.tvLedState);
        }
    }
}
