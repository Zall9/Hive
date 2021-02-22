package com.example.hive.UploadPackage;

public class Post {
    private String nom;
    private String imageURL;
    private int nbLike;
    private String auteur;

    public Post(String nom, String imageURL, int nbLike, String auteur){
        this.auteur = auteur;
        this.imageURL = imageURL;
        this.nom = nom;
        this.nbLike = nbLike;
    }

    public String getImageURL(){
        return  imageURL;
    }
}
