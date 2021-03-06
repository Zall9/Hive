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
    public ArrayList<Topic> userTopicList;
    public ArrayList<Role> userRoleList;
    public ArrayList<Badges> badgesList;
    public ArrayList<Commentaire> commentList;
    public Date dateInscription;



    //constructeur à utiliser pour creer un nouvel utilisateur
    public User(int idUser,String nom,String prenom,String email){
        this.idUser=idUser;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.userTopicList=userTopicList;
        this.userRoleList=userRoleList;
        this.badgesList=badgesList;
        this.commentList=commentList;
        this.dateInscription= Calendar.getInstance().getTime();

    }
    //METHODS


    public ArrayList<Role> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(ArrayList<Role> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public ArrayList<Badges> getBadgesList() {
        return badgesList;
    }

    public void setBadgesList(ArrayList<Badges> badgesList) {
        this.badgesList = badgesList;
    }

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

    public ArrayList<Topic> getUserTopicList() {
        return userTopicList;
    }

    public void setUserTopicList(ArrayList<Topic> userTopicList) {
        this.userTopicList = userTopicList;
    }
}
