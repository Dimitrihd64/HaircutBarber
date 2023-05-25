package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.MainActivity;
import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.databinding.ActivityFechaCitaBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class FechaCitaActivity extends AppCompatActivity {

    ArrayList<Cita> citasList;

    ActivityFechaCitaBinding binding;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFechaCitaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        citasList = new ArrayList<>();

        //cuando selecione una fecha y acepte se comprobara la fecha si es posterior al dia actual
        binding.btSaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int Dia = binding.datepicker.getDayOfMonth();
                int Mes = binding.datepicker.getMonth();
                int Año = binding.datepicker.getYear();

                comprobarFecha(Dia, Mes, Año);


            }
        });


    }

    //Si la fecha es correcta se enviara juto con un bundle a la actividad HorasActivity
    private void comprobarFecha(int dia, int mes, int año) {
        Calendar fechaActual = Calendar.getInstance();
        Calendar fechaSeleccionada = Calendar.getInstance();
        fechaSeleccionada.set(año, mes, dia);
        if (fechaSeleccionada.compareTo(fechaActual) > 0) {
            mes = mes + 1;
            String Fecha = dia + "-" + mes + "-" + año;
            Bundle bundle = new Bundle();

            Intent intent = new Intent(FechaCitaActivity.this, HorasActivity.class);
            bundle.putString("Fecha", Fecha);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "La Fecha debe ser posterior a hoy", Toast.LENGTH_SHORT).show();


        }
    }


}