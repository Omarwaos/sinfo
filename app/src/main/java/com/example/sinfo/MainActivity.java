package com.example.sinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtUser, edtPassword;

    Button btnLogin;

    private void loadUI(){

        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        loadUI();

        btnLogin.setOnClickListener(v -> {
            String user = edtUser.getText().toString();
            String password = edtPassword.getText().toString();

            if (user.isEmpty()) {
                edtUser.setError("Ingresa tu usuario");
                edtUser.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                edtPassword.setError("Ingresa tu contraseña");
                edtPassword.requestFocus();
                return;
            }

            // 3. Validar credenciales
            if (user.equals("omar") && password.equals("123456")) {
                Intent intent = new Intent(getApplicationContext(), index.class);
                startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}