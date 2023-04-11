package com.example.haircutbarber3.LogIn_SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.MainActivity;
import com.example.haircutbarber3.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseUtils.getFirebaseAuth();


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtEmailLogIn.getText().toString();
                String password = binding.txtPasswdLogIn.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
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

            }
        });

    }

    public Boolean isLogin() {
        return user != null;
    }

    private void doLogIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El login es correcto, y ya tengo la instancia del user
                            user = mAuth.getCurrentUser();
                            Toast.makeText(LogInActivity.this, "Ha iniciado sesion correctamente", Toast.LENGTH_SHORT).show();

                            finish();

                        } else {
                            // El login ha fallado
                            Toast.makeText(LogInActivity.this, "Ha fallado el Login,Registate", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}