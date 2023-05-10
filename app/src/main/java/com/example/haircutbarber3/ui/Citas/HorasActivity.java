package com.example.haircutbarber3.ui.Citas;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.MainActivity;
import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.R;
import com.example.haircutbarber3.databinding.ActivityHorasBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HorasActivity extends AppCompatActivity {

    ActivityHorasBinding binding;
    MainActivity mainActivity;
    private List<Cita> citas;
    private List<Date> horasDisponibles;
    private List<Date> horasOcupadas;
    private FirebaseUser user;
    private DatabaseReference citasRef;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHorasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        citasRef = FirebaseUtils.getDatabase().getReference().child("CitasList");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String Fecha = bundle.getString("Fecha");

        citas = new ArrayList<>();
        horasDisponibles = new ArrayList<>();
        horasOcupadas = new ArrayList<>();

        try {
            crearHoras(Fecha);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    private void crearHoras(String Fecha) throws ParseException {

        //pruebaFirebase
        ///*
        citasRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Cita>> gtiCita = new GenericTypeIndicator<ArrayList<Cita>>() {
                };

                if (snapshot.exists()) {
                    citas.clear();
                    citas.addAll(snapshot.getValue(gtiCita));

                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date fechaDia = null;
                    try {
                        fechaDia = formatter.parse(Fecha);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fechaDia);
                    cal.set(Calendar.HOUR_OF_DAY, 12);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);

                    // Lista predefinida de horas disponibles de 12:00 a 17:30 con intervalos de 30 minutos
                    while (cal.get(Calendar.HOUR_OF_DAY) < 18 || (cal.get(Calendar.HOUR_OF_DAY) == 18 && cal.get(Calendar.MINUTE) == 0)) {
                        horasDisponibles.add(cal.getTime());
                        cal.add(Calendar.MINUTE, 30);

                        for (Cita cita : citas) {
                            Date horaCita = null;
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                horaCita = format.parse(cita.getFecha() + " " + cita.getHora());
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }

                            // Comparar la fecha de la cita con la fecha en fechaDia
                            SimpleDateFormat formatFechaDia = new SimpleDateFormat("dd-MM-yyyy");
                            if (formatFechaDia.format(horaCita).equals(formatFechaDia.format(fechaDia))) {
                                if (horasDisponibles.contains(horaCita)) {
                                    horasDisponibles.remove(horaCita);
                                    horasOcupadas.add(horaCita);
                                }
                            }
                        }
                    }

                    String[] componentesFecha = Fecha.split("-");

                    int dia = Integer.parseInt(componentesFecha[0]);
                    int mes = Integer.parseInt(componentesFecha[1]);
                    int año = Integer.parseInt(componentesFecha[2]);


                    crearBotones(dia, mes, año);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date fechaDia = null;
                    try {
                        fechaDia = formatter.parse(Fecha);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fechaDia);
                    cal.set(Calendar.HOUR_OF_DAY, 12);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);

                    while (cal.get(Calendar.HOUR_OF_DAY) < 18 || (cal.get(Calendar.HOUR_OF_DAY) == 18 && cal.get(Calendar.MINUTE) == 0)) {
                        horasDisponibles.add(cal.getTime());
                        cal.add(Calendar.MINUTE, 30);
                    }

                    String[] componentesFecha = Fecha.split("-");

                    int dia = Integer.parseInt(componentesFecha[0]);
                    int mes = Integer.parseInt(componentesFecha[1]);
                    int año = Integer.parseInt(componentesFecha[2]);


                    crearBotones(dia, mes, año);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//*/
        //fin prueba firebase
        /*

         */
    }

    private void crearBotones(int dia, int mes, int año) {

        for (final Date hora : horasDisponibles) {
            Button botonDisponibles = new Button(this);


            botonDisponibles.setText(new SimpleDateFormat("HH:mm").format(hora));

            botonDisponibles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Agregar la hora a la lista de horas ocupadas
                    horasOcupadas.add(hora);
                    horasDisponibles.remove(hora);

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(HorasActivity.this, TipoServicioActivity.class);
                    bundle.putInt("Dia", dia);
                    bundle.putInt("Mes", mes);
                    bundle.putInt("Año", año);
                    bundle.putSerializable("Hora", hora);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    // Actualizar el layout de botones
                    binding.layoutHoras.removeView(v);

                    finish();

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