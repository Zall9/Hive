package com.example.hive.javaClasses;

import android.widget.ArrayAdapter;

import com.example.hive.R;

import java.util.ArrayList;

public class Badges {
    public String nomBadge;
    public int condiOpten;
    public int idImage;
    public boolean estPossede;
    public Badges(String nomBadge, int idImage, boolean estPossede, int condiOpten){
        this.nomBadge=nomBadge;
        this.idImage = idImage;
        this.estPossede = estPossede;
        this.condiOpten = condiOpten;
    }

    public String getNomBadge() {
        return nomBadge;
    }

    public void setNomBadge(String nomBadge) {
        this.nomBadge = nomBadge;
    }

    public int getIdImage() { return idImage; }

    public void setIdImage(int idImage) { this.idImage = idImage; }

    public static ArrayList<Badges> creerListeBadges(User user){
        int nbPost = user.getNbPost();
        int nbLikeRecu = user.getNbLikeRecu();
        int nbLikeFait = user.getNbLikeFait();
        int nbCommentaire = user.getNbCommentaire();

        ArrayList<Badges> mesBadges = new ArrayList<Badges>();
        Badges badges = new Badges("Post de bronze", R.drawable.badge_post_bronze, nbPost>=1, R.string.badge_post_bronze);
        mesBadges.add(badges);
        Badges badgesR = new Badges("Post d'argent", R.drawable.badge_post_argent, nbPost>=3, R.string.badge_post_argent);
        mesBadges.add(badgesR);
        Badges badgesO = new Badges("Post d'or", R.drawable.badge_post_or, nbPost>=5, R.string.badge_post_or);
        mesBadges.add(badgesO);
        return mesBadges;
    }
}
