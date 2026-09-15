package com.example.sinfo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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


    //1. Objeto que sirva como canal de comunicacion

    RequestQueue requestQueue;

    //2. EndPoint (Dirección que apunta WS)
    //127.0.0.1=Localhost
    private final String URL= "http://127.0.0.1:3000/alumnos";

    private void registrarAlumno(){
        //Habilitar el canal
        requestQueue = Volley.newRequestQueue(this);
        //3. ¿Qué dato necesita el WS? Rpta: JSON
        JSONObject jsonObject = new JSONObject();
        //4. Asignar datos al JSON
        try {
            jsonObject.put("apellidos",edtApellidosReg.getText().toString());
            jsonObject.put("nombres",edtNombresReg.getText().toString());
            jsonObject.put("telefono",edtTelefonoReg.getText().toString());
            jsonObject.put("direccion",edtDireccionReg.getText().toString());
            jsonObject.put("email",edtEmailReg.getText().toString());
        } catch (JSONException e) {
            //enviar mensajes de errores
            Log.e("Error_JSON", e.toString());
            throw new RuntimeException(e);
        }
        //5. ¿Qué metodo utilizaré para enviar los datos? Rpta: POST
        //¿Qué objeto obtengo del WS? - JSON
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String mensaje = jsonObject.getString("message");
                            int id = jsonObject.getInt("id");
                            Toast.makeText(getApplicationContext(),mensaje+ "- ID: "+id, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("Error WS", volleyError.toString());
                        Toast.makeText(getApplicationContext(),"No se pudo grabar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //6. Enviar los datos al WS
        requestQueue.add(jsonObjectRequest);

    }//RegistrarAlumno


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
            registrarAlumno();


        });
    }
}