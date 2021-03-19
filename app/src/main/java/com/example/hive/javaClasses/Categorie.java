package com.example.hive.javaClasses;

import java.util.ArrayList;

public class Categorie {
    public String nomCategorie;
    private ArrayList<Topic> listeTopic;
    public Categorie(String nomCategorie){
        this.nomCategorie=nomCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
    @Override
    public String toString(){return this.nomCategorie+"";}

}
