package com.example.trashmap.helpers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trashmap.MainActivity;
import com.example.trashmap.R;
import com.example.trashmap.authorization.LoginActivity;
import com.example.trashmap.authorization.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OpenWindow extends AppCompatActivity {

    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_window);
        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = myAuth.getCurrentUser();

        Intent intent;
        if(cUser != null){
            Toast.makeText(OpenWindow.this, "User ON", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, MainActivity.class);
        }
        else{
            Toast.makeText(OpenWindow.this, "User OFF", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }
}