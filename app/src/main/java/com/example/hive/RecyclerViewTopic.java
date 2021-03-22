package com.example.hive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.LoadPackage.DataAdapter;
import com.example.hive.LoadPackage.RecyclerViewAdapter;
import com.example.hive.javaClasses.Topic;
import com.example.hive.javaClasses.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RecyclerViewTopic extends RecyclerView.Adapter<RecyclerViewTopic.ViewHolder> {

    private Context context;
    private ArrayList<Topic> mesTopics;
    private Activity activity;


    public RecyclerViewTopic(ArrayList<Topic> mesTopics, Context context, Activity activity) {

        super();
        this.mesTopics = mesTopics;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_topics, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Topic topicObject = mesTopics.get(position);

        holder.textNomTopic.setText(topicObject.getNomTopic());
        if(topicObject.estAbonne){
            holder.buttonAbonne.setText("Abonnée");
        }else{
            holder.buttonAbonne.setText("S'abonner");
        }

        holder.buttonAbonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User)activity.getIntent().getExtras().getSerializable("User");
                Abonnement(topicObject.estAbonne, user, topicObject.getNomTopic());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mesTopics.size();
    }


    public void Abonnement(boolean estAbonne, User user, String nomTopic){
        String url = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/abonnement.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REP", response);
                        if (response.equals("true")){
                            Toast.makeText(context, "Vous êtes abonné(e) !", Toast.LENGTH_SHORT).show();
                        }else if(response.equals("false")){
                            Toast.makeText(context, "Vous êtes désabonné(e) !", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Erreur lors de l'abonnement", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = activity.getIntent();

                        intent.putExtra("User", user);

                        activity.finish();
                        activity.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String estAbonneStr = "";
                if(estAbonne) { estAbonneStr = "1"; }else{ estAbonneStr = "0";}
                params.put("estAbonne", estAbonneStr);
                params.put("idUser", String.valueOf(user.getIdUser()));
                params.put("nomTopic", nomTopic);
                String nomCategorie = activity.getIntent().getStringExtra("nomCategorie");
                params.put("nomCategorie", nomCategorie);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textNomTopic;

        public Button buttonAbonne;

        public ViewHolder(View itemView) {

            super(itemView);

            textNomTopic = (TextView) itemView.findViewById(R.id.text_topic_cardview);

            buttonAbonne = (Button) itemView.findViewById(R.id.button_topic_cardview);

        }
    }
}
