package com.example.hive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hive.javaClasses.Commentaire;

import java.util.ArrayList;

public class RecyclerViewCommentaire extends RecyclerView.Adapter<RecyclerViewCommentaire.ViewHolder> {
    private ArrayList<Commentaire> mesCommentaires;
    private Context context;

    public RecyclerViewCommentaire(ArrayList<Commentaire> mesCommentaires, Context context){
        this.mesCommentaires = mesCommentaires;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_commentaire, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Commentaire commentaire = mesCommentaires.get(position);
        String auteurName = commentaire.getNomAuteur()+ " "+ commentaire.getPrenomAuteur();
        holder.textAuteur.setText(auteurName);
        holder.textCommentaire.setText(commentaire.getContenu());

    }

    @Override
    public int getItemCount() {
        return mesCommentaires.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textAuteur, textCommentaire;

        public ViewHolder(View itemView) {

            super(itemView);

            textAuteur = (TextView) itemView.findViewById(R.id.auteur_cardview_commentaire);

            textCommentaire = (TextView) itemView.findViewById(R.id.commentaire_cardview_commentaire);

        }
    }
}
