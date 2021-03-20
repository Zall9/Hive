package com.example.hive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.LoadPackage.DataAdapter;
import com.example.hive.LoadPackage.RecyclerViewAdapter;
import com.example.hive.javaClasses.Categorie;
import com.example.hive.javaClasses.Topic;
import com.example.hive.javaClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DescriptionCategorie extends AppCompatActivity {

    private StringRequest RequestOfJSonArray;
    private String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/recup_topic.php";

    private String Nom_Topic_JSON = "nomTopic";
    private String est_Abonne_JSON = "estAbonne";
    private RecyclerView recList;
    private RecyclerViewTopic rvt;

    private String nomCategorie;
    private TextView nomCategorieTW;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_categorie);
        recList = (RecyclerView) findViewById(R.id.recyclerview_topic);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        nomCategorie = getIntent().getStringExtra("nomCategorie");
        nomCategorieTW = (TextView) findViewById(R.id.nom_categorie_topic_list);
        nomCategorieTW.setText(nomCategorie);
        JSON_HTTP_CALL();
    }



    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new StringRequest(Request.Method.POST, HTTP_JSON_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ParseJSonResponse(jsonArray);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DescriptionCategorie.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                User user = (User)getIntent().getExtras().getSerializable("User");
                //nomCategorie = getIntent().getExtras().getString("nomCatagorie");

                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", String.valueOf(user.getIdUser()));
                params.put("nomCategorie", nomCategorie);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array){
        ArrayList<Topic> mesTopics = new ArrayList<Topic>();
        for(int i = 0; i<array.length(); i++) {

            Topic GetTopics = new Topic();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetTopics.setNomTopic(json.getString(Nom_Topic_JSON));

                GetTopics.setEstAbonne(json.getBoolean(est_Abonne_JSON));
                // Adding image title name in array to display on RecyclerView click event.

            } catch (JSONException e) {

                e.printStackTrace();
            }
            mesTopics.add(GetTopics);
        }

        rvt = new RecyclerViewTopic(mesTopics, this);

        recList.setAdapter(rvt);


    }




}