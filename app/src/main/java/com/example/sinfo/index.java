package com.example.sinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class index extends AppCompatActivity {

    Button btnListado, btnBuscador, btnRegistro;

    private void loadUI(){
        btnListado = findViewById(R.id.btnListado);
        btnBuscador = findViewById(R.id.btnBuscador);
        btnRegistro = findViewById(R.id.btnRegistro);

    }

    private void openActivity(Class interfaz){
        Intent intent = new Intent(getApplicationContext(),interfaz);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index);

        loadUI();

        btnListado.setOnClickListener(v -> {openActivity(Listado.class);});
        btnBuscador.setOnClickListener(v -> {openActivity(Buscador.class);});
        btnRegistro.setOnClickListener(v -> openActivity(Registro.class));
    }
}