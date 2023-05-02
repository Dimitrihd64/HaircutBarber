package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.databinding.ActivityTipoServicioBinding;

import java.util.ArrayList;
import java.util.Date;

public class TipoServicioActivity extends AppCompatActivity {
    private ActivityTipoServicioBinding binding;
    private ArrayList<String> productosList;
    private double precioTotal = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTipoServicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productosList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int Dia = bundle.getInt("Dia");
        int Mes = bundle.getInt("Mes");
        int Año = bundle.getInt("Año");
        Date Hora = (Date) bundle.getSerializable("Hora");

        preciosCheckbox(Dia, Mes, Año, Hora);

    }


    private void preciosCheckbox(int Dia, int Mes, int Año, Date Hora) {

        double precioCorteAdulto = 10.0, precioCorteNiño = 8.0, precioPeloYBarba = 15.0, precioBarba = 7.0, precioTinte = 30.0, precioPermanente = 40.0, precioLavado = 3.0;

        binding.btPedirCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precioTotal = 0.0;
                if (binding.cbAdulto.isChecked()) {
                    precioTotal += precioCorteAdulto;
                    productosList.add("Corte Adulto");
                }
                if (binding.cbNino.isChecked()) {
                    precioTotal += precioCorteNiño;
                    productosList.add("Corte Niño");
                }
                if (binding.cbPeloBarba.isChecked()) {
                    precioTotal += precioPeloYBarba;
                    productosList.add("Pelo y Barba");
                }
                if (binding.cbBarba.isChecked()) {
                    precioTotal += precioBarba;
                    productosList.add("Corte Barba");
                }
                if (binding.cbTinte.isChecked()) {
                    precioTotal += precioTinte;
                    productosList.add("Tinte");
                }
                if (binding.cbPermanente.isChecked()) {
                    precioTotal += precioPermanente;
                    productosList.add("Permanente");
                }
                if (binding.cbLavado.isChecked()) {
                    precioTotal += precioLavado;
                    productosList.add("+ Lavado");
                }
                binding.lbPrecioFinal.setText(String.valueOf(precioTotal));

                Intent intent = new Intent(TipoServicioActivity.this, ResumenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Dia", Dia);
                bundle.putInt("Mes", Mes);
                bundle.putInt("Año", Año);
                bundle.putStringArrayList("Servicios", productosList);
                bundle.putSerializable("Hora", Hora);
                bundle.putDouble("Precio", precioTotal);
                intent.putExtras(bundle);
                startActivity(intent);

                finish();
            }
        });


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        binding.cbTinte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cbPermanente.setChecked(false);
                    binding.cbPermanente.setEnabled(false);
                } else {
                    binding.cbPermanente.setEnabled(true);
                }
            }
        });
        binding.cbPermanente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cbTinte.setChecked(false);
                    binding.cbTinte.setEnabled(false);
                } else {
                    binding.cbTinte.setEnabled(true);
                }

            }
        });


        binding.cbPeloBarba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.cbBarba.setEnabled(false);
                    binding.cbBarba.setChecked(false);
                    binding.cbAdulto.setEnabled(false);
                    binding.cbAdulto.setChecked(false);


                } else {

                    binding.cbBarba.setEnabled(true);
                    binding.cbAdulto.setEnabled(true);
                }
            }
        });


    }
}