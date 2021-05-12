package com.example.hive.LoadPackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hive.PostPackage.PostActivity;
import com.example.hive.R;
import com.example.hive.javaClasses.User;

import java.util.List;

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
                actvity.startActivity(intent);
            }
        });

        Viewholder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likePost(user, dataAdapterOBJ.getImageCategorie(), dataAdapterOBJ.getImageTopic(), dataAdapterOBJ.getImageTitle());
                //TODO faire la fonction boloss
            }
        });

        Viewholder.commentaireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO mettre l'intent + le pop up du commentaire
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

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleTextView, ImageAuteurTextView, ImageTopicTextView, ImageCategorieTextView, ImageNbLikeTextView, ImageRole;

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
