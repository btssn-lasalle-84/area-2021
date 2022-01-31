package com.example.area;

import android.util.Log;

import java.io.Serializable;
import java.util.Vector;

/**
 * @file Equipe.java
 * @brief Déclaration de la classe Equipe
 * @author BRUNET Bastien
 * $LastChangedRevision: 116 $
 * $LastChangedDate: 2021-06-10 09:03:59 +0200 (jeu. 10 juin 2021) $
 */

/**
 * @class Equipe
 * @brief Classe regroupant les informations d'une équipe
 */

public class Equipe implements Serializable
{
    /**
     * Constantes
     */
    public static final int INDEX_JOUEUR_A = 0;//<! Index du joueur A dans le vecteur de joueur
    public static final int INDEX_JOUEUR_B = 1;//<! Index du joueur B dans le vecteur de joueur
    public static final int INDEX_JOUEUR_C = 2;//<! Index du joueur C dans le vecteur de joueur
    public static final int INDEX_JOUEUR_D = 3;//<! Index du joueur D dans le vecteur de joueur

    public static final int INDEX_JOUEUR_W = 0;//<! Index du joueur W dans le vecteur de joueur
    public static final int INDEX_JOUEUR_X = 1;//<! Index du joueur X dans le vecteur de joueur
    public static final int INDEX_JOUEUR_Y = 2;//<! Index du joueur Y dans le vecteur de joueur
    public static final int INDEX_JOUEUR_Z = 3;//<! Index du joueur Z dans le vecteur de joueur
    private static final String TAG = "_Equipe";//<! TAG pour les logs

    /**
     * Attributs
     */
    private String nomClub;//<! le nom du club auqel appartient l'équipe
    private Vector<Joueur> joueurs;//<! les joueurs de l'équipe
    private int id;//<! identifiant unique de l'équipe dans la base de données
    private int nbPartiesGagnees;//<! nombre de points de l'équipe dans la rencontre

    /**
     * @brief Constructeur de la classe Equipe
     * @param nomClub Nom du club auquel appartient l'équipe
     * @param joueurs Joueurs qui constituent l'equipe
     */
    public Equipe(String nomClub, Vector<Joueur> joueurs)
    {
        this.nomClub = nomClub;
        this.joueurs = joueurs;
        this.id = 0;
        this.nbPartiesGagnees = 0;
    }

    /**
     * @brief Accesseur de la l'attribut nomClub
     */
    public final String getNomClub()
    {
      return nomClub;
    }

    /**
     * @brief Accesseur de la l'attribut joueurs
     */
    public Vector<Joueur> getJoueurs()
    {
        return joueurs;
    }

    /**
     * @brief Accesseur de la l'attribut nbPartiesGagnees
     */
    public final int getNbPartiesGagnees()
    {
        return nbPartiesGagnees;
    }

    /**
     * @brief Méthode de l'attribut joueur
     */
    public void incrementerScore()
    {
        nbPartiesGagnees++;
        Log.d(TAG,"Score de l'équipe "+ nomClub + " = " + Integer.toString(nbPartiesGagnees));
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}
