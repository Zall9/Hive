package com.example.hive.AdminPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.LoadPackage.DataAdapter;
import com.example.hive.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPanelPost extends AppCompatActivity {

    private String nomTopic;
    private int idCategorie;
    private TextView nbPostTV, nbLikeTV, nbArchiveTV;
    private StringRequest RequestOfJSonArray ;
    private RequestQueue requestQueue ;
    private String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/recup_info_topic.php";
    private List<DataAdapter> ListOfdataAdapter, ListOfdataAdapter2;
    private RecyclerViewPostAdmin recyclerViewadapter, recyclerViewadapter2;
    private RecyclerView recyclerViewPost, recyclerViewPostAR;
    private RecyclerView.LayoutManager layoutManagerOfrecyclerView, layoutManagerOfrecyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_post);

        nomTopic = getIntent().getExtras().getString("nomTopic");
        idCategorie = getIntent().getExtras().getInt("idCategorie");

        nbPostTV = (TextView) findViewById(R.id.admin_panel_post_nbPost_value);
        nbLikeTV = (TextView) findViewById(R.id.admin_panel_post_nbLike_value);
        nbArchiveTV = (TextView) findViewById(R.id.admin_panel_post_nbArchive_value);

        recyclerViewPost = (RecyclerView) findViewById(R.id.admin_panel_post_recyclerview_post);
        recyclerViewPostAR = (RecyclerView) findViewById(R.id.admin_panel_post_recyclerview_archive);

        recyclerViewPost.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        recyclerViewPost.setLayoutManager(layoutManagerOfrecyclerView);

        recyclerViewPostAR.setHasFixedSize(true);
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(this);
        recyclerViewPostAR.setLayoutManager(layoutManagerOfrecyclerView2);

        JSON_HTTP_CALL();
    }



    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new StringRequest(Request.Method.POST, HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            ParseJSonResponse(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminPanelPost.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nomTopic", nomTopic);
                params.put("idCategorie", String.valueOf(idCategorie));
                return params;
            }
        };


        requestQueue = Volley.newRequestQueue(AdminPanelPost.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONObject object) throws JSONException {

        JSONArray infoTopicJSON = object.getJSONArray("infoTopic");
        JSONArray listePost = object.getJSONArray("listePost");
        JSONArray listePostArchive = object.getJSONArray("listePostA");

        nbPostTV.setText(infoTopicJSON.getString(0));
        nbLikeTV.setText(infoTopicJSON.getString(1));
        nbArchiveTV.setText(infoTopicJSON.getString(2));

        ListOfdataAdapter = new ArrayList<>();
        for(int i=0; i<listePost.length(); i++){
            DataAdapter dataAdapter = new DataAdapter();

            JSONObject json = null;

            json = listePost.getJSONObject(i);
            dataAdapter.setIdPost(json.getInt("idPost"));
            dataAdapter.setImageUrl(json.getString("image_path"));
            dataAdapter.setImageNomAuteur(json.getString("nomAuteur"));
            dataAdapter.setImagePrenomAuteur(json.getString("prenomAuteur"));
            dataAdapter.setImagenbLike(json.getString("nbLike"));
            dataAdapter.setImageTitle(json.getString("image_name"));

            ListOfdataAdapter.add(dataAdapter);
        }
        ListOfdataAdapter2 = new ArrayList<>();
        for(int i=0; i<listePostArchive.length(); i++){
            DataAdapter dataAdapter2 = new DataAdapter();

            JSONObject json = null;

            json = listePostArchive.getJSONObject(i);
            dataAdapter2.setIdPost(json.getInt("idPost"));
            dataAdapter2.setImageUrl(json.getString("image_path"));
            dataAdapter2.setImageNomAuteur(json.getString("nomAuteur"));
            dataAdapter2.setImagePrenomAuteur(json.getString("prenomAuteur"));
            dataAdapter2.setImagenbLike(json.getString("nbLike"));
            dataAdapter2.setImageTitle(json.getString("image_name"));

            ListOfdataAdapter2.add(dataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewPostAdmin(ListOfdataAdapter, false, this);

        recyclerViewadapter2 = new RecyclerViewPostAdmin(ListOfdataAdapter2, true, this);

        recyclerViewPost.setAdapter(recyclerViewadapter);

        recyclerViewPostAR.setAdapter(recyclerViewadapter2);

    }
}
