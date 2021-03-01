package com.example.hive.javaClasses;



import java.util.Calendar;
import java.util.Date;

public class Commentaire {
    public int idCom;
    public String contenu;
    public User auteur;
    public Date dateCommentaire;
    Commentaire(User auteur, String contenu){
        this.idCom=idCom;
        this.contenu=contenu;
        this.auteur=auteur;
        this.dateCommentaire=Calendar.getInstance().getTime();
    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public User getAuteur() {
        return auteur;
    }

    public void setAuteur(User auteur) {
        this.auteur = auteur;
    }

    public Date getDateCommentaire() {
        return dateCommentaire;
    }
}
