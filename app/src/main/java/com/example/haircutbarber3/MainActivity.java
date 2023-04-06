package com.example.haircutbarber3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.haircutbarber3.Models.Cita;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;


    private ArrayList<Cita> citasList;
    private FirebaseDatabase database;
    private DatabaseReference refCitas;
    private FirebaseUser user;
    private FirebaseAuth userAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        citasList = new ArrayList<>();

        //Firebase config

        //userAuth = FirebaseAuth.getInstance();
        userAuth = FirebaseUtils.getFirebaseAuth();

        user = FirebaseUtils.getFirebaseAuth().getCurrentUser();

        database = FirebaseDatabase.getInstance("https://haircutbarberdb-default-rtdb.europe-west1.firebasedatabase.app/");


        ///la mierda de firebase va en los fragments
        comprobarEstado();


        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open_nav, R.string.close_nav);
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

                switch (item.getItemId()) {
                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "Esto va tambien", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_info:
                        Toast.makeText(MainActivity.this, "Esto va tambien", Toast.LENGTH_SHORT).show();
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_login:

                        if (user != null) {
                            FirebaseUtils.getFirebaseAuth().signOut();
                        } else {
                            startActivity(new Intent(MainActivity.this, LogInActivity.class));
                        }
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

    private void comprobarEstado() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
                View headerView = binding.navView.getHeaderView(0);

                TextView lbCorreo = headerView.findViewById(R.id.lbCorreoHeader);
                Menu menu = binding.navView.getMenu();
                MenuItem LogIn = menu.findItem(R.id.nav_login);

                if (user != null) {
                    lbCorreo.setText(user.getEmail());
                    LogIn.setIcon(R.drawable.baseline_logout_24);
                    LogIn.setTitle("Cerrar Sesion");

                } else {
                    lbCorreo.setText(R.string.app_name);
                    LogIn.setIcon(R.drawable.baseline_login_24);
                    LogIn.setTitle("Iniciar Sesion");

                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}