package com.example.hive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.javaClasses.Categorie;
import com.example.hive.javaClasses.User;
import com.example.hive.utils.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategorieFragment extends Fragment {
    ListView view;
    Activity monActivite;
    ArrayList<Categorie> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categorie, container, false);
        view = rootView.findViewById(R.id.list_view);




        recupCategorie(getActivity(), new VolleyCallBack() {
            @Override
            public void onSuccess(ArrayList<Categorie> result) {
                categories = result;
                Log.d("CATEGORIE","Dans on success");
                List<HashMap<String, String>> liste = new ArrayList<HashMap<String,String>>();
                HashMap<String, String> hashCategorie;

                for (Categorie c : categories){
                    hashCategorie = new HashMap<String, String>();
                    Log.d("CATEGORIE", c.toString());
                    hashCategorie.put("text1",c.toString());
                    liste.add(hashCategorie);

                }

                ListAdapter adapter = new SimpleAdapter(getActivity(),
                        //Valeurs à insérer
                        liste,
                        /*
                         * Layout de chaque élément (là, il s'agit d'un layout par défaut
                         * pour avoir deux textes l'un au-dessus de l'autre, c'est pourquoi on
                         * n'affiche que le nom et le numéro d'une personne)
                         */
                        android.R.layout.simple_list_item_1,
                        /*
                         * Les clés des informations à afficher pour chaque élément :
                         *  - la valeur associée à la clé « text1 » sera la première information
                         *  - la valeur associée à la clé « text2 » sera la seconde information
                         */
                        new String[] {"text1"},
                        /*
                         * Enfin, les layouts à appliquer à chaque widget de notre élément
                         * (ce sont des layouts fournis par défaut) :
                         *  - la première information appliquera le layout « android.R.id.text1 »
                         *  - la seconde information appliquera le layout « android.R.id.text2 »
                         */
                        new int[] {android.R.id.text1});
                //Pour finir, on donne à la ListView le SimpleAdapter
                view.setAdapter(adapter);

                view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Categorie categorieClique = categories.get(position);
                        Intent intent = new Intent(getActivity(), DescriptionCategorie.class);
                        intent.putExtra("nomCategorie", categorieClique.toString());
                        Log.d("CATE", categorieClique.toString());
                        User user = (User)getActivity().getIntent().getExtras().getSerializable("User");
                        intent.putExtra("User", user);
                        startActivity(intent);
                    }
                });

            }
        });

        Log.d("CATEGORIE","Apres on success");

        return rootView;
    }


    public static void recupCategorie(Activity monActivite, final VolleyCallBack volleyCallBack){
        String url = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/show_categorie.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Categorie> mesCategories = new ArrayList<>();

                            JSONArray jsonArrayCat = new JSONArray(response);
                            for (int i=0; i<jsonArrayCat.length(); i++){
                                mesCategories.add(new Categorie(jsonArrayCat.getString(i)));
                            }
                            volleyCallBack.onSuccess(mesCategories);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(monActivite, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(monActivite);
        requestQueue.add(stringRequest);
    }
}