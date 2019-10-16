package com.example.testeapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView campoJson;
    RequestQueue requisicaoVolley;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Leitor de Api's");
        setContentView(R.layout.activity_main);

        campoJson = findViewById(R.id.tv_json_api);
        Button botaoExibe = findViewById(R.id.btn_exibir);
        botaoExibe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessarJson();
            }
        });
    }
    private void acessarJson(){

        requisicaoVolley = Volley.newRequestQueue(this);
        String url = "https://api.myjson.com/bins/1bsm2a";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("materias");

                            for(int i = 0; i< jsonArray.length();i++){
                                JSONObject materia = jsonArray.getJSONObject(i);
                                String nome = materia.getString("nome");
                                int nota = materia.getInt("nota");
                                campoJson.append("Materia: " + nome + " -> "+ nota +"\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requisicaoVolley.add(request);
    }
}
