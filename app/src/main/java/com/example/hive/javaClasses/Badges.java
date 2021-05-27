package com.example.hive.javaClasses;

import android.util.Log;
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
        String nom = user.getNom();
        String prenom = user.getPrenom();
        int maxPost = 5, maxLikeFait = 25, maxLikeRecu = 25, maxCom = 35;
        //Post
        ArrayList<Badges> mesBadges = new ArrayList<Badges>();
        Badges badges = new Badges("Post de bronze", R.drawable.badge_post_bronze, nbPost>=1, R.string.badge_post_bronze);
        mesBadges.add(badges);
        Badges badgesR = new Badges("Post d'argent", R.drawable.badge_post_argent, nbPost>=3, R.string.badge_post_argent);
        mesBadges.add(badgesR);
        Badges badgesO = new Badges("Post d'or", R.drawable.badge_post_or, nbPost>=maxPost, R.string.badge_post_or);
        mesBadges.add(badgesO);

        //Like fait
        Badges badges1 = new Badges("Likeurz de bronze", R.drawable.badge_like_fait_bronze, nbLikeFait>=3, R.string.badge_like_fait_bronze);
        mesBadges.add(badges1);
        Badges badges2 = new Badges("Likeurz d'argent",R.drawable.badge_like_fait_argent,nbLikeFait>=10, R.string.badge_like_fait_argent);
        mesBadges.add(badges2);
        Badges badges3 = new Badges("Likeurz d'or", R.drawable.badge_like_fait_or, nbLikeFait>=maxLikeFait, R.string.badge_like_fait_or);
        mesBadges.add(badges3);

        //Like Recu
        Badges badges4 = new Badges("Liké de bronze",R.drawable.badge_like_recu_bronze, nbLikeRecu>=5, R.string.badge_like_recu_bronze);
        mesBadges.add(badges4);
        Badges badges5 = new Badges("Liké d'argent",R.drawable.badge_like_recu_argent, nbLikeRecu>=15, R.string.badge_like_recu_argent);
        mesBadges.add(badges5);
        Badges badges6 = new Badges("Liké d'or", R.drawable.badge_like_recu_or, nbLikeRecu>=maxLikeRecu, R.string.badge_like_recu_or);
        mesBadges.add(badges6);

        //Commentaire
        Badges badges7 = new Badges("Commentaire de bronze",R.drawable.badge_commentaire_bronze, nbCommentaire>=10, R.string.badge_commentaire_bronze);
        mesBadges.add(badges7);
        Badges badges8 = new Badges("Commentaire d'argent", R.drawable.badge_commentaire_argent, nbCommentaire>=25, R.string.badge_commentaire_argent);
        mesBadges.add(badges8);
        Badges badges9 = new Badges("Commentaire d'or", R.drawable.badge_commentaire_or, nbCommentaire>=maxCom, R.string.badge_commentaire_or);
        mesBadges.add(badges9);

        //Les Badges Ultime
        Badges badgeUltime = new Badges("Badge Utltime",R.drawable.hive, ((nbPost>=maxPost) &&
                                                                                     (nbLikeFait>=maxLikeFait) &&
                                                                                     (nbLikeRecu>=maxLikeRecu) &&
                                                                                     (nbCommentaire>=maxCom))
        ,R.string.badge_ultime);
        mesBadges.add(badgeUltime);

        Badges martyLeS = new Badges("Le Dieu vivant", R.drawable.marty, nom.equals("Marty"),R.string.badge_marty);
        mesBadges.add(martyLeS);
        Badges wayntal = new Badges("Totalement loufoque",R.drawable.wyntal_pixel_art,(prenom.equals("David") && nom.equals("Wayntal")), R.string.badge_wayntal);
        mesBadges.add(wayntal);
        Badges boussion = new Badges("La valeur sur",R.drawable.boussion_pixel_art,nom.equals("Boussion"), R.string.badge_boussion);
        mesBadges.add(boussion);


        return mesBadges;
    }
}
