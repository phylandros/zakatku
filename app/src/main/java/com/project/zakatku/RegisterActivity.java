package com.project.zakatku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText signupNama, signupEmail, signupNoTel, signupUsername, signupPassword, signupConpassword;

    Button btnSignup, btnBack;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupNama = findViewById(R.id.namasignup);
        signupEmail = findViewById(R.id.emailsignup);
        signupNoTel = findViewById(R.id.notelsignup);
        signupUsername = findViewById(R.id.usernamesignup);
        signupPassword = findViewById(R.id.passwordsignup);
        signupConpassword = findViewById(R.id.conpasswordsignup);

        btnSignup = findViewById(R.id.btn_signup);
        btnBack = findViewById(R.id.btn_back);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama = signupNama.getText().toString();
                String email = signupEmail.getText().toString();
                String noTel = signupNoTel.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String conpasswrod = signupConpassword.getText().toString();
                String setrole = "user";

                if (nama.isEmpty()||email.isEmpty()||noTel.isEmpty()||username.isEmpty()||password.isEmpty()||conpasswrod.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Isi Field yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    String userUsername = signupUsername.getText().toString().trim();

                    database = FirebaseDatabase.getInstance("https://zakatku-35bff-default-rtdb.asia-southeast1.firebasedatabase.app");
                    reference = database.getReference("users");
                    Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

                    checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                            Toast.makeText(RegisterActivity.this, "Username sudah terdaftar. Silakan pilih username lain.", Toast.LENGTH_SHORT).show();
                            } else {
                                HelperClass helperClass = new HelperClass(nama, email, noTel, username, password, setrole);
                                reference.child(username).setValue(helperClass);

                                Toast.makeText(RegisterActivity.this,"Register Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(RegisterActivity.this, "Terjadi kesalahan: ", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}