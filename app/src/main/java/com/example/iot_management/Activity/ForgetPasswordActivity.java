package com.example.iot_management.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_management.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText editTextText_mail;
    Button widget_reset_button;
    TextView backToLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        // Khởi tạo các view
        editTextText_mail = findViewById(R.id.etEmail);
        widget_reset_button = findViewById(R.id.btnSubmit);
        backToLogin = findViewById(R.id.textViewBackToLogin);
        fAuth = FirebaseAuth.getInstance();

        // Xử lý sự kiện khi nhấn nút "Gửi yêu cầu reset mật khẩu"
        widget_reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextText_mail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTextText_mail.setError("Bạn phải nhập email!");
                    return;
                }

                sendPasswordResetRequest(email);
            }
        });

        // Xử lý sự kiện quay lại màn hình đăng nhập
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPasswordActivity.this, loginActivity.class));
                finish();
            }
        });
    }

    private void sendPasswordResetRequest(String email) {
        fAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgetPasswordActivity.this, "Đã gửi yêu cầu reset mật khẩu tới email của bạn.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgetPasswordActivity.this, loginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
