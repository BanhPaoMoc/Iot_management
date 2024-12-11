//package com.example.iot_management.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.iot_management.R;
//import com.example.iot_management.models.Device;
//
//import java.util.List;
//
//public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
//    private List<Device> deviceList;
//
//    public DeviceAdapter(List<Device> deviceList) {
//        this.deviceList = deviceList;
//    }
//
//    @Override
//    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
//        return new DeviceViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(DeviceViewHolder holder, int position) {
//        Device device = deviceList.get(position);
//        holder.name.setText(device.getType());
//
////        if (device instanceof LedDevice) {
////            LedDevice ledDevice = (LedDevice) device;
////            holder.state.setText(ledDevice.getState() ? "Bật" : "Tắt");
////            holder.toggleButton.setVisibility(View.VISIBLE);
////            holder.toggleButton.setOnClickListener(v -> {
////                // Toggle LED State
////                ledDevice.setState(!ledDevice.getState());
////                // Cập nhật Firebase khi trạng thái thay đổi
////            });
////        } else if (device instanceof DhtDevice) {
////            DhtDevice dhtDevice = (DhtDevice) device;
////            holder.state.setText("Nhiệt độ: " + dhtDevice.getTemperature() + "°C, Độ ẩm: " + dhtDevice.getHumidity() + "%");
////            holder.toggleButton.setVisibility(View.GONE);
////        } else if (device instanceof GasSensorDevice) {
////            GasSensorDevice gasDevice = (GasSensorDevice) device;
////            holder.state.setText("Khí gas: " + (gasDevice.isGasDetected() ? "Phát hiện" : "Không phát hiện") + ", Mức độ: " + gasDevice.getGasLevel());
////            holder.toggleButton.setVisibility(View.GONE);
////        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return deviceList.size();
//    }
//
//    public class DeviceViewHolder extends RecyclerView.ViewHolder {
//        TextView name, state;
//        Button toggleButton;
//
//        public DeviceViewHolder(View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.device_name);
//            state = itemView.findViewById(R.id.device_state);
//            toggleButton = itemView.findViewById(R.id.toggle_button);
//        }
//    }
//}


package com.example.iot_management.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iot_management.R;
import com.example.iot_management.models.Device;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> deviceList;

    public DeviceAdapter(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device device = deviceList.get(position);

        // Hiển thị ID thiết bị
        holder.tvDeviceId.setText(device.getId());

        if (device.getType().equals("led")) {
            // Hiển thị trạng thái LED
            holder.tvState.setText("State: " + (device.isState() ? "ON" : "OFF"));
            holder.btnToggleState.setVisibility(View.VISIBLE);

            // Thiết lập sự kiện click cho nút điều khiển LED
            holder.btnToggleState.setOnClickListener(v -> {
                device.setState(!device.isState()); // Đảo trạng thái
                notifyItemChanged(position); // Cập nhật lại item chỉ thay đổi
            });

            // Ẩn các thông tin không liên quan đến LED
            holder.tvTemperature.setVisibility(View.GONE);
            holder.tvHumidity.setVisibility(View.GONE);
            holder.tvGasLevel.setVisibility(View.GONE);

        } else if (device.getType().equals("dht")) {
            // Hiển thị nhiệt độ và độ ẩm
            holder.tvTemperature.setText("Temperature: " + device.getTemperature() + "°C");
            holder.tvHumidity.setText("Humidity: " + device.getHumidity() + "%");

            // Ẩn các thông tin không liên quan đến DHT
            holder.tvState.setVisibility(View.GONE);
            holder.btnToggleState.setVisibility(View.GONE);
            holder.tvGasLevel.setVisibility(View.GONE);

        } else if (device.getType().equals("gas_sensor")) {
            // Hiển thị mức gas
            holder.tvGasLevel.setText("Gas Level: " + device.getGasLevel());

            // Ẩn các thông tin không liên quan đến Gas Sensor
            holder.tvState.setVisibility(View.GONE);
            holder.btnToggleState.setVisibility(View.GONE);
            holder.tvTemperature.setVisibility(View.GONE);
            holder.tvHumidity.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView tvDeviceId, tvState, tvTemperature, tvHumidity, tvGasLevel;
        Button btnToggleState;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceId = itemView.findViewById(R.id.tvDeviceId);
            tvState = itemView.findViewById(R.id.tvState);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvGasLevel = itemView.findViewById(R.id.tvGasLevel);
            btnToggleState = itemView.findViewById(R.id.btnToggleState);
        }
    }
}
