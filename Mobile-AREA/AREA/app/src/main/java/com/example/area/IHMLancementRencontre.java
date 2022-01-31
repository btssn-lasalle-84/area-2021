package com.example.area;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.AutoCompleteTextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.Vector;

/**
 * @file IHMLancementRencontre.java
 * @brief Déclaration de la classe IHMLancementRencontre
 * @author BRUNET Bastien
 **/

/**
 * @class IHMLancementRencontre
 * @brief L'activité principale de l'application AREA
 */

public class IHMLancementRencontre extends AppCompatActivity
{
    /**
     * Constantes
     */
    private static final String TAG = "_IHMLancementRencontre";  //!< TAG pour les logs
    public static final int DEMARRAGE_RENCONTRE = 0;  //!< Code pour le lancement de l'activité IHMGestionRencontre
    public static final String ID_INTENT_LANCEMENT_RENCONTRE = "Rencontre";//!< Identifiant de l'Intent permettant de lancer l'activité IHMGestioNRencontre
    private static final String TITRE_EQUIPE_A = "Equipe A";//!< Titre de la section concernant l'équipe A
    private static final String TITRE_EQUIPE_B = "Equipe B";//!< Titre de la section concernant l'équipe B
    private static final String TEXTE_NOM_EQUIPE = "Nom de l'équipe :";//!< Texte devant les champs de saisie du nom des équipes
    private static final String TEXTE_JOUEUR_A = "A";//!< Texte devant les champs de saisie des informations du joueur A
    private static final String TEXTE_JOUEUR_B = "B";//!< Texte devant les champs de saisie des informations du joueur B
    private static final String TEXTE_JOUEUR_C = "C";//!< Texte devant les champs de saisie des informations du joueur C
    private static final String TEXTE_JOUEUR_D = "D";//!< Texte devant les champs de saisie des informations du joueur D
    private static final String TEXTE_JOUEUR_W = "W";//!< Texte devant les champs de saisie des informations du joueur W
    private static final String TEXTE_JOUEUR_X = "X";//!< Texte devant les champs de saisie des informations du joueur X
    private static final String TEXTE_JOUEUR_Y = "Y";//!< Texte devant les champs de saisie des informations du joueur Y
    private static final String TEXTE_JOUEUR_Z = "Z";//!< Texte devant les champs de saisie des informations du joueur Z
    private static final String TITRE_COLONNE_JOUEUR = "Joueur";//!< Titre de la colonne Joueur
    private static final String TITRE_COLONNE_NOM = "Nom";//!< Titre de la colonne Nom
    private static final String TITRE_COLONNE_PRENOM = "Prénom";//!< Titre de la colonne Prénom
    private static final String TITRE_COLONNE_NUMERO_LICENSE = "Numéro de licence";//!< Titre de la colonne Numero de licence
    private static final String TITRE_PARAMETRES_RENCONTRE = "Paramètres de la rencontre";//!< Titre de la section de saisie de paramètres de la rencontre
    private static final String TEXTE_NB_PARTIES_GAGNANTE = "Nombre de parties gagnantes";//!< Texte devant le champ de saisie du nombre de parties gagnantes
    private static final String TEXTE_NB_MANCHES_GAGNANTE = "Nombre de manches gagnantes";//!< Texte devant le champ de saisie du nombre de manches gagnantes
    private static final String TEXTE_NB_POINTS_MANCHE = "Nombre de points par manche";//!< Texte devant les champs de saisie du nom des nombre de points par manche
    private static final String TEXTE_BOUTON_VALIDER = "Valider";//!< Texte du bouton valider
    private static final String MESSAGE_ERREUR_SAISIE = "Veuillez saisir une valeur";//!< Texte en cas d'erreur de saisie

    /**
     * Ressources IHM
     */
    private Button boutonValider;//!< Bouton permettant de valider la saisie
    private TextView textViewEquipeA;//!< Affichage du titre de la section concernant l'équipe A
    private TextView textViewEquipeB;//!< Affichage du titre de la section concernant l'équipe B
    private TextView textViewNomEquipeA;//!< Affichage du texte devant le champ de saisie du nom de l'équipe A
    private AutoCompleteTextView editTextNomEquipeA;//!< Champ permettant la saisie du nom de l'équipe A
    private TextView textViewNomEquipeB;//!< Affichage du texte devant le champ de saisie du nom de l'équipe B
    private AutoCompleteTextView editTextNomEquipeB;//!< Champ permettant la saisie du nom de l'équipe B
    private TextView textViewTitreColonneJoueurEquipeA;//!< Affichage du titre de la colonne Joueur
    private TextView textViewTitreColonneNomJoueurEquipeA;//!< Affichage du titre de la colonne Nom
    private TextView textViewTitreColonnePrenomJoueurEquipeA;//!< Affichage du titre de la colonne Prénom
    private TextView textViewTitreColonneLicenseJoueurEquipeA;//!< Affichage du titre de la colonne Numero de licence
    private TextView textViewTitreColonneJoueurEquipeB;//!< Affichage du titre la colonne Joueur
    private TextView textViewTitreColonneNomJoueurEquipeB;//!< Affichage du titre de la colonne Nom
    private TextView textViewTitreColonnePrenomJoueurEquipeB;//!< Affichage du titre de la colonne Prénom
    private TextView textViewTitreColonneLicenseJoueurEquipeB;//!< Affichage du titre de la colonne Numero de licence
    private TextView textViewJoueurAEquipeA;//!< Affichage du texte devant les champs de saisie des informations du joueur A de l'équipe A
    private TextView textViewJoueurBEquipeA;//!< Affichage du texte devant les champs de saisie des informations du joueur B de l'équipe A
    private TextView textViewJoueurCEquipeA;//!< Affichage du texte devant les champs de saisie des informations du joueur C de l'équipe A
    private TextView textViewJoueurDEquipeA;//!< Affichage du texte devant les champs de saisie des informations du joueur D de l'équipe A
    private TextView textViewJoueurAEquipeB;//!< Affichage du texte devant les champs de saisie des informations du joueur A de l'équipe B
    private TextView textViewJoueurBEquipeB;//!< Affichage du texte devant les champs de saisie des informations du joueur B de l'équipe B
    private TextView textViewJoueurCEquipeB;//!< Affichage du texte devant les champs de saisie des informations du joueur C de l'équipe B
    private TextView textViewJoueurDEquipeB;//!< Affichage du texte devant les champs de saisie des informations du joueur D de l'équipe B
    private TextView textViewNbPartiesGagnante;//!< Affichage du texte devant les champs de saisie du nombre de parties gagnantes
    private TextView textViewNbManchesGagnante;//!< Affichage du texte devant les champs de saisie du nombre de manches gagnantes
    private TextView textViewNbPointsParManche;//!< Affichage du texte devant les champs de saisie du nombre de points par manche
    private TextView textViewParametresRencontre;//!< Affichage du titre de la section de saisie de paramètres de la rencontre
    private AutoCompleteTextView editTextNomJoueurAEquipeA;//!< Champ permettant la saisie du nom du joueur A de l'équipe A
    private AutoCompleteTextView editTextPrenomJoueurAEquipeA;//!< Champ permettant la saisie du prénom du joueur A de l'équipe A
    private AutoCompleteTextView editTextNumeroLicenseJoueurAEquipeA;//!< Champ permettant la saisie du numéro de licence du joueur A de l'équipe A
    private AutoCompleteTextView editTextNomJoueurBEquipeA;//!< Champ permettant la saisie du nom du joueur B de l'équipe A
    private AutoCompleteTextView editTextPrenomJoueurBEquipeA;//!< Champ permettant la saisie du prénom du joueur B de l'équipe A
    private AutoCompleteTextView editTextNumeroLicenseJoueurBEquipeA;//!< Champ permettant la saisie du numéro de licence du joueur B de l'équipe A
    private AutoCompleteTextView editTextNomJoueurCEquipeA;//!< Champ permettant la saisie du nom du joueur C de l'équipe A
    private AutoCompleteTextView editTextPrenomJoueurCEquipeA;//!< Champ permettant la saisie du prénom du joueur C de l'équipe A
    private AutoCompleteTextView editTextNumeroLicenseJoueurCEquipeA;//!< Champ permettant la saisie du numéro de licence du joueur C de l'équipe A
    private AutoCompleteTextView editTextNomJoueurDEquipeA;//!< Champ permettant la saisie du nom du joueur D de l'équipe A
    private AutoCompleteTextView editTextPrenomJoueurDEquipeA;//!< Champ permettant la saisie du prénom du joueur D de l'équipe A
    private AutoCompleteTextView editTextNumeroLicenseJoueurDEquipeA;//!< Champ permettant la saisie du numéro de licence du joueur D de l'équipe A
    private AutoCompleteTextView editTextNomJoueurAEquipeB;//!< Champ permettant la saisie du nom du joueur A de l'équipe B
    private AutoCompleteTextView editTextPrenomJoueurAEquipeB;//!< Champ permettant la saisie du prénom du joueur A de l'équipe B
    private AutoCompleteTextView editTextNumeroLicenseJoueurAEquipeB;//!< Champ permettant la saisie du numéro de licence du joueur A de l'équipe B
    private AutoCompleteTextView editTextNomJoueurBEquipeB;//!< Champ permettant la saisie du nom du joueur B de l'équipe B
    private AutoCompleteTextView editTextPrenomJoueurBEquipeB;//!< Champ permettant la saisie du prénom du joueur B de l'équipe B
    private AutoCompleteTextView editTextNumeroLicenseJoueurBEquipeB;//!< Champ permettant la saisie du numéro de licence du joueur B de l'équipe B
    private AutoCompleteTextView editTextNomJoueurCEquipeB;//!< Champ permettant la saisie du nom du joueur C de l'équipe B
    private AutoCompleteTextView editTextPrenomJoueurCEquipeB;//!< Champ permettant la saisie du prénom du joueur C de l'équipe B
    private AutoCompleteTextView editTextNumeroLicenseJoueurCEquipeB;//!< Champ permettant la saisie du numéro de licence du joueur C de l'équipe B
    private AutoCompleteTextView editTextNomJoueurDEquipeB;//!< Champ permettant la saisie du nom du joueur D de l'équipe B
    private AutoCompleteTextView editTextPrenomJoueurDEquipeB;//!< Champ permettant la saisie du prénom du joueur D de l'équipe B
    private AutoCompleteTextView editTextNumeroLicenseJoueurDEquipeB;//!< Champ permettant la saisie du numéro de licence du joueur D de l'équipe B
    private AutoCompleteTextView editTextNbPartiesGagnante;//!< Champ permettant la saisie du nombre de parties gagnantes
    private AutoCompleteTextView editTextNbManchesGagnante;//!< Champ permettant la saisie du nombre manches gagnantes
    private AutoCompleteTextView editTextNbPointsParManche;//!< Champ permettant la saisie du nombre points par manche

    /**
     * Attributs
     */
    BaseDeDonnees baseDeDonnees = null;

    /**
     * @brief Méthode appelée à la création de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ihm_lancement_rencontre);

        getSupportActionBar().setTitle(getString(R.string.app_name) + " v" + BuildConfig.VERSION_NAME);

        recupererRessourcesIHM();

        initialiserRessourcesIHM();

        connecterBoutons();

        initialiserSuggestions();

        initialiserRencontre();

    }

    /**
     * @brief Méthode permettant d'initialiser les champs avec des données
     */
    private void initialiserRencontre()
    {
        editTextNomEquipeA.setText("PPC Avignon");
        editTextNomEquipeB.setText("PPC Sorgues");
        editTextNomJoueurAEquipeA.setText("RUIZ");
        editTextPrenomJoueurAEquipeA.setText("Jean michel");
        editTextNumeroLicenseJoueurAEquipeA.setText("139328");
        editTextNomJoueurBEquipeA.setText("GUIDARELLI");
        editTextPrenomJoueurBEquipeA.setText("Nicolas");
        editTextNumeroLicenseJoueurBEquipeA.setText("841827");
        editTextNomJoueurCEquipeA.setText("KRIER");
        editTextPrenomJoueurCEquipeA.setText("Eric");
        editTextNumeroLicenseJoueurCEquipeA.setText("843368");
        editTextNomJoueurDEquipeA.setText("REDOR");
        editTextPrenomJoueurDEquipeA.setText("Simon");
        editTextNumeroLicenseJoueurDEquipeA.setText("844443");
        editTextNomJoueurAEquipeB.setText("BEAUMONT");
        editTextPrenomJoueurAEquipeB.setText("Jérôme");
        editTextNumeroLicenseJoueurAEquipeB.setText("843944");
        editTextNomJoueurBEquipeB.setText("SAULNIER");
        editTextPrenomJoueurBEquipeB.setText("Christian");
        editTextNumeroLicenseJoueurBEquipeB.setText("303504");
        editTextNomJoueurCEquipeB.setText("FILAFERRO");
        editTextPrenomJoueurCEquipeB.setText("Thomas");
        editTextNumeroLicenseJoueurCEquipeB.setText("645758");
        editTextNomJoueurDEquipeB.setText("COMTE");
        editTextPrenomJoueurDEquipeB.setText("Emmanuel");
        editTextNumeroLicenseJoueurDEquipeB.setText("842353");
        editTextNbPartiesGagnante.setText("8");
        editTextNbManchesGagnante.setText("3");
        editTextNbPointsParManche.setText("11");

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
    }

    /**
     * @brief Recupère les ressources graphiques de l'activité
     */
    private void recupererRessourcesIHM()
    {
        boutonValider = findViewById(R.id.boutonValider);
        textViewEquipeA = findViewById(R.id.textViewEquipeA);
        textViewEquipeB = findViewById(R.id.textViewEquipeB);
        textViewNomEquipeA = findViewById(R.id.textViewNomEquipeA);
        editTextNomEquipeA = findViewById(R.id.editTextNomEquipeA);
        textViewNomEquipeB = findViewById(R.id.textViewNomEquipeB);
        editTextNomEquipeB = findViewById(R.id.editTextNomEquipeB);
        textViewTitreColonneJoueurEquipeA = findViewById(R.id.textViewTitreColonneJoueurEquipeA);
        textViewTitreColonneNomJoueurEquipeA = findViewById(R.id.textViewTitreColonneNomJoueurEquipeA);
        textViewTitreColonnePrenomJoueurEquipeA = findViewById(R.id.textViewTitreColonnePrenomJoueurEquipeA);
        textViewTitreColonneLicenseJoueurEquipeA = findViewById(R.id.textViewTitreColonneLicenseJoueurEquipeA);
        textViewTitreColonneJoueurEquipeB = findViewById(R.id.textViewTitreColonneJoueurEquipeB);
        textViewTitreColonneNomJoueurEquipeB = findViewById(R.id.textViewTitreColonneNomJoueurEquipeB);
        textViewTitreColonnePrenomJoueurEquipeB = findViewById(R.id.textViewTitreColonnePrenomJoueurEquipeB);
        textViewTitreColonneLicenseJoueurEquipeB = findViewById(R.id.textViewTitreColonneLicenseJoueurEquipeB);
        textViewJoueurAEquipeA = findViewById(R.id.textViewJoueurAEquipeA);
        textViewJoueurBEquipeA = findViewById(R.id.textViewJoueurBEquipeA);
        textViewJoueurCEquipeA = findViewById(R.id.textViewJoueurCEquipeA);
        textViewJoueurDEquipeA = findViewById(R.id.textViewJoueurDEquipeA);
        textViewNbPartiesGagnante = findViewById(R.id.textViewNbPartiesGagnante);
        textViewNbManchesGagnante = findViewById(R.id.textViewNbManchesGagnante);
        textViewNbPointsParManche = findViewById(R.id.textViewNbPointsParManche);
        textViewParametresRencontre = findViewById(R.id.textViewParametresRencontre);
        editTextNomJoueurAEquipeA = findViewById(R.id.editTextNomJoueurAEquipeA);
        editTextPrenomJoueurAEquipeA = findViewById(R.id.editTextPrenomJoueurAEquipeA);
        editTextNumeroLicenseJoueurAEquipeA = findViewById(R.id.editTextNumeroLicenseJoueurAEquipeA);
        editTextNomJoueurBEquipeA = findViewById(R.id.editTextNomJoueurBEquipeA);
        editTextPrenomJoueurBEquipeA = findViewById(R.id.editTextPrenomJoueurBEquipeA);
        editTextNumeroLicenseJoueurBEquipeA = findViewById(R.id.editTextNumeroLicenseJoueurBEquipeA);
        editTextNomJoueurCEquipeA = findViewById(R.id.editTextNomJoueurCEquipeA);
        editTextPrenomJoueurCEquipeA = findViewById(R.id.editTextPrenomJoueurCEquipeA);
        editTextNumeroLicenseJoueurCEquipeA = findViewById(R.id.editTextNumeroLicenseJoueurCEquipeA);
        editTextNomJoueurDEquipeA = findViewById(R.id.editTextNomJoueurDEquipeA);
        editTextPrenomJoueurDEquipeA = findViewById(R.id.editTextPrenomJoueurDEquipeA);
        editTextNumeroLicenseJoueurDEquipeA = findViewById(R.id.editTextNumeroLicenseJoueurDEquipeA);
        editTextNomJoueurAEquipeB = findViewById(R.id.editTextNomJoueurAEquipeB);
        editTextPrenomJoueurAEquipeB = findViewById(R.id.editTextPrenomJoueurAEquipeB);
        editTextNumeroLicenseJoueurAEquipeB = findViewById(R.id.editTextNumeroLicenseJoueurAEquipeB);
        editTextNomJoueurBEquipeB = findViewById(R.id.editTextNomJoueurBEquipeB);
        editTextPrenomJoueurBEquipeB = findViewById(R.id.editTextPrenomJoueurBEquipeB);
        editTextNumeroLicenseJoueurBEquipeB = findViewById(R.id.editTextNumeroLicenseJoueurBEquipeB);
        editTextNomJoueurCEquipeB = findViewById(R.id.editTextNomJoueurCEquipeB);
        editTextPrenomJoueurCEquipeB = findViewById(R.id.editTextPrenomJoueurCEquipeB);
        editTextNumeroLicenseJoueurCEquipeB = findViewById(R.id.editTextNumeroLicenseJoueurCEquipeB);
        editTextNomJoueurDEquipeB = findViewById(R.id.editTextNomJoueurDEquipeB);
        editTextPrenomJoueurDEquipeB = findViewById(R.id.editTextPrenomJoueurDEquipeB);
        editTextNumeroLicenseJoueurDEquipeB = findViewById(R.id.editTextNumeroLicenseJoueurDEquipeB);
        editTextNbPartiesGagnante = findViewById(R.id.editTextNbPartiesGagnante);
        editTextNbManchesGagnante = findViewById(R.id.editTextNbManchesGagnante);
        editTextNbPointsParManche = findViewById(R.id.editTextNbPointsParManche);
        textViewJoueurAEquipeB = findViewById(R.id.textViewJoueurAEquipeB);
        textViewJoueurBEquipeB = findViewById(R.id.textViewJoueurBEquipeB);
        textViewJoueurCEquipeB = findViewById(R.id.textViewJoueurCEquipeB);
        textViewJoueurDEquipeB = findViewById(R.id.textViewJoueurDEquipeB);

        // Donne le focus
        boutonValider.setFocusableInTouchMode(true);
        boutonValider.requestFocus();
    }

    /**
     * @brief Initialise les ressources graphiques de l'activité
     */
    private void initialiserRessourcesIHM()
    {
        textViewEquipeA.setText(TITRE_EQUIPE_A);
        textViewEquipeB.setText(TITRE_EQUIPE_B);
        textViewNomEquipeA.setText(TEXTE_NOM_EQUIPE);
        textViewNomEquipeB.setText(TEXTE_NOM_EQUIPE);

        textViewJoueurAEquipeA.setText(TEXTE_JOUEUR_A);
        textViewJoueurBEquipeA.setText(TEXTE_JOUEUR_B);
        textViewJoueurCEquipeA.setText(TEXTE_JOUEUR_C);
        textViewJoueurDEquipeA.setText(TEXTE_JOUEUR_D);

        textViewJoueurAEquipeB.setText(TEXTE_JOUEUR_W);
        textViewJoueurBEquipeB.setText(TEXTE_JOUEUR_X);
        textViewJoueurCEquipeB.setText(TEXTE_JOUEUR_Y);
        textViewJoueurDEquipeB.setText(TEXTE_JOUEUR_Z);

        textViewTitreColonneJoueurEquipeA.setText(TITRE_COLONNE_JOUEUR);
        textViewTitreColonneNomJoueurEquipeA.setText(TITRE_COLONNE_NOM);
        textViewTitreColonnePrenomJoueurEquipeA.setText(TITRE_COLONNE_PRENOM);
        textViewTitreColonneLicenseJoueurEquipeA.setText(TITRE_COLONNE_NUMERO_LICENSE);

        textViewTitreColonneJoueurEquipeB.setText(TITRE_COLONNE_JOUEUR);
        textViewTitreColonneNomJoueurEquipeB.setText(TITRE_COLONNE_NOM);
        textViewTitreColonnePrenomJoueurEquipeB.setText(TITRE_COLONNE_PRENOM);
        textViewTitreColonneLicenseJoueurEquipeB.setText(TITRE_COLONNE_NUMERO_LICENSE);

        textViewParametresRencontre.setText(TITRE_PARAMETRES_RENCONTRE);

        textViewNbPartiesGagnante.setText(TEXTE_NB_PARTIES_GAGNANTE);
        textViewNbManchesGagnante.setText(TEXTE_NB_MANCHES_GAGNANTE);
        textViewNbPointsParManche.setText(TEXTE_NB_POINTS_MANCHE);

        boutonValider.setText(TEXTE_BOUTON_VALIDER);
    }

    /**
     * @brief Définit le comportement des boutons
     */
    private void connecterBoutons()
    {
        boutonValider.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(validerChamps(recupererChampsSaisie()))
                {
                    lancerRencontre(genererRencontre());
                }
            }
        });
    }

    /**
     * @brief Lance une Rencontre
     * @param rencontre La partie à lancer
     */
    private void lancerRencontre(Rencontre rencontre)
    {
        final Intent intent = new Intent(IHMLancementRencontre.this, IHMGestionRencontre.class);

        rencontre = baseDeDonnees.insererRencontre(rencontre);
        baseDeDonnees.insererParties(rencontre);
        rencontre.setParties(baseDeDonnees.getParties(rencontre.getId()));

        intent.putExtra(ID_INTENT_LANCEMENT_RENCONTRE, rencontre);
        Log.d(TAG,"Lancement de l'activité IHMGestionRencontre");
        startActivityForResult(intent, DEMARRAGE_RENCONTRE);
    }

    /**
     * @brief Récupère tout les champs de saisie de l'activité
     * @return Les champs en question
     */
    private Vector<AutoCompleteTextView> recupererChampsSaisie()
    {
        ViewGroup vues = (ViewGroup)findViewById(R.id.constraintLayoutLancementRencontre);
        Vector listeChamps = new Vector<AutoCompleteTextView>();

        for (int i = 0; i < vues.getChildCount(); i++)
        {
            ViewGroup champsSaisie;
            if (vues.getChildAt(i) instanceof LinearLayout || vues.getChildAt(i) instanceof TableLayout)
            {
                Log.d(TAG, vues.getChildAt(i).toString());
                champsSaisie = (ViewGroup) vues.getChildAt(i);

                for (int j = 0; j< champsSaisie.getChildCount(); j++)
                {
                    if (champsSaisie.getChildAt(j) instanceof TableRow)
                    {
                        ViewGroup ligne = (ViewGroup) champsSaisie.getChildAt(j);
                        for (int k = 0; k < ligne.getChildCount(); k++)
                        {
                            if (ligne.getChildAt(k) instanceof AutoCompleteTextView)
                                listeChamps.add(ligne.getChildAt(k));
                        }
                    }

                    if (champsSaisie.getChildAt(j) instanceof AutoCompleteTextView)
                        listeChamps.add(champsSaisie.getChildAt(j));
                }
            }
        }
        return listeChamps;
    }

    /**
     * @brief Vérifie si les champs sont valides et affiche une erreur sur le champs dans le cas contraire
     * @param  listeChamps Les champs à vérifier
     * @return Retourne true si tout les champs sont valides et false sinon
     */
    private boolean validerChamps(Vector<AutoCompleteTextView> listeChamps)
    {
        boolean champsVides = false;
        boolean champsMultiples = false;
        for (int i = 0; i < listeChamps.size(); i++)
        {
            if (listeChamps.elementAt(i).getText().toString().length() == 0)
            {
                listeChamps.elementAt(i).setError(MESSAGE_ERREUR_SAISIE);
                Log.d(TAG,"Erreur de saisie");
                champsVides = true;
            }
        }
        champsMultiples = chercherDoublons(listeChamps);

        return !(champsVides || champsMultiples);
    }

    /**
     * @brief Recherche les doublons parmis les numéros de licence
     * @param  listeChamps Les champs de l'activité
     * @return Retourne true si il y a doublons
     */
    private boolean chercherDoublons(Vector<AutoCompleteTextView> listeChamps)
    {
        String valeur = "";
        int nbOccurenceValeur = 0;
        for (int i = 0; i < listeChamps.size(); i++)
        {
            if(listeChamps.elementAt(i).getParent() instanceof TableRow && listeChamps.elementAt(i).getInputType() == 2)
            {
                valeur = listeChamps.elementAt(i).getText().toString();
                Log.d(TAG,"Numéro de licence");
                for (int j = 0; j < listeChamps.size(); j++)
                {
                    if(listeChamps.elementAt(j).getParent() instanceof TableRow && listeChamps.elementAt(j).getInputType() == 2 && valeur.equals(listeChamps.elementAt(j).getText().toString()))
                    {
                        nbOccurenceValeur++;
                        Log.d(TAG,"Nombre occurence de la chaine : '" + valeur + "' : " + Integer.toString(nbOccurenceValeur));
                    }
                }
                if (nbOccurenceValeur > 1)
                {
                    listeChamps.elementAt(i).setError("Ces numéros de licences sont identiques");
                    return true;
                }
                nbOccurenceValeur = 0;
            }
        }
        return false;
    }

    /**
     * @brief Génere une renontre à partir des saisies
     * @return La rencontre générée
     */
    private Rencontre genererRencontre()
    {
        Vector<Joueur> joueursEquipe1 = new Vector<Joueur>();
        joueursEquipe1.add(new Joueur(editTextNomJoueurAEquipeA.getText().toString(),editTextPrenomJoueurAEquipeA.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurAEquipeA.getText().toString())));
        joueursEquipe1.add(new Joueur(editTextNomJoueurBEquipeA.getText().toString(),editTextPrenomJoueurBEquipeA.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurBEquipeA.getText().toString())));
        joueursEquipe1.add(new Joueur(editTextNomJoueurCEquipeA.getText().toString(),editTextPrenomJoueurCEquipeA.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurCEquipeA.getText().toString())));
        joueursEquipe1.add(new Joueur(editTextNomJoueurDEquipeA.getText().toString(),editTextPrenomJoueurDEquipeA.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurDEquipeA.getText().toString())));

        Vector<Joueur> joueursEquipe2 = new Vector<Joueur>();
        joueursEquipe2.add(new Joueur(editTextNomJoueurAEquipeB.getText().toString(),editTextPrenomJoueurAEquipeB.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurAEquipeB.getText().toString())));
        joueursEquipe2.add(new Joueur(editTextNomJoueurBEquipeB.getText().toString(),editTextPrenomJoueurBEquipeB.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurBEquipeB.getText().toString())));
        joueursEquipe2.add(new Joueur(editTextNomJoueurCEquipeB.getText().toString(),editTextPrenomJoueurCEquipeB.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurCEquipeB.getText().toString())));
        joueursEquipe2.add(new Joueur(editTextNomJoueurDEquipeB.getText().toString(),editTextPrenomJoueurDEquipeB.getText().toString(),
                Integer.parseInt(editTextNumeroLicenseJoueurDEquipeB.getText().toString())));

        Equipe equipeA = new Equipe(editTextNomEquipeA.getText().toString(), joueursEquipe1);
        Equipe equipeB = new Equipe(editTextNomEquipeB.getText().toString(), joueursEquipe2);

        Rencontre rencontre = new Rencontre(equipeA,equipeB,Integer.parseInt(editTextNbManchesGagnante.getText().toString()),
                Integer.parseInt(editTextNbPointsParManche.getText().toString()),Integer.parseInt(editTextNbPartiesGagnante.getText().toString()));

        return rencontre;
    }

    /**
     * @brief Ajoute les suggestions à toute les zones de saisie
     */
    private void initialiserSuggestions()
    {
        baseDeDonnees = new BaseDeDonnees(this);
        Vector<Equipe> equipes  = baseDeDonnees.getEquipes();

        ajouterSuggestionsNomsEquipes(editTextNomEquipeA,equipes);
        ajouterSuggestionsNomsEquipes(editTextNomEquipeB,equipes);

        Vector<Joueur> joueurs = new Vector<Joueur>();

        for (int i = 0; i < equipes.size(); i++)
        {
            joueurs.addAll(equipes.elementAt(i).getJoueurs());
        }

        ajouterSuggestionsJoueurX(editTextNomJoueurAEquipeA,editTextPrenomJoueurAEquipeA,editTextNumeroLicenseJoueurAEquipeA,joueurs);
        ajouterSuggestionsJoueurX(editTextNomJoueurBEquipeA,editTextPrenomJoueurBEquipeA,editTextNumeroLicenseJoueurBEquipeA,joueurs);
        ajouterSuggestionsJoueurX(editTextNomJoueurCEquipeA,editTextPrenomJoueurCEquipeA,editTextNumeroLicenseJoueurCEquipeA,joueurs);
        ajouterSuggestionsJoueurX(editTextNomJoueurDEquipeA,editTextPrenomJoueurDEquipeA,editTextNumeroLicenseJoueurDEquipeA,joueurs);
        ajouterSuggestionsJoueurX(editTextNomJoueurAEquipeB,editTextPrenomJoueurAEquipeB,editTextNumeroLicenseJoueurAEquipeB,joueurs);
        ajouterSuggestionsJoueurX(editTextNomJoueurBEquipeB,editTextPrenomJoueurBEquipeB,editTextNumeroLicenseJoueurBEquipeB,joueurs);
        ajouterSuggestionsJoueurX(editTextNomJoueurCEquipeB,editTextPrenomJoueurCEquipeB,editTextNumeroLicenseJoueurCEquipeB,joueurs);
        ajouterSuggestionsJoueurX(editTextNomJoueurDEquipeB,editTextPrenomJoueurDEquipeB,editTextNumeroLicenseJoueurDEquipeB,joueurs);

    }

    /**
     * @brief Ajoute les noms, prénoms et numéros de licence des joueurs en suggestions aux zones de saisie passées en paramètres
     * @param editTextNomJoueur La zone de saisie du nom
     * @param editTextPrenomJoueur La zone de saisie du prénom
     * @param editTextNumeroLicenceJoueur La zone de saisie du numéro de licence
     * @param joueurs Les joueurs dont les noms doivent etres ajoutés
     */
    private void ajouterSuggestionsJoueurX(AutoCompleteTextView editTextNomJoueur,AutoCompleteTextView editTextPrenomJoueur,AutoCompleteTextView editTextNumeroLicenceJoueur,Vector<Joueur> joueurs)
    {
        ajouterSuggestionsNomsJoueurs(editTextNomJoueur,joueurs);
        ajouterSuggestionsPrenomsJoueurs(editTextPrenomJoueur,joueurs);
        ajouterSuggestionsNumeroLicenceJoueurs(editTextNumeroLicenceJoueur,joueurs);
    }

    /**
     * @brief Ajoute les noms des équipes en suggestions à la zone de saisie passée en paramètres
     * @param editTextNomEquipe La zone de saisie
     * @param equipes Les équipes dont les noms doivent etres ajoutés
     */
    private void ajouterSuggestionsNomsEquipes(AutoCompleteTextView editTextNomEquipe, Vector<Equipe> equipes)
    {
        String[] nomsEquipes = new String[equipes.size()];
        for (int i = 0; i < equipes.size(); i++)
        {
            nomsEquipes[i] = equipes.elementAt(i).getNomClub();
        }
        appliquerAdapter(nomsEquipes,editTextNomEquipe);
    }

    /**
     * @brief Ajoute les noms des joueurs en suggestions à la zone de saisie passée en paramètres
     * @param editTextNomJoueur La zone de saisie
     * @param joueurs Les joueurs dont les noms doivent etres ajoutés
     */
    private void ajouterSuggestionsNomsJoueurs(AutoCompleteTextView editTextNomJoueur, Vector<Joueur> joueurs)
    {
        String[] nomsJoueurs = new String[joueurs.size()];
        for (int i = 0; i < joueurs.size(); i++)
        {
            nomsJoueurs[i] = joueurs.elementAt(i).getNom();
        }
        appliquerAdapter(nomsJoueurs,editTextNomJoueur);
    }

    /**
     * @brief Ajoute les prénoms des joueurs en suggestions à la zone de saisie passée en paramètres
     * @param editTextPrenomJoueur La zone de saisie
     * @param joueurs Les joueurs dont les prénoms doivent etres ajoutés
     */
    private void ajouterSuggestionsPrenomsJoueurs(AutoCompleteTextView editTextPrenomJoueur, Vector<Joueur> joueurs)
    {
        String[] prenomsJoueurs = new String[joueurs.size()];
        for (int i = 0; i < joueurs.size(); i++)
        {
            prenomsJoueurs[i] = joueurs.elementAt(i).getPrenom();
        }
        appliquerAdapter(prenomsJoueurs,editTextPrenomJoueur);
    }

    /**
     * @brief Ajoute les numéros de licence des joueurs en suggestions à la zone de saisie passée en paramètres
     * @param editTextNumeroLicenceJoueur La zone de saisie
     * @param joueurs Les joueurs dont les numéros de licence doivent etres ajoutés
     */
    private void ajouterSuggestionsNumeroLicenceJoueurs(AutoCompleteTextView editTextNumeroLicenceJoueur, Vector<Joueur> joueurs)
    {
        String[] numerosLicenceJoueurs = new String[joueurs.size()];
        for (int i = 0; i < joueurs.size(); i++)
        {
            numerosLicenceJoueurs[i] = Integer.toString(joueurs.elementAt(i).getNumLicence());
        }
        appliquerAdapter(numerosLicenceJoueurs,editTextNumeroLicenceJoueur);
    }

    /**
     * @brief Créée et applique un adapter a la zone de saisie passée en paramètre
     * @param editText La zone de saisie
     * @param suggestions Les suggestions à ajouter
     */
    private void appliquerAdapter(String[] suggestions, AutoCompleteTextView editText)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, suggestions);
        editText.setAdapter(adapter);
    }
}
