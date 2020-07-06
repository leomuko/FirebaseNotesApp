package com.example.firebasenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText mUserEmail;
    private EditText mUserPassword;
    private TextView mRegisterText;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUserEmail = findViewById(R.id.register_email_editText);
        mUserPassword = findViewById(R.id.register_password_editText);
        mRegisterButton = findViewById(R.id.register_button);

        mRegisterText = findViewById(R.id.register_text);

        mAuth = FirebaseAuth.getInstance();

        mRegisterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new  Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performUserRegister();
            }
        });
    }
    private void performUserRegister() {
        String email = mUserEmail.getText().toString();
        String password = mUserPassword.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }else if(password.isEmpty()){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("Register","User created");
                            Toast.makeText(getBaseContext(),"User account created successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Log.d("Register", "User not created");
                            Toast.makeText(getBaseContext(), "User account not created successfully. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}