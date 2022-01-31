package com.example.area;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ListIterator;
import java.util.Vector;

/**
 * @file BaseDeDonnees.java
 * @brief Déclaration de la classe BaseDeDonnees
 * @author BRUNET Bastien
 * $LastChangedRevision: 125 $
 * $LastChangedDate: 2021-06-11 15:03:25 +0200 (ven. 11 juin 2021) $
 */

/**
 * @class BaseDeDonnees
 * @brief Classe permettant de manipuler la base de données
 */

public class BaseDeDonnees
{
    private static final String TAG = "_BaseDeDonnees";//<! TAG pour les logs
    private SQLiteDatabase bdd = null;//<! L'accès à la base de données
    private SQLiteAREA SQLiteAREA = null;//<! Permet l'initialisation de la base de données

    /**
     * Constantes
     */
    private static final int INDEX_ID_JOUEUR = 0;
    private static final int INDEX_NOM_JOUEUR = 2;
    private static final int INDEX_PRENOM_JOUEUR = 3;
    private static final int INDEX_ID_EQUIPE = 0;
    private static final int INDEX_NOM_CLUB = 1;

    private static final int INDEX_ID_PARTIE = 0;
    private static final int INDEX_ID_RENCONTRE = 1;
    private static final int INDEX_ID_JOUEUR_A = 2;
    private static final int INDEX_ID_JOUEUR_B = 3;
    private static final int INDEX_ID_JOUEUR_W = 4;
    private static final int INDEX_ID_JOUEUR_X = 5;
    private static final int INDEX_NB_MANCHES_GAGNANTES = 6;
    private static final int INDEX_NB_POINTS_PAR_MANCHES= 7;
    private static final int INDEX_TYPE_PARTIE = 8;
    private static final int INDEX_FIN_PARTIE = 10;

    private static final int TYPE_PARTIE_SIMPLE = 1;
    private static final int TYPE_PARTIE_DOUBLE = 2;

    private static final String DEBUT_REQUETE_INSERTION_PARTIE =  "INSERT INTO Partie(idPartie, idRencontre, idJoueurA, idJoueurB, idJoueurW, idJoueurX, nbManchesGagnantes, nbPointsParManche, typePartie, debut) VALUES (NULL,";
    private static final String DEBUT_REQUETE_TERMINER_PARTIE = "UPDATE Partie SET fin=DATETIME('now') WHERE idPartie=";
    private static final String DEBUT_REQUETE_INSERTION_RENCONTRE = "INSERT INTO Rencontre(idRencontre, idEquipeA, idEquipeB, nbPartiesGagnantes, estFinie, horodatage) VALUES (NULL,";
    private static final String FIN_REQUETE_INSERTION_RENCONTRE = "0,DATETIME('now'))";
    private static final String REQUETE_ID_RENCONTRE = "SELECT MAX(idRencontre) FROM Rencontre";
    private static final String REQUETE_ID_EQUIPE = "SELECT MAX(idEquipe) FROM Equipe";
    private static final String DEBUT_REQUETE_INSERTION_JOUEUR = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (";
    private static final String DEBUT_REQUETE_PRESENCE_JOUEUR = "SELECT * FROM Joueur WHERE numeroLicence='";
    private static final String DEBUT_REQUETE_INSERTION_EQUIPE = "INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,";
    private static final String DEBUT_REQUETE_ID_EQUIPE = "SELECT idEquipe FROM Equipe WHERE nomClub = '";

    /**
     * @brief Constructeur de la classe BaseDeDonnees
     * @param context le contexte dans lequel l'objet est créé
     */
    public BaseDeDonnees(Context context)
    {
        this.SQLiteAREA = new SQLiteAREA(context);
    }

    /**
     * @brief Ouvre un accés à la base de données
     */
    private void ouvrir()
    {
        Log.d(TAG, "ouvrir()");
        if (bdd == null)
            bdd = SQLiteAREA.getWritableDatabase();
    }

    /**
     * @brief Ferme l'accés à la base de données
     */
    private void fermer()
    {
        Log.d(TAG, "fermer()");
        if (bdd != null)
            if (bdd.isOpen())
                bdd.close();
    }

    /**
     * @brief Permet d'effectuer une requete sur la base de données
     * @param requete la requete a éffectuer
     */
    private Cursor effectuerRequete(String requete)
    {
        ouvrir();

        Cursor curseurResultat = bdd.rawQuery(requete,null);

        Log.d(TAG,"effectuerRequete() : Exécution de la requete : " + requete);

        Log.d(TAG,"effectuerRequete() : Nombre de résultats : " + Integer.toString(curseurResultat.getCount()));

        return curseurResultat;
    }

    /**
     * @brief Permet d'effectuer une requete pour récupérer toutes les équipes
     * @return Les objets equipes ainsi récupérés
     */
    public Vector<Equipe> getEquipes()
    {
        Vector<Equipe> equipes = new Vector<Equipe>();
        String requete = "SELECT * FROM Equipe";

        Cursor curseurResultat = effectuerRequete(requete);

        for (int i = 0; i < curseurResultat.getCount(); i++)
        {
            curseurResultat.moveToNext();
            Log.d(TAG,"idEquipe = " + curseurResultat.getString(INDEX_ID_EQUIPE) + " | " + "nomClub = " + curseurResultat.getString(INDEX_NOM_CLUB));
            equipes.add(new Equipe(curseurResultat.getString(INDEX_NOM_CLUB),getJoueurEquipe(curseurResultat.getInt(INDEX_ID_EQUIPE))));
        }

        return equipes;
    }

    /**
     * @brief Permet d'effectuer une requete pour récupérer tout les joueurs d'une équipe
     * @param idEquipe L'identifiant de l'équipe en question
     * @return Les objets joueurs ainsi récupérés
     */
    private Vector<Joueur> getJoueurEquipe(int idEquipe)
    {
        Vector<Joueur> joueurs = new Vector<Joueur>();
        String requete = "SELECT * FROM Joueur WHERE idEquipe = " + Integer.toString(idEquipe);

        Cursor curseurResultat = effectuerRequete(requete);

        for (int i = 0; i < curseurResultat.getCount(); i++)
        {
            curseurResultat.moveToNext();
            Log.d(TAG,"numeroLicence = " + curseurResultat.getString(INDEX_ID_JOUEUR) + " | " + "nom = " + curseurResultat.getString(INDEX_NOM_JOUEUR) + " | " + "prenom = " + curseurResultat.getString(INDEX_PRENOM_JOUEUR));
            joueurs.add(new Joueur(curseurResultat.getString(INDEX_NOM_JOUEUR),curseurResultat.getString(INDEX_PRENOM_JOUEUR),curseurResultat.getInt(INDEX_ID_JOUEUR)));
        }

        return joueurs;
    }

    /**
     * @brief Permet d'effectuer une requete pour récupérer un joueur grâce à son numéro de licence
     * @param numeroLicence L'identifiant de l'équipe en question
     * @return Le joueur ainsi récupérés
     */
    private Joueur getJoueur(int numeroLicence)
    {
        Joueur joueur;
        String requete = "SELECT * FROM Joueur WHERE numeroLicence = " + Integer.toString(numeroLicence);
        Cursor curseurResultat = effectuerRequete(requete);

        if(curseurResultat.getCount() == 0)
            return null;

        curseurResultat.moveToNext();
        Log.d(TAG,"numeroLicence = " + curseurResultat.getString(INDEX_ID_JOUEUR) + " | " + "nom = " + curseurResultat.getString(INDEX_NOM_JOUEUR) + " | " + "prenom = " + curseurResultat.getString(INDEX_PRENOM_JOUEUR));
        joueur = new Joueur(curseurResultat.getString(INDEX_NOM_JOUEUR),curseurResultat.getString(INDEX_PRENOM_JOUEUR),curseurResultat.getInt(INDEX_ID_JOUEUR));

        return joueur;
    }

    /**
     * @brief Permet d'effectuer une requete pour récupérer les parties d'une rencontre
     * @param idRencontre L'identifiant de la rencontre
     * @return Les parties récupérées
     */
    public Vector<Partie> getParties(int idRencontre)
    {
        Vector<Partie> parties = new Vector<Partie>();
        Vector<Joueur> joueursA = new Vector<Joueur>();
        Vector<Joueur> joueursB = new Vector<Joueur>();

        String requete = "SELECT * FROM Partie WHERE idRencontre = " + Integer.toString(idRencontre);

        Cursor curseurResultat = effectuerRequete(requete);

        for (int i = 0; i < curseurResultat.getCount(); i++)
        {
            curseurResultat.moveToNext();
            Log.d(TAG,"idPartie = " + curseurResultat.getString(INDEX_ID_PARTIE) + " | " + "idRencontre = " + curseurResultat.getString(INDEX_ID_RENCONTRE) + " | " + "idJoueurA = " + curseurResultat.getString(INDEX_ID_JOUEUR_A) + " | " + " idJoueurB = " + curseurResultat.getString(INDEX_ID_JOUEUR_B) + " | " + " idJoueurW = " + curseurResultat.getString(INDEX_ID_JOUEUR_W)+ " | " + " idJoueurX = " + curseurResultat.getString(INDEX_ID_JOUEUR_X) + " | " + " typePartie = " + curseurResultat.getString(INDEX_TYPE_PARTIE) + " | " + " fin = " + curseurResultat.getString(INDEX_FIN_PARTIE));

            joueursA.add(getJoueur(curseurResultat.getInt(INDEX_ID_JOUEUR_A)));
            joueursB.add(getJoueur(curseurResultat.getInt(INDEX_ID_JOUEUR_W)));

            if (curseurResultat.getInt(INDEX_TYPE_PARTIE) == TYPE_PARTIE_DOUBLE)
            {
                joueursA.add(getJoueur(curseurResultat.getInt(INDEX_ID_JOUEUR_B)));
                joueursB.add(getJoueur(curseurResultat.getInt(INDEX_ID_JOUEUR_X)));
            }

            parties.add(new Partie(curseurResultat.getInt(INDEX_NB_MANCHES_GAGNANTES),curseurResultat.getInt(INDEX_NB_POINTS_PAR_MANCHES),(Vector<Joueur>)joueursA.clone(),(Vector<Joueur>)joueursB.clone(),curseurResultat.getInt(INDEX_ID_PARTIE)));

            if (curseurResultat.getString(INDEX_FIN_PARTIE) != null)
                parties.lastElement().setEstFinie(true);

            parties.lastElement().setManches(getSetsPartie(parties.lastElement().getId()));

            joueursA.clear();
            joueursB.clear();
        }

        return parties;
    }

    /**
     * @brief Permet d'effectuer une requete pour insérer les parties d'une rencontre
     * @param rencontre La rencontre en question
     */
    public void insererParties(Rencontre rencontre)
    {
        Vector<Partie> parties = rencontre.getParties();
        ListIterator<Partie> it = parties.listIterator();
        String requete = "";
        Partie partie = null;

        while(it.hasNext())
        {
            partie = it.next();
            if (partie.getJoueursA().size() > 1)
                requete = DEBUT_REQUETE_INSERTION_PARTIE + Integer.toString(rencontre.getId()) +  "," + Integer.toString(partie.getJoueursA().elementAt(Partie.POSITION_PREMIER_JOUEUR).getNumLicence()) + "," + Integer.toString(partie.getJoueursA().elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getNumLicence()) + "," + Integer.toString(partie.getJoueursB().elementAt(Partie.POSITION_PREMIER_JOUEUR).getNumLicence()) + "," + Integer.toString(partie.getJoueursB().elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getNumLicence()) + "," + Integer.toString(rencontre.getNbManchesGagnantes()) + "," + Integer.toString(rencontre.getNbPointsParManche()) + ",2," + "'2021-01-29 08:15:00')";
            else
                requete = DEBUT_REQUETE_INSERTION_PARTIE + Integer.toString(rencontre.getId()) +  "," + Integer.toString(partie.getJoueursA().elementAt(Partie.POSITION_PREMIER_JOUEUR).getNumLicence()) + "," + Integer.toString(partie.getJoueursA().elementAt(Partie.POSITION_PREMIER_JOUEUR).getNumLicence()) + "," + Integer.toString(partie.getJoueursB().elementAt(Partie.POSITION_PREMIER_JOUEUR).getNumLicence()) + "," + Integer.toString(partie.getJoueursB().elementAt(Partie.POSITION_PREMIER_JOUEUR).getNumLicence()) + "," + Integer.toString(rencontre.getNbManchesGagnantes()) + "," + Integer.toString(rencontre.getNbPointsParManche()) + ",1," + "'2021-01-29 08:15:00')";

            Log.d(TAG,"insererParties() : " + requete);
            ouvrir();
            bdd.execSQL(requete);
        }
    }

    /**
     * @brief Permet d'effectuer une requete pour récupérer l'identifiant d'une équipe grâce à son nom
     * @param nomEquipe Le nom de l'équipe en question
     * @return L'id de l'équipe
     */
    public int getIdEquipe(String nomEquipe)
    {
        Cursor curseurResultat = effectuerRequete(DEBUT_REQUETE_ID_EQUIPE + nomEquipe + "'");
        curseurResultat.moveToNext();
        if (curseurResultat.getCount() == 1)
            return (curseurResultat.getInt(0));
        return -1;
    }

    /**
     * @brief Permet d'effectuer une requete pour insérer une équipe
     * @param equipe L'équipe à insérer
     * @return L'équipe insérée avec son identifiant mis à jour
     */
    public Equipe insererEquipe(Equipe equipe)
    {
        ouvrir();
        bdd.execSQL(DEBUT_REQUETE_INSERTION_EQUIPE + equipe.getNomClub() + "'");
        Cursor curseurResultat = effectuerRequete(REQUETE_ID_EQUIPE);
        curseurResultat.moveToNext();
        equipe.setId(curseurResultat.getInt(0));

        return equipe;
    }

    /**
     * @brief Permet d'effectuer une pour vérifier la présence d'un joueur en BDD
     * @param idJoueur L'id du joueur
     * @return true si présent, false sinon
     */
    public boolean verifierPresenceJoueur(int idJoueur)
    {
        Cursor curseurResultat = effectuerRequete(DEBUT_REQUETE_PRESENCE_JOUEUR + idJoueur + "'");
        if (curseurResultat.getCount() == 0)
            return false;
        return true;
    }

    /**
     * @brief Permet d'effectuer une requete pour insérer un joueur
     * @param joueur Le joueur à insérer
     * @param equipe L'équipe dont le joueur fait partie
     */
    public void insererJoueur(Joueur joueur,Equipe equipe)
    {
        ouvrir();
        bdd.execSQL(DEBUT_REQUETE_INSERTION_JOUEUR + Integer.toString(joueur.getNumLicence()) + "," + Integer.toString(equipe.getId()) + ",'" + joueur.getNom() + "','" + joueur.getPrenom() + "')");
    }

    /**
     * @brief Permet d'effectuer une requete pour vérifier la présence d'une équipe en BDD et l'insère si elle n'est pas dèja présente
     * @param equipe L'équipe
     * @return L'équipe insérée
     */
    private Equipe verifierPresenceEquipe(Equipe equipe)
    {
        if (getIdEquipe(equipe.getNomClub()) == -1)
        {
            Log.d(TAG,"insererRencontre() Nouvelle équipe");
            equipe = insererEquipe(equipe);
        }

        return equipe;
    }

    /**
     * @brief Permet d'effectuer une requete pour insérer les joueurs d'une équipe
     * @param equipe L'équipe des joueurs à insérer
     */
    private void insererJoueursEquipe(Equipe equipe)
    {
        for (int i=0; i < equipe.getJoueurs().size(); i++)
        {
            if (!verifierPresenceJoueur(equipe.getJoueurs().elementAt(i).getNumLicence()))
                insererJoueur(equipe.getJoueurs().elementAt(i),equipe);
        }
    }

    /**
     * @brief Permet d'effectuer une requete pour insérer une rencontre
     * @param rencontre La rencontre à insérer
     * @return La rencontre insérée avec son identifiant mis à jour
     */
    public Rencontre insererRencontre(Rencontre rencontre)
    {
        String requete = "";

        rencontre.setEquipeA(verifierPresenceEquipe(rencontre.getEquipeA()));
        rencontre.setEquipeB(verifierPresenceEquipe(rencontre.getEquipeB()));

        insererJoueursEquipe(rencontre.getEquipeA());
        insererJoueursEquipe(rencontre.getEquipeB());

        requete = DEBUT_REQUETE_INSERTION_RENCONTRE + Integer.toString(rencontre.getEquipeA().getId()) + "," + Integer.toString(rencontre.getEquipeB().getId()) + "," + Integer.toString(rencontre.getNbPartiesGagnantes()) + "," + FIN_REQUETE_INSERTION_RENCONTRE;
        bdd.execSQL(requete);
        Cursor curseurResultat = effectuerRequete(REQUETE_ID_RENCONTRE);
        curseurResultat.moveToNext();
        rencontre.setId(curseurResultat.getInt(0));

        Log.d(TAG,"insererRencontre() : Insertion de la rencontre avec l'ID : " + Integer.toString(rencontre.getId()));

        return rencontre;
    }

    /**
     * @brief Permet d'effectuer une requete terminer une partie
     * @param idPartie La partie à terminer
     */
    public void terminerPartie(int idPartie)
    {
        String requete = DEBUT_REQUETE_TERMINER_PARTIE + Integer.toString(idPartie);
        bdd.execSQL(requete);
    }

    /**
     * @brief Permet d'effectuer une requete pour commencer un set
     * @param partie La partie dont le set fait parti
     */
    public void commencerSet(Partie partie)
    {
        Log.d(TAG,"commencerSet()");
        ouvrir();
        String requete = "SELECT * FROM SCORE WHERE idPartie=" + Integer.toString(partie.getId()) + " AND numeroSet=" + Integer.toString(partie.getManches().size());
        // INSERT INTO Score(idPartie,numeroSet, pointsJoueurEquipeA, pointsJoueurEquipeB, debut) VALUES (1,3,0,0,DATETIME('now'));
        Cursor curseurResultat = effectuerRequete(requete);
        if (curseurResultat.getCount() == 0)
        {
            requete = "INSERT INTO Score(idPartie,numeroSet, pointsJoueurEquipeA, pointsJoueurEquipeB, debut) VALUES (" + Integer.toString(partie.getId()) + "," + Integer.toString(partie.getManches().size()) + ",0,0,DATETIME('now'))";
            Log.d(TAG, "commencerSet() : " + requete);
            bdd.execSQL(requete);
        }
    }

    /**
     * @brief Permet d'effectuer une requete pour terminer un set
     * @param partie La partie dont le set fait parti
     */
    public void terminerSet(Partie partie)
    {
        Log.d(TAG,"terminerSet()");
        ouvrir();
        // UPDATE Score SET fin=DATETIME('now'), pointsJoueurEquipeA=11, pointsJoueurEquipeB='9' WHERE idPartie='1' AND numeroSet='3';
        String requete = "UPDATE Score SET fin=DATETIME('now'),pointsJoueurEquipeA=" + Integer.toString(partie.getManches().lastElement().elementAt(0)) + ",pointsJoueurEquipeB=" + Integer.toString(partie.getManches().lastElement().elementAt(1)) + " WHERE idPartie=" + Integer.toString(partie.getId()) + " AND numeroSet=" + Integer.toString(partie.getManches().size()-1) +"";
        Log.d(TAG,"terminerSet() : " + requete);
        bdd.execSQL(requete);
    }

    /**
     * @brief Permet d'effectuer une requete pour réuperer les sets d'une partie
     * @param idPartie L'id de la partie
     * @return Les sets récupérés
     */
    public Vector<Vector<Integer>> getSetsPartie(int idPartie)
    {
        Log.d(TAG,"getSetsPartie()");
        ouvrir();
        Vector<Vector<Integer>> manches = new Vector<>();
        Vector<Integer> mancheActuelle = new Vector<Integer>();
        String requete = "SELECT pointsJoueurEquipeA,pointsJoueurEquipeB FROM Score WHERE idPartie=" + Integer.toString(idPartie);
        Cursor curseurResultat = effectuerRequete(requete);

        for (int i= 0; i < curseurResultat.getCount(); i++)
        {
            curseurResultat.moveToNext();
            Log.d(TAG,"getSetsPartie() : Score joueurA = " + curseurResultat.getString(0));
            Log.d(TAG,"getSetsPartie() : Score joueurB = " + curseurResultat.getString(1));
            mancheActuelle.add(curseurResultat.getInt(0));
            mancheActuelle.add(curseurResultat.getInt(1));
            manches.add((Vector<Integer>) mancheActuelle.clone());
            mancheActuelle.clear();
        }

        return manches;
    }
}