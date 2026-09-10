package com.example.sinfo;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<String> listDatos;
    public AdapterDatos(ArrayList<String> listEntrada) {
        this.listDatos = listEntrada;
    }
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Estamos indicando al RV cual es el XML que servira como plantilla
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderDatos(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }
    @Override
    public int getItemCount() {
        return listDatos.size();
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        //Estos TextView forman parte de la plantilla (item_list.xml)
        TextView txtApeNom,txtDireccion,txtTelefono;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtApeNom = itemView.findViewById(R.id.txtApeNom);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
        }
        public void asignarDatos(String s) {
            txtApeNom.setText(s);
            txtDireccion.setText(s);
            txtTelefono.setText(s);
        }
    }
}
