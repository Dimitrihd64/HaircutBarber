package com.example.haircutbarber3.ui.Citas;

import android.os.Bundle;

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
import java.util.List;

public class ListaCitasActivity extends AppCompatActivity {
    private ActivityListaCitasBinding binding;
    private List<Cita> citas;
    private FirebaseUser user;
    private DatabaseReference citasRef;
    private CitasAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaCitasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        citasRef = FirebaseUtils.getDatabase().getReference().child("CitasList");

        citas = new ArrayList<>();
        adapter = new CitasAdapter(this, R.layout.cita_list_model, citas);
        binding.container.setAdapter(adapter);
        binding.container.setLayoutManager(new LinearLayoutManager(this));

        String userEmail = FirebaseUtils.getFirebaseAuth().getCurrentUser().getEmail();

        if (userEmail.equalsIgnoreCase("admin@haircutbarber.com")) {
            citasRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        citas.clear();
                        GenericTypeIndicator<ArrayList<Cita>> gtiCita = new GenericTypeIndicator<ArrayList<Cita>>() {
                        };
                        citas.addAll(snapshot.getValue(gtiCita));
                        adapter.notifyItemRangeChanged(0, citas.size());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
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

                        adapter.notifyItemRangeChanged(0, citas.size());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

}