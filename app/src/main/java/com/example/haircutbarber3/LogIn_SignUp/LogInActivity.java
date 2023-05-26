package com.example.haircutbarber3.LogIn_SignUp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.R;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseUtils.getFirebaseAuth();
        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Boton LogIn
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

        //tiene la posibilidad de recuperar la contraseña
        binding.txtPasswdRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogContraseña();
            }
        });
        //si no tiene una cuenta registrada podra crearla
        binding.signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));

            }
        });
    }

    private void dialogContraseña() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recuperar contraseña");
        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.passwd_dialog, null);
        builder.setView(view);
        final EditText emailEditText = view.findViewById(R.id.txtEmailReset);

        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LogInActivity.this, "Ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
                } else {
                    resetPassword(email);
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail("dimitrihd64@gmail.com").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LogInActivity.this, "Se ha enviado un correo de recuperacion", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogInActivity.this, "No se pudo enviar el correo electrónico de restablecimiento de contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Metodo que iniciara sesion con las credenciales
    private void doLogIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // El login es correcto, y ya tengo la instancia del user
                    user = mAuth.getCurrentUser();
                    Toast.makeText(LogInActivity.this, "Ha iniciado sesion correctamente", Toast.LENGTH_SHORT).show();

                    finish();

                } else {
                    // El login ha fallado
                    Toast.makeText(LogInActivity.this, "Email o Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}