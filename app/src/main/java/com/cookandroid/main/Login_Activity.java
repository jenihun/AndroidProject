package com.cookandroid.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Login_Activity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Firebase Authentication을 사용하여 로그인 시도
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // 로그인 성공
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // uid를 얻어옴
                                    String uid = user.getUid();

                                    // FCM 토큰 얻어오기
                                    FirebaseMessaging.getInstance().getToken()
                                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                                @Override
                                                public void onComplete(@NonNull Task<String> task) {
                                                    if (task.isSuccessful()) {
                                                        String fcmToken = task.getResult();

                                                        // Firebase 실시간 데이터베이스에 매핑 정보 저장
                                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uid_fcm_mapping");
                                                        databaseReference.child(uid).setValue(fcmToken)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        // 매핑 정보 저장 성공
                                                                        Toast.makeText(Login_Activity.this, "UID와 FCM 토큰이 매핑되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        updateUI(user);
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        // 매핑 정보 저장 실패
                                                                        Toast.makeText(Login_Activity.this, "매핑 정보를 저장하는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                                        updateUI(null);
                                                                    }
                                                                });
                                                    } else {
                                                        // FCM 토큰 얻어오기 실패
                                                        Toast.makeText(Login_Activity.this, "FCM 토큰을 얻어오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                                                        updateUI(null);
                                                    }
                                                }
                                            });
                                } else {
                                    // 로그인 실패
                                    Toast.makeText(Login_Activity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // 로그인 성공 시 다음 액티비티로 이동 또는 필요한 작업 수행
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            intent.putExtra("USER_EMAIL", user.getEmail());
            startActivity(intent);
            finish();  // 현재 액티비티를 종료
        }
    }
}