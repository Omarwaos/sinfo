package com.example.sinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAlumnos extends RecyclerView.Adapter<AdapterAlumnos.ViewHolderDatos>{

    ArrayList<String> listAlumnos;

    public AdapterAlumnos(ArrayList<String> listDatos){
        this.listAlumnos = listDatos;
    }

    @NonNull
    @Override
    public AdapterAlumnos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAlumnos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listAlumnos.get(position));

    }

    @Override
    public int getItemCount() {
        return listAlumnos.size();
    }

    //En esta subclase, gestionaremos cada vista que se renderiza en RV
    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        TextView dato;
        public ViewHolderDatos(@NonNull View itemView){
            super(itemView);
            dato = itemView.findViewById(R.id.txtNombre);
        }

        public void asignarDatos(String s){
            dato.setText(s);

        }

    }
}
