package com.example.area;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Set;
import java.util.Vector;

// Pour Logcat : _IHMGestionRencontre|_IHMGestionPartie|_Partie|_LiaisonBluetooth|_ProtocoleAREA

/**
 * @file IHMGestionPartie.java
 * @brief Déclaration de la classe IHMGestionPartie
 * @author BRUNET Bastien
 * $LastChangedRevision: 130 $
 * $LastChangedDate: 2021-06-24 10:57:25 +0200 (jeu. 24 juin 2021) $
 */

/**
 * @class IHMGestionPartie
 * @brief L'activité de gestion d'une partie de l'application AREA
 */
public class IHMGestionPartie extends AppCompatActivity
{
    /**
     * Constantes
     */
    private static final String TAG = "_IHMGestionPartie";  //!< TAG pour les logs
    private static final String TEXTE_BOUTON_TEMPS_MORT = "Temps mort";//!< Le texte des boutons de temps mort
    private static final String TEXTE_BOUTON_RETIRER_POINT = "-1";//!< Le texte des boutons pour retirer un point
    private static final String TEXTE_BOUTON_AJOUTER_POINT = "+1";//!< Le texte des boutons pour ajouter un point
    private static final String TEXTE_TOAST_NET = "NET";//!< Le texte apparissant dans le toast affichant un NET
    private static final String TEXTE_CONNEXION_MODULE_NET = "Module NET ";//!< Le texte permettant d'indiquer l'état de la connexion au module NET
    private static final String TEXTE_CONNEXION_MODULE_AFFICHEUR = "Module Afficheur ";//!< Le texte permettant d'indiquer l'état du module Afficheur
    private static final String TEXTE_CONNEXION_MODULE_SCORE = "Module Score";//!< Le texte permettant d'indiquer l'état du module Score
    private static final String TEXTE_BOUTON_REPRENDRE = "Reprendre";
    private static final String TEXTE_CHANGEMENT_COTE = "Changement de côté";
    private static final String TEXTE_TEMPS_MORT = "Temps mort";

    private static final int INDEX_MODULE_AFFICHEUR = 0;
    private static final int INDEX_MODULE_SCORE = 1;
    public static final int DUREE_CHANGEMENT_COTE = 60000;
    public static final int DUREE_TEMPS_MORT = 60000;

    /**
     * Ressources IHM
     */
    private TextView nomJoueurA;//!< L'affichage du nom du premier joueur de l'équipe A
    private TextView prenomJoueurA;//!< L'affichage du prénom du premier joueur de l'équipe A
    private TextView nomDeuxiemeJoueurA;///!< L'affichage du nom du deuxième joueur de l'équipe A
    private TextView prenomDeuxiemeJoueurA;//!< L'affichage du prénom du deuxième joueur de l'équipe A
    private Button boutonRetirerPointJoueurA;//!< Le bouton permettant de retirer un point au(x) joueur(s) A
    private Button boutonAjouterPointJoueurA;//!< Le bouton permettant d'ajouter un point au(x) joueur(s) A
    private ImageView imageServeurJoueurA;//!< Icone signifiant que le premier joueur de l'équipe A doit servir
    private ImageView imageServeurDeuxiemeJoueurA;//!< Icone signifiant que le deuxième joueur de l'équipe A doit servir
    private Button boutonTempsMortJoueurA;//!< Le bouton permettant de déclencher un temps mort pour le(s) joueur(s) A
    private TextView pointsJoueurA;//!< L'affichage des points du ou des joueur(s) A
    private TextView manchesJoueurA;//!< L'affichage du nombre de manches du ou des joueur(s) A
    private TextView tiret;//!< Tiret séparant les scores
    private TextView nomJoueurB;//!< L'affichage du nom du premier joueur de l'équipe B
    private TextView prenomJoueurB;//!< L'affichage du prénom du premier joueur de l'équipe B
    private TextView nomDeuxiemeJoueurB;//!< L'affichage du nom du deuxième joueur de l'équipe B
    private TextView prenomDeuxiemeJoueurB;//!< L'affichage du prénom du deuxième joueur de l'équipe B
    private Button boutonRetirerPointJoueurB;//!< Le bouton permettant de retirer un point au(x) joueur(s) B
    private Button boutonAjouterPointJoueurB;//!< Le bouton permettant d'ajouter un point au(x) joueur(s) B
    private ImageView imageServeurJoueurB;//!< Icone signifiant que le premier joueur de l'équipe A doit servir
    private ImageView imageServeurDeuxiemeJoueurB;//!< Icone signifiant que le deuxième joueur de l'équipe A doit servir
    private Button boutonTempsMortJoueurB;//!< Le bouton permettant de déclencher un temps mort pour le(s) joueur(s) A
    private TextView pointsJoueurB;//!< L'affichage des points du ou des joueur(s) A
    private TextView manchesJoueurB;//!< L'affichage du nombre de manches du ou des joueur(s) A
    private Toast toastNet;//!< Le toast apparaissant lors d'un NET
    private View layoutNet;//!< Le layout du toast apparaissant lors d'un NET
    private TextView net;//!< Le text dans le toast apparaissant lors d'un NET
    private TextView connexionModuleNet;//!< Le texte devant l'image permettant d'indiquer l'état de la connexion au module NET
    private ImageView imageConnexionModuleNet;//!< L'image permettant d'indiquer l'état de la connexion au module NET
    private TextView connexionModuleAfficheur;//!< Le texte devant l'image permettant d'indiquer l'état de la connexion au module Afficheur
    private ImageView imageConnexionModuleAfficheur;//!< L'image permettant d'indiquer l'état de la connexion au module Afficheur
    private TextView connexionModuleScore;//!< Le texte devant l'image permettant d'indiquer l'état de la connexion au module Score
    private ImageView imageConnexionModuleScore;//!< L'image permettant d'indiquer l'état de la connexion au module Score
    private TextView timer;
    private TextView messageTimer;
    private Button boutonInterruptionTimer;

    /**
     * Attributs
     */
    private LiaisonBluetooth liaisonModuleNet = null;//<! Liaison bluetooth avec le module NET
    private Vector<LiaisonBluetooth> modulesAffichage = null;
    private Handler handler = null;//<! Le handler utilisé par l'activité
    private Partie partie = null;//<! La partie que gère l'activité
    private CountDownTimer decompte = null;
    int nbManchesAAfficher = 0;
    BaseDeDonnees baseDeDonnees = null;

    /**
     * @brief Méthode appelée à la création de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ihm_gestion_partie);
        Log.d(TAG, "onCreate()");

        partie = (Partie) getIntent().getSerializableExtra(IHMGestionRencontre.ID_INTENT_LANCEMENT_PARTIE);

        baseDeDonnees = new BaseDeDonnees(this);
        baseDeDonnees.commencerSet(partie);

        initialiserHandler();

        initialiserLiaisonBluetooth();

        recupererRessourcesIHM();

        initialiserRessourcesIHM();

        connecterBoutons();
    }

    /**
     * @brief Méthode appelée au démarrage après le onCreate() ou un restart après un onStop()
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    /**
     * @brief Méthode appelée après onStart() ou après onPause()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    /**
     * @brief Méthode appelée après qu'une boîte de dialogue s'est affichée (on reprend sur un onResume()) ou avant onStop() (activité plus visible)
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    /**
     * @brief Méthode appelée lorsque l'activité n'est plus visible
     */
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    /**
     * @brief Méthode appelée à la destruction de l'application (après onStop() et détruite par le système Android)
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        deconnecterModules();
    }

    /**
     * @brief Déconnecte tout les modules connectés
     */
    private void deconnecterModules()
    {
        liaisonModuleNet.deconnecter();
        for(int i = 0; i < modulesAffichage.size(); i++)
        {
            modulesAffichage.elementAt(i).deconnecter();
        }
    }

    /**
     * @brief Recupère les ressources graphiques de l'activité
     */
    private void recupererRessourcesIHM()
    {
        recupererRessourcesJoueurA();

        tiret = findViewById(R.id.tiret);

        recupererRessourcesJoueurB();

        LayoutInflater layoutInflater = getLayoutInflater();
        layoutNet = layoutInflater.inflate(R.layout.toast_net,findViewById(R.id.layout_toast_net));
        net = layoutNet.findViewById(R.id.texte_net);
        toastNet = new Toast(getApplicationContext());

        imageConnexionModuleNet = findViewById(R.id.imageConnexionModuleNet);
        connexionModuleNet = findViewById(R.id.connexionModuleNet);

        imageConnexionModuleAfficheur = findViewById(R.id.imageConnexionModuleAfficheur);
        connexionModuleAfficheur = findViewById(R.id.connexionModuleAfficheur);

        imageConnexionModuleScore = findViewById(R.id.imageConnexionModuleScore);
        connexionModuleScore = findViewById(R.id.connexionModuleScore);

        timer = findViewById(R.id.timer);
        messageTimer = findViewById(R.id.messageTimer);
        boutonInterruptionTimer = findViewById(R.id.boutonInterruptionTimer);

        if ((boolean)getIntent().getSerializableExtra(IHMGestionRencontre.ID_INTENT_POSITION_INVERSE))
            permuterCoteIHM();
    }

    /**
     * @brief Recupère les ressources graphiques relatives au joueur B
     */
    private void recupererRessourcesJoueurB()
    {
        nomJoueurB = findViewById(R.id.nomJoueurB);
        prenomJoueurB = findViewById(R.id.prenomJoueurB);
        nomDeuxiemeJoueurB = findViewById(R.id.nomDeuxiemeJoueurB);
        prenomDeuxiemeJoueurB = findViewById(R.id.prenomDeuxiemeJoueurB);
        boutonRetirerPointJoueurB = findViewById(R.id.boutonRetirerPointJoueurB);
        boutonAjouterPointJoueurB = findViewById(R.id.boutonAjouterPointJoueurB);
        imageServeurJoueurB = findViewById(R.id.imageServeurJoueurB);
        imageServeurDeuxiemeJoueurB = findViewById(R.id.imageServeurDeuxiemeJoueurB);
        boutonTempsMortJoueurB = findViewById(R.id.boutonTempsMortJoueurB);
        pointsJoueurB = findViewById(R.id.pointsJoueurB);
        manchesJoueurB = findViewById(R.id.manchesJoueurB);
    }

    /**
     * @brief Recupère les ressources graphiques relatives au joueur A
     */
    private void recupererRessourcesJoueurA()
    {
        nomJoueurA = findViewById(R.id.nomJoueurA);
        prenomJoueurA = findViewById(R.id.prenomJoueurA);
        nomDeuxiemeJoueurA = findViewById(R.id.nomDeuxiemeJoueurA);
        prenomDeuxiemeJoueurA = findViewById(R.id.prenomDeuxiemeJoueurA);
        boutonRetirerPointJoueurA = findViewById(R.id.boutonRetirerPointJoueurA);
        boutonAjouterPointJoueurA = findViewById(R.id.boutonAjouterPointJoueurA);
        imageServeurJoueurA = findViewById(R.id.imageServeurJoueurA);
        imageServeurDeuxiemeJoueurA = findViewById(R.id.imageServeurDeuxiemeJoueurA);
        boutonTempsMortJoueurA = findViewById(R.id.boutonTempsMortJoueurA);
        pointsJoueurA = findViewById(R.id.pointsJoueurA);
        manchesJoueurA = findViewById(R.id.manchesJoueurA);
    }

    /**
     * @brief Passe le(s) joueur(s) de gauche à droite et inversement
     */
    private void permuterCoteIHM()
    {
        Log.d(TAG,"Changement de côté");
        if (nomJoueurA.getId() == R.id.nomJoueurA)
        {
            nomJoueurA = findViewById(R.id.nomJoueurB);
            prenomJoueurA = findViewById(R.id.prenomJoueurB);
            nomDeuxiemeJoueurA = findViewById(R.id.nomDeuxiemeJoueurB);
            prenomDeuxiemeJoueurA = findViewById(R.id.prenomDeuxiemeJoueurB);
            boutonRetirerPointJoueurA = findViewById(R.id.boutonRetirerPointJoueurB);
            boutonAjouterPointJoueurA = findViewById(R.id.boutonAjouterPointJoueurB);
            imageServeurJoueurA = findViewById(R.id.imageServeurJoueurB);
            imageServeurDeuxiemeJoueurA = findViewById(R.id.imageServeurDeuxiemeJoueurB);
            boutonTempsMortJoueurA = findViewById(R.id.boutonTempsMortJoueurB);
            pointsJoueurA = findViewById(R.id.pointsJoueurB);
            manchesJoueurA = findViewById(R.id.manchesJoueurB);

            nomJoueurB = findViewById(R.id.nomJoueurA);
            prenomJoueurB = findViewById(R.id.prenomJoueurA);
            nomDeuxiemeJoueurB = findViewById(R.id.nomDeuxiemeJoueurA);
            prenomDeuxiemeJoueurB = findViewById(R.id.prenomDeuxiemeJoueurA);
            boutonRetirerPointJoueurB = findViewById(R.id.boutonRetirerPointJoueurA);
            boutonAjouterPointJoueurB = findViewById(R.id.boutonAjouterPointJoueurA);
            imageServeurJoueurB = findViewById(R.id.imageServeurJoueurA);
            imageServeurDeuxiemeJoueurB = findViewById(R.id.imageServeurDeuxiemeJoueurA);
            boutonTempsMortJoueurB = findViewById(R.id.boutonTempsMortJoueurA);
            pointsJoueurB = findViewById(R.id.pointsJoueurA);
            manchesJoueurB = findViewById(R.id.manchesJoueurA);

            modulesAffichage.elementAt(INDEX_MODULE_SCORE).envoyer(ProtocolAREA.fabriquerTramePosition(true));
        }
        else
        {
            recupererRessourcesJoueurA();
            recupererRessourcesJoueurB();
            modulesAffichage.elementAt(INDEX_MODULE_SCORE).envoyer(ProtocolAREA.fabriquerTramePosition(false));
        }

        afficherNomsJoueursA();
        afficherNomsJoueursB();

        afficherScore();
        afficherServeur();
        connecterBoutons();
        actualiserEtatBoutonTempsMorts();
    }

    /**
     * @brief Initialise les ressources graphiques de l'activité
     */
    private void initialiserRessourcesIHM()
    {
        afficherNomsJoueursA();
        afficherNomsJoueursB();

        afficherScore();
        afficherServeur();

        afficherBoutonsEquipeA();
        afficherBoutonsEquipeB();

        afficherNet();

        afficherConnexionModulesBluetooth();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initialiserPositions()
    {
        if ((boolean)getIntent().getSerializableExtra(IHMGestionRencontre.ID_INTENT_POSITION_INVERSE))
            modulesAffichage.elementAt(INDEX_MODULE_SCORE).envoyer(ProtocolAREA.fabriquerTramePosition(true));
        else
            modulesAffichage.elementAt(INDEX_MODULE_SCORE).envoyer(ProtocolAREA.fabriquerTramePosition(false));
    }

    /**
     * @brief Affiche les ressources pour le Bluetooth des modules
     */
    private void afficherConnexionModulesBluetooth()
    {
        imageConnexionModuleNet.setColorFilter(Color.RED);
        connexionModuleNet.setText(TEXTE_CONNEXION_MODULE_NET);
        imageConnexionModuleAfficheur.setColorFilter(Color.RED);
        connexionModuleAfficheur.setText(TEXTE_CONNEXION_MODULE_AFFICHEUR);
        imageConnexionModuleScore.setColorFilter(Color.RED);
        connexionModuleScore.setText(TEXTE_CONNEXION_MODULE_SCORE);
    }

    /**
     * @brief Affiche les ressources pour le NET
     */
    private void afficherNet()
    {
        tiret.setText("-");
        net.setText(TEXTE_TOAST_NET);
        toastNet.setDuration(Toast.LENGTH_LONG);
        toastNet.setGravity(Gravity.TOP, 0, 150);
        toastNet.setView(layoutNet);
    }

    /**
     * @brief Affiche les boutons pour l'équipe A
     */
    private void afficherBoutonsEquipeA()
    {
        boutonAjouterPointJoueurA.setText(TEXTE_BOUTON_AJOUTER_POINT);
        boutonRetirerPointJoueurA.setText(TEXTE_BOUTON_RETIRER_POINT);
        boutonTempsMortJoueurA.setText(TEXTE_BOUTON_TEMPS_MORT);
    }

    /**
     * @brief Affiche les boutons pour l'équipe B
     */
    private void afficherBoutonsEquipeB()
    {
        boutonAjouterPointJoueurB.setText(TEXTE_BOUTON_AJOUTER_POINT);
        boutonRetirerPointJoueurB.setText(TEXTE_BOUTON_RETIRER_POINT);
        boutonTempsMortJoueurB.setText(TEXTE_BOUTON_TEMPS_MORT);
    }

    /**
     * @brief Initialise le handler permettant le passage des Messages (trames reçues) entre les classes LiaisonBluetooth et IHMGestionPartie
     */
    private void initialiserHandler()
    {
        this.handler = new Handler(this.getMainLooper())
        {
            @Override
            public void handleMessage(@NonNull Message message)
            {
                Log.d(TAG, "[Handler] id du message = " + message.what);
                Log.d(TAG, "[Handler] contenu du message = " + message.obj.toString());

                switch (message.what)
                {
                    case LiaisonBluetooth.CREATION_SOCKET:
                        Log.d(TAG, "[Handler] CREATION_SOCKET = " + message.obj.toString());
                        break;
                    case LiaisonBluetooth.CONNEXION_SOCKET:
                        Log.d(TAG, "[Handler] CONNEXION_SOCKET = " + message.obj.toString());
                        afficherEtatConnexionBluetooth(message.obj.toString(),true);
                        if (message.obj.toString().equals(modulesAffichage.elementAt(INDEX_MODULE_AFFICHEUR).getNomModule()))
                            modulesAffichage.elementAt(INDEX_MODULE_AFFICHEUR).envoyer(ProtocolAREA.fabriquerTrameAfficheur(ProtocolAREA.TRAME_AFFICHEUR_ETAT_PARTIE, partie));
                        if (message.obj.toString().equals(modulesAffichage.elementAt(INDEX_MODULE_SCORE).getNomModule()))
                        {
                            modulesAffichage.elementAt(INDEX_MODULE_SCORE).envoyer(ProtocolAREA.fabriquerTrameAfficheur(ProtocolAREA.TRAME_AFFICHEUR_ETAT_PARTIE, partie));
                            if (partie.getManches().size() == 0)
                                initialiserPositions();
                        }
                        break;
                    case LiaisonBluetooth.DECONNEXION_SOCKET:
                        Log.d(TAG, "[Handler] DECONNEXION_SOCKET = " + message.obj.toString());
                        afficherEtatConnexionBluetooth(message.obj.toString(),false);
                        break;
                    case LiaisonBluetooth.RECEPTION_TRAME:
                        if(ProtocolAREA.verifierTrameNet(message.obj.toString()))
                        {
                            toastNet.show();
                            envoyerTramesNet();
                        }
                        break;
                }
            }
        };
    }

    /**
     * @brief Envoie une trame score à tout les modules d'affichage connectés
     */
    private void envoyerTramesScore()
    {
        for(int i = 0; i < modulesAffichage.size(); i++)
        {
            modulesAffichage.elementAt(i).envoyer(ProtocolAREA.fabriquerTrameAfficheur(ProtocolAREA.TRAME_AFFICHEUR_SCORE, partie));
        }
    }

    /**
     * @brief Envoie une trame net à tout les modules d'affichage connectés
     */
    private void envoyerTramesNet()
    {
        for(int i = 0; i < modulesAffichage.size(); i++)
        {
            modulesAffichage.elementAt(i).envoyer(ProtocolAREA.fabriquerTrameAfficheur(ProtocolAREA.TRAME_AFFICHEUR_NET, partie));
        }
    }

    /**
     * @brief Envoie une trame étatPartie à tout les modules d'affichage connectés
     */
    private void envoyerTramesEtatPartie()
    {
        for(int i = 0; i < modulesAffichage.size(); i++)
        {
            modulesAffichage.elementAt(i).envoyer(ProtocolAREA.fabriquerTrameAfficheur(ProtocolAREA.TRAME_AFFICHEUR_ETAT_PARTIE, partie));
        }
    }

    private void envoyerTramesAfficheurDernierPoint()
    {
        for(int i = 0; i < modulesAffichage.size(); i++)
        {
            modulesAffichage.elementAt(i).envoyer(ProtocolAREA.fabriquerTrameScoreAfficheurDernierPoint(partie));
        }
    }

    /**
     * @brief Initialise la liaison Bluetooth
     */
    private void initialiserLiaisonBluetooth()
    {
        /**
         * @todo Remplacer l'adresse MAC par le nom du module et gérer son identification
         */
        liaisonModuleNet = new LiaisonBluetooth(ProtocolAREA.NOM_MODULE_NET, handler);
        liaisonModuleNet.connecter();

        modulesAffichage = new Vector<LiaisonBluetooth>();

        modulesAffichage.add(new LiaisonBluetooth(ProtocolAREA.NOM_MODULE_AFFICHEUR_AREA, handler));
        modulesAffichage.add(new LiaisonBluetooth(ProtocolAREA.NOM_MODULE_SCORE_AREA, handler));

        for (int i = 0; i < modulesAffichage.size(); i++)
        {
            modulesAffichage.elementAt(i).connecter();
        }

    }

    /**
     * @brief Définit le comportement des boutons
     */
    private void connecterBoutons()
    {
        boutonAjouterPointJoueurA.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) { ajouterPointJoueurA(); }
        });

        boutonAjouterPointJoueurB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) { ajouterPointJoueurB(); }
        });

        boutonRetirerPointJoueurA.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) { retirerPointJoueurA(); }
        });

        boutonRetirerPointJoueurB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) { retirerPointJoueurB(); }
        });

        boutonInterruptionTimer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                decompte.cancel();
                if (messageTimer.getText().equals(TEXTE_CHANGEMENT_COTE))
                {
                    permuterCoteIHM();
                    envoyerTramesScore();
                }
                cacherDecompte();
                afficherScore();
                inverserEtatBoutons();
                actualiserEtatBoutonTempsMorts();
            }
        });

        boutonTempsMortJoueurA.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (partie.getTempsMortsJoueursA() > 0)
                {
                    demarrerDecompte(DUREE_TEMPS_MORT, TEXTE_TEMPS_MORT, TEXTE_BOUTON_REPRENDRE);
                    partie.setTempsMortsJoueursA(0);
                    boutonTempsMortJoueurA.setEnabled(false);
                }
            }
        });

        boutonTempsMortJoueurB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (partie.getTempsMortsJoueursB() > 0)
                {
                    demarrerDecompte(DUREE_TEMPS_MORT, TEXTE_TEMPS_MORT, TEXTE_BOUTON_REPRENDRE);
                    partie.setTempsMortsJoueursB(0);
                    boutonTempsMortJoueurB.setEnabled(false);
                }
            }
        });
    }

    /**
     * @brief Méthode appelée pour retirer un point au joueur B
     */
    private void retirerPointJoueurB()
    {
        Log.d(TAG,"retirerPointJoueurB");
        partie.retirerPointJoueursB();
        actualiserAffichageRetraitPoint();

    }

    /**
     * @brief Méthode appelée pour retirer un point au joueur A
     */
    private void retirerPointJoueurA()
    {
        Log.d(TAG,"retirerPointJoueurA");
        partie.retirerPointJoueursA();
        actualiserAffichageRetraitPoint();

    }

    /**
     * @brief Actualise l'affichage et décelenche l'envoi de trames lors du retrait d'un point
     */
    private void actualiserAffichageRetraitPoint()
    {
        envoyerTramesScore();
        afficherScore();
        afficherServeur();
        liaisonModuleNet.envoyer(ProtocolAREA.TRAME_SERVICE);
    }

    /**
     * @brief Méthode appelée pour ajouter un point au joueur B
     */
    private void ajouterPointJoueurB()
    {
        Log.d(TAG,"ajouterPointJoueurB");
        partie.ajouterPointJoueursB();
        actualiserAffichageAjoutPoint();
    }

    /**
     * @brief Méthode appelée pour ajouter un point au joueur A
     */
    private void ajouterPointJoueurA()
    {
        Log.d(TAG,"ajouterPointJoueurA");
        partie.ajouterPointJoueursA();
        actualiserAffichageAjoutPoint();
    }

    /**
     * @brief Actualise l'affichage et décelenche l'envoi de trames lors de l'ajout d'un point
     */
    private void actualiserAffichageAjoutPoint()
    {
        int nbManchesAffichees = 0;
        if (!manchesJoueurA.getText().toString().equals(""))
            nbManchesAffichees = Integer.parseInt(manchesJoueurA.getText().toString()) + Integer.parseInt(manchesJoueurB.getText().toString());
        if (nbManchesAffichees == partie.getManches().size())
            envoyerTramesScore();
        afficherScore();
        afficherServeur();
        liaisonModuleNet.envoyer(ProtocolAREA.TRAME_SERVICE);
        if (partie.estFinie())
            finish();
    }

    /**
     * @brief Affiche le nom et le prénom des JoueursA
     */
    private void afficherNomsJoueursA()
    {
        Vector<Joueur> joueursA = partie.getJoueursA();

        nomJoueurA.setText(joueursA.elementAt(0).getNom() + " ");
        prenomJoueurA.setText(joueursA.elementAt(0).getPrenom());

        if(joueursA.size() > 1)
        {
            nomDeuxiemeJoueurA.setText(joueursA.elementAt(1).getNom() + " ");
            prenomDeuxiemeJoueurA.setText(joueursA.elementAt(1).getPrenom());
        }
    }

    /**
     * @brief Affiche le nom et le prénom des JoueursB
     */
    private void afficherNomsJoueursB()
    {
        Vector<Joueur> joueursB = partie.getJoueursB();

        nomJoueurB.setText(joueursB.elementAt(0).getNom() + " ");
        prenomJoueurB.setText(joueursB.elementAt(0).getPrenom());

        if(joueursB.size() > 1)
        {
            nomDeuxiemeJoueurB.setText(joueursB.elementAt(1).getNom() + " ");
            prenomDeuxiemeJoueurB.setText(joueursB.elementAt(1).getPrenom());
        }
    }

    /**
     * @brief Affiche les points et les manches gagnées des joueurs de la partie
     */
    private void afficherScore()
    {
        Log.d(TAG,"afficherScore()");
        String texteManchesJoueurA = manchesJoueurA.getText().toString();
        String texteManchesJoueurB = manchesJoueurB.getText().toString();
        int nbManchesAffichees = 0;
        if (!manchesJoueurA.getText().toString().equals(""))
            nbManchesAffichees = Integer.parseInt(manchesJoueurA.getText().toString()) + Integer.parseInt(manchesJoueurB.getText().toString());

        pointsJoueurA.setText(Integer.toString(partie.getPointsJoueursA()));
        manchesJoueurA.setText(Integer.toString(partie.getManchesJoueursA()));

        pointsJoueurB.setText(Integer.toString(partie.getPointsJoueursB()));
        manchesJoueurB.setText(Integer.toString(partie.getManchesJoueursB()));

        if (nbManchesAAfficher != partie.getManches().size())
        {
            pointsJoueurA.setText(partie.getManches().lastElement().elementAt(0).toString());
            pointsJoueurB.setText(partie.getManches().lastElement().elementAt(1).toString());
            manchesJoueurA.setText(texteManchesJoueurA);
            manchesJoueurB.setText(texteManchesJoueurB);

            baseDeDonnees.terminerSet(partie);
            if (!partie.estFinie())
                baseDeDonnees.commencerSet(partie);

            envoyerTramesAfficheurDernierPoint();
            demarrerDecompte(DUREE_CHANGEMENT_COTE,TEXTE_CHANGEMENT_COTE,TEXTE_BOUTON_REPRENDRE);

            nbManchesAAfficher++;
        }

    }

    /**
     * @brief Démarre un décompte s'affichant au centre de l'écran
     * @param duree La durée en ms du décompte
     * @param message Le message justifiant le décompte
     * @param texteBoutonInterruption Le texte du bouton pour interrompre le décompte
     */
    private void demarrerDecompte(long duree,String message, String texteBoutonInterruption)
    {
        messageTimer.setText(message);
        boutonInterruptionTimer.setText(texteBoutonInterruption);
        boutonInterruptionTimer.setVisibility(Button.VISIBLE);
        inverserEtatBoutons();
        decompte = new CountDownTimer(duree, 1000)
        {

            public void onTick(long millisUntilFinished) {
                timer.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                cacherDecompte();
                afficherScore();
                inverserEtatBoutons();
                actualiserEtatBoutonTempsMorts();
                envoyerTramesScore();
                if (message.equals(TEXTE_CHANGEMENT_COTE))
                    permuterCoteIHM();
            }
        }.start();
    }

    /**
     * @brief Cache tout les affichages lié au décompte
     */
    private void cacherDecompte()
    {
        timer.setText("");
        messageTimer.setText("");
        boutonInterruptionTimer.setText("");
        boutonInterruptionTimer.setVisibility(Button.INVISIBLE);
    }

    /**
     * @brief Affiche une image devant le nom du serveur
     */
    private void afficherServeur()
    {
         Joueur serveur = partie.getServeur();
         Vector<Joueur> joueursA = partie.getJoueursA();
         int indexServeur = joueursA.indexOf(serveur);
         String nomCompletServeur = serveur.getNom() + " " + serveur.getPrenom();

        renitialiserAffichageServeur();

        if(indexServeur != -1)
         {
            if (nomCompletServeur.equals((String)nomJoueurA.getText()+prenomJoueurA.getText()))
                imageServeurJoueurA.setVisibility(View.VISIBLE);
            else
                imageServeurDeuxiemeJoueurA.setVisibility(View.VISIBLE);
         }
         else
         {
             if (nomCompletServeur.equals((String)nomJoueurB.getText()+prenomJoueurB.getText()))
                 imageServeurJoueurB.setVisibility(View.VISIBLE);
             else
                 imageServeurDeuxiemeJoueurB.setVisibility(View.VISIBLE);
         }
    }

    /**
     * @brief Réinitialise l'affichage du serveur
     */
    private void renitialiserAffichageServeur()
    {
        imageServeurJoueurA.setVisibility(View.INVISIBLE);
        imageServeurDeuxiemeJoueurA.setVisibility(View.INVISIBLE);
        imageServeurJoueurB.setVisibility(View.INVISIBLE);
        imageServeurDeuxiemeJoueurB.setVisibility(View.INVISIBLE);
    }

    /**
     * @brief Affiche l'état de la connexion bluetooth des modules
     * @param nomModule Le nom du module concerné
     * @param estConnecte Représente l'état de la connexion du module
     */
    private void afficherEtatConnexionBluetooth(String nomModule, boolean estConnecte)
    {
        if (nomModule.startsWith(ProtocolAREA.NOM_MODULE_NET))
        {
            changerEtatConnexionBluetooth(imageConnexionModuleNet,estConnecte);
        }
        else
        {
            if(nomModule.startsWith(ProtocolAREA.NOM_MODULE_AFFICHEUR_AREA))
                changerEtatConnexionBluetooth(imageConnexionModuleAfficheur,estConnecte);
            else
                changerEtatConnexionBluetooth(imageConnexionModuleScore,estConnecte);
        }
    }

    /**
     * @brief Affiche une image rouge ou verte en fonction du booleen passé en paramètre
     * @param imageConnexionModule L'image a changer de couleur
     * @param estConnecte Représente l'état de la connexion du module
     */
    private void changerEtatConnexionBluetooth(ImageView imageConnexionModule,boolean estConnecte)
    {
        imageConnexionModule.setColorFilter(Color.RED);
        if (estConnecte)
            imageConnexionModule.setColorFilter(Color.GREEN);
    }

    /**
     * @brief Active ou désactive les boutons selon leur état initial
     */
    private void inverserEtatBoutons()
    {
        boolean etatBouton = true;
        if (boutonAjouterPointJoueurA.isEnabled())
            etatBouton = false;
        boutonAjouterPointJoueurA.setEnabled(etatBouton);
        boutonAjouterPointJoueurB.setEnabled(etatBouton);
        boutonRetirerPointJoueurA.setEnabled(etatBouton);
        boutonRetirerPointJoueurB.setEnabled(etatBouton);
        boutonTempsMortJoueurA.setEnabled(etatBouton);
        boutonTempsMortJoueurB.setEnabled(etatBouton);
    }

    private void actualiserEtatBoutonTempsMorts()
    {
        if (partie.getTempsMortsJoueursA() > 0)
            boutonTempsMortJoueurA.setEnabled(true);
        else
            boutonTempsMortJoueurA.setEnabled(false);

        if (partie.getTempsMortsJoueursB() > 0)
            boutonTempsMortJoueurB.setEnabled(true);
        else
            boutonTempsMortJoueurB.setEnabled(false);
    }

    /**
     * @brief Termine l'activité
     */
    @Override
    public void finish()
    {
        Log.d(TAG, "finish()");
        if (partie.estFinie())
        {
            envoyerTramesScore();
            try
            {
                Thread.holdsLock(this);
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            envoyerTramesEtatPartie();
        }
        final Intent intent = new Intent(IHMGestionPartie.this,IHMGestionRencontre.class);
        intent.putExtra(IHMGestionRencontre.ID_INTENT_FIN_PARTIE,partie);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
