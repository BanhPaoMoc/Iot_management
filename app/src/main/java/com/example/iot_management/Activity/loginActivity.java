package com.example.iot_management.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_management.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    EditText editTextText_mail, editTextText_password;
    Button widget_login_button;
    TextView registertext;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextText_mail = findViewById(R.id.editTextText_mail);
        editTextText_password = findViewById(R.id.editTextText_password);
        fAuth = FirebaseAuth.getInstance();
        widget_login_button = findViewById(R.id.widget_login_button);
        registertext = findViewById(R.id.registertext);

        widget_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextText_mail.getText().toString().trim();
                String password = editTextText_password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    editTextText_mail.setError("Bạn Phải Nhập Email!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editTextText_password.setError("Bạn Cần Phải Nhập Mật Khẩu!");
                    return;
                }
                if (password.length() < 6) {
                    editTextText_password.setError("Mật Khẩu Phải Có Độ Dài > 6 Ký Tự!");
                    return;
                }

                // Authentication with Firebase
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(loginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                            // Lưu trạng thái đăng nhập
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            // Chuyển đến MainActivity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish(); // Kết thúc LoginActivity
                        } else {
                            Toast.makeText(loginActivity.this, "Đăng nhập thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // Set OnClickListener for the register text view
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), registerActivity.class));
            }
        });
    }
}
