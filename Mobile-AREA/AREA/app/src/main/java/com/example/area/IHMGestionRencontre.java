package com.example.area;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Vector;

/**
 * @file IHMGestionRencontre.java
 * @brief Déclaration de la classe IHMGestionRencontre
 * @author BRUNET Bastien
 * $LastChangedRevision: 125 $
 * $LastChangedDate: 2021-06-11 15:03:25 +0200 (ven. 11 juin 2021) $
 */

/**
 * @class IHMGestionRencontre
 * @brief L'activité permettant de gérer une rencontre
 */
public class IHMGestionRencontre extends AppCompatActivity
{
    /**
     * Constantes
     */
    private static final String TAG = "_IHMGestionRencontre";  //!< TAG pour les logs
    public static final int DEMARRAGE_PARTIE = 0;  //!< Code pour le lancement de l'activité IHMGestionPartie
    private static final String TEXTE_BOUTON_DEMARRER_PARTIE = "Démarrer"; //!< Texte du bouton pour démarrer une partie
    public static final String ID_INTENT_LANCEMENT_PARTIE = "Partie";//!< Identifiant de l'Intent permettant de lancer l'activité IHMGestionPartie
    public static final String TITRE_ALERT_DIALOG_DEMANDER_SERVEUR = "Veuillez sélectionner le premier joueur à servir :";//!< Texte pour demander qui est le serveur
    public static final String TITRE_ACTIVITE = "Liste des parties pour la rencontre :  ";//!< Texte affiché en haut de l'activité 
    public static final String ID_INTENT_FIN_PARTIE = "PartieFinie";//!< Identifiant de l'Intent permettant recu de l'activité IHMGestionPartie
    public static final String ID_INTENT_POSITION_INVERSE = "PositionInverse";//!< Identifiant de l'Intent permettant d'informer IHMGestionPartie de la position des joueurs
    private static final CharSequence[] POSITIONS = {"Gauche","Droite"};//!< Les deux positions possibles affichées dans la boite de dialogue permettant de choisir le côté du joueurA

    /**
     * Ressources IHM
     */
    private Button boutonDemarrerPartie;//!< Le bouton permettant de démarrer une partie
    private AlertDialog.Builder alertDialogBuilderDemanderServeur;//!< L'objet servant à construire la boite de dialogue permettant le choix du premier serveur
    private AlertDialog alertDialogDemanderServeur;//!< La boite de dialogue permettant le choix du premier serveur
    private ListView listeParties;//!< La liste des parties
    private AlertDialog.Builder alertDialogBuilderDemanderCote;//!< L'objet servant à construire la boite de dialogue permettant le choix du côté du relanceur
    private AlertDialog alertDialogDemanderCote;//!< La boite de dialogue permettant le choix du côté du relanceur

    /**
     * Attributs
     */
    private Rencontre rencontre;//!< La rencontre gérée par l'activitée
    private Handler handler = null;//!< Le handler de l'activitée
    private LiaisonBluetooth liaisonModuleAfficheur = null;//!< La liaison bluetooth avec le module afficheur
    private BaseDeDonnees baseDeDonnees = null;

    /**
     * @brief Méthode appelée à la création de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ihm_gestion_rencontre);
        Log.d(TAG, "onCreate()");

        baseDeDonnees = new BaseDeDonnees(this);

        rencontre = (Rencontre) getIntent().getSerializableExtra(IHMLancementRencontre.ID_INTENT_LANCEMENT_RENCONTRE);

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
        liaisonModuleAfficheur.deconnecter();
    }

    /**
     * @brief Méthode appelée à la destruction de l'application (après onStop() et détruite par le système Android)
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
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
                        liaisonModuleAfficheur.envoyer(ProtocolAREA.fabriquerTrameAfficheurRencontre(rencontre));
                        try
                        {
                            Thread.holdsLock(this);
                            Thread.sleep(500);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        envoyerPartiesAfficheur();
                        break;
                    case LiaisonBluetooth.DECONNEXION_SOCKET:
                        Log.d(TAG, "[Handler] DECONNEXION_SOCKET = " + message.obj.toString());
                        break;
                    case LiaisonBluetooth.RECEPTION_TRAME:
                        Log.d(TAG, "[Handler] RECEPTION_TRAME = " + message.obj.toString());
                        break;
                }
            }
        };
    }

    /**
     * @brief Initialise la liaison Bluetooth
     */
    private void initialiserLiaisonBluetooth()
    {
        Log.d(TAG,"initialiserLiaisonBluetooth()");
        liaisonModuleAfficheur = new LiaisonBluetooth(ProtocolAREA.NOM_MODULE_AFFICHEUR_AREA, handler);
        liaisonModuleAfficheur.connecter();
    }

    /**
     * @brief Envoie toute les parties de la rencontre au module Afficheur
     */
    private void envoyerPartiesAfficheur()
    {
        Log.d(TAG,"envoyerPartiesAfficheur()");
        Vector<Partie> parties = rencontre.getParties();

        for (int i = 0; i < parties.size(); i++)
        {
            liaisonModuleAfficheur.envoyer(ProtocolAREA.fabriquerTrameAfficheur(ProtocolAREA.TRAME_AFFICHEUR_INFO_PARTIE,parties.elementAt(i)));
        }
    }

    /**
     * @brief Recupère les ressources graphiques de l'activité
     */
    private void recupererRessourcesIHM()
    {
        Log.d(TAG,"recupererRessourcesIHM()");
        boutonDemarrerPartie = findViewById(R.id.boutonDemarrerPartie);
        alertDialogBuilderDemanderServeur = new AlertDialog.Builder(this);
        listeParties = findViewById(R.id.listeParties);
        alertDialogBuilderDemanderCote = new AlertDialog.Builder(this);
    }

    /**
     * @brief Initialise les ressources graphiques de l'activité
     */
    private void initialiserRessourcesIHM()
    {
        Log.d(TAG,"initialiserRessourcesIHM()");
        boutonDemarrerPartie.setText(TEXTE_BOUTON_DEMARRER_PARTIE);

        alertDialogBuilderDemanderServeur.setTitle(TITRE_ALERT_DIALOG_DEMANDER_SERVEUR);

        alertDialogDemanderServeur = alertDialogBuilderDemanderServeur.create();

        alertDialogDemanderCote = alertDialogBuilderDemanderCote.create();

        afficherParties();

        getSupportActionBar().setTitle( TITRE_ACTIVITE + rencontre.getEquipeA().getNomClub() + " VS " + rencontre.getEquipeB().getNomClub());
    }

    /**
     * @brief Définit le comportement des boutons
     */
    private void connecterBoutons()
    {
        Log.d(TAG,"connecterBoutons()");
        boutonDemarrerPartie.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.d(TAG,"onClickBoutonDemarrerPartie()");
                if(listeParties.getCheckedItemPosition() != AdapterView.INVALID_POSITION)
                {
                    demanderServeur(rencontre.getParties().elementAt(listeParties.getCheckedItemPosition()));
                }
            }
        });

        listeParties.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d(TAG,"onItemClickListeParties() position = " + position);
                listeParties.setItemChecked(position,true);
            }
        });
    }

    /**
     * @brief Traite le retour de l'activité IHMGestionPartie
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() resultCode : " + resultCode);

        // Exemple
        if(requestCode == DEMARRAGE_PARTIE && resultCode == RESULT_OK)
        {
            Log.d(TAG, "onActivityResult() IHMGestionPartie s'est terminée avec succès !");

            Partie partie = (Partie) data.getSerializableExtra(IHMGestionRencontre.ID_INTENT_FIN_PARTIE);
            if (partie.estFinie())
            {
                Log.d(TAG, "onActivityResult() position = " + listeParties.getCheckedItemPosition());
                Log.d(TAG,"onActivityResult() childCount = " + listeParties.getChildCount());

                baseDeDonnees.terminerPartie(partie.getId());
                afficherParties();

                if(partie.getJoueursA() == partie.getVainqueur())
                    rencontre.ajouterPointEquipe(Rencontre.INDEX_EQUIPE_A);
                else
                    rencontre.ajouterPointEquipe(Rencontre.INDEX_EQUIPE_B);
            }
        }
    }

    /**
     * @brief Demande à l'arbitre qui est le premier à servir
     */
    private void demanderServeur(Partie partie)
    {
        Log.d(TAG,"demanderServeur()");
        Vector<Joueur> joueursA = partie.getJoueursA();
        Vector<Joueur> joueursB = partie.getJoueursB();
        CharSequence[] nomJoueurs = recupererNomsJoueurs(joueursA, joueursB);

        alertDialogBuilderDemanderServeur.setItems(nomJoueurs, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int numeroJoueur)
            {
                Log.d(TAG,"onClickDemanderServeur()");
                // Partie simple ?
                if(partie.getNbJoueurs() == 2)
                {
                    if(numeroJoueur == 0)
                    {
                        Log.d(TAG,"Serveur dans équipe A à la position : " + numeroJoueur + " (" + joueursA.elementAt(numeroJoueur).getNom() + ")");
                        joueursA.elementAt(numeroJoueur).setEstServeur(true);
                        partie.setJoueursA(joueursA);

                    }
                    else
                    {
                        Log.d(TAG,"Serveur dans équipe B à la position : " + (numeroJoueur-1) + " (" + joueursB.elementAt(numeroJoueur-1).getNom() + ")");
                        joueursB.elementAt(numeroJoueur-1).setEstServeur(true);
                        partie.setJoueursB(joueursB);
                    }
                }
                else if(partie.getNbJoueurs() == 4) // Partie double ?
                {
                    if(numeroJoueur < 2)
                    {
                        Log.d(TAG,"Serveur dans équipe A à la position : " + numeroJoueur + " (" + joueursA.elementAt(numeroJoueur).getNom() + ")");
                        joueursA.elementAt(numeroJoueur).setEstServeur(true);
                        partie.setJoueursA(joueursA);
                    }
                    else
                    {
                        Log.d(TAG,"Serveur dans équipe B à la position : " + (numeroJoueur-2) + " (" + joueursB.elementAt(numeroJoueur-2).getNom() + ")");
                        joueursB.elementAt(numeroJoueur-2).setEstServeur(true);
                        partie.setJoueursB(joueursB);
                    }
                }
                else
                {
                    Log.e(TAG,"Le nombre de joueurs dans cette partie est invalide !");
                }

                demanderCote(partie);
            }
        });

        afficherChoixServeur();
    }

    /**
     * @brief Affiche la boite de dialogue qui demande qui doit servir en premier
     */
    private void afficherChoixServeur()
    {
        Log.d(TAG,"afficherChoixServeur()");
        alertDialogDemanderServeur = alertDialogBuilderDemanderServeur.create();

        alertDialogDemanderServeur.show();
    }

    /**
     * @brief Demande de quel coté se trouve les joueurs d'une partie
     */
    private void demanderCote(Partie partie)
    {
        Log.d(TAG,"demanderCote()");
        // Partie simple ?
        if (partie.getNbJoueurs() == 2)
        {
            alertDialogBuilderDemanderCote.setTitle("Veuillez sélectionner le coté de " + partie.getJoueursA().firstElement().getNom() + partie.getJoueursA().firstElement().getPrenom());
        }
        else if (partie.getNbJoueurs() == 4) // Partie double ?
        {
            alertDialogBuilderDemanderCote.setTitle("Veuillez sélectionner le coté de " + partie.getJoueursA().firstElement().getNom() + " " + partie.getJoueursA().firstElement().getPrenom() + " et " + partie.getJoueursA().elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getNom() + " " + partie.getJoueursA().elementAt(Partie.POSITION_DEUXIEME_JOUEUR).getPrenom());
        }

        alertDialogBuilderDemanderCote.setItems(POSITIONS, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int numeroCote)
            {
                Log.d(TAG,"onClickDemanderCote()");
                if (numeroCote == 0)
                    lancerPartie(partie,false);
                else
                    lancerPartie(partie,true);
            }
        });

        afficherChoixCote();
    }

    /**
     * @brief Affiche la boite de dialogue qui demande de quel cote se trouve le relanceur
     */
    private void afficherChoixCote()
    {
        Log.d(TAG,"afficherChoixCote()");
        alertDialogDemanderCote = alertDialogBuilderDemanderCote.create();

        alertDialogDemanderCote.show();
    }

    /**
     * @brief Récupère les noms des joueurs et de leur club dans un tableau de CharSequence
     */
    private CharSequence[] recupererNomsJoueurs(Vector<Joueur> joueursA, Vector<Joueur> joueursB)
    {
        Log.d(TAG,"recupererNomsJoueurs()");
        int nbJoueurs = joueursA.size() + joueursB.size();
        CharSequence nomJoueurs[] = new CharSequence[nbJoueurs];
        ListIterator<Joueur> it = joueursA.listIterator();
        int i = 0;
        Joueur joueur;

        while (it.hasNext())
        {
            joueur = it.next();
            nomJoueurs[i] = "[ " + rencontre.getEquipeA().getNomClub() + "] " + joueur.getNom()+ " " + joueur.getPrenom();
            i++;
        }

        it = joueursB.listIterator();

        while (it.hasNext())
        {
            joueur = it.next();
            nomJoueurs[i] = "[ " + rencontre.getEquipeB().getNomClub() + "] " + joueur.getNom()+ " " + joueur.getPrenom();
            i++;
        }
        return nomJoueurs;
    }

    /**
     * @brief Lance une partie
     * @param partie La partie à lancer
     */
    private void lancerPartie(Partie partie, boolean positionInverse)
    {
        Log.d(TAG,"lancerPartie()");
        final Intent intent = new Intent(IHMGestionRencontre.this, IHMGestionPartie.class);
        intent.putExtra(ID_INTENT_LANCEMENT_PARTIE, partie);
        intent.putExtra(ID_INTENT_POSITION_INVERSE, positionInverse);
        Log.d(TAG,"Lancement de l'activité IHMGestionPartie");
        startActivityForResult(intent, DEMARRAGE_PARTIE);
    }

    /**
     * @brief Affiche une liste de toutes les parties d'une rencontre
     */
    private void afficherParties()
    {
        Log.d(TAG,"afficherParties()");

        rencontre.setParties(baseDeDonnees.getParties(rencontre.getId()));
        Vector<Partie> parties = rencontre.getParties();
        ListIterator<Partie> it = parties.listIterator();
        ArrayList<String> arrayListParties = new ArrayList<String>();
        AdapterListe adapter = null;
        Partie partie = null;
        int i = 0;

        adapter = new AdapterListe(this, android.R.layout.simple_list_item_single_choice, arrayListParties);

        while(it.hasNext())
        {
            partie = it.next();
            arrayListParties.add(formaterPartie(partie));
            if (partie.estFinie())
                adapter.desactiverItem(i);
            i++;
        }

        adapter.setNotifyOnChange(false);
        listeParties.setAdapter(adapter);
    }

    /**
     * @brief Formate une partie sous forme de String en respectant la structure suivante : NOM_JOUEUR_A PrenomJoueurA VS NOM_JOUEUR_B PrenomJoueurB
     */
    private String formaterPartie(Partie partie)
    {
        Log.d(TAG,"formaterPartie()");
        String partieFormatee = new String();
        Vector<Joueur> joueursA = partie.getJoueursA();
        Vector<Joueur> joueursB = partie.getJoueursB();
        ListIterator<Joueur> it = joueursA.listIterator();
        Joueur joueur = null;

        Log.d(TAG,"formaterPartie() : nbJoueursA = " + Integer.toString(joueursA.size()));
        Log.d(TAG,"formaterPartie() : nbJoueursB = " + Integer.toString(joueursB.size()));

        if ((partie.getVainqueur() != null) && partie.getVainqueur().equals(joueursA))
            partieFormatee += "<b>";

        while (it.hasNext())
        {
            joueur = it.next();
            partieFormatee += joueur.getNom() + " " + joueur.getPrenom() + " ";

        }

        if ((partie.getVainqueur() != null) && partie.getVainqueur().equals(joueursA))
            partieFormatee += "</b>";

        it = joueursB.listIterator();
        partieFormatee += " VS  ";

        if ((partie.getVainqueur() != null) && partie.getVainqueur().equals(joueursB))
            partieFormatee += "<b>";

        while (it.hasNext())
        {
            joueur = it.next();
            partieFormatee += joueur.getNom() + " " + joueur.getPrenom() + " ";
        }

        if ((partie.getVainqueur() != null) && partie.getVainqueur().equals(joueursB))
            partieFormatee += "</b>";

        Log.d(TAG,"formaterPartie() : Nb sets = " + Integer.toString(partie.getManches().size()));
        Log.d(TAG,"formaterPartie() : " + partieFormatee);

        return partieFormatee;
    }

    private class AdapterListe extends ArrayAdapter<String>
    {
        private Vector<Integer> positionsDesactivees = null;
        LayoutInflater inflater = null;
        int ressource = 0;

        public AdapterListe(Context context, int ressource, ArrayList<String> donnees)
        {
            super(context,ressource,donnees);
            this.positionsDesactivees = new Vector<>();
            this.inflater = LayoutInflater.from(context);
            this.ressource = ressource;
        }

        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent)
        {
            Log.d(TAG,"getView()");
            if (convertView == null)
            {
                convertView = inflater.inflate(ressource,parent, false);
            }

            CheckedTextView textView = convertView.findViewById(android.R.id.text1);
            textView.setText(Html.fromHtml(this.getItem(position)));
            if (positionsDesactivees.contains(position))
            {
                textView.setEnabled(false);
                textView.setClickable(false);
                textView.setOnClickListener(null);
            }
            else
            {
                textView.setEnabled(true);
                textView.setClickable(true);
                textView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        listeParties.setItemChecked(position,true);
                    }
                });
            }

            return convertView;
        }

        public void desactiverItem(int position)
        {
            Log.d(TAG,"desactiverItem()");
            if (!positionsDesactivees.contains(position))
                positionsDesactivees.add(position);
        }
    }
}
