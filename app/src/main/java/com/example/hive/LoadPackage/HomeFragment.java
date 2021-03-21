package com.example.hive.LoadPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.DescriptionCategorie;
import com.example.hive.PostActivity;
import com.example.hive.R;
import com.example.hive.javaClasses.Role;
import com.example.hive.javaClasses.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {
    List<DataAdapter> ListOfdataAdapter;
    RecyclerView recyclerView;
    String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/img_display.php";
    String Image_Name_JSON = "title";
    String Image_URL_JSON = "image";
    String Nom_Auteur_JSON = "nomAuteur";
    String Prenom_Auteur_JSON = "prenomAuteur";
    String Topic_JSON = "topic";
    String Categorie_JSON = "categorie";
    String nbLike_JSON = "nbLike";
    String listeRole_JSON = "listeRole";
    // Liste des atributs des Posts

    StringRequest RequestOfJSonArray ;
    RequestQueue requestQueue ;

    View view ;

    int RecyclerViewItemPosition ;

    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    RecyclerViewAdapter recyclerViewadapter;

    ArrayList<String> ImageTitleNameArrayListForClick;
    ArrayList<String> ImageTitleQuanArrayListForClick;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ImageTitleNameArrayListForClick = new ArrayList<>();
        ImageTitleQuanArrayListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);
        JSON_HTTP_CALL();

        // Implementing Click Listener on RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);

                    Intent intent = new Intent(getActivity(), PostActivity.class);
                    //intent.putExtra("nomCategorie", categorieClique.toString());
                    intent.putExtra("nomAuteur", recyclerViewadapter.getNomAuteur(RecyclerViewItemPosition));
                    intent.putExtra("prenomAuteur", recyclerViewadapter.getPrenomAuteur(RecyclerViewItemPosition));
                    intent.putExtra("nomPost", recyclerViewadapter.getNomPost(RecyclerViewItemPosition));
                    intent.putExtra("nomCategorie", recyclerViewadapter.getCategorie(RecyclerViewItemPosition));
                    intent.putExtra("nomTopic", recyclerViewadapter.getTopic(RecyclerViewItemPosition));
                    intent.putExtra("urlImage", recyclerViewadapter.getImageUrl(RecyclerViewItemPosition));
                    intent.putExtra("nbLike", recyclerViewadapter.getNbLike(RecyclerViewItemPosition));
                    User user = (User)getActivity().getIntent().getExtras().getSerializable("User");
                    intent.putExtra("idUser", user.getIdUser());
                    intent.putExtra("Role", recyclerViewadapter.getRole(RecyclerViewItemPosition));
                    startActivity(intent);



                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return rootView;
    }

    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new StringRequest(Request.Method.POST, HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jsonArray = null;
                        Log.d("OUI", response);
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

    public void ParseJSonResponse(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapter GetDataAdapter2 = new DataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);

                GetDataAdapter2.setImageTitle(json.getString(Image_Name_JSON));

                // Adding image title name in array to display on RecyclerView click event.
                //TODO a modifer pour quand on clique sur l'image ca ouvre une nouvelle activé
                ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
                Log.i("URL_IMAGE", json.getString(Image_URL_JSON));
                GetDataAdapter2.setImageUrl(json.getString(Image_URL_JSON));
                GetDataAdapter2.setImageNomAuteur(json.getString(Nom_Auteur_JSON));
                GetDataAdapter2.setImagePrenomAuteur(json.getString(Prenom_Auteur_JSON));
                GetDataAdapter2.setImageTopic(json.getString(Topic_JSON));
                GetDataAdapter2.setImageCategorie(json.getString(Categorie_JSON));
                GetDataAdapter2.setImagenbLike(json.getString(nbLike_JSON));

                JSONArray jsonArrayRole = json.getJSONArray(listeRole_JSON);
                ArrayList<String> listeRole = new ArrayList<String>();
                for(int j=0; j<jsonArrayRole.length(); j++){
                    String role = null;
                    role = jsonArrayRole.getString(j);
                    listeRole.add(role);
                }

                String rolePlusHaut = gererRole(listeRole);
                GetDataAdapter2.setImageRole(rolePlusHaut);

            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }


    public String gererRole(ArrayList<String> listeRole){
        //(Dieu) >Créateur > Admin > Membre
        //Pour Wayntal -> le prof loufoque
        String rolePlusHaut ="";
        for (String r: listeRole) {
            //Créateur - Admin - Membre
            if(r.equals("Créateur")){
                rolePlusHaut = "Créateur";
            }else if(r.equals("Admin") && !rolePlusHaut.equals("Créateur")){
                rolePlusHaut = "Admin";
            }else if(r.equals("Membre") && (!rolePlusHaut.equals("Admin") && !rolePlusHaut.equals("Créateur"))){
                rolePlusHaut = "Membre";
            }

        }
        return  rolePlusHaut;
    }
}


