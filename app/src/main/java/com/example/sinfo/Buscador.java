package com.example.sinfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Buscador extends AppCompatActivity {

    EditText edtIDBsc, edtApellidosBsc, edtNombresBsc, edtTelefonoBsc, edtDireccionBsc, edtEmailBsc;

    Button btnBuscar, btnEliminar, btnActualizar, btnReiniciar;

    RequestQueue requestQueue;

    private final String URL="http://192.168.101.15:3000/alumnos";

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

        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }

    private void validarError(int statusCode, String errorJSON){
        if(statusCode == 404) {
            try {
                JSONObject jsonObject = new JSONObject(errorJSON);
                String mensajeError = jsonObject.getString("message");
                Toast.makeText(getApplicationContext(), mensajeError, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void buscarAlumno(){
        if (edtIDBsc.getText().toString().isEmpty()){
            edtIDBsc.setError("Campo requerido");
            edtIDBsc.requestFocus();
            return;
        }

        requestQueue = Volley.newRequestQueue(this);
        String endPoint = URL + "/" + edtIDBsc.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            edtApellidosBsc.setText(jsonObject.getString("apellidos"));
                            edtNombresBsc.setText(jsonObject.getString("nombres"));
                            edtTelefonoBsc.setText(jsonObject.getString("telefono"));
                            edtDireccionBsc.setText(jsonObject.getString("direccion"));
                            edtEmailBsc.setText(jsonObject.getString("email"));

                            btnActualizar.setEnabled(true);
                            btnEliminar.setEnabled(true);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //MANEJO DE ERRORES
                        //Si el servidor retorna un código 40X (es un error)
                        NetworkResponse response = volleyError.networkResponse;
                        //Validar si existe un código de error
                        if (response != null && response.data != null) {
                            //MÁS IMPORTANTE - código de error
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);

                        }
                    }
                }
        );

        //Meter la carta en el sobre :v
        requestQueue.add(jsonObjectRequest);

    }

    private void actualizarAlumno() {
        // 1. Validar que el ID no esté vacío
        if (edtIDBsc.getText().toString().trim().isEmpty()) {
            edtIDBsc.setError("Debe buscar un alumno primero");
            edtIDBsc.requestFocus();
            return;
        }

        // 2. Construir el JSON con los datos editados
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("apellidos", edtApellidosBsc.getText().toString().trim());
            jsonBody.put("nombres", edtNombresBsc.getText().toString().trim());
            jsonBody.put("telefono", edtTelefonoBsc.getText().toString().trim());
            jsonBody.put("direccion", edtDireccionBsc.getText().toString().trim());
            jsonBody.put("email", edtEmailBsc.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al empaquetar los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Endpoint con el ID del registro a modificar
        String endPoint = URL + "/" + edtIDBsc.getText().toString().trim();

        // 4. Crear la petición PUT
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, // Cambiar a POST si tu API no acepta PUT
                endPoint,
                jsonBody,           // Aquí se envía el cuerpo con los datos modificados
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Alumno actualizado con éxito", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null) {
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Enviar la petición a la cola
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        requestQueue.add(jsonObjectRequest);
    }

    private void confirmarActualizacion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar actualización")
                .setMessage("¿Estás seguro de guardar los cambios para este alumno?")
                .setPositiveButton("Actualizar", (dialog, which) -> {
                    actualizarAlumno();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarAlumno() {
        String id = edtIDBsc.getText().toString().trim();
        if (id.isEmpty()) {
            edtIDBsc.setError("Debe buscar un alumno primero");
            edtIDBsc.requestFocus();
            return;
        }

        String endPoint = URL + "/" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                endPoint,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Alumno eliminado con éxito", Toast.LENGTH_SHORT).show();

                        limpiar();
                        btnActualizar.setEnabled(false);
                        btnEliminar.setEnabled(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null) {
                            int statusCode = response.statusCode;
                            String errorJSON = new String(response.data);
                            validarError(statusCode, errorJSON);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        requestQueue.add(jsonObjectRequest);
    }

    private void confirmarEliminacion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este alumno? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    eliminarAlumno();
                })
                .setNegativeButton("Cancelar", null)
                .show();
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

        btnBuscar.setOnClickListener(v -> {buscarAlumno();});

        btnReiniciar.setOnClickListener(v -> {
            limpiar();
            edtIDBsc.setText("");
            Toast.makeText(this, "Formulario reiniciado", Toast.LENGTH_SHORT).show();
        });

        btnActualizar.setOnClickListener(v->{
            confirmarActualizacion();
        });

        btnEliminar.setOnClickListener(v -> {confirmarEliminacion();});
    }
}