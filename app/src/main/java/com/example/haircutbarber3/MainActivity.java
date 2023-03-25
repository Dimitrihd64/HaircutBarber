package com.example.haircutbarber3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.haircutbarber3.databinding.ActivityMainBinding;
import com.example.haircutbarber3.ui.Detalles.DetallesFragment;
import com.example.haircutbarber3.ui.Novedades.NovedadesFragment;
import com.example.haircutbarber3.ui.Servicios.CortesFragment;
import com.example.haircutbarber3.ui.Servicios.ProductosFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;


    private FirebaseDatabase database;
    private DatabaseReference refCitas;
    private FirebaseUser user;
    private FirebaseAuth userAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Firebase config
        //userAuth=FirebaseAuth.getInstance();

        //user = FirebaseAuth.getInstance().getCurrentUser();

        //database = FirebaseDatabase.getInstance("https://haircutbarberdb-default-rtdb.europe-west1.firebasedatabase.app/");
        //refCitas = database.getReference(user.getUid()).child("Citas");





        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.open_nav,R.string.close_nav);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        replaceFragment(new NovedadesFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.news:
                    replaceFragment(new NovedadesFragment());

                    break;
                case R.id.details:
                    replaceFragment(new DetallesFragment());
                    break;
                case R.id.haircuts:
                    replaceFragment(new CortesFragment());

                    break;
                case R.id.products:
                    replaceFragment(new ProductosFragment());
                    break;
            }
            return true;
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "Esto va tambien", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_info:
                        Toast.makeText(MainActivity.this, "Esto va tambien", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_login:
                        logIn();
                }


                return true;
            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FechaCitaActivity.class));


            }
        });

    }

    private void logIn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vistaDialog = LayoutInflater.from(this).inflate(R.layout.login_alert_dialog, null);
        builder.setTitle("Iniciar Sesion");
        builder.setView(vistaDialog);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView email = vistaDialog.findViewById(R.id.txtEmailAlertLogIn);
                TextView password = vistaDialog.findViewById(R.id.txtPasswdAlertLogIn);

                if (!email.getText().toString().isEmpty()&& !password.getText().toString().isEmpty()) {
                    doLogIn(email,password);
                }else{
                    Toast.makeText(MainActivity.this, "Rellena los campos", Toast.LENGTH_SHORT).show();
                }
                


            }


        });
        builder.create().show();


    }

    private void doLogIn(TextView email, TextView password) {
        Toast.makeText(this, "AQUI LLEGO", Toast.LENGTH_SHORT).show();
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }



}