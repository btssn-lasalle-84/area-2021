package com.example.area;

import android.util.Log;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

/**
 * @file Partie.java
 * @brief Déclaration de la classe Partie
 * @author BRUNET Bastien
 * $LastChangedRevision: 125 $
 * $LastChangedDate: 2021-06-11 15:03:25 +0200 (ven. 11 juin 2021) $
 */

/**
 * @class Partie
 * @brief Classe permettant la gestion d'une partie
 */

public class Partie implements Serializable
{
    /**
     * Constantes
     */
    private static final String TAG = "_Partie";//<! TAG pour les logs
    public static final int POSITION_PREMIER_JOUEUR = 0;//<! Position du premier joueur dans les Vectors joueursA et joueurB
    public static final int POSITION_DEUXIEME_JOUEUR = 1;//<! Position du deuxième joueur dans les Vectors joueursA et joueurB
    private static final int NB_TEMPS_MORTS_MAX = 1;

    /**
     * Attributs
     */
    private int nbManchesGagnantes;//<! Le nombre de manches à gagner pour remporter la partie
    private int nbPointsParManche;//<! Le nombre de points à gagner pour remporter une manche
    private boolean estFinie;//<! Permet de savoir si la partie est terminée ou non
    private Vector<Joueur> joueursA;//<! Le ou les joueurs de l'equipe A
    private Vector<Joueur> joueursB;//<! Le ou les joueurs de l'equipe B
    private int pointsJoueursA;//<! Les points du ou des joueurs de l'equipe A
    private int pointsJoueursB;//<! Les points du ou des joueurs de l'equipe B
    private int manchesJoueursA;//<! Les manches gagnées du ou des joueurs de l'equipe A
    private int manchesJoueursB;//<! Les manches gagnées du ou des joueurs de l'equipe B
    private int id;//<! Identifiant de la partie
    private int nbPointsMaxMancheActuelle;//<! Attribut permettant de gérer les deux points d'écarts dans une manche
    private Vector<Vector<Integer>> manches = null;//<! Les manches déjà joueés
    private int tempsMortsJoueursA;//<! Le nombre de temps morts restants pour le joueur A
    private int tempsMortsJoueursB;//<! Le nombre de temps morts restants pour le joueur B

    /**
     * @brief Constructeur de la classe Partie
     * @param nbManchesGagnantes Le nombre de manches à gagner pour remporter la partie
     * @param nbPointsParManche Le nombre de points à gagner pour remporter une manche
     * @param joueursA Le ou les joueurs de l'equipe A
     * @param joueursB Le ou les joueurs de l'equipe B
     * @param id Identifiant de la partie
     */
    public Partie(int nbManchesGagnantes, int nbPointsParManche, Vector<Joueur> joueursA, Vector<Joueur> joueursB, int id)
    {
        this.nbManchesGagnantes = nbManchesGagnantes;
        this.nbPointsParManche = nbPointsParManche;
        this.nbPointsMaxMancheActuelle = nbPointsParManche;
        this.estFinie = false;
        this.pointsJoueursA = 0;
        this.pointsJoueursB = 0;
        this.manchesJoueursA = 0;
        this.manchesJoueursB = 0;
        this.id = id;
        this.joueursA = joueursA;
        this.joueursB = joueursB;
        this.manches = new Vector<Vector<Integer>>();
        this.tempsMortsJoueursA = NB_TEMPS_MORTS_MAX;
        this.tempsMortsJoueursB = NB_TEMPS_MORTS_MAX;
    }

    /**
     * @brief Accesseur de l'attribut joueursA
     */
    public Vector<Joueur> getJoueursA()
    {
        return joueursA;
    }

    /**
     * @brief Accesseur de l'attribut joueursB
     */
    public Vector<Joueur> getJoueursB()
    {
        return joueursB;
    }

    /**
     * @brief Accesseur de l'attribut pointsJoueursA
     */
    public int getPointsJoueursA()
    {
        return pointsJoueursA;
    }

    /**
     * @brief Accesseur de l'attribut pointsJoueursB
     */
    public int getPointsJoueursB()
    {
        return pointsJoueursB;
    }

    /**
     * @brief Accesseur de l'attribut manchesJoueursA
     */
    public int getManchesJoueursA()
    {
        return manchesJoueursA;
    }

    /**
     * @brief Accesseur de l'attribut manchesJoueursB
     */
    public int getManchesJoueursB()
    {
        return manchesJoueursB;
    }

    /**
     * @brief Accesseur de l'attribut iD
     */
    public int getId()
    {
        return id;
    }

    /**
     * @brief Accesseur de l'attribut estFinie
     */
    public boolean estFinie()
    {
        return estFinie;
    }

    public int getNbPointsMaxMancheActuelle()
    {
        return nbPointsMaxMancheActuelle;
    }

    public Vector<Vector<Integer>> getManches()
    {
        return manches;
    }

    /**
     * @brief Méthode permettant d'incrémenter le score des joueursA
     */
    public void ajouterPointJoueursA()
    {
        pointsJoueursA++;
        gererPointsEcart();
        gererService();

        if (pointsJoueursA == nbPointsMaxMancheActuelle)
        {
            manchesJoueursA++;
            Vector<Integer> mancheActuelle = new Vector<Integer>();
            mancheActuelle.add(new Integer(pointsJoueursA));
            mancheActuelle.add(new Integer(pointsJoueursB));
            manches.add(mancheActuelle);
            pointsJoueursA = 0;
            pointsJoueursB = 0;
            nbPointsMaxMancheActuelle = nbPointsParManche;
            verifierPartieFinie();
        }
    }

    /**
     * @brief Méthode permettant d'incrémenter le score des joueursB
     */
    public void ajouterPointJoueursB()
    {
        pointsJoueursB++;
        gererPointsEcart();
        gererService();

        if (pointsJoueursB == nbPointsMaxMancheActuelle)
        {
            manchesJoueursB++;
            Vector<Integer> mancheActuelle = new Vector<Integer>();
            mancheActuelle.add(new Integer(pointsJoueursA));
            mancheActuelle.add(new Integer(pointsJoueursB));
            manches.add(mancheActuelle);
            pointsJoueursA = 0;
            pointsJoueursB = 0;
            nbPointsMaxMancheActuelle = nbPointsParManche;
            verifierPartieFinie();
        }
    }

    /**
     * @brief Méthode permettant de décrémenter le score des joueursA
     */
    public void retirerPointJoueursA()
    {
        if(pointsJoueursA != 0)
        {
            gererService();
            gererPointsEcart();
            pointsJoueursA--;
        }
    }

    /**
     * @brief Méthode permettant de décrémenter le score des joueursB
     */
    public void retirerPointJoueursB()
    {
        if(pointsJoueursB != 0)
        {
            gererService();
            gererPointsEcart();
            pointsJoueursB--;
        }
    }

    /**
     * @brief Méthode permettant de vérifier si la partie est finie
     */
    private void verifierPartieFinie()
    {
        if (manchesJoueursB == nbManchesGagnantes || manchesJoueursA == nbManchesGagnantes)
        {
            estFinie = true;
            Log.d(TAG,"Partie finie !");
        }
    }

    /**
     * @brief Méthode permettant de gérer les points d'écarts lors d'une manche
     */
    private void gererPointsEcart()
    {
        if (pointsJoueursA == pointsJoueursB && (pointsJoueursA == (nbPointsMaxMancheActuelle-1 )))
        {
            nbPointsMaxMancheActuelle++;
        }
        else if ( nbPointsMaxMancheActuelle > nbPointsParManche && pointsJoueursA == pointsJoueursB && (pointsJoueursA == (nbPointsMaxMancheActuelle-2 )))
        {
            nbPointsMaxMancheActuelle--;
        }
        Log.d(TAG,"nbPointsMaxMancheActuelle : " + Integer.toString(nbPointsMaxMancheActuelle));
    }

    /**
     * @brief Méthode permettant de gérer l'attribution du service
     */
    private void gererService()
    {
        int pointsMarques = pointsJoueursA + pointsJoueursB;
        if(pointsMarques % 2 == 0 && pointsMarques != 0)
        {
            if(joueursA.size() == 1)
            {
                changerServeurSimple();
            }
            else
            {
                changerServeurDouble();
            }
        }
        else if (joueursA.size() == 2)
        {
            Joueur serveur = null;
            serveur = trouverServeur(joueursA);
            if (serveur != null)
            {
                joueursA = permuterServeurRelanceurDuo(joueursA,serveur);
            }
            else
            {
                serveur = trouverServeur(joueursB);
                joueursB = permuterServeurRelanceurDuo(joueursB,serveur);
            }
        }
    }

    /**
     * @brief Méthode permettant de changer le serveur de duo en double
     */
    private void changerServeurDouble()
    {
        Joueur serveur = null;
        serveur = trouverServeur(joueursA);
        int indexServeur = 0;
        if (serveur != null)
        {
            indexServeur = joueursA.indexOf(serveur);
            joueursB.elementAt(indexServeur).setEstServeur(true);
            joueursA.elementAt(indexServeur).setEstServeur(false);
        }
        else
        {
            serveur = trouverServeur(joueursB);
            indexServeur = joueursB.indexOf(serveur);
            joueursA.elementAt(indexServeur).setEstServeur(true);
            joueursB.elementAt(indexServeur).setEstServeur(false);
        }
    }

    /**
     * @brief Méthode permettant de changer le serveur en simple
     */
    private void changerServeurSimple()
    {
        if (joueursA.elementAt(POSITION_PREMIER_JOUEUR).estServeur())
        {
            joueursA.elementAt(POSITION_PREMIER_JOUEUR).setEstServeur(false);
            joueursB.elementAt(POSITION_PREMIER_JOUEUR).setEstServeur(true);
        }
        else
        {
            joueursB.elementAt(POSITION_PREMIER_JOUEUR).setEstServeur(false);
            joueursA.elementAt(POSITION_PREMIER_JOUEUR).setEstServeur(true);
        }
    }

    /**
     * @brief Méthode permettant de changer le serveur au sein d'un duo en double
     */
    private Vector<Joueur> permuterServeurRelanceurDuo(Vector<Joueur> duo, Joueur serveur)
    {
        int indexServeur = 0;
        indexServeur = duo.indexOf(serveur);
        serveur.setEstServeur(false);
        if (indexServeur == POSITION_DEUXIEME_JOUEUR)
        {
            duo.setElementAt(serveur,POSITION_DEUXIEME_JOUEUR);
            duo.elementAt(POSITION_PREMIER_JOUEUR).setEstServeur(true);
        }
        else
        {
            duo.setElementAt(serveur,POSITION_PREMIER_JOUEUR);
            duo.elementAt(POSITION_DEUXIEME_JOUEUR).setEstServeur(true);
        }

        return duo;
    }

    /**
     * @brief Trouve le serveur dans un vecteur de joueurs puis retourne ce joueur ou null si il n'y en a aucun
     */
    private Joueur trouverServeur(Vector<Joueur> joueurs)
    {
        Iterator<Joueur> itJoueurs = joueurs.iterator();
        Joueur serveur;

        while(itJoueurs.hasNext())
        {
            serveur = itJoueurs.next();
            if (serveur.estServeur())
            {
                return serveur;
            }
        }
        return null;
    }

    /**
     * @brief Trouve le serveur et le retourne
     */
    public Joueur getServeur()
    {
        Joueur serveur = trouverServeur(joueursA);

        if(serveur == null)
        {
            serveur = trouverServeur(joueursB);
        }

        Log.d(TAG,"Serveur : " + serveur.getNom() + " " + serveur.getPrenom() );

        return serveur;
    }

    /**
     * @brief Retourne le nombre de joueurs dans la partie
     */
    public int getNbJoueurs()
    {
        return (joueursA.size() + joueursB.size());
    }

    /**
     * @brief Mutateur de l'attribut joueursA
     */
    public void setJoueursA(Vector<Joueur> joueursA)
    {
        this.joueursA = joueursA;
    }

    /**
     * @brief Mutateur de l'attribut joueursB
     */
    public void setJoueursB(Vector<Joueur> joueursB)
    {
        this.joueursB = joueursB;
    }

    /**
     * @brief Trouve le gagnant et le retourne, si il n'y en a pas retourne null
     */
    public Vector<Joueur> getVainqueur()
    {
        if (estFinie)
        {
            if (manchesJoueursA > manchesJoueursB)
                return joueursA;
            else
                return joueursB;
        }
        else
        {
            return null;
        }
    }

    /**
     * @brief Accesseur de l'attribut tempsMortsJoueursA
     */
    public int getTempsMortsJoueursA()
    {
        return tempsMortsJoueursA;
    }

    /**
     * @brief Accesseur de l'attribut tempsMortsJoueursB
     */
    public int getTempsMortsJoueursB()
    {
        return tempsMortsJoueursB;
    }

    /**
     * @brief Mutateur de l'attribut tempsMortsJoueursA
     */
    public void setTempsMortsJoueursA(int tempsMortsJoueursA)
    {
        this.tempsMortsJoueursA = tempsMortsJoueursA;
    }

    /**
     * @brief Mutateur de l'attribut tempsMortsJoueursB
     */
    public void setTempsMortsJoueursB(int tempsMortsJoueursB)
    {
        this.tempsMortsJoueursB = tempsMortsJoueursB;
    }

    public void setEstFinie(boolean estFinie)
    {
        this.estFinie = estFinie;
    }

    public void setManches(Vector<Vector<Integer>> manches)
    {
        this.manches = manches;

        Vector<Integer> manche = null;

        for (int i = 0; i < manches.size(); i++)
        {
            manche = this.manches.elementAt(i);
            if ((manche != null) && (manche.elementAt(0) > manche.elementAt(1)))
                manchesJoueursA++;
            else
                manchesJoueursB++;
        }
    }
}
