package com.example.iot_management.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_management.R;
import com.example.iot_management.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    EditText editTextText_username, editTextText_mail, editTextText_phone, editTextText_password;
    Button widget_register_button;
    TextView textlogin;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private static final String TAG = "registerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        editTextText_username = findViewById(R.id.editTextText_username);
        editTextText_mail = findViewById(R.id.editTextText_mail);
        editTextText_phone = findViewById(R.id.editTextText_phone);
        editTextText_password = findViewById(R.id.editTextText_password);
        widget_register_button = findViewById(R.id.widget_register_button);
        textlogin = findViewById(R.id.textlogin);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(this, "User Already" + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
            finish();
        }

        widget_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKyTaiKhoan();
            }
        });

        textlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
            }
        });
    }

    private void dangKyTaiKhoan() {
        String hoTen = editTextText_username.getText().toString().trim();
        String email = editTextText_mail.getText().toString().trim();
        String soDienThoai = editTextText_phone.getText().toString().trim();
        String matKhau = editTextText_password.getText().toString().trim();

        if (hoTen.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || matKhau.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, matKhau)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Lấy thông tin người dùng vừa đăng ký
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        if (firebaseUser != null) {
                            // Lưu thông tin bổ sung vào Realtime Database
                            String userId = firebaseUser.getUid();
                            User user = new User(hoTen, email, soDienThoai, matKhau);
                            Log.d(TAG, "User created: " + user);
                            // Đảm bảo mDatabase đã được khởi tạo
                            if (mDatabase != null) {
                                // Đảm bảo mDatabase trỏ đúng đến nhánh "Users"
                                DatabaseReference userRef = mDatabase.child("Users").child(userId);
                                userRef.setValue(user)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Log.d(TAG, "User data saved to database");
                                                Toast.makeText(registerActivity.this, "Đăng ký thành công và lưu vào Realtime Database!", Toast.LENGTH_SHORT).show();
                                                // Điều hướng đến MainActivity sau khi đăng ký thành công
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                finish(); // Đóng Activity đăng ký
                                            } else {
                                                // Hiển thị thông báo lỗi nếu không lưu được thông tin
                                                Log.e(TAG, "Error saving user data: " + task1.getException().getMessage());
                                                Toast.makeText(registerActivity.this, "Lỗi khi lưu thông tin người dùng: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e(TAG, "Database connection error: " + e.getMessage());
                                            Toast.makeText(registerActivity.this, "Lỗi kết nối Database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Log.e(TAG, "Database reference is null");
                                Toast.makeText(registerActivity.this, "Database reference bị null!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Log.e(TAG, "Registration failed: " + task.getException().getMessage());
                        Toast.makeText(registerActivity.this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
