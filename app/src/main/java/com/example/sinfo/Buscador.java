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

public class Buscador extends AppCompatActivity {

    EditText edtIDBsc, edtApellidosBsc, edtNombresBsc, edtTelefonoBsc, edtDireccionBsc, edtEmailBsc;

    Button btnBuscar, btnEliminar, btnActualizar, btnReiniciar;

    private void loadUI(){

        edtIDBsc = findViewById(R.id.edtIDBsc);
        edtApellidosBsc = findViewById(R.id.edtApellidosBsc);
        edtNombresBsc = findViewById(R.id.edtNombresBsc);
        edtTelefonoBsc = findViewById(R.id.edtTelefonoBsc);
        edtDireccionBsc = findViewById(R.id.edtDireccionBsc);
        edtEmailBsc = findViewById(R.id.edtEmailBsc);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
    }

    private void limpiar() {
        edtIDBsc.setText("");
        edtApellidosBsc.setText("");
        edtNombresBsc.setText("");
        edtTelefonoBsc.setText("");
        edtDireccionBsc.setText("");
        edtEmailBsc.setText("");

        edtIDBsc.requestFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscador);

        loadUI();

        btnBuscar.setOnClickListener(v -> {

            String codigo = edtIDBsc.getText().toString();

            if (codigo.equals("2")) {
                Toast.makeText(this, "Producto encontrado", Toast.LENGTH_SHORT).show();
            } else if (codigo.isEmpty()) {
                Toast.makeText(this, "Ingresa un número", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                limpiar();
            }
        });

        btnReiniciar.setOnClickListener(v -> {
            limpiar();
            edtIDBsc.setText("");
            Toast.makeText(this, "Formulario reiniciado", Toast.LENGTH_SHORT).show();
        });
    }
}