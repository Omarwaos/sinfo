package com.example.sinfo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    private RecyclerView recyclerAlumnos;
    private RequestQueue requestQueue;

    private final String URL = "http://127.0.0.1:3000/alumnos";

    private void loadUI() {
        recyclerAlumnos = findViewById(R.id.recyclerAlumnos);
        recyclerAlumnos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void cargarLista() {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        renderizarLista(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Listado.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // ¡Fundamental! Si no agregas el request a la cola, nunca se ejecuta
        requestQueue.add(jsonArrayRequest);
    }

    private void renderizarLista(JSONArray jsonArray) {
        try {
            ArrayList<Alumno> listaAlumnos = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.optInt("id", 0);
                String apellidos = jsonObject.optString("apellidos", "");
                String nombres = jsonObject.optString("nombres", "");
                String telefono = jsonObject.optString("telefono", "");
                String direccion = jsonObject.optString("direccion", "");
                String email = jsonObject.optString("email", "");

                Alumno alumno = new Alumno(apellidos, nombres, telefono, direccion);
                alumno.setId(id);
                alumno.setEmail(email);

                listaAlumnos.add(alumno);
            }

            AdapterAlumnos adapter = new AdapterAlumnos(listaAlumnos);
            recyclerAlumnos.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al procesar datos del servidor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listado);

        loadUI();
        cargarLista();
    }
}