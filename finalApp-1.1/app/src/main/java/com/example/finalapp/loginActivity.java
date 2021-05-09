package com.example.finalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private Button button;
    EditText mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;

    public String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Authentication Stuff
        mEmail = findViewById(R.id.emailInput);
        mPassword = findViewById(R.id.passwordInputLogin);
        mRegisterBtn = findViewById(R.id.signUpButton);
        mLoginBtn = findViewById(R.id.loginButton);

        fAuth = FirebaseAuth.getInstance();

        button = (Button) findViewById(R.id.switchActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
                openActivity();
            }
        });

        if(fAuth.getCurrentUser() != null){
            Intent intent = new Intent( loginActivity.this, MainActivity2.class);

            intent.putExtra("currentUser", fAuth.getCurrentUser().getEmail());
            startActivity(intent);
        }


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be more than 6 Characters");
                    return;
                }

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(loginActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent( loginActivity.this,MainActivity2.class);

                            intent.putExtra("currentUser", email);
                            startActivity(intent);



                        }else {
                            Toast.makeText(loginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    public void openActivity() {
        Intent intent = new Intent(this, signUpActivity.class);
        startActivity(intent);
    }
}