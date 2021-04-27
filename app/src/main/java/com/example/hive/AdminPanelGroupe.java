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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.ComptePackage.AdminPanel;
import com.example.hive.ComptePackage.DataAdapterTopicAbo;
import com.example.hive.ComptePackage.RecyclerViewCompte;
import com.example.hive.LoadPackage.HomeFragment;
import com.example.hive.javaClasses.Role;
import com.example.hive.javaClasses.Topic;
import com.example.hive.javaClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminPanelGroupe extends AppCompatActivity {

    private String nomTopic;
    private int idUser, idCategorie;
    private ArrayList<String> listeRoles;
    private String rolePlusHaut;
    private StringRequest RequestOfJSonArray ;
    private RequestQueue requestQueue ;
    private String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/recup_utilisateur_topic.php";
    private TextView createurTV;
    private RecyclerView adminsRV, membresRV;
    private RecyclerViewListeUt recyclerViewListeA, recyclerViewListeM;
    private RecyclerView.LayoutManager layoutManagerOfrecyclerView, layoutManagerOfrecyclerView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_groupe);

        createurTV = (TextView) findViewById(R.id.admin_panel_groupe_nom_createur);

        nomTopic = getIntent().getExtras().getString("nomTopic");
        idUser = getIntent().getExtras().getInt("idUser");
        listeRoles = getIntent().getExtras().getStringArrayList("listeRoles");
        idCategorie = getIntent().getExtras().getInt("idCategorie");
        rolePlusHaut = HomeFragment.gererRole(listeRoles);

        adminsRV = (RecyclerView) findViewById(R.id.admin_panel_recyclerview_admin);
        membresRV = (RecyclerView) findViewById(R.id.admin_panel_recyclerview_membre);

        adminsRV.setHasFixedSize(true);
        membresRV.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(this);
        adminsRV.setLayoutManager(layoutManagerOfrecyclerView);
        membresRV.setLayoutManager(layoutManagerOfrecyclerView2);

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
                            Log.d("RORO", e.toString());
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminPanelGroupe.this, error.toString(), Toast.LENGTH_SHORT).show();
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


        requestQueue = Volley.newRequestQueue(AdminPanelGroupe.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONObject object) throws JSONException {
        ArrayList<String> listeAdmins = new ArrayList<String>();
        ArrayList<String> listeMembres = new ArrayList<String>();
        JSONArray createurJSON = object.getJSONArray("Créateur");
        JSONArray adminsJSON = object.getJSONArray("Admin");
        JSONArray membresJSON = object.getJSONArray("Membre");

        String createur = createurJSON.getString(0);
        for(int i = 0; i<adminsJSON.length(); i++) {
            try {
                listeAdmins.add(adminsJSON.getString(i));

            } catch (JSONException e) {

                e.printStackTrace();
            }

        }


        for(int i = 0; i<membresJSON.length(); i++) {
            try {
                listeMembres.add(membresJSON.getString(i));

            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        createurTV.setText(createur);
        recyclerViewListeA = new RecyclerViewListeUt(listeAdmins, rolePlusHaut, "Admin");
        recyclerViewListeM = new RecyclerViewListeUt(listeMembres, rolePlusHaut, "Membre");

        adminsRV.setAdapter(recyclerViewListeA);
        membresRV.setAdapter(recyclerViewListeM);

    }
}