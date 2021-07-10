package com.ahyeon.chatapp2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";   //태그변수
    private FirebaseAuth mAuth;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        final EditText etId = (EditText)findViewById(R.id.etId);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);    //final로 하거나 밖으로 전역변수로 선언하거나
        progressbar = (ProgressBar)findViewById(R.id.progressbar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();    //long은 길게 보여주기, short은 짧게
                String strEmail = etId.getText().toString();
                String strPassword = etPassword.getText().toString();

                if(strEmail.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please insert Email", Toast.LENGTH_SHORT).show();
                    return;     //이러면 이 밑 코드는 실행이 되지 않음
                }
                if(strPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please insert password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    progressbar.setVisibility(View.GONE);
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);

                                    String strUserEmail = user.getEmail();
                                    String strName = user.getDisplayName();
                                    Log.d(TAG, "strUserEmail: "+strUserEmail+", strName: "+strName);
                                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);  //출발지와 도착지
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressbar.setVisibility(View.GONE);
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = etId.getText().toString();
                String strPassword = etPassword.getText().toString();
                if(strEmail.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please insert Email", Toast.LENGTH_SHORT).show();
                    return;     //이러면 이 밑 코드는 실행이 되지 않음
                }
                if(strPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please insert password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this, "email: "+strId+", pw: "+strPassword, Toast.LENGTH_LONG).show();
                mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    progressbar.setVisibility(View.GONE);
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressbar.setVisibility(View.GONE);
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}
