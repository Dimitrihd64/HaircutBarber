package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.databinding.ActivityResumenBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResumenActivity extends AppCompatActivity {

    private ActivityResumenBinding binding;
    private DatabaseReference refCitas;
    private FirebaseUser user;
    private ArrayList<Cita> citasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResumenBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        citasList = new ArrayList<>();

        user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        refCitas = FirebaseUtils.getDatabase().getReference(user.getEmail().replace(".", "_")).child("Citas");

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

        binding.lbFechaCita.setText(c.getDia() + " - " + c.getMes() + " - " + c.getAño());
        binding.lbHoraCita.setText(c.getHora());
        binding.lbServicioCita.setText(c.getServiciosList().toString());
        binding.lbPrecioCita.setText(c.getPrecio() + " €");

        refCitas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    citasList.clear();
                    GenericTypeIndicator<ArrayList<Cita>> gtiCita =
                            new GenericTypeIndicator<ArrayList<Cita>>() {
                            };
                    citasList.addAll(snapshot.getValue(gtiCita));

                    for (Cita c : citasList) {
                        Log.d("Cita Añadida", "onDataChange: " + c.toString());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.btReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citasList.add(c);
                refCitas.setValue(citasList);
            }
        });

    }
}