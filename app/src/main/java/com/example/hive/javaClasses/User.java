package com.example.hive.javaClasses;

import android.graphics.Bitmap;
import android.media.Image;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {
    //ATTRIBUTES
    public int idUser;
    public String nom, prenom, email;
    private String password;
    public Image profilePic;

    public Date dateInscription;
    public int nbPost, nbLikeRecu,nbLikeFait, nbCommentaire;


    //constructeur à utiliser pour creer un nouvel utilisateur
    public User(int idUser,String nom,String prenom,String email, int nbPost, int nbLikeRecu,int nbLikeFait, int nbCommentaire){
        this.idUser=idUser;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.dateInscription= Calendar.getInstance().getTime();
        this.nbPost = nbPost;
        this.nbLikeRecu = nbLikeRecu;
        this.nbLikeFait = nbLikeFait;
        this.nbCommentaire = nbCommentaire;
    }
    //METHODS



    public int getIdUser() {
        return idUser;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUser = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }

    public int getNbPost() { return nbPost; }

    public int getNbCommentaire() { return nbCommentaire; }

    public void setNbPost(int nbPost) { this.nbPost = nbPost; }

    public void setNbCommentaire(int nbCommentaire) { this.nbCommentaire = nbCommentaire; }

    public int getNbLikeRecu() { return nbLikeRecu; }

    public int getNbLikeFait() { return nbLikeFait; }

    public void setNbLikeRecu(int nbLikeRecu) { this.nbLikeRecu = nbLikeRecu; }

    public void setNbLikeFait(int nbLikeFait) { this.nbLikeFait = nbLikeFait; }
}
