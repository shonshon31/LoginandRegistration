package com.example.loginandregistration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextAddress, editPhone;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    Button buttonReg;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextAddress = findViewById(R.id.address);
        editPhone = findViewById(R.id.contact);
        buttonReg = (Button) findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progress2);
        textView = findViewById(R.id.logLink);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                progressBar.setVisibility(View.VISIBLE);
                String email, password, addie, phone;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                addie = String.valueOf(editTextAddress.getText());
                phone = String.valueOf(editPhone.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Registration.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Registration.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(addie)){
                    Toast.makeText(Registration.this, "Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(Registration.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Your Account has been Created.",
                                            Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                            finish();

                                } else {
                                    Toast.makeText(Registration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


    }
}