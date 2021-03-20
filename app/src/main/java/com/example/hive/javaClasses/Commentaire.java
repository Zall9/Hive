package com.example.hive.javaClasses;



import java.util.Calendar;
import java.util.Date;

public class Commentaire {
    public int idCom;
    public String contenu;
    public String nomAuteur, prenomAuteur;
    public Date dateCommentaire;
    public Commentaire(String auteur, String contenu){
        this.idCom=idCom;
        this.contenu=contenu;
        this.nomAuteur=auteur;
        this.dateCommentaire=Calendar.getInstance().getTime();
    }

    public Commentaire(){
        this.idCom = -1;
        this.contenu = "error";
        this.nomAuteur = "nom null";
        this.prenomAuteur = "prenom null";
        this.dateCommentaire = null;
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

    public Date getDateCommentaire() {
        return dateCommentaire;
    }

    public String getNomAuteur() { return nomAuteur; }

    public void setNomAuteur(String nomAuteur) { this.nomAuteur = nomAuteur; }

    public String getPrenomAuteur() { return prenomAuteur; }
    
    public void setPrenomAuteur(String prenomAuteur) { this.prenomAuteur = prenomAuteur; }




}
