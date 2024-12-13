package com.example.iot_management.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.models.Dht;

import java.util.List;

public class DhtAdapter extends RecyclerView.Adapter<DhtAdapter.DhtViewHolder> {
    private List<Dht> dhtList;

    public DhtAdapter(List<Dht> dhtList) {
        this.dhtList = dhtList;
    }

    @NonNull
    @Override
    public DhtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dht, parent, false);
        return new DhtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DhtViewHolder holder, int position) {
        Dht dht = dhtList.get(position);
        holder.tvHumidity.setText("Humidity: " + dht.getHumidity());
        holder.tvTemperature.setText("Temperature: " + dht.getTemperature());
    }

    @Override
    public int getItemCount() {
        return dhtList.size();
    }

    public static class DhtViewHolder extends RecyclerView.ViewHolder {
        TextView tvHumidity, tvTemperature;

        public DhtViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
        }
    }
}