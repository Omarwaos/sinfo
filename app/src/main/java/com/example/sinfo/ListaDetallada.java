package com.example.sinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaDetallada extends AppCompatActivity {

    ArrayList<String> listaAlumnos = new ArrayList<>();

    //Adaptador (trasferir informacion obtenida del WS > Lista > RV)
    AdapterDatos adapterDatos;

    RecyclerView recyclerAlumnos_D;

    //Canal de comunicacion
    RequestQueue requestQueue;

    //EndPoint del WS

    private final String URL = "http://192.168.101.15:3000/alumnos";

    private void loadUI(){recyclerAlumnos_D = findViewById(R.id.recyclerAlumnos_D);}

    private void cargarLista() {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.i("Datos_obtenidos",jsonArray.toString());
                        String DatosCompletos = ""; //Apellidos + nombres
                        for(int i = 0; i< jsonArray.length();i++){
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                DatosCompletos += jsonObject.getString("apellidos");
                                DatosCompletos += " ";
                                DatosCompletos += jsonObject.getString("nombres");
                                listaAlumnos.add(DatosCompletos);
                                DatosCompletos = "";

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            adapterDatos.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error en el WS",volleyError.toString());
                    }
                }
        );

        // ¡Fundamental! Si no agregas el request a la cola, nunca se ejecuta
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_detallada);
        loadUI();

        adapterDatos = new AdapterDatos(listaAlumnos);
        recyclerAlumnos_D.setLayoutManager(new LinearLayoutManager(this));
        recyclerAlumnos_D.setAdapter(adapterDatos);
        cargarLista();

    }
}











