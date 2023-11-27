package com.cookandroid.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {

    private EditText editTextRegisterEmail;
    private EditText editTextRegisterPassword;
    private EditText editTextConfirmPassword;
    private Button buttonRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_register);

        mAuth = FirebaseAuth.getInstance();

        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 버튼 클릭 시
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = editTextRegisterEmail.getText().toString();
        String password = editTextRegisterPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (!password.equals(confirmPassword)) {
            // 비밀번호와 확인 비밀번호가 일치하지 않을 경우
            Toast.makeText(Register_Activity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공 시
                            Toast.makeText(Register_Activity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register_Activity.this, Login_Activity.class));
                            finish();
                        } else {
                            // 회원가입 실패 시
                            Toast.makeText(Register_Activity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            Exception exception = task.getException();
                            if(exception != null){
                                Log.e("Registration",exception.getMessage());
                            }
                        }
                    }
                });
    }
}