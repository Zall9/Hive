package com.example.hive;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hive.javaClasses.Badges;

import java.util.ArrayList;


public class RecyclerViewAdapterBadge extends RecyclerView.Adapter<RecyclerViewAdapterBadge.ViewHolder> {
    Context context;
    Activity activity;
    ArrayList<Badges> badges;

    public RecyclerViewAdapterBadge(ArrayList<Badges> badges, Context context){
        this.badges = badges;
        this.context = context;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_badge, parent, false);

        RecyclerViewAdapterBadge.ViewHolder viewHolder = new RecyclerViewAdapterBadge.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Badges badge = badges.get(position);
        if(badge.estPossede){
            holder.IMbadge.setImageResource(badge.idImage);
        }else{
            holder.IMbadge.setImageResource(R.drawable.badge_non_obtenu);
        }

        holder.IMbadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if(badge.estPossede){
                    builder.setIcon(badge.idImage);
                }else{
                    builder.setIcon(R.drawable.badge_non_obtenu);
                }
                builder.setTitle(badge.getNomBadge())
                        .setCancelable(true);
                builder.setMessage(context.getResources().getString(badge.condiOpten));

                builder.setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() { return badges.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{

    public ImageView IMbadge;


    public ViewHolder(View itemView) {

        super(itemView);

        IMbadge = (ImageView) itemView.findViewById(R.id.cardview_badge_img);


    }
}
}
