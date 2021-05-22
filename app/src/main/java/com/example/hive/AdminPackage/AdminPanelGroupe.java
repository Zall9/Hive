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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.LoadPackage.HomeFragment;
import com.example.hive.R;

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
    private TextView createurTV, adminTV, membreTV, banniTV;
    private RecyclerView adminsRV, membresRV, banniRV;
    private RecyclerViewListeUt recyclerViewListeA, recyclerViewListeM, recyclerViewListeB;
    private RecyclerView.LayoutManager layoutManagerOfrecyclerView, layoutManagerOfrecyclerView2, layoutManagerOfrecyclerView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_groupe);

        createurTV = (TextView) findViewById(R.id.admin_panel_groupe_nom_createur);
        adminTV = (TextView) findViewById(R.id.admin_panel_groupe_admin);
        membreTV = (TextView) findViewById(R.id.admin_panel_groupe_membre);
        banniTV = (TextView) findViewById(R.id.admin_panel_groupe_banni);

        nomTopic = getIntent().getExtras().getString("nomTopic");
        idUser = getIntent().getExtras().getInt("idUser");
        listeRoles = getIntent().getExtras().getStringArrayList("listeRoles");
        idCategorie = getIntent().getExtras().getInt("idCategorie");
        rolePlusHaut = HomeFragment.gererRole(listeRoles);

        adminsRV = (RecyclerView) findViewById(R.id.admin_panel_recyclerview_admin);
        membresRV = (RecyclerView) findViewById(R.id.admin_panel_recyclerview_membre);
        banniRV = (RecyclerView) findViewById(R.id.admin_panel_recyclerview_banni);

        adminsRV.setHasFixedSize(true);
        membresRV.setHasFixedSize(true);
        banniRV.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        layoutManagerOfrecyclerView2 = new LinearLayoutManager(this);
        layoutManagerOfrecyclerView3 = new LinearLayoutManager(this);
        adminsRV.setLayoutManager(layoutManagerOfrecyclerView);
        membresRV.setLayoutManager(layoutManagerOfrecyclerView2);
        banniRV.setLayoutManager(layoutManagerOfrecyclerView3);

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
        ArrayList<String> listeBanni = new ArrayList<String>();
        ArrayList<Integer> listeAdminsId = new ArrayList<Integer>();
        ArrayList<Integer> listeMembreId = new ArrayList<Integer>();
        ArrayList<Integer> listeBanniId = new ArrayList<Integer>();
        JSONArray createurJSON = object.getJSONArray("Créateur");
        JSONArray adminsJSON = object.getJSONArray("Admin");
        JSONArray membresJSON = object.getJSONArray("Membre");
        JSONArray banniJSON = object.getJSONArray("Banni");
        JSONArray adminsIdJSON = object.getJSONArray("AdminId");
        JSONArray membreIdJSON = object.getJSONArray("MembreId");
        JSONArray banniIDJSON = object.getJSONArray("BanniId");


        String createur = createurJSON.getString(0);
        adminTV.setText("Admins : "+String.valueOf(adminsIdJSON.length()));
        membreTV.setText("Membres : "+String.valueOf(membresJSON.length()));
        banniTV.setText("Membre Banni : "+String.valueOf(banniJSON.length()));
        for(int i = 0; i<adminsJSON.length(); i++) {
            try {
                listeAdmins.add(adminsJSON.getString(i));
                listeAdminsId.add(adminsIdJSON.getInt(i));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
        for(int i = 0; i<membresJSON.length(); i++) {
            try {
                listeMembres.add(membresJSON.getString(i));
                listeMembreId.add(membreIdJSON.getInt(i));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
        for(int i = 0; i<banniJSON.length(); i++) {
            try {
                listeBanni.add(banniJSON.getString(i));
                listeBanniId.add(banniIDJSON.getInt(i));
            } catch (JSONException e) {

                e.printStackTrace();
            }

        }

        createurTV.setText(createur);
        recyclerViewListeA = new RecyclerViewListeUt(listeAdmins, rolePlusHaut, "Admin", listeAdminsId, idCategorie, nomTopic, this, this);
        recyclerViewListeM = new RecyclerViewListeUt(listeMembres, rolePlusHaut, "Membre", listeMembreId, idCategorie, nomTopic, this, this);
        recyclerViewListeB = new RecyclerViewListeUt(listeBanni, rolePlusHaut, "Banni", listeBanniId, idCategorie, nomTopic, this, this);

        adminsRV.setAdapter(recyclerViewListeA);
        membresRV.setAdapter(recyclerViewListeM);
        banniRV.setAdapter(recyclerViewListeB);

    }
}