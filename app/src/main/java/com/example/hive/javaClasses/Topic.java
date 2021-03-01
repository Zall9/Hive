package com.example.hive.javaClasses;

import java.util.ArrayList;

//ToDo
public class Topic {
    private int idTopic;
    public String nomTopic;
    public ArrayList<Post> topicList;
    public ArrayList<Role> topicRoleList;
    //Constructeur à utiliser lors de la création d'un nouveau TOPIC (liste de posts vide)
    public Topic(int idTopic, String nomTopic){
        this.idTopic=idTopic;
        this.nomTopic=nomTopic;
        this.topicList=new ArrayList<Post>();
        this.topicRoleList=new ArrayList<Role>();
    }

    //si le topic existe deja
    public Topic(int idTopic, String nomTopic, ArrayList<Post> topicList, ArrayList<Role> topicRoleList){
        this.idTopic=idTopic;
        this.nomTopic=nomTopic;
        this.topicList=topicList;
        this.topicRoleList=topicRoleList;
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

    public ArrayList<Post> getTopicList() {
        return topicList;
    }

    public void setTopicList(ArrayList<Post> topicList) {
        this.topicList = topicList;
    }
}
