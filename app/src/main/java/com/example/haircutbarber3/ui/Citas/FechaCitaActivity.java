package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.MainActivity;
import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.databinding.ActivityFechaCitaBinding;

import java.util.ArrayList;

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

        binding.btSaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Dia = binding.datepicker.getDayOfMonth();
                int Mes = binding.datepicker.getMonth();
                int Año = binding.datepicker.getYear();

                binding.lbFecha.setText("Dia: " + Dia + " Mes: " + Mes + " Año: " + Año);
                Bundle bundle = new Bundle();

                Intent intent = new Intent(FechaCitaActivity.this, HorasActivity.class);
                bundle.putInt("Dia", Dia);
                bundle.putInt("Mes", Mes);
                bundle.putInt("Año", Año);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });


    }


}