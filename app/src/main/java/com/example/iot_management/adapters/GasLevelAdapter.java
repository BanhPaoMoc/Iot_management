package com.example.iot_management.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.models.GasLevel;

import java.util.List;

public class GasLevelAdapter extends RecyclerView.Adapter<GasLevelAdapter.GasLevelViewHolder> {
    private List<GasLevel> gasLevelList;

    public GasLevelAdapter(List<GasLevel> gasLevelList) {
        this.gasLevelList = gasLevelList;
    }

    @NonNull
    @Override
    public GasLevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gas_level, parent, false);
        return new GasLevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GasLevelViewHolder holder, int position) {
        GasLevel gasLevel = gasLevelList.get(position);
        holder.tvGasSensorId.setText("ID: " + gasLevel.getDeviceId() );
        holder.tvGasSensor.setText("Gas Sensor: " + gasLevel.getGas_sensor());
    }

    @Override
    public int getItemCount() {
        return gasLevelList.size();
    }

    public static class GasLevelViewHolder extends RecyclerView.ViewHolder {
        TextView tvGasSensor, tvGasSensorId;

        public GasLevelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGasSensor = itemView.findViewById(R.id.tvGasSensor);
            tvGasSensorId = itemView.findViewById(R.id.tvGasSensorId);
        }
    }
}
