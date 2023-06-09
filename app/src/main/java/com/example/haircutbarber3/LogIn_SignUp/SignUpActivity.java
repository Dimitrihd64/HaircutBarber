package com.example.haircutbarber3.LogIn_SignUp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //firebase config
        mAuth = FirebaseUtils.getFirebaseAuth();

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Boton SignUp
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.txtEmailSignUp.getText().toString();
                String password = binding.txtPasswdSignUp.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    doSignUp(email, password);
                }

                finish();
            }
        });
    }

    //Metodo que dará de alta al nuevo usuario
    private void doSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    Toast.makeText(SignUpActivity.this, "Error en el Registro", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}