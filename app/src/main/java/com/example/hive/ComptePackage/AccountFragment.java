package com.example.hive.ComptePackage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.LoadPackage.HomeFragment;
import com.example.hive.R;
import com.example.hive.javaClasses.Role;
import com.example.hive.javaClasses.Topic;
import com.example.hive.javaClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {

    private TextView nomTV, prenomTV, nbLikeTV, nbPostTV;
    private String nom, prenom, nbLike, nbPost;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    private StringRequest RequestOfJSonArray ;
    private String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/recup_topic_abonne.php";
    private RequestQueue requestQueue ;
    private User user;
    private RecyclerViewCompte recyclerViewadapter;
    private DataAdapterTopicAbo GetDataATA;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        user = (User)getActivity().getIntent().getExtras().getSerializable("User");
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        nomTV = (TextView) rootView.findViewById(R.id.user_nom);

        prenomTV = (TextView) rootView.findViewById(R.id.user_prenom);

        nbLikeTV = (TextView) rootView.findViewById(R.id.user_nbLike);

        nbPostTV = (TextView) rootView.findViewById(R.id.user_nbPost);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_topic_abonne);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);
        JSON_HTTP_CALL();

        return rootView;
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
                        Toast.makeText(getActivity(),   error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                User user = (User)getActivity().getIntent().getExtras().getSerializable("User");
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", String.valueOf(user.getIdUser()));

                return params;
            }
        };


        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(RequestOfJSonArray);
    }


    public void ParseJSonResponse(JSONObject object) throws JSONException {

        JSONArray listeTopicRoleJSON = object.getJSONArray("listeTopicRole");
        int nbLike =  object.getInt("nbLike");
        int nbPost =  object.getInt("nbPost");
        ArrayList<String[]> listeTopicRoles = new ArrayList<String[]>();
        ArrayList<ArrayList<String>> listeDeRoles = new ArrayList<ArrayList<String>>();
        for(int i = 0; i<listeTopicRoleJSON.length(); i++) {

            GetDataATA = new DataAdapterTopicAbo();

            JSONObject json = null;
            try {

                json = listeTopicRoleJSON.getJSONObject(i);
                String nomTopic = json.getString("nomTopic");
                JSONArray listeRoleJSON = json.getJSONArray("Role");

                ArrayList<String> listeRole = new ArrayList<String>();
                for(int j=0; j<listeRoleJSON.length(); j++){
                    listeRole.add(listeRoleJSON.getString(j));

                }
                listeDeRoles.add(listeRole);
                Role r = new Role(HomeFragment.gererRole(listeRole), user.idUser);
                Topic t = new Topic(nomTopic);
                String [] tempo = new String[2];
                tempo[0] = t.getNomTopic();
                tempo[1] = r.getNomRole();
                listeTopicRoles.add(tempo);


            } catch (JSONException e) {

                e.printStackTrace();
            }

        }
        GetDataATA.setNbLike(nbLike);
        GetDataATA.setNbPost(nbPost);
        GetDataATA.setListeTopicRole(listeTopicRoles);


        nomTV.setText(user.getNom());
        prenomTV.setText(user.getPrenom());
        nbPostTV.setText(String.valueOf(nbPost));
        nbLikeTV.setText(String.valueOf(nbLike));
        recyclerViewadapter = new RecyclerViewCompte(GetDataATA, getActivity(), getActivity(), listeDeRoles);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}
