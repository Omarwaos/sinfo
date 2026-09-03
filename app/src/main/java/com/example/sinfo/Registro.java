package com.example.sinfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Registro extends AppCompatActivity {

    EditText edtApellidosReg, edtNombresReg, edtTelefonoReg, edtDireccionReg, edtEmailReg;

    Button btnGuardar;

    private void loadUI(){

        edtApellidosReg = findViewById(R.id.edtApellidosReg);
        edtNombresReg = findViewById(R.id.edtNombresReg);
        edtTelefonoReg = findViewById(R.id.edtTelefonoReg);
        edtDireccionReg = findViewById(R.id.edtDireccionReg);
        edtEmailReg = findViewById(R.id.edtEmailReg);
        btnGuardar = findViewById(R.id.btnGuardar);
    }

    private boolean validarCampos() {

        EditText[] campos = {
                edtApellidosReg,
                edtNombresReg,
                edtTelefonoReg,
                edtDireccionReg,
                edtEmailReg
        };

        for (EditText campo : campos) {

            if (campo.getText().toString().trim().isEmpty()) {
                campo.setError("Este campo es obligatorio");
                campo.requestFocus();
                return false;
            }
        }

        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        loadUI();

        btnGuardar.setOnClickListener(v -> {
            if (!validarCampos()) {
                return;
            }

            String apellidos = edtApellidosReg.getText().toString().trim();
            String nombres = edtNombresReg.getText().toString().trim();
            String telefono = edtTelefonoReg.getText().toString().trim();
            String direccion = edtDireccionReg.getText().toString().trim();
            String email = edtEmailReg.getText().toString().trim();

            Toast.makeText(v.getContext(),"Registro Guardado",Toast.LENGTH_SHORT).show();

        });
    }
}