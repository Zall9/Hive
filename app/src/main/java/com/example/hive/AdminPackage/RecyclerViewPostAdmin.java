package com.example.hive.AdminPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.hive.LoadPackage.DataAdapter;
import com.example.hive.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewPostAdmin extends RecyclerView.Adapter<RecyclerViewPostAdmin.ViewHolder>{

    private List<DataAdapter> dataAdapters;
    private boolean estArchive;
    private Context context;
    private Activity activity;


    public RecyclerViewPostAdmin(List<DataAdapter> dataAdapters, boolean estArchive, Context context){
        this.dataAdapters = dataAdapters;
        this.estArchive = estArchive;
        this.context = context;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public RecyclerViewPostAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_admin_panel_post_post, parent, false);

        RecyclerViewPostAdmin.ViewHolder viewHolder = new RecyclerViewPostAdmin.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPostAdmin.ViewHolder holder, int position) {
        DataAdapter dataAdapterOBJ =  dataAdapters.get(position);

        holder.nomPostTV.setText(dataAdapterOBJ.getImageTitle());

        holder.nomAuteurTV.setText(dataAdapterOBJ.getImagePrenomAuteur()+" "+dataAdapterOBJ.getImageNomAuteur());

        if(estArchive){
            holder.bArchiver.setText("Désarchiver");
            holder.IVdelete.setImageResource(R.drawable.ic_delete);

            holder.IVdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Voulez vous vraiment supprimer ce Post ?");
                    builder.setIcon(R.drawable.ic_delete);
                    builder.setTitle("Confirmation de Suppression")
                            .setCancelable(true)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delete(dataAdapterOBJ.getIdPost());
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

            holder.bArchiver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    archiverDesarchiver(dataAdapterOBJ.getIdPost(), false);
                    refresh();
                }
            });
        }else{
            holder.bArchiver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    archiverDesarchiver(dataAdapterOBJ.getIdPost(), true);
                    refresh();
                }
            });
        }
    }

    @Override
    public int getItemCount() { return dataAdapters.size(); }


    public void archiverDesarchiver(int idPost, boolean archive){
        String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/archiver_desarchiver_post.php";
        StringRequest RequestOfJSonArray = new StringRequest(Request.Method.POST, HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Erreur")){
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        }
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("idPost", String.valueOf(idPost));
                params.put("archive", String.valueOf(archive));
                return params;
            }
        };

        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(RequestOfJSonArray);
    }


    public void delete(int idPost){
        String HTTP_JSON_URL = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/delete_post_archive.php";
        StringRequest RequestOfJSonArray = new StringRequest(Request.Method.POST, HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Erreur lors de la suppression")){
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        }
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("idPost", String.valueOf(idPost));

                return params;
            }
        };

        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(RequestOfJSonArray);
    }


    public void refresh(){
        Intent intent = activity.getIntent();

        activity.finish();
        activity.startActivity(intent);
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nomAuteurTV, nomPostTV;
        public Button bArchiver;
        public ImageView IVdelete;

        public ViewHolder(View itemView) {

            super(itemView);

            nomAuteurTV = (TextView) itemView.findViewById(R.id.cardview_APP_post_nomAuteur);

            nomPostTV = (TextView) itemView.findViewById(R.id.cardview_APP_post_nomPost);

            bArchiver = (Button) itemView.findViewById(R.id.cardview_APP_post_buttonArchiver);

            IVdelete = (ImageView) itemView.findViewById(R.id.cardview_APP_post_delete);


        }
    }
}
