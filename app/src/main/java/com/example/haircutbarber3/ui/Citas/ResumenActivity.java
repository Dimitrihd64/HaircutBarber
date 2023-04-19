package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.databinding.ActivityResumenBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResumenActivity extends AppCompatActivity {

    private ActivityResumenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResumenBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int Dia = bundle.getInt("Dia");
        int Mes = bundle.getInt("Mes");
        int Año = bundle.getInt("Año");
        Date Hora = (Date) bundle.getSerializable("Hora");
        double precioTotal = bundle.getDouble("Precio");
        String horaFormat = new SimpleDateFormat("HH:mm").format(Hora);

        Cita c = new Cita(horaFormat, Dia, Mes, Año, precioTotal);
        binding.textView15.setText(c.toString());
    }
}