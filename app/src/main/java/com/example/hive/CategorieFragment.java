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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.javaClasses.Categorie;
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
                for (Categorie c : categories){
                    Log.d("CATEGORIE", c.toString());
                }



            }
        });

        Log.d("CATEGORIE","Apres on success");

        return rootView;
    }


    public void recupCategorie(Activity monActivite, final VolleyCallBack volleyCallBack){
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