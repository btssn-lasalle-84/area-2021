package com.example.area;

import java.io.Serializable;
import java.util.Vector;

/**
 * @file Rencontre.java
 * @brief Déclaration de la classe Rencontre
 * @author BRUNET Bastien
 * $LastChangedRevision: 116 $
 * $LastChangedDate: 2021-06-10 09:03:59 +0200 (jeu. 10 juin 2021) $
 */

/**
 * @class Rencontre
 * @brief Classe qui permet la gestion d'une rencontre entre deux équipes
 */
public class Rencontre implements Serializable
{
    /**
     * Attributs
     */
    private Vector<Equipe> equipes; //!< les deux équipes
    private Vector<Partie> parties; //!< les parties d'une rencontre
    private int id = 1; //!< l'identifiant de la rencontre
    private boolean estFinie = false; //!< l'état de la rencontre
    private int nbManchesGagnantes; //!< nombre de manches (ou sets) pour remporter une Partie
    private int nbPointsParManche; //!< nombre de points pour remporter une manche (ou set)
    private int nbPartiesGagnantes; //!< nombre de parties à gagner pour remporter la rencontre
    /**
     * Constantes
     */
    public static final int INDEX_EQUIPE_A = 0;//!< Index de l'équipe A dans le Vector Equipe
    public static final int INDEX_EQUIPE_B = 1;//!< Index de l'équipe B dans le Vector Equipe
    public static final int NB_MANCHES_GAGNANTES_DEFAUT = 3; //!< nombre de manches (ou sets) par défaut pour remporter une Partie
    public static final int NB_POINTS_PAR_MANCHE_DEFAUT = 11; //!< nombre de points par défaut pour remporter une manche (ou set)
    public static final int NB_PARTIES_GAGNANTES = 7; //!< nombre de parties à gagner par défaut pour remporter la rencontre
    private static final String TAG = "_Rencontre";//TAG pour les logs

    /**
     * @brief Constructeur pour une rencontre avec les paramètres de partie par défaut
     * @param equipeA
     * @param equipeB
     */
    public Rencontre(Equipe equipeA, Equipe equipeB)
    {
        this.nbManchesGagnantes = NB_MANCHES_GAGNANTES_DEFAUT;
        this.nbPointsParManche = NB_POINTS_PAR_MANCHE_DEFAUT;
        this.nbPartiesGagnantes = NB_PARTIES_GAGNANTES;

        initialiserRencontre(equipeA, equipeB);

        initialiserParties();
    }

    /**
     * @brief Constructeur
     * @param equipeA
     * @param equipeB
     * @param nbManchesGagnantes nombre de manches (ou sets) pour remporter une Partie
     * @param nbPointsParManche nombre de points pour remporter une manche (ou set)
     */
    public Rencontre(Equipe equipeA, Equipe equipeB, int nbManchesGagnantes, int nbPointsParManche, int nbPartiesGagnantes)
    {
        this.nbManchesGagnantes = nbManchesGagnantes;
        this.nbPointsParManche = nbPointsParManche;
        this.nbPartiesGagnantes = nbPartiesGagnantes;

        initialiserRencontre(equipeA, equipeB);

        initialiserParties();
    }

    /**
     * @brief Initialise une rencontre entre deux équipes
     */
    private void initialiserRencontre(Equipe equipeA, Equipe equipeB)
    {
        this.equipes = new Vector<Equipe>();
        this.equipes.add(equipeA);
        this.equipes.add(equipeB);
    }

    /**
     * @brief Initialise les parties selon l'ordre d'un feuille de rencontre
     */
    private void initialiserParties()
    {
        this.parties = new Vector<Partie>();

        initialiserPartieSimple(Equipe.INDEX_JOUEUR_A, Equipe.INDEX_JOUEUR_W, 0);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_B, Equipe.INDEX_JOUEUR_X, 1);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_C, Equipe.INDEX_JOUEUR_Y, 2);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_D, Equipe.INDEX_JOUEUR_Z, 3);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_A, Equipe.INDEX_JOUEUR_X, 4);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_B, Equipe.INDEX_JOUEUR_W, 5);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_D, Equipe.INDEX_JOUEUR_Y, 6);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_C, Equipe.INDEX_JOUEUR_Z, 7);
        initialiserPartieDouble(Equipe.INDEX_JOUEUR_A, Equipe.INDEX_JOUEUR_B, Equipe.INDEX_JOUEUR_W, Equipe.INDEX_JOUEUR_Y, 8);
        initialiserPartieDouble(Equipe.INDEX_JOUEUR_C, Equipe.INDEX_JOUEUR_D, Equipe.INDEX_JOUEUR_X, Equipe.INDEX_JOUEUR_Z, 9);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_D, Equipe.INDEX_JOUEUR_W, 10);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_C, Equipe.INDEX_JOUEUR_X, 11);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_A, Equipe.INDEX_JOUEUR_Z, 12);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_B, Equipe.INDEX_JOUEUR_Y, 13);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_C, Equipe.INDEX_JOUEUR_W, 14);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_D, Equipe.INDEX_JOUEUR_X, 15);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_A, Equipe.INDEX_JOUEUR_Y, 16);
        initialiserPartieSimple(Equipe.INDEX_JOUEUR_B, Equipe.INDEX_JOUEUR_Z, 17);
    }

    /**
     * @brief Initialise une partie en simple
     * @param indexJoueurA l'index du joueur dans l'équipe A
     * @param indexJoueurB l'index du joueur dans l'équipe B
     */
    private void initialiserPartieSimple(int indexJoueurA, int indexJoueurB, int id)
    {
        Vector<Joueur> joueursPartiesA = new Vector<Joueur>();
        Vector<Joueur> joueursPartiesB = new Vector<Joueur>();

        Vector<Joueur> joueursEquipeA = this.equipes.get(INDEX_EQUIPE_A).getJoueurs();
        Vector<Joueur> joueursEquipeB = this.equipes.get(INDEX_EQUIPE_B).getJoueurs();

        joueursPartiesA.add(joueursEquipeA.elementAt(indexJoueurA));
        joueursPartiesB.add(joueursEquipeB.elementAt(indexJoueurB));
        parties.add(new Partie(this.nbManchesGagnantes, this.nbPointsParManche, joueursPartiesA, joueursPartiesB, id));
    }

    /**
     * @brief Initialise une partie en double
     * @param indexJoueurA l'index du joueur dans l'équipe A
     * @param indexDeuxiemeJoueurA l'index du deuxième joueur dans l'équipe A
     * @param indexJoueurB l'index du joueur dans l'équipe B
     * @param indexDeuxiemeJoueurB l'index du deuxième joueur dans l'équipe B
     */
    private void initialiserPartieDouble(int indexJoueurA, int indexDeuxiemeJoueurA, int indexJoueurB, int indexDeuxiemeJoueurB, int id)
    {
      Vector<Joueur> joueursPartiesA = new Vector<Joueur>();
      Vector<Joueur> joueursPartiesB = new Vector<Joueur>();

      Vector<Joueur> joueursEquipeA = this.equipes.get(INDEX_EQUIPE_A).getJoueurs();
      Vector<Joueur> joueursEquipeB = this.equipes.get(INDEX_EQUIPE_B).getJoueurs();

      joueursPartiesA.add(joueursEquipeA.elementAt(indexJoueurA));
      joueursPartiesA.add(joueursEquipeA.elementAt(indexDeuxiemeJoueurA));
      joueursPartiesB.add(joueursEquipeB.elementAt(indexJoueurB));
      joueursPartiesB.add(joueursEquipeB.elementAt(indexDeuxiemeJoueurB));
      parties.add(new Partie(this.nbManchesGagnantes, this.nbPointsParManche, joueursPartiesA, joueursPartiesB, id));
    }

    /**
     * @brief Ajoute un point à une équipe donnée
     * @param indexEquipe l'index de l'équipe à laquelle ajouter le point
     */
    public void ajouterPointEquipe(int indexEquipe)
    {
        equipes.elementAt(indexEquipe).incrementerScore();

        if (equipes.elementAt(indexEquipe).getNbPartiesGagnees() == this.nbPartiesGagnantes)
        {
            this.estFinie = true;
        }
    }

    /**
     * @brief Accesseur de l'attribut parties
     * @return Vector<Partie> les parties de la rencontre
     */
    public Vector<Partie> getParties()
    {
        return parties;
    }

    /**
     * @brief Accesseur de l'attribut equipeA
     * @return Equipe l'équipe A
     */
    public Equipe getEquipeA()
    {
        return this.equipes.get(INDEX_EQUIPE_A);
    }

    /**
     * @brief Accesseur de l'attribut equipeB
     * @return Equipe l'équipe B
     */
    public Equipe getEquipeB()
    {
        return this.equipes.get(INDEX_EQUIPE_B);
    }

    /**
     * @brief Accesseur de l'attribut estFinie
     * @return boolean
     */
    public boolean estFinie()
    {
        return this.estFinie;
    }

    /**
     * @brief Accesseur de l'attribut nbManchesGagnantes
     * @return int
     */
    public int getNbManchesGagnantes()
    {
        return this.nbManchesGagnantes;
    }

    /**
     * @brief Accesseur de l'attribut nbPartiesGagnantes
     * @return int
     */
    public int getNbPartiesGagnantes()
    {
        return this.nbPartiesGagnantes;
    }

    /**
     * @brief Accesseur de l'attribut nbPointsParManche
     * @return int
     */
    public int getNbPointsParManche()
    {
        return this.nbPointsParManche;
    }

    public int getId()
    {
        return id;
    }

    public void setParties(Vector<Partie> parties)
    {
        this.parties = parties;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setEquipeA(Equipe equipe)
    {
        equipes.setElementAt(equipe,INDEX_EQUIPE_A);
    }

    public void setEquipeB(Equipe equipe)
    {
        equipes.setElementAt(equipe,INDEX_EQUIPE_B);
    }
}
