package com.example.hive.javaClasses;

import java.util.ArrayList;

public class Post {
    private String nom;
    private String imageURL;
    private int nbLike;
    private String auteur;
    private String postContent;

    public ArrayList<Commentaire> commentList;
    public Post(String nom, String imageURL, int nbLike, String auteur, String postContent){
        this.auteur = auteur;
        this.imageURL = imageURL;
        this.nom = nom;
        this.nbLike = nbLike;
        this.postContent=postContent;
        this.commentList=new ArrayList<Commentaire>();
    }
    public Post(String nom, String imageURL, int nbLike, String auteur, String postContent, ArrayList<Commentaire> commentList){
        this.auteur = auteur;
        this.imageURL = imageURL;
        this.nom = nom;
        this.nbLike = nbLike;
        this.postContent=postContent;
        this.commentList=commentList;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getNbLike() {
        return nbLike;
    }

    public void setNbLike(int nbLike) {
        this.nbLike = nbLike;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public ArrayList<Commentaire> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Commentaire> commentList) {
        this.commentList = commentList;
    }

    public String getImageURL(){
        return  imageURL;
    }
}
