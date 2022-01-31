package com.example.area;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @file SQLiteAREA.java
 * @brief Déclaration de la classe SQLiteAREA
 * @author BRUNET Bastien
 */

/**
 * @class SQLiteAREA
 * @brief Classe qui permet la création de la base de données
 */
public class SQLiteAREA extends SQLiteOpenHelper
{
    private static final String TAG = "_SQLiteAREA";  //!< TAG pour les logs
    public static final String  DATABASE_NAME = "mobile-area.db";//!< Le nom de la base de données
    public static final int     DATABASE_VERSION = 3;//!< La version de la base de données

    /**
     * Requêtes de création des tables
     */
    private static final String CREATE_TABLE_EQUIPE = "CREATE TABLE IF NOT EXISTS Equipe(idEquipe INTEGER PRIMARY KEY AUTOINCREMENT, nomClub VARCHAR);";
    private static final String CREATE_TABLE_JOUEUR = "CREATE TABLE IF NOT EXISTS Joueur(numeroLicence INTEGER PRIMARY KEY, idEquipe INTEGER NOT NULL, nom VARCHAR, prenom VARCHAR, CONSTRAINT fk_idEquipe_1 FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe), UNIQUE(nom,prenom));";
    private static final String CREATE_TABLE_RENCONTRE = "CREATE TABLE IF NOT EXISTS Rencontre(idRencontre INTEGER PRIMARY KEY AUTOINCREMENT, idEquipeA INTEGER NOT NULL, idEquipeB INTEGER NOT NULL, nbPartiesGagnantes INTEGER DEFAULT 0, estFinie INTEGER DEFAULT 0, horodatage DATETIME NOT NULL, CONSTRAINT fk_idEquipe_A FOREIGN KEY (idEquipeA) REFERENCES Equipe(idEquipe), CONSTRAINT fk_idEquipe_B FOREIGN KEY (idEquipeB) REFERENCES Equipe(idEquipe));";
    private static final String CREATE_TABLE_PARTIE = "CREATE TABLE IF NOT EXISTS Partie(idPartie INTEGER PRIMARY KEY AUTOINCREMENT, idRencontre INTEGER NOT NULL, idJoueurA INTEGER NOT NULL, idJoueurB INTEGER NOT NULL, idJoueurW INTEGER NOT NULL, idJoueurX INTEGER NOT NULL, nbManchesGagnantes INTEGER DEFAULT 0, nbPointsParManche INTEGER DEFAULT 0, typePartie INTEGER DEFAULT 1, debut DATETIME NOT NULL, fin DATETIME, CONSTRAINT fk_idRencontre_1 FOREIGN KEY (idRencontre) REFERENCES Rencontre(idRencontre), CONSTRAINT fk_idJoueur_A FOREIGN KEY (idJoueurA) REFERENCES Joueur(numeroLicence), CONSTRAINT fk_idJoueur_B FOREIGN KEY (idJoueurB) REFERENCES Joueur(numeroLicence), CONSTRAINT fk_idJoueur_W FOREIGN KEY (idJoueurW) REFERENCES Joueur(numeroLicence), CONSTRAINT fk_idJoueur_X FOREIGN KEY (idJoueurX) REFERENCES Joueur(numeroLicence));";
    private static final String CREATE_TABLE_SCORE = "CREATE TABLE IF NOT EXISTS Score(idPartie INTEGER NOT NULL, numeroSet INTEGER NOT NULL, pointsJoueurEquipeA INTEGER DEFAULT 0, pointsJoueurEquipeB INTEGER DEFAULT 0, debut DATETIME NOT NULL, fin DATETIME, CONSTRAINT pk_score PRIMARY KEY (idPartie,numeroSet), CONSTRAINT fk_idPartie_1 FOREIGN KEY (idPartie) REFERENCES Partie(idPartie));";

    /**
     * Requêtes d'insertion de initiales dans la base de données
     */
    private static final String INSERT_TABLE_EQUIPE_1 = "INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'PPC Avignon');";
    private static final String INSERT_TABLE_EQUIPE_2 = "INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'PPC Pernes');";
    private static final String INSERT_TABLE_EQUIPE_3 = "INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'PPC Sorgues');";
    private static final String INSERT_TABLE_EQUIPE_4 = "INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'AS Cavaillon TT');";
    private static final String INSERT_TABLE_EQUIPE_5 = "INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'C.S. Pertuis');";
    private static final String INSERT_TABLE_EQUIPE_6 = "INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'T.T. Morières');";

    private static final String INSERT_TABLE_JOUEUR_1 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (9419829,1,'BOUDRIMIL','Kamal');";
    private static final String INSERT_TABLE_JOUEUR_2 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (844443,1,'REDOR','Simon');";
    private static final String INSERT_TABLE_JOUEUR_3 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843368,1,'KRIER','Eric');";
    private static final String INSERT_TABLE_JOUEUR_4 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (139328,1,'RUIZ','Jean michel');";
    private static final String INSERT_TABLE_JOUEUR_5 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (841827,1,'GUIDARELLI','Nicolas');";
    private static final String INSERT_TABLE_JOUEUR_6 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (9420542,2,'RUAULT','Nicolas');";
    private static final String INSERT_TABLE_JOUEUR_7 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845682,2,'CROUZET','Lionel');";
    private static final String INSERT_TABLE_JOUEUR_8 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (847650,2,'FLORES','Fabien');";
    private static final String INSERT_TABLE_JOUEUR_9 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845423,2,'BRESSON','Thibault');";
    private static final String INSERT_TABLE_JOUEUR_10 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (844549,2,'FAGOO','Damien');";
    private static final String INSERT_TABLE_JOUEUR_11 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843944,3,'BEAUMONT','Jérôme');";
    private static final String INSERT_TABLE_JOUEUR_12 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (303504,3,'SAULNIER','Christian');";
    private static final String INSERT_TABLE_JOUEUR_13 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (842353,3,'COMTE','Emmanuel');";
    private static final String INSERT_TABLE_JOUEUR_14 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (842363,3,'LEVRARD','Mickael');";
    private static final String INSERT_TABLE_JOUEUR_15 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (645758,3,'FILAFERRO','Thomas');";
    private static final String INSERT_TABLE_JOUEUR_16 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (846543,3,'DUBOURG ','Dylan');";
    private static final String INSERT_TABLE_JOUEUR_17 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (841424,4,'ALBERT','Pierre-david');";
    private static final String INSERT_TABLE_JOUEUR_18 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843634,4,'MANGIN','Frederic');";
    private static final String INSERT_TABLE_JOUEUR_19 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (9110467,4,'ZENATY','Julien');";
    private static final String INSERT_TABLE_JOUEUR_20 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843871,4,'MANGIN','Thierry');";
    private static final String INSERT_TABLE_JOUEUR_21 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (842471,4,'DESPRES','Gregory');";
    private static final String INSERT_TABLE_JOUEUR_22 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (3330232,5,'BADRE','Corentin');";
    private static final String INSERT_TABLE_JOUEUR_23 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845470,5,'CHOUISNARD','Enzo');";
    private static final String INSERT_TABLE_JOUEUR_24 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (261769,5,'LASCOMBE','Nicolas');";
    private static final String INSERT_TABLE_JOUEUR_25 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845078,5,'BOINARD','Thomas');";
    private static final String INSERT_TABLE_JOUEUR_26 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (84326,5,'SOLER','Michel');";
    private static final String INSERT_TABLE_JOUEUR_27 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (305731,6,'OPEZZO','Mathieu');";
    private static final String INSERT_TABLE_JOUEUR_28 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (1310341,6,'AMBROSINO','Stephane');";
    private static final String INSERT_TABLE_JOUEUR_29 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (136310,6,'MALET','Sebastien');";
    private static final String INSERT_TABLE_JOUEUR_30 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (349167,6,'ROUTTIER','Julien');";
    private static final String INSERT_TABLE_JOUEUR_31 = "INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (43041,6,'JOULLIE','Arnaud');";

    /**
     * Requêtes de test
     */
    private static final String INSERT_TABLE_RENCONTRE_1 = "INSERT INTO Rencontre(idRencontre, idEquipeA, idEquipeB, nbPartiesGagnantes, estFinie, horodatage) VALUES (NULL,3,1,8,0,'2021-01-29 08:00:00');";
    private static final String INSERT_TABLE_PARTIE_1 = "INSERT INTO Partie(idPartie, idRencontre, idJoueurA, idJoueurB, idJoueurW, idJoueurX, nbManchesGagnantes, nbPointsParManche, typePartie, debut) VALUES (NULL,1,843944,9419829,843944,9419829,3,11,1,'2021-01-29 08:15:00');";
    private static final String INSERT_TABLE_PARTIE_2 = "INSERT INTO Partie(idPartie, idRencontre, idJoueurA, idJoueurB, idJoueurW, idJoueurX, nbManchesGagnantes, nbPointsParManche, typePartie, debut) VALUES (NULL,1,303504,844443,303504,844443,3,11,1,'2021-01-29 08:15:00');";
    private static final String INSERT_TABLE_PARTIE_3 = "INSERT INTO Partie(idPartie, idRencontre, idJoueurA, idJoueurB, idJoueurW, idJoueurX, nbManchesGagnantes, nbPointsParManche, typePartie, debut) VALUES (NULL,1,843944,303504,9419829,844443,3,11,2,'2021-01-29 08:15:00');";

    /**
     * @brief Constructeur de la classe SQLiteAREA
     */
    public SQLiteAREA(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * @brief Méthode appelée à la création de la base de données
     * @param db La base de données créée
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d(TAG, "onCreate() path = " + db.getPath());
        creerTables(db);
        insererDonneesInitiales(db);
    }

    /**
     * @brief Méthode appelée lors de la mise à jour de la base de données
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.d(TAG, "onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS Score;");
        db.execSQL("DROP TABLE IF EXISTS Partie;");
        db.execSQL("DROP TABLE IF EXISTS Rencontre;");
        db.execSQL("DROP TABLE IF EXISTS Joueur;");
        db.execSQL("DROP TABLE IF EXISTS Equipe;");
        /*db.execSQL("DROP TABLE Equipe;");
        db.execSQL("DROP TABLE Joueur;");
        db.execSQL("DROP TABLE Rencontre;");
        db.execSQL("DROP TABLE Partie;");
        db.execSQL("DROP TABLE Score;");*/
        onCreate(db);
    }

    /**
     * @brief Méthode permettant d'éxecuter les requêtes de création des tables
     * @param db La base de données sur laquelle éxecuter les requêtes
     */
    private void creerTables(SQLiteDatabase db)
    {
        Log.d(TAG, "creerTables()");
        db.execSQL(CREATE_TABLE_EQUIPE);
        db.execSQL(CREATE_TABLE_JOUEUR);
        db.execSQL(CREATE_TABLE_RENCONTRE);
        db.execSQL(CREATE_TABLE_PARTIE);
        db.execSQL(CREATE_TABLE_SCORE);
    }

    /**
     * @brief Méthode permettant d'éxecuter les requêtes d'insertion de données initiales
     * @param db La base de données sur laquelle éxecuter les requêtes
     */
    private void insererDonneesInitiales(SQLiteDatabase db)
    {
        Log.d(TAG, "insererDonneesInitiales()");
        db.execSQL(INSERT_TABLE_EQUIPE_1);
        db.execSQL(INSERT_TABLE_EQUIPE_2);
        db.execSQL(INSERT_TABLE_EQUIPE_3);
        db.execSQL(INSERT_TABLE_EQUIPE_4);
        db.execSQL(INSERT_TABLE_EQUIPE_5);
        db.execSQL(INSERT_TABLE_EQUIPE_6);

        db.execSQL(INSERT_TABLE_JOUEUR_1);
        db.execSQL(INSERT_TABLE_JOUEUR_2);
        db.execSQL(INSERT_TABLE_JOUEUR_3);
        db.execSQL(INSERT_TABLE_JOUEUR_4);
        db.execSQL(INSERT_TABLE_JOUEUR_5);
        db.execSQL(INSERT_TABLE_JOUEUR_6);
        db.execSQL(INSERT_TABLE_JOUEUR_7);
        db.execSQL(INSERT_TABLE_JOUEUR_8);
        db.execSQL(INSERT_TABLE_JOUEUR_9);
        db.execSQL(INSERT_TABLE_JOUEUR_10);
        db.execSQL(INSERT_TABLE_JOUEUR_11);
        db.execSQL(INSERT_TABLE_JOUEUR_12);
        db.execSQL(INSERT_TABLE_JOUEUR_13);
        db.execSQL(INSERT_TABLE_JOUEUR_14);
        db.execSQL(INSERT_TABLE_JOUEUR_15);
        db.execSQL(INSERT_TABLE_JOUEUR_16);
        db.execSQL(INSERT_TABLE_JOUEUR_17);
        db.execSQL(INSERT_TABLE_JOUEUR_18);
        db.execSQL(INSERT_TABLE_JOUEUR_19);
        db.execSQL(INSERT_TABLE_JOUEUR_20);
        db.execSQL(INSERT_TABLE_JOUEUR_21);
        db.execSQL(INSERT_TABLE_JOUEUR_22);
        db.execSQL(INSERT_TABLE_JOUEUR_23);
        db.execSQL(INSERT_TABLE_JOUEUR_24);
        db.execSQL(INSERT_TABLE_JOUEUR_25);
        db.execSQL(INSERT_TABLE_JOUEUR_26);
        db.execSQL(INSERT_TABLE_JOUEUR_27);
        db.execSQL(INSERT_TABLE_JOUEUR_28);
        db.execSQL(INSERT_TABLE_JOUEUR_29);
        db.execSQL(INSERT_TABLE_JOUEUR_30);
        db.execSQL(INSERT_TABLE_JOUEUR_31);

        // Tests
        /*db.execSQL(INSERT_TABLE_RENCONTRE_1);
        db.execSQL(INSERT_TABLE_PARTIE_1);
        db.execSQL(INSERT_TABLE_PARTIE_2);
        db.execSQL(INSERT_TABLE_PARTIE_3);*/
    }
}
