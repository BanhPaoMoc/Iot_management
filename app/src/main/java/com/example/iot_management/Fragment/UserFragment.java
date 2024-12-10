package com.example.iot_management.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.iot_management.Activity.loginActivity;
import com.example.iot_management.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserFragment extends Fragment {
    private static final String TAG = "UserFragment";

    private TextView tvUserName, tvUserEmail, tvUserRole;
    private ImageView ivUserAvatar;
    private View llAbout, llTerms;
    private Button btnLogout;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Ánh xạ các view
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        tvUserRole = view.findViewById(R.id.tvUserRole);
        ivUserAvatar = view.findViewById(R.id.ivUserAvatar);
        llAbout = view.findViewById(R.id.llAbout);
        llTerms = view.findViewById(R.id.llTerms);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Khởi tạo Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Sự kiện click cho mục Giới thiệu
        llAbout.setOnClickListener(v -> {
            // Mở trang Giới thiệu
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/about"));
            startActivity(intent);
        });

        // Lấy thông tin người dùng hiện tại
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            loadUserData(userId);
        }

        // Sự kiện click cho mục Điều khoản và Chính sách
        llTerms.setOnClickListener(v -> {
            // Mở trang Điều khoản và Chính sách
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/terms"));
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            // Đăng xuất người dùng khỏi Firebase
            FirebaseAuth.getInstance().signOut();

            // Chuyển hướng về màn hình đăng nhập
            Intent intent = new Intent(getActivity(), loginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            // Kết thúc hoạt động hiện tại
            getActivity().finish();
        });

        return view;
    }

    private void loadUserData(String userId) {
        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Lấy thông tin từ snapshot
                    String hoTen = snapshot.child("hoTen").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    Boolean role = snapshot.child("role").getValue(Boolean.class);

                    // Hiển thị thông tin lên giao diện
                    tvUserName.setText(hoTen != null ? "Họ tên: " + hoTen : "N/A");
                    tvUserEmail.setText(email != null ? "Email:" +email : "N/A");
                    tvUserRole.setText(role != null && role ? "Vai trò: Quản trị viên" : "Vài trò: Người dùng");
                } else {
                    Log.e(TAG, "User data not found for ID: " + userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
