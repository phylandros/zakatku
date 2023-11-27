package com.project.zakatku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView txtSignUp, txtForgotPassword;

    Button btnLogin;

    EditText signinUsername, signinPassowrd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signinUsername = findViewById(R.id.usernamelogin);
        signinPassowrd = findViewById(R.id.passwordlogin);
        txtForgotPassword = findViewById(R.id.lupapsswd);
        txtSignUp = findViewById(R.id.txtsignup);
        btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername()|!validatePassword()){

                } else {
                    checkUser();
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public Boolean validateUsername(){
        String val = signinUsername.getText().toString();
        if (val.isEmpty()){
            signinUsername.setError("Username Kosong.");
            return false;
        } else {
            signinUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = signinPassowrd.getText().toString();
        if (val.isEmpty()){
            signinPassowrd.setError("Password Kosong.");
            return false;
        } else {
            signinPassowrd.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = signinUsername.getText().toString().trim();
        String userPassword = signinPassowrd.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://zakatku-35bff-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    signinUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (Objects.equals(passwordFromDB, userPassword)){
                        signinUsername.setError(null);

                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String displayName = snapshot.child(userUsername).child("nama").getValue(String.class);
                        String setrole = snapshot.child(userUsername).child("setrole").getValue(String.class);
                        editor.putString("userId", userUsername);
                        editor.putString("displayName", displayName);
                        editor.putString("setrole", setrole);
                        editor.apply();


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);


                    } else {
                        signinPassowrd.setError("Password Salah.");
                        signinPassowrd.requestFocus();
                    }
                } else {
                    signinUsername.setError("Username Tidak ditemukan.");
                    signinUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed() {

    }
}