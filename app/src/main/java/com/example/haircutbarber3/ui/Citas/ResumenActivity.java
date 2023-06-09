package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import java.util.Arrays;
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
        refCitas = FirebaseUtils.getDatabase().getReference().child("CitasList");

        //Bundle info
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int Dia = bundle.getInt("Dia");
        int Mes = bundle.getInt("Mes");
        int Año = bundle.getInt("Año");
        Date Hora = (Date) bundle.getSerializable("Hora");
        ArrayList<String> servicios = bundle.getStringArrayList("Servicios");
        double precioTotal = bundle.getDouble("Precio");

        //se almacena en variables toda la info de la cita
        String horaFormat = new SimpleDateFormat("HH:mm").format(Hora);
        String Fecha = Dia + "-" + Mes + "-" + Año;
        String Nombre = "Cita " + Fecha;
        String Usuario = user.getEmail();

        //se le da un id a la cita
        String citaId = refCitas.push().getKey();

        //se crea el objeto cita
        Cita c = new Cita(Usuario, citaId, Nombre, horaFormat, Fecha, servicios, precioTotal);


        String serviciosList = Arrays.toString(c.getServicios().toArray());
        serviciosList = serviciosList.replaceAll("\\[|\\]", "");

        //Se muestra en un resumen la informacion de la cita
        binding.lbFechaCita.setText(c.getFecha());
        binding.lbHoraCita.setText(c.getHora());
        binding.lbServicioCita.setText(serviciosList);
        binding.lbPrecioCita.setText(c.getPrecio() + " €");

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Almacenamos la lista de citas de firebase en un array
        refCitas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    citasList.clear();
                    GenericTypeIndicator<ArrayList<Cita>> gtiCita =
                            new GenericTypeIndicator<ArrayList<Cita>>() {
                            };
                    citasList.addAll(snapshot.getValue(gtiCita));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //en el boton Reservar se añadira la cita al Array de citas y se guardara la lista completa en Firebase
        binding.btReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citasList.add(c);
                refCitas.setValue(citasList);
                Toast.makeText(ResumenActivity.this, "Cita añadida correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}