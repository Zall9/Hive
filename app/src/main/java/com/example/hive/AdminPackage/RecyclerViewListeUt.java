package com.example.hive.AdminPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewListeUt extends RecyclerView.Adapter<RecyclerViewListeUt.ViewHolder>{
    private String rolePlusHaut;
    private ArrayList<String> listeUt;
    private String listeAafficher;
    private ArrayList<Integer> listeUtId;
    private int idCategorie;
    private String nomTopic;
    private Context context;
    private Activity activity;
    private StringRequest RequestOfJSonArray ;
    private String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/gere_grade_utilisateur.php";
    private RequestQueue requestQueue ;

    public RecyclerViewListeUt(ArrayList<String> listeUt, String rolePlusHaut, String listeAafficher, ArrayList<Integer> listeUtId, int idCategorie, String nomTopic, Context context, Activity activity){
        super();
        this.rolePlusHaut = rolePlusHaut;
        this.listeUt = listeUt;
        this.listeAafficher = listeAafficher;
        this.listeUtId = listeUtId;
        this.idCategorie = idCategorie;
        this.nomTopic = nomTopic;
        this.context = context;
        this.activity = activity;
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
                holder.downgradeIM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gereGrade(listeUtId.get(position), "downgrade");
                        refresh();
                    }
                });
            }else{
                holder.upgradeIM.setImageResource(R.drawable.ic_upgrade);
                holder.upgradeIM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gereGrade(listeUtId.get(position), "upgrade");
                        refresh();
                    }
                });
            }
            holder.banIM.setImageResource(R.drawable.ic_ban);

            holder.banIM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Voulez vous vraiment ban : "+listeUt.get(position));
                    builder.setIcon(R.drawable.ic_ban);
                    builder.setTitle("Confirmation de ban")
                            .setCancelable(true)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    gereGrade(listeUtId.get(position), "ban");
                                    refresh();
                                }
                            });

                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

        }else if(rolePlusHaut.equals("Admin") && listeAafficher.equals("Membre")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            holder.upgradeIM.setImageResource(R.drawable.ic_upgrade);
            holder.upgradeIM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gereGrade(listeUtId.get(position), "upgrade");
                    refresh();
                }
            });
            holder.banIM.setImageResource(R.drawable.ic_ban);

            holder.banIM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage("Voulez vous vraiment ban : "+listeUt.get(position));
                    builder.setIcon(R.drawable.ic_ban);
                    builder.setTitle("Confirmation de ban")
                    .setCancelable(true)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gereGrade(listeUtId.get(position), "ban");
                            refresh();
                        }
                    });

                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });
        }

        //On affiche les banni
        if(listeAafficher.equals("Banni")){
            holder.banIM.setImageResource(R.drawable.ic_admin_panel_groupe);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            holder.banIM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage("Voulez vous vraiment déban : "+listeUt.get(position));
                    builder.setIcon(R.drawable.ic_admin_panel_groupe);
                    builder.setTitle("Confirmation de Déban")
                            .setCancelable(true)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    gereGrade(listeUtId.get(position), "unban");
                                    refresh();
                                }
                            });

                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() { return listeUt.size(); }


    public void refresh(){
        Intent intent = activity.getIntent();

        int idUser = activity.getIntent().getExtras().getInt("idUser");
        ArrayList<String> listeRoles = activity.getIntent().getExtras().getStringArrayList("listeRoles");

        intent.putExtra("nomTopic", nomTopic);
        intent.putExtra("idCategorie", idCategorie);
        intent.putExtra("idUser", idUser);
        intent.putExtra("listeRoles", listeRoles);

        activity.finish();
        activity.startActivity(intent);
    }


    public void gereGrade(int idUser, String action){
        RequestOfJSonArray = new StringRequest(Request.Method.POST, HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", String.valueOf(idUser));
                params.put("nomTopic", nomTopic);
                params.put("idCategorie", String.valueOf(idCategorie));
                params.put("typeAction", action);
                return params;
            }
        };


        requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(RequestOfJSonArray);
    }



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
