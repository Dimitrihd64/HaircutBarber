package com.example.haircutbarber3.ui.Citas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.databinding.ActivityTipoServicioBinding;

public class TipoServicioActivity extends AppCompatActivity {
    private ActivityTipoServicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTipoServicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}