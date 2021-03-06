package com.example.hive.LoadPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hive.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<DataAdapter> dataAdapters;
    ImageLoader imageLoader;

    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
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

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageUrl(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getImageUrl(), imageLoader);

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

        public ViewHolder(View itemView) {

            super(itemView);

            ImageTitleTextView = (TextView) itemView.findViewById(R.id.nom_post) ;

            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;

            ImageAuteurTextView = (TextView) itemView.findViewById(R.id.nom_auteur);

            ImageTopicTextView = (TextView) itemView.findViewById(R.id.nom_topic);

            ImageCategorieTextView = (TextView) itemView.findViewById(R.id.nom_categorie);

            ImageNbLikeTextView = (TextView) itemView.findViewById(R.id.text_nblike);

            ImageRole = (TextView) itemView.findViewById(R.id.role_auteur);

        }
    }
}
