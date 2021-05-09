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

public class signUpActivity extends AppCompatActivity {
    private Button button;
    EditText mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Authentication Stuff
        mEmail = findViewById(R.id.emailInput3);
        mPassword = findViewById(R.id.passwordInput);
        mRegisterBtn = findViewById(R.id.signUpButton);
        mLoginBtn = findViewById(R.id.loginButton);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            Intent intent = new Intent( signUpActivity.this, MainActivity2.class);

            intent.putExtra("currentUser", fAuth.getCurrentUser().getEmail());
            startActivity(intent);
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
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

                //progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(signUpActivity.this, "user Created", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent( signUpActivity.this, MainActivity2.class);

                            intent.putExtra("currentUser", email);
                            startActivity(intent);
                        }else{
                        //LEFT OFF ON 12:20 Login & Register Android App Using Firebase 2/4
                            Toast.makeText(signUpActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        button = (Button) findViewById(R.id.switchActivity2);
        button.setOnClickListener(new View.OnClickListener() {

          // Change Activities on button click
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

    }
    //Change Activities
    public void openActivity2() {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}