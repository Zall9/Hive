package com.example.hive.LoadPackage;

public class DataAdapter
{
    public String ImageURL;
    public String ImageTitle;
    private String ImageNomAuteur;
    private String ImagePrenomAuteur;
    private String ImageTopic;
    private String ImageCategorie;
    private String ImagenbLike;
    private String ImageRole;

    public String getImageUrl() {
        return ImageURL;
    }
    public void setImageUrl(String imageServerUrl) {
        this.ImageURL = imageServerUrl;
    }

    public String getImageTitle() {
        return ImageTitle;
    }
    public void setImageTitle(String Imagetitlename) {
        this.ImageTitle = Imagetitlename;
    }

    public String getImageNomAuteur() { return ImageNomAuteur; }
    public void setImageNomAuteur(String imageNomAuteur) { ImageNomAuteur = imageNomAuteur; }

    public String getImagePrenomAuteur() { return ImagePrenomAuteur; }
    public void setImagePrenomAuteur(String imagePrenomAuteur) { ImagePrenomAuteur = imagePrenomAuteur; }

    public String getImageTopic() { return ImageTopic; }
    public void setImageTopic(String imageTopic) { ImageTopic = imageTopic; }

    public String getImageCategorie() { return ImageCategorie; }
    public void setImageCategorie(String imageCategorie) { ImageCategorie = imageCategorie; }

    public String getImagenbLike() { return ImagenbLike; }
    public void setImagenbLike(String imagenbLike) { ImagenbLike = imagenbLike; }

    public String getImageRole() { return ImageRole; }
    public void setImageRole(String imageRole) { ImageRole = imageRole; }







}
