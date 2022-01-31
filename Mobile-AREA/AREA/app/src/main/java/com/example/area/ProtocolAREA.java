package com.example.area;

/**
 * @file ProtocolAREA.java
 * @brief Déclaration de la classe ProtocolAREA
 * @author BRUNET Bastien
 * $LastChangedRevision: 129 $
 * $LastChangedDate: 2021-06-22 15:05:53 +0200 (mar. 22 juin 2021) $
 */

import android.util.Log;

import java.util.Vector;

/**
 * @class ProtocolAREA
 * @brief Les détails du protocole AREA
 */
public class ProtocolAREA
{
    /**
     * Constantes
     */
    private static final String TAG = "_ProtocoleAREA";  //!< TAG pour les logs
    public static final String NOM_MODULE_NET = "NET_AREA";//!< nom du module NET
    public static final String NOM_MODULE_AFFICHEUR_AREA = "raspberrypi";//!< nom du module AFFICHEUR
    public static final String NOM_MODULE_SCORE_AREA = "SCORE_AREA";//!< nom du module SCORE
    public static final String ADRESSE_MODULE_NET = "24:6F:28:7B:E1:06";//!< addrese MAC du module NET
    public static final String ADRESSE_MODULE_AFFICHEUR = "DC:A6:32:52:7D:B5";//!< addrese MAC du module AFFICHEUR
    public static final String DELIMITEUR_CHAMP = ";";//!< le délimiteur permettant de séparer les champs des trames
    public static final String DELIMITEUR_FIN = "\r\n";//!< le délimiteur de fin de trame
    public static final String DEBUT_TRAME = "MOBILE_AREA";//!< début des trame dont l'application est expéditrice
    // MODULE_NET
    public static final String TRAME_SERVICE = "MOBILE_AREA;SERVICE\r\n";//!< la trame d'initialistaion du mode détection
    // MODULE_AFFICHEUR
    public static final int TRAME_AFFICHEUR_RENCONTRE = 0;//!< code pour une trame afficheur rencontre
    public static final int TRAME_AFFICHEUR_INFO_PARTIE = 1;//!< code pour une trame afficheur d'information de partie
    public static final int TRAME_AFFICHEUR_SCORE = 2;//!< code pour une trame afficheur de score
    public static final int TRAME_AFFICHEUR_ETAT_PARTIE = 3;//!< code pour une trame afficheur d'état de partie
    public static final int TRAME_AFFICHEUR_NET = 4;//!< code pour une trame afficheur NET
    //MODULE_SCORE
    public static final int TRAME_SCORE_POSITION = 0;//!< code pour une trame Score de position
    public static final String POSITION_GAUCHE = "GAUCHE";
    public static final String POSITION_DROITE = "DROITE";

    /**
     * Utilitaires
     */

    /**
     * @brief Méthode permettant de fabiquer les trames à destination du module Afficheur
     * @param typeTrame Code défininissant le type de trame à fabriquer
     * @param partie La partie utilisée pour fabriquer la trame
     * @return La trame fabriquée par la méthode
     */
    public static String fabriquerTrameAfficheur(int typeTrame, Partie partie)
    {
        String trame = DEBUT_TRAME;

        switch (typeTrame)
        {
            case TRAME_AFFICHEUR_INFO_PARTIE:
                /**
                 * MOBILE_AREA;1;ID_PARTIE;NOM_JOUEUR_A;PRENOM_JOUEUR_A;[NOM_DEUXIEME_JOUEUR_A];[PRENOM_DEUXIEME_JOUEUR_A];NOM_JOUEUR_B;PRENOM_JOUEUR_B;
                 * [NOM_DEUXIEME_JOUEUR_B];[PRENOM_DEUXIEME_JOUEUR_B]\r\n
                 */
                Vector<Joueur> joueursA = partie.getJoueursA();
                Vector<Joueur> joueursB = partie.getJoueursB();
                if (joueursA.size() > 1)
                    trame += DELIMITEUR_CHAMP + TRAME_AFFICHEUR_INFO_PARTIE + DELIMITEUR_CHAMP + partie.getId() + DELIMITEUR_CHAMP + joueursA.elementAt(Partie.POSITION_PREMIER_JOUEUR).getNom() + DELIMITEUR_CHAMP +
                            joueursA.elementAt(Partie.POSITION_PREMIER_JOUEUR).getPrenom() + DELIMITEUR_CHAMP + joueursA.elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getNom() + DELIMITEUR_CHAMP +
                            joueursA.elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getPrenom() + DELIMITEUR_CHAMP + joueursB.elementAt(Partie.POSITION_PREMIER_JOUEUR).getNom() + DELIMITEUR_CHAMP +
                            joueursB.elementAt(Partie.POSITION_PREMIER_JOUEUR).getPrenom() + DELIMITEUR_CHAMP + joueursB.elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getNom() + DELIMITEUR_CHAMP +
                            joueursB.elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getPrenom() + DELIMITEUR_FIN;
                else
                    trame += DELIMITEUR_CHAMP + TRAME_AFFICHEUR_INFO_PARTIE + DELIMITEUR_CHAMP + partie.getId() + DELIMITEUR_CHAMP + joueursA.elementAt(Partie.POSITION_PREMIER_JOUEUR).getNom() + DELIMITEUR_CHAMP +
                            joueursA.elementAt(Partie.POSITION_PREMIER_JOUEUR).getPrenom() + DELIMITEUR_CHAMP + joueursB.elementAt(Partie.POSITION_PREMIER_JOUEUR).getNom() + DELIMITEUR_CHAMP +
                            joueursB.elementAt(Partie.POSITION_PREMIER_JOUEUR).getPrenom() + DELIMITEUR_FIN;
                break;
            case TRAME_AFFICHEUR_SCORE:
                /**
                 * MOBILE_AREA;2;ID_PARTIE;POINTS_JOUEUR_A;POINTS_JOUEUR_B;
                 * NB_MANCHES_GAGNEES_JOUEUR_A;NB_MANCHES_GAGNEES_JOUEUR_B\r\n
                 */
                trame += DELIMITEUR_CHAMP + TRAME_AFFICHEUR_SCORE + DELIMITEUR_CHAMP + partie.getId() + DELIMITEUR_CHAMP + partie.getPointsJoueursA() +
                        DELIMITEUR_CHAMP + partie.getPointsJoueursB() + DELIMITEUR_CHAMP + partie.getManchesJoueursA() + DELIMITEUR_CHAMP +
                        partie.getManchesJoueursB() + DELIMITEUR_FIN;
                break;
            case TRAME_AFFICHEUR_ETAT_PARTIE:
                /**
                 * MOBILE_AREA;3;ID_PARTIE;ETAT\r\n
                 * Le champs état peut prendre deux valeurs :
                 * - La partie est en cours : DEMARREE
                 * - La partie est en terminée : TERMINEE
                 */
                String etat = "DEMARREE";
                if (partie.estFinie())
                    etat = "TERMINEE";
                trame += DELIMITEUR_CHAMP + TRAME_AFFICHEUR_ETAT_PARTIE + DELIMITEUR_CHAMP + partie.getId() + DELIMITEUR_CHAMP + etat + DELIMITEUR_FIN;
                break;
            case TRAME_AFFICHEUR_NET:
                /**
                 * MOBILE_AREA;4;ID_PARTIE\r\n
                 */
                trame += DELIMITEUR_CHAMP + TRAME_AFFICHEUR_NET + DELIMITEUR_CHAMP + partie.getId() + DELIMITEUR_FIN;
                break;
        }

        Log.d(TAG, "fabriquerTrameAfficheur() trame = " + trame);

        return trame;
    }

    /**
     * @brief Méthode permettant de fabiquer les trames à destination du module Afficheur dédiée spécifiquement aux la trame concernant une rencontre
     * @param rencontre La rencontre utilisée dans la trame qui va etre fabriquée
     * @return La trame fabriquée par la méthode
     */
    public static String fabriquerTrameAfficheurRencontre(Rencontre rencontre)
    {
        String trame = DEBUT_TRAME;

        trame += DELIMITEUR_CHAMP + TRAME_AFFICHEUR_RENCONTRE + DELIMITEUR_CHAMP + rencontre.getEquipeA().getNomClub() + DELIMITEUR_CHAMP + rencontre.getEquipeB().getNomClub() + DELIMITEUR_FIN;

        Log.d(TAG, "fabriquerTrameAfficheurRencontre() trame = " + trame);

        return trame;
    }
    /**
     * @brief Méthode permettant de vérifier l'intégrité d'une trame NET
     * @return true si la trame est intègre, sinon false
     */
    public static boolean verifierTrameNet(String trame)
    {
        if (trame.startsWith("NET_AREA") && trame.contains(";NET"))
        {
            return true;
        }
        return false;
    }

    public static String fabriquerTrameScoreAfficheurDernierPoint(Partie partie)
    {
        String trame = DEBUT_TRAME;

        String pointsJoueursA = partie.getManches().lastElement().elementAt(Partie.POSITION_PREMIER_JOUEUR).toString();
        String pointsJoueursB = partie.getManches().lastElement().elementAt(Partie.POSITION_DEUXIEME_JOUEUR).toString();
        String manchesJoueurA = Integer.toString(partie.getManchesJoueursA());
        String manchesJoueurB = Integer.toString(partie.getManchesJoueursB());

        if(partie.getManches().lastElement().elementAt(Partie.POSITION_PREMIER_JOUEUR) > partie.getManches().lastElement().elementAt(Partie.POSITION_DEUXIEME_JOUEUR))
            manchesJoueurA = Integer.toString(partie.getManchesJoueursA()-1);
        else
            manchesJoueurB = Integer.toString(partie.getManchesJoueursB()-1);

        trame += DELIMITEUR_CHAMP + TRAME_AFFICHEUR_SCORE + DELIMITEUR_CHAMP + partie.getId() + DELIMITEUR_CHAMP + pointsJoueursA +
                DELIMITEUR_CHAMP + pointsJoueursB + DELIMITEUR_CHAMP + manchesJoueurA + DELIMITEUR_CHAMP +
                manchesJoueurB + DELIMITEUR_FIN;

        return trame;
    }

    public static String fabriquerTramePosition(boolean estADroite)
    {
        String trame = DEBUT_TRAME;

        /**
         * MOBILE_AREA;0;POSITION\r\n
         * Le champs POSITION peut prendre deux valeurs :
         * - GAUCHE
         * - DROITE
         */

        if (estADroite)
            trame += DELIMITEUR_CHAMP + TRAME_SCORE_POSITION + DELIMITEUR_CHAMP + POSITION_DROITE + DELIMITEUR_FIN;
        else
            trame += DELIMITEUR_CHAMP + TRAME_SCORE_POSITION + DELIMITEUR_CHAMP + POSITION_GAUCHE + DELIMITEUR_FIN;

        Log.d(TAG, "fabriquerTramePosition() trame = " + trame);

        return trame;
    }
}
