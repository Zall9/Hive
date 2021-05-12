package com.example.hive.LoadPackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hive.PostPackage.PostActivity;
import com.example.hive.R;
import com.example.hive.javaClasses.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<DataAdapter> dataAdapters;
    ImageLoader imageLoader;
    Activity actvity;

    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context, Activity actvity){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
        this.actvity = actvity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {
        DataAdapter dataAdapterOBJ =  dataAdapters.get(position);
        User user = (User)actvity.getIntent().getExtras().getSerializable("User");
        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageUrl(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getImageUrl(), imageLoader);

        Viewholder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(actvity, PostActivity.class);
                //intent.putExtra("nomCategorie", categorieClique.toString());
                intent.putExtra("nomAuteur", dataAdapterOBJ.getImageNomAuteur());
                intent.putExtra("prenomAuteur", dataAdapterOBJ.getImagePrenomAuteur());
                intent.putExtra("nomPost", dataAdapterOBJ.getImageTitle());
                intent.putExtra("nomCategorie", dataAdapterOBJ.getImageCategorie());
                intent.putExtra("nomTopic", dataAdapterOBJ.getImageTopic());
                intent.putExtra("urlImage", dataAdapterOBJ.getImageUrl());
                intent.putExtra("nbLike", dataAdapterOBJ.getImagenbLike());
                intent.putExtra("idUser", user.getIdUser());
                intent.putExtra("Role", dataAdapterOBJ.getImageRole());
                intent.putExtra("ouvrirCommentaire", false);
                actvity.startActivity(intent);
            }
        });

        Viewholder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePost(user, dataAdapterOBJ.getImageCategorie(), dataAdapterOBJ.getImageTopic(), dataAdapterOBJ.getImageTitle(), Viewholder.likeButton, Viewholder.ImageNbLikeTextView, dataAdapterOBJ);
            }
        });

        Viewholder.commentaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(actvity, PostActivity.class);
                intent.putExtra("nomAuteur", dataAdapterOBJ.getImageNomAuteur());
                intent.putExtra("prenomAuteur", dataAdapterOBJ.getImagePrenomAuteur());
                intent.putExtra("nomPost", dataAdapterOBJ.getImageTitle());
                intent.putExtra("nomCategorie", dataAdapterOBJ.getImageCategorie());
                intent.putExtra("nomTopic", dataAdapterOBJ.getImageTopic());
                intent.putExtra("urlImage", dataAdapterOBJ.getImageUrl());
                intent.putExtra("nbLike", dataAdapterOBJ.getImagenbLike());

                intent.putExtra("idUser", user.getIdUser());
                intent.putExtra("Role", dataAdapterOBJ.getImageRole());
                intent.putExtra("ouvrirCommentaire", true);
                actvity.startActivity(intent);
            }
        });


        Viewholder.ImageTitleTextView.setText(dataAdapterOBJ.getImageTitle());
        String auteur_full = dataAdapterOBJ.getImageNomAuteur()+" "+dataAdapterOBJ.getImagePrenomAuteur();
        Viewholder.ImageAuteurTextView.setText(auteur_full);
        String t = "Topic : "+dataAdapterOBJ.getImageTopic();
        String c = "Categorie : "+dataAdapterOBJ.getImageCategorie();
        Viewholder.ImageTopicTextView.setText(t);
        Viewholder.ImageCategorieTextView.setText(c);
        Viewholder.ImageNbLikeTextView.setText(dataAdapterOBJ.getImagenbLike());
        Viewholder.ImageRole.setText(dataAdapterOBJ.getImageRole());
        if(dataAdapterOBJ.getaLike()){
            Viewholder.likeButton.setBackgroundResource(R.drawable.ic_baseline_favorite_orange_24);
        }else{
            Viewholder.likeButton.setBackgroundResource(R.drawable.ic_baseline_favorite_grey_24);
        }
    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public String getNomAuteur(int position){ return dataAdapters.get(position).getImageNomAuteur(); }

    public String getPrenomAuteur(int position){ return dataAdapters.get(position).getImagePrenomAuteur();}

    public String getNomPost(int position){ return dataAdapters.get(position).getImageTitle(); }

    public String getCategorie(int position){ return dataAdapters.get(position).getImageCategorie(); }

    public String getTopic(int position){ return dataAdapters.get(position).getImageTopic(); }

    public String getImageUrl(int position){ return dataAdapters.get(position).getImageUrl(); }

    public String getNbLike(int position){ return dataAdapters.get(position).getImagenbLike(); }

    public String getRole(int position){ return dataAdapters.get(position).getImageRole();}


    public void likePost(User user, String nomCategorie, String nomTopic, String nomPost, AppCompatButton likePost, TextView nbLike, DataAdapter dataAdapterOBJ){
        String url = "http://os-vps418.infomaniak.ch:1180/l2_gr_8/like_post.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int valNbLike = Integer.parseInt(nbLike.getText().toString());
                        if (response.equals("Vous avez dislike")){
                            likePost.setBackgroundResource(R.drawable.ic_baseline_favorite_grey_24);
                            valNbLike -= 1;
                            dataAdapterOBJ.setaLike(false);
                        }else{
                            likePost.setBackgroundResource(R.drawable.ic_baseline_favorite_orange_24);
                            valNbLike += 1;
                            dataAdapterOBJ.setaLike(true);
                        }

                        nbLike.setText(String.valueOf(valNbLike));
                        dataAdapterOBJ.setImagenbLike(String.valueOf(valNbLike));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(actvity, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", String.valueOf(user.getIdUser()));
                params.put("nomPost", nomPost);
                params.put("nomTopic", nomTopic);
                params.put("nomCategorie", nomCategorie);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(actvity);
        requestQueue.add(stringRequest);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleTextView, ImageAuteurTextView, ImageTopicTextView, ImageCategorieTextView, ImageNbLikeTextView, ImageRole, nbLike;

        public NetworkImageView VollyImageView ;

        public RelativeLayout relativeLayout;

        public AppCompatButton likeButton, commentaireButton;


        public ViewHolder(View itemView) {

            super(itemView);

            ImageTitleTextView = (TextView) itemView.findViewById(R.id.nom_post) ;

            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;

            ImageAuteurTextView = (TextView) itemView.findViewById(R.id.nom_auteur);

            ImageTopicTextView = (TextView) itemView.findViewById(R.id.nom_topic);

            ImageCategorieTextView = (TextView) itemView.findViewById(R.id.nom_categorie);

            ImageNbLikeTextView = (TextView) itemView.findViewById(R.id.text_nblike);

            ImageRole = (TextView) itemView.findViewById(R.id.role_auteur);

            relativeLayout = itemView.findViewById(R.id.Father);

            likeButton = (AppCompatButton) itemView.findViewById(R.id.likeCheckBox);

            commentaireButton = (AppCompatButton) itemView.findViewById(R.id.checkBox3);


        }
    }

}
