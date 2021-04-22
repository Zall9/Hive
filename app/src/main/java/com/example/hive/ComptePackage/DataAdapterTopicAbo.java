package com.example.hive.ComptePackage;

import com.example.hive.javaClasses.Role;
import com.example.hive.javaClasses.Topic;

import java.util.ArrayList;
import java.util.Map;

public class DataAdapterTopicAbo {

    private int nbLike, nbPost;
    private ArrayList<String []> listeTopicRole;

    public int getNbLike() { return nbLike; }

    public int getNbPost() { return nbPost; }

    public ArrayList<String[]> getListeTopicRole() { return listeTopicRole; }

    public void setNbLike(int nbLike) { this.nbLike = nbLike; }

    public void setNbPost(int nbPost) { this.nbPost = nbPost; }

    public void setListeTopicRole(ArrayList<String[]> listeTopicRole) { this.listeTopicRole = listeTopicRole; }
}
