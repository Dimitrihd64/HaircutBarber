package com.example.haircutbarber3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.LogIn_SignUp.LogInActivity;
import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.databinding.ActivityMainBinding;
import com.example.haircutbarber3.ui.Citas.FechaCitaActivity;
import com.example.haircutbarber3.ui.Citas.ListaCitasActivity;
import com.example.haircutbarber3.ui.Detalles.DetallesFragment;
import com.example.haircutbarber3.ui.Info.InfoActivity;
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
        userAuth = FirebaseUtils.getFirebaseAuth();
        user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        database = FirebaseUtils.getDatabase();
        //comprobamos el estado del user (si hay logIn o no)
        comprobarEstado();

        //Config Menu lateral
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open_nav, R.string.close_nav);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Cambiamos la pantalla de inicio al fragment Novedades
        replaceFragment(new NovedadesFragment());

        //Definimos que fragment se mostrara en cada boton del menu de navegación
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


        //Config interno para el menu lateral y
        //definimos que actividad se mostrara en cada boton del menu lateral

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_citas:
                        if (user != null) {
                            startActivity(new Intent(MainActivity.this, ListaCitasActivity.class));
                            binding.drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                        } else {
                            Toast.makeText(MainActivity.this, "Debes iniciar sesion", Toast.LENGTH_SHORT).show();
                        }
                    case R.id.nav_info:
                        startActivity(new Intent(MainActivity.this, InfoActivity.class));
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

        //Botón principal donde se accederá a la creación de citas
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FechaCitaActivity.class));
            }
        });
    }

    //Aquí se comprueba el LogIn y se hacen los cambios
    //necesarios visuales y funcionales dependiendo de si esta o no Logueado
    private void comprobarEstado() {
        MenuItem menuItem = binding.navView.getMenu().findItem(R.id.nav_citas);
        ImageView ivAvatar = binding.navView.getHeaderView(0).findViewById(R.id.ivAvatar);
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
                    binding.fab.setEnabled(true);
                    menuItem.setVisible(true);
                    ivAvatar.setImageDrawable(getDrawable(R.drawable.logo));


                } else {
                    lbCorreo.setText(R.string.app_name);
                    LogIn.setIcon(R.drawable.baseline_login_24);
                    LogIn.setTitle("Iniciar Sesion");
                    binding.fab.setEnabled(false);
                    menuItem.setVisible(false);
                    Toast.makeText(MainActivity.this, "Inicia sesion para añadir citas", Toast.LENGTH_SHORT).show();
                    ivAvatar.setImageDrawable(getDrawable(R.drawable.avatardefault_92824));
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);

    }

    //funcion para remplazar los fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}