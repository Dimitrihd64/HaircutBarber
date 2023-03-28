package com.example.haircutbarber3;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    MainActivity mainActivity;

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
        cal.set(Calendar.HOUR_OF_DAY, 12);
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


        /*Para el textView3(era la prueba de funcionamiento)
        StringBuilder sb = new StringBuilder();
        for (Date hora : horasOcupadas) {
            sb.append(new SimpleDateFormat("HH:mm").format(hora)).append("\n");
        }
        binding.textView3.setText(sb.toString());
        */

        for (final Date hora : horasDisponibles) {
            Button botonDisponibles = new Button(this);
            botonDisponibles.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            botonDisponibles.setText(new SimpleDateFormat("HH:mm").format(hora));

            botonDisponibles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cita cita = new Cita();
                    cita.setFecha(hora);

                    // Agregar la cita a la lista de citas programadas
                    citas.add(cita);

                    // Agregar la hora a la lista de horas ocupadas
                    horasOcupadas.add(hora);
                    horasDisponibles.remove(hora);


                    // Actualizar el layout de botones
                    binding.layoutHoras.removeView(v);

                    for (Date hora : horasOcupadas) {
                        Log.d("HORASSSSSSSSSSSSS", "horas:" + hora.toString());

                    }
                }
            });


            binding.layoutHoras.addView(botonDisponibles);

            GradientDrawable gd = new GradientDrawable();
            gd.setColor(getResources().getColor(R.color.yellow));
            gd.setStroke(10, getResources().getColor(R.color.white));
            gd.setCornerRadius(10);
            botonDisponibles.setBackground(gd);


        }


    }
}