package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

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

        binding.cbLavado.setEnabled(false);
        preciosCheckbox(Dia, Mes, Año, Hora);

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //se establece un precio  cada servicio y se almacena en un array
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


                Intent intent = new Intent(TipoServicioActivity.this, ResumenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Dia", Dia);
                bundle.putInt("Mes", Mes);
                bundle.putInt("Año", Año);
                bundle.putStringArrayList("Servicios", productosList);
                bundle.putSerializable("Hora", Hora);
                bundle.putDouble("Precio", precioTotal);
                intent.putExtras(bundle);
                //si la lista esta vacia no podra acceder a la confirmacion de la cita
                if (productosList.isEmpty()) {
                    Toast.makeText(TipoServicioActivity.this, "Debes escojer al menos un servicio", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                    finish();
                }


            }
        });


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //condiciones de los servicios
        binding.cbBarba.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cbLavado.setEnabled(true);
                } else {
                    binding.cbLavado.setEnabled(false);
                    binding.cbLavado.setChecked(false);
                }
            }
        });
        binding.cbAdulto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cbLavado.setEnabled(true);
                } else {
                    binding.cbLavado.setEnabled(false);
                    binding.cbLavado.setChecked(false);
                }
            }
        });

        binding.cbNino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cbLavado.setEnabled(true);
                } else {
                    binding.cbLavado.setEnabled(false);
                    binding.cbLavado.setChecked(false);
                }
            }
        });


        binding.cbTinte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cbPermanente.setChecked(false);
                    binding.cbPermanente.setEnabled(false);
                    binding.cbLavado.setEnabled(true);
                } else {
                    binding.cbPermanente.setEnabled(true);
                    binding.cbLavado.setEnabled(false);
                    binding.cbLavado.setChecked(false);
                }
            }
        });
        binding.cbPermanente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.cbTinte.setChecked(false);
                    binding.cbTinte.setEnabled(false);
                    binding.cbLavado.setEnabled(true);
                } else {
                    binding.cbTinte.setEnabled(true);
                    binding.cbLavado.setEnabled(false);
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
                    binding.cbLavado.setEnabled(true);


                } else {

                    binding.cbBarba.setEnabled(true);
                    binding.cbAdulto.setEnabled(true);
                    binding.cbLavado.setEnabled(false);
                }
            }
        });


    }
}