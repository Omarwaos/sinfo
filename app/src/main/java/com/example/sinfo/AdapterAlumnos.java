package com.example.sinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAlumnos extends RecyclerView.Adapter<AdapterAlumnos.ViewHolderDatos>{

    ArrayList<Alumno> listAlumnos;

    public AdapterAlumnos(ArrayList<Alumno> listDatos){
        this.listAlumnos = listDatos;
    }

    @NonNull
    @Override
    public AdapterAlumnos.ViewHolderDatos onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull AdapterAlumnos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listAlumnos.get(position));
    }

    @Override
    public int getItemCount() {
        return listAlumnos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        // Declaramos los 4 TextViews del ítem
        TextView txtNombre, txtTelefono, txtDireccion, txtEmail;

        public ViewHolderDatos(@NonNull View itemView){
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtEmail = itemView.findViewById(R.id.txtEmail);
        }

        public void asignarDatos(Alumno alumno){
            // 1. Mostrar Nombre y Apellido
            String nombreCompleto = alumno.getApellidos() + " " + alumno.getNombres();
            txtNombre.setText(nombreCompleto);

            // 2. Mostrar datos del objeto Alumno
            txtTelefono.setText("Teléfono: " + alumno.getTelefono());
            txtDireccion.setText("Dirección: " + alumno.getDireccion());
            txtEmail.setText("Email: " + alumno.getEmail());
        }

    }
}