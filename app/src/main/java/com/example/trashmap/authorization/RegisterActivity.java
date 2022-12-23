package com.example.trashmap.authorization;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trashmap.MainActivity;
import com.example.trashmap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText regEmail, regPass, regName;
    private Button regSignUp;
    private FirebaseAuth myAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();


        regSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regSignUpOnClick();
            }
        });
    }

    private void regSignUpOnClick(){
        if(!TextUtils.isEmpty(regEmail.getText().toString()) &&
                !TextUtils.isEmpty(regPass.getText().toString()) &&
                !TextUtils.isEmpty(regName.getText().toString())) {
            myAuth.createUserWithEmailAndPassword(regEmail.getText().toString(), regPass.getText().toString())
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = myAuth.getCurrentUser();
                            if (task.isSuccessful()) {
                                sendLetterOnEmail();

                                assert user != null;
                                if(user.isEmailVerified()){
                                    Intent intent;
                                    intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(RegisterActivity.this, "Проверьте вашу почту для подтверждения почты", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Не удалось зарегистрироваться", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(RegisterActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendLetterOnEmail(){
        FirebaseUser user = myAuth.getCurrentUser();

        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Проверьте вашу почту для подтверждения почты", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Ошибка отправки письма на почту", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Проверяем, вошёл ли уже пользователь
    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init(){
        regEmail = findViewById(R.id.registerEmail);
        regName = findViewById(R.id.registerName);
        regPass = findViewById(R.id.registerPass);
        regSignUp = findViewById(R.id.registerSignUp);
        myAuth = FirebaseAuth.getInstance();
    }
}