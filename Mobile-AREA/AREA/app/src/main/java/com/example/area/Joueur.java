package com.example.area;

/**
 * @file Joueur.java
 * @brief Déclaration de la classe Joueur
 * @author BRUNET Bastien
 * $LastChangedRevision: 70 $
 * $LastChangedDate: 2021-05-26 16:47:09 +0200 (mer. 26 mai 2021) $
 */

import java.io.Serializable;

/**
 * @class Joueur
 * @brief Classe regroupant les informations d'un joueur
 */

public class Joueur implements Serializable
{
    /**
     * Attributs
     */
      private String nom;//<! Le nom du joueur
      private String prenom;//<! Le prénom du joueur
      private int numLicence;//<! Le numéro de licence du joueur
      private boolean estServeur;//<! Permet de savoir si le joueur est serveur ou non

    /**
     * @brief Constructeur de la classe Joueur
     * @param nom Le nom du joueur
     * @param prenom Le prénom du joueur
     * @param numLicence Le numéro de licence du joueur
     */
      public Joueur(String nom, String prenom, int numLicence)
      {
          this.nom = nom;
          this.prenom = prenom;
          this.numLicence = numLicence;
          this.estServeur = false;
      }

    /**
     * @brief Accesseur de l'attribut nom
     */
    public String getNom()
      {
          return nom;
      }

    /**
     * @brief Accesseur de l'attribut prenom
     */
    public String getPrenom()
      {
          return prenom;
      }

    /**
     * @brief Accesseur de l'attribut estServeur
     */
    public boolean estServeur()
      {
          return estServeur;
      }

    /**
     * @brief Mutateur de l'attribut estServeur
     */
    public void setEstServeur(boolean estServeur)
    {
        this.estServeur = estServeur;
    }

    /**
     * @brief Accesseur de l'attribut numLicence
     */
    public int getNumLicence()
    {
        return numLicence;
    }

}