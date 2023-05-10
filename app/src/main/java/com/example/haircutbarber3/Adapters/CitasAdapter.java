package com.example.haircutbarber3.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haircutbarber3.Firebase.FirebaseUtils;
import com.example.haircutbarber3.Models.Cita;
import com.example.haircutbarber3.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitaVH> {
    private final Context context;
    private final int resource;
    private final List<Cita> citas;

    public CitasAdapter(Context context, int resource, List<Cita> citas) {
        this.context = context;
        this.resource = resource;
        this.citas = citas;
    }

    @NonNull
    @Override
    public CitasAdapter.CitaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View citaView = LayoutInflater.from(context).inflate(resource, null);
        citaView.setLayoutParams(
                new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new CitaVH(citaView);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasAdapter.CitaVH holder, int position) {

        holder.lbFecha.setText(citas.get(position).getFecha());
        holder.lbHora.setText(citas.get(position).getHora());
        String email = citas.get(position).getUsuario();
        String username = email.split("@")[0];
        holder.lbCliente.setText(username);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarResumen(holder.getAdapterPosition()).show();
            }
        });

    }

    private Dialog mostrarResumen(int adapterPosition) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.detalles_cita_view);

        Button btAceptar = dialog.findViewById(R.id.btAceptar);
        Button btEliminar = dialog.findViewById(R.id.btEliminar);
        TextView lbFecha = dialog.findViewById(R.id.lbFechaCita);
        TextView lbHora = dialog.findViewById(R.id.lbHoraCita);
        TextView lbServicio = dialog.findViewById(R.id.lbServicioCita);
        TextView lbPrecio = dialog.findViewById(R.id.lbPrecioCita);


        lbFecha.setText(citas.get(adapterPosition).getFecha());
        lbHora.setText(citas.get(adapterPosition).getHora());
        lbServicio.setText(citas.get(adapterPosition).getServicios().toString());
        lbPrecio.setText(String.valueOf(citas.get(adapterPosition).getPrecio()));


        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                citas.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                actualizarListaFirebase(citas);
                dialog.dismiss();
            }
        });


        return dialog;
    }

    private void actualizarListaFirebase(List<Cita> citas) {
        DatabaseReference ref = FirebaseUtils.getDatabase().getReference().child("CitasList");
        ref.setValue(citas);
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }


    public class CitaVH extends RecyclerView.ViewHolder {

        TextView lbFecha, lbCliente, lbHora;

        public CitaVH(@NonNull View itemView) {
            super(itemView);
            lbFecha = itemView.findViewById(R.id.lbFechaAdapter);
            lbHora = itemView.findViewById(R.id.lbHoraAdapter);
            lbCliente = itemView.findViewById(R.id.lbClienteAdapter);


        }
    }
}
