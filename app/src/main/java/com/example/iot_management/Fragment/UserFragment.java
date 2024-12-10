package com.example.iot_management.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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


public class UserFragment extends Fragment {

    private TextView tvUserName, tvUserEmail, tvUserRole;
    private ImageView ivUserAvatar;
    private View llAbout, llTerms;
    private Button btnLogout;

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

        // Đặt tên, email, và vai trò người dùng
        tvUserName.setText("Nguyễn Văn A");
        tvUserEmail.setText("nguyenvana@example.com");
        tvUserRole.setText("Quản trị viên");

        // Sự kiện click cho mục Giới thiệu
        llAbout.setOnClickListener(v -> {
            // Mở trang Giới thiệu
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/about"));
            startActivity(intent);
        });

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
}
