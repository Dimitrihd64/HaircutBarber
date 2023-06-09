package com.example.haircutbarber3.ui.Citas;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.haircutbarber3.Adapters.CitasAdapter;
import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.R;
import com.example.haircutbarber3.databinding.ActivityListaCitasBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListaCitasActivity extends AppCompatActivity {
    private ActivityListaCitasBinding binding;
    private List<Cita> citas;
    private FirebaseUser user;
    private DatabaseReference citasRef;
    private CitasAdapter adapter;
    private ArrayList<Cita> citasGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaCitasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Firebase config
        user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        citasRef = FirebaseUtils.getDatabase().getReference().child("CitasList");

        //Arrays
        citas = new ArrayList<>();
        citasGlobal = new ArrayList<>();

        //Adapter config
        adapter = new CitasAdapter(this, R.layout.cita_list_model, citas);
        binding.container.setAdapter(adapter);
        binding.container.setLayoutManager(new LinearLayoutManager(this));

        String userEmail = FirebaseUtils.getFirebaseAuth().getCurrentUser().getEmail();

        //Dependiendo del usuario que ha iniciado sesion tendra accesso a ver la lista completa
        //de citas de todos los usuarios o del mismo usuario solamente
        if (userEmail.equalsIgnoreCase("admin@haircutbarber.com")) {
            //admin
            citasRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        citas.clear();
                        GenericTypeIndicator<ArrayList<Cita>> gtiCita = new GenericTypeIndicator<ArrayList<Cita>>() {
                        };
                        citas.addAll(snapshot.getValue(gtiCita));
                        ordenarCitas(citas);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            //usuario normal
            citasRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        citas.clear();
                        GenericTypeIndicator<ArrayList<Cita>> gtiCita = new GenericTypeIndicator<ArrayList<Cita>>() {
                        };
                        citas.addAll(snapshot.getValue(gtiCita));
                        List<Cita> citasFiltered = new ArrayList<Cita>();
                        for (Cita cita : citas) {
                            if (cita.getUsuario().equals(userEmail)) {
                                citasFiltered.add(cita);
                            }
                        }
                        citas.clear();
                        citas.addAll(citasFiltered);
                        ordenarCitas(citas);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    //Todas las citas estaran ordenadas por fecha y hora
    private void ordenarCitas(List<Cita> citas) {
        Collections.sort(citas, new Comparator<Cita>() {
            @Override
            public int compare(Cita cita1, Cita cita2) {
                int resultadoFecha = cita1.getFecha().compareTo(cita2.getFecha());
                if (resultadoFecha != 0) {
                    return resultadoFecha;
                }
                return cita1.getHora().compareTo(cita2.getHora());
            }
        });
        adapter.notifyItemRangeChanged(0, citas.size());
    }

}