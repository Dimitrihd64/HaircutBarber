package com.example.haircutbarber3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView email = findViewById(R.id.txtEmailLogIn);
                TextView password = findViewById(R.id.txtPasswdLogIn);

                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    doLogIn(email, password);
                } else {
                    Toast.makeText(LogInActivity.this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this, "Contraseña olvidada", Toast.LENGTH_SHORT).show();
            }
        });
        binding.signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                finish();
            }
        });

    }

    private void doLogIn(TextView email, TextView password) {
        Toast.makeText(LogInActivity.this, "ESTO FUNCIONA", Toast.LENGTH_SHORT).show();
    }


}