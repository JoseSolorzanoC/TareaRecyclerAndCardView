package uteq.appmoviles.tarea2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uteq.appmoviles.tarea2.cardlist.Issue.ListAdapter;
import uteq.appmoviles.tarea2.model.Issue;

public class MainActivity extends AppCompatActivity {

    Button btnSearch;
    TextView txtId;
    List<Issue> elements;
    private static final String URL = "https://revistas.uteq.edu.ec/ws/";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setConfiguration();
    }

    public void setConfiguration() {
        btnSearch = findViewById(R.id.btnBuscar);
        txtId = findViewById(R.id.txtId);
        queue = Volley.newRequestQueue(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    public void fetchData() {
        Gson gson = new Gson();
        String volleyUrl = URL + "issues.php?j_id=" + txtId.getText().toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, volleyUrl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type issueListType = new TypeToken<ArrayList<Issue>>() {
                        }.getType();
                        try {
                            elements = gson.fromJson(response.toString(), issueListType);
                            if (elements.size() > 0) {
                                fillData();
                            } else {
                                Toast.makeText(MainActivity.this, String.format("No existe información asociada al ID=%s", txtId.getText()), Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e) {
                            Toast.makeText(MainActivity.this, String.format("No existe información asociada al ID=%s", txtId.getText()), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Ocurrió un error al obtener la información", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
        queue.add(jsonArrayRequest);
    }

    public void fillData(){
        ListAdapter listAdapter = new ListAdapter(elements, this);
        RecyclerView recyclerView = findViewById(R.id.rvData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}