package com.example.haircutbarber3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.databinding.ActivityHorasBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HorasActivity extends AppCompatActivity {

    ActivityHorasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHorasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int Dia = bundle.getInt("Dia");
        int Mes = bundle.getInt("Mes");
        int Año = bundle.getInt("Año");


        List<Cita> citas = new ArrayList<>();

        List<Date> horasDisponibles = new ArrayList<>();
        List<Date> horasOcupadas = new ArrayList<>();

        Date fechaDia = new Date(Dia, Mes, Año);

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaDia);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        while (cal.get(Calendar.HOUR_OF_DAY) < 18) { // Hora de fin de las citas
            Date horaActual = (Date) cal.getTime();

            // Comprobar si hay una cita programada para esta hora
            boolean horaOcupada = false;
            for (Cita cita : citas) {
                if (cita.getFecha().equals(horaActual)) {
                    horaOcupada = true;
                    break;
                }
            }
            if (horaOcupada) {
                horasOcupadas.add(horaActual);
            } else {
                horasDisponibles.add(horaActual);
            }
            cal.add(Calendar.MINUTE, 30);
        }

        StringBuilder sb = new StringBuilder();
        for (Date hora : horasOcupadas) {
            sb.append(new SimpleDateFormat("HH:mm").format(hora)).append("\n");
        }
        binding.textView3.setText(sb.toString());
    }
}