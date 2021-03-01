package com.example.hive;

public class Credentials {
    private String Nom, Prenom, Email, Password;

    Credentials(String username,String prenom,String email, String password){
        this.Nom = username;
        this.Prenom = prenom;
        this.Email =email;
        this.Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
