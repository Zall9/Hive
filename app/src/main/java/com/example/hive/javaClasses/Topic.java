package com.example.hive.javaClasses;

import java.util.ArrayList;

//ToDo
public class Topic {
    private int idTopic;
    public String nomTopic;
    public boolean estAbonne;
    public ArrayList<Post> postList;
    public ArrayList<Role> topicRoleList;

    public Topic(){
        this.idTopic = -1;
        this.nomTopic = "ErreurNomTopic";
        this.estAbonne = false;
        this.postList = new ArrayList<Post>();
        this.topicRoleList = new ArrayList<Role>();
    }
    //Constructeur à utiliser lors de la création d'un nouveau TOPIC (liste de posts vide)
    public Topic(int idTopic, String nomTopic){
        this.idTopic=idTopic;
        this.nomTopic=nomTopic;
        this.postList=new ArrayList<Post>();
        this.topicRoleList=new ArrayList<Role>();
    }

    //si le topic existe deja
    public Topic(int idTopic, String nomTopic, ArrayList<Post> topicList, ArrayList<Role> topicRoleList){
        this.idTopic=idTopic;
        this.nomTopic=nomTopic;
        this.postList=topicList;
        this.topicRoleList=topicRoleList;
    }

    public Topic(String nomTopic){
        this.idTopic = -1;
        this.nomTopic = nomTopic;
        this.estAbonne = false;
        this.postList = new ArrayList<Post>();
        this.topicRoleList = new ArrayList<Role>();
    }

    public ArrayList<Role> getTopicRoleList() {
        return topicRoleList;
    }

    public void setTopicRoleList(ArrayList<Role> topicRoleList) {
        this.topicRoleList = topicRoleList;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public String getNomTopic() {
        return nomTopic;
    }

    public void setNomTopic(String nomTopic) {
        this.nomTopic = nomTopic;
    }

    public void setEstAbonne(boolean estAbonne) { this.estAbonne = estAbonne; }

    public boolean isEstAbonne() { return estAbonne; }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<Post> topicList) {
        this.postList = topicList;
    }
}
