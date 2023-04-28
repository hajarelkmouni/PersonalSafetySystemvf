package com.example.personalsafetysystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalsafetysystem.LoginActivity;
import com.example.personalsafetysystem.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    EditText textEmail, textPassword, edtConfirmPassword;
    TextView login;
    String txtFullName, txtEmail, txtMobile, txtPassword, txtConfirmPassword;

    Button btnSignUpAcc;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        login = findViewById(R.id.login);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnSignUpAcc = findViewById(R.id.btnSignUpAcc);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUpAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail = textEmail.getText().toString().trim();
                txtPassword = textPassword.getText().toString().trim();
                txtConfirmPassword = edtConfirmPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(txtEmail)) {
                    if (txtEmail.matches(emailPattern)) {
                        if (!TextUtils.isEmpty(txtPassword)) {
                            if (!TextUtils.isEmpty(txtConfirmPassword)) {
                                if (txtConfirmPassword.equals(txtPassword)) {
                                    SignUpUser();
                                } else {
                                    edtConfirmPassword.setError("Confirm Password and Password should be same.");
                                }
                            } else {
                                edtConfirmPassword.setError("Confirm Password Field can't be empty");
                            }
                        } else {
                            textPassword.setError("Password Field can't be empty");
                        }

                    } else {
                        textEmail.setError("Enter a valid Email Address");
                    }
                } else {
                    textEmail.setError("Email Field can't be empty");
                }


            }
        });

    }

    private void SignUpUser() {

        btnSignUpAcc.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterActivity.this, "Sign Up Successful !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();

                btnSignUpAcc.setVisibility(View.VISIBLE);
            }
        });

    }
}