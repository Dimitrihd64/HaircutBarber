package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.databinding.ActivityResumenBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        ArrayList<String> servicios = bundle.getStringArrayList("Servicios");
        double precioTotal = bundle.getDouble("Precio");
        String horaFormat = new SimpleDateFormat("HH:mm").format(Hora);
        String Nombre = "Cita " + Dia + "-" + Mes + "-" + Año;

        Cita c = new Cita(Nombre, horaFormat, Dia, Mes, Año, servicios, precioTotal);
        binding.lbNombreCita.setText("Cita " + c.getDia() + " - " + c.getMes() + " - " + c.getAño());
        binding.lbFechaCita.setText("Fecha : " + c.getDia() + " - " + c.getMes() + " - " + c.getAño());
        binding.lbHoraCita.setText("Hora : " + c.getHora());
        binding.lbServicioCita.setText("Servicios : " + c.getServiciosList().toString());
        binding.lbPrecioCita.setText("Precio Total : " + c.getPrecio());

    }
}