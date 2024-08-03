package com.manga.mangareaderapp.viewmodel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manga.mangareaderapp.R;
import com.manga.mangareaderapp.model.User;

import java.util.Objects;

public class PasswordAct extends AppCompatActivity {
    ImageView ivBack;
    TextInputLayout oldPass;
    TextInputLayout newPass;
    TextInputLayout newPass2;
    Button btnClick;
    String oldPasstxt;
    String newPasstxt;
    String newPass2txt;
    String pass;
    String email; // Giả sử email được lưu trữ trong User

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ivBack = findViewById(R.id.imgBack);
        oldPass = findViewById(R.id.txtOldPass);
        newPass = findViewById(R.id.txtNewPass);
        newPass2 = findViewById(R.id.txtnewPass2);
        btnClick = findViewById(R.id.btnChangePass);
        ivBack.setOnClickListener(v -> finish());
        FirebaseAuth auth;
        FirebaseDatabase database;

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        database.getReference().child("Users").child(Objects.requireNonNull(auth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    pass = user.getPassWord();
                    email = user.getUserName(); // Lấy email từ dữ liệu người dùng
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnClick.setOnClickListener(view -> {
            oldPasstxt = Objects.requireNonNull(oldPass.getEditText()).getText().toString();
            newPasstxt = Objects.requireNonNull(newPass.getEditText()).getText().toString();
            newPass2txt = Objects.requireNonNull(newPass2.getEditText()).getText().toString();

            if (oldPasstxt.equals(pass)) {
                if (newPasstxt.equals(newPass2txt)) {
                    FirebaseUser user = auth.getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPasstxt);

                    // Xác thực lại người dùng
                    user.reauthenticate(credential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Cập nhật mật khẩu trong Firebase Authentication
                            user.updatePassword(newPasstxt).addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    // Cập nhật mật khẩu trong Realtime Database
                                    database.getReference().child("Users").child(auth.getUid()).child("passWord").setValue(newPasstxt);
                                    Toast.makeText(PasswordAct.this, "Mật khẩu đã được cập nhật.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(PasswordAct.this, "Có lỗi xảy ra khi cập nhật mật khẩu.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(PasswordAct.this, "Xác thực không thành công. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(PasswordAct.this, "Xác nhận lại mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(PasswordAct.this, "Mật khẩu cũ không chính xác!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
