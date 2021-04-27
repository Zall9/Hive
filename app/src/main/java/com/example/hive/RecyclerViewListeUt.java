package com.example.hive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.hive.LoadPackage.DataAdapter;
import com.example.hive.LoadPackage.RecyclerViewAdapter;

import java.util.ArrayList;

public class RecyclerViewListeUt extends RecyclerView.Adapter<RecyclerViewListeUt.ViewHolder>{
    private String rolePlusHaut;
    private ArrayList<String> listeUt;
    private String listeAafficher;

    public RecyclerViewListeUt(ArrayList<String> listeUt, String rolePlusHaut, String listeAafficher){
        super();
        this.rolePlusHaut = rolePlusHaut;
        this.listeUt = listeUt;
        this.listeAafficher = listeAafficher;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_admin_panel_utilisateur, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nomUt = listeUt.get(position);
        holder.nomUt.setText(nomUt);

        if(rolePlusHaut.equals("Créateur")){
            if(listeAafficher.equals("Admin")){
                holder.downgradeIM.setImageResource(R.drawable.ic_retrograde);
            }else{
                holder.upgradeIM.setImageResource(R.drawable.ic_upgrade);
            }
            holder.banIM.setImageResource(R.drawable.ic_ban);

        }else if(rolePlusHaut.equals("Admin") && listeAafficher.equals("Membre")) {
            holder.upgradeIM.setImageResource(R.drawable.ic_upgrade);
            holder.banIM.setImageResource(R.drawable.ic_ban);
        }
    }

    @Override
    public int getItemCount() { return listeUt.size(); }


    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomUt;
        public ImageView upgradeIM, downgradeIM, banIM;

        public ViewHolder(View itemView) {

            super(itemView);

            nomUt = (TextView) itemView.findViewById(R.id.cardview_admin_panel_admin_nomAdmin);

            upgradeIM = (ImageView) itemView.findViewById(R.id.cardview_admin_panel_admin_upgrade);

            downgradeIM = (ImageView) itemView.findViewById(R.id.cardview_admin_panel_admin_retrograde);

            banIM = (ImageView) itemView.findViewById(R.id.cardview_admin_panel_admin_ban);

        }
    }
}
