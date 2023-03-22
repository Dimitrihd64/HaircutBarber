package com.example.haircutbarber3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.haircutbarber3.ui.Detalles.DetallesFragment;
import com.example.haircutbarber3.ui.Novedades.NovedadesFragment;
import com.example.haircutbarber3.ui.Servicios.CortesFragment;
import com.example.haircutbarber3.ui.Servicios.ProductosFragment;
import com.example.haircutbarber3.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Esto funciona", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }



}