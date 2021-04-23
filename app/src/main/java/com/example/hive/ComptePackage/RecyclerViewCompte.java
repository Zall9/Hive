package com.example.hive.ComptePackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hive.PostPackage.PostActivity;
import com.example.hive.R;
import com.example.hive.javaClasses.User;

import java.util.ArrayList;

public class RecyclerViewCompte extends RecyclerView.Adapter<RecyclerViewCompte.ViewHolder>{
    private DataAdapterTopicAbo adapterTopicAbo;
    private Context context;
    private Activity activity;
    private ArrayList<ArrayList<String>> listeRoles;

    public RecyclerViewCompte(DataAdapterTopicAbo adapterTopicAbo, Context context, Activity activity, ArrayList<ArrayList<String>> listeRoles){
        this.adapterTopicAbo = adapterTopicAbo;
        this.context = context;
        this.activity = activity;
        this.listeRoles = listeRoles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_topics_abonne, parent, false);

        RecyclerViewCompte.ViewHolder viewHolder = new RecyclerViewCompte.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nomTopic = adapterTopicAbo.getListeTopicRole().get(position)[0];
        ArrayList<String> listeNomRole = listeRoles.get(position);
        String temp = "";
        for (int i = 0; i < listeNomRole.size(); i++) {
            if(i!=0){
                temp += " - "+listeNomRole.get(i);
            }
            else{
                temp += listeNomRole.get(i);
            }
        }
        String nomRole = adapterTopicAbo.getListeTopicRole().get(position)[1];
        holder.roleTopic.setText(temp);
        holder.nomTopic.setText(nomTopic);
        if(nomRole != "Membre"){
            holder.imageAdmin.setImageResource(R.drawable.ic_param);

            holder.imageAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AdminPanel.class);
                    //intent.putExtra("nomCategorie", categorieClique.toString())
                    intent.putExtra("nomTopic", nomTopic);
                    User user = (User)activity.getIntent().getExtras().getSerializable("User");
                    intent.putExtra("idUser", user.getIdUser());
                    activity.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() { return adapterTopicAbo.getListeTopicRole().size(); }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomTopic, roleTopic;
        public ImageView imageAdmin;
        public ViewHolder(View itemView) {

            super(itemView);

            nomTopic = (TextView) itemView.findViewById(R.id.cardview_topic_abonne_nom_topic);

            roleTopic = (TextView) itemView.findViewById(R.id.cardview_topic_abonne_role_topic);

            imageAdmin = (ImageView) itemView.findViewById(R.id.cardview_topic_abonne_param_topic);

        }
    }
}
