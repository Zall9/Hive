package com.example.hive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.hive.LoadPackage.DataAdapter;
import com.example.hive.LoadPackage.RecyclerViewAdapter;
import com.example.hive.javaClasses.Topic;

import java.util.ArrayList;


public class RecyclerViewTopic extends RecyclerView.Adapter<RecyclerViewTopic.ViewHolder> {

    private Context context;
    private ArrayList<Topic> mesTopics;

    public RecyclerViewTopic(ArrayList<Topic> mesTopics, Context context) {

        super();
        this.mesTopics = mesTopics;
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return mesTopics.size();
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
