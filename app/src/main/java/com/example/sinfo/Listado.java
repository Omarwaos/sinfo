package com.example.sinfo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    ArrayList<String> listaAlumnos = new ArrayList<>();
    RecyclerView recyclerAlumnos;

    private void loadUI(){
        recyclerAlumnos = findViewById(R.id.recyclerAlumnos);

    }

    private void cargarLista(){
        for (int i =1; i<=10; i++){
            listaAlumnos.add("Maria");
            listaAlumnos.add("Juan");
            listaAlumnos.add("Pablo");
            listaAlumnos.add("Santiago");
            listaAlumnos.add("Hugo");
            listaAlumnos.add("Roxana");
            listaAlumnos.add("Licet");
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

        loadUI();
        cargarLista();
        recyclerAlumnos.setLayoutManager(new LinearLayoutManager(this));
        AdapterAlumnos adaptador = new AdapterAlumnos(listaAlumnos);
        recyclerAlumnos.setAdapter(adaptador);
    }
}