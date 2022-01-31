#include "IHMAfficheur.h"
#include "ui_IHMAfficheur.h"
#include "Rencontre.h"
#include "Communication.h"
#include <QtDebug>

/**
 * @file IHMAfficheur.cpp
 * @brief Définition de la classe IHMAfficheur
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

/**
 * @brief Constructeur de la classe IHMAfficheur
 *
 * @fn IHMAfficheur::IHMAfficheur
 * @param parent L'adresse de l'objet parent, si nullptr IHMAfficheur sera la fenêtre principale
 */

IHMAfficheur::IHMAfficheur(QWidget *parent) : QMainWindow(parent), ui(new Ui::IHMAfficheur), rencontre(nullptr), communication(nullptr), tempsPartieGauche(new QTime(0,0)), tempsPartieDroite(new QTime(0,0)), timerPartieGauche(new QTimer()), timerPartieDroite(new QTimer()), chronometrePartieGauche(new QElapsedTimer()), chronometrePartieDroite(new QElapsedTimer())
{
    qDebug() << Q_FUNC_INFO;

    disponibiliteAffichages = {0,0};
    chronometrePartieGauche->start();
    chronometrePartieDroite->start();

    initialiserIHM();

    initialiserFenetreAcceuil();



    #ifndef TEST_SANS_BLUETOOTH
    initialiserCommunication();
    #endif

    #ifdef TEST_SANS_BLUETOOTH
    commencerRencontre("PPC Avignon", "PPC Sorgues");
    connect(timerPartieGauche,SIGNAL(timeout()),this, SLOT(actualiserTimerGauche()));
    connect(timerPartieDroite,SIGNAL(timeout()),this, SLOT(actualiserTimerDroite()));

    QStringList infosTrame = {"MOBILE_AREA", "1","2","GUIDARELLI","Nicolas","SAULNIER","Christian"};
    rencontre->creerPartieSimple(infosTrame);
    changerEtatPartie(2,"DEMARREE");
    actualiserScore(2,9,6,1,3);
    changerEtatPartie(2,"TERMINEE");

    QStringList infosTrame2 = {"MOBILE_AREA", "1","3","GUIDARELLI","Nicolas","SAULNIER","Christian","BEAUMONT", "Jérôme", "RUIZ", "Jean michel"};
    rencontre->creerPartieDouble(infosTrame2);
    changerEtatPartie(3,"DEMARREE");
    actualiserScore(3,9,6,1,3);
    changerEtatPartie(3,"TERMINEE");

    QStringList infosTrame3 = {"MOBILE_AREA", "1","4","BEAUMONT", "Jérôme", "RUIZ", "Jean michel"};
    rencontre->creerPartieSimple(infosTrame3);
    changerEtatPartie(4,"DEMARREE");
    actualiserScore(4,9,6,1,3);
    changerEtatPartie(4,"TERMINEE");

    QStringList infosTrame4 = {"MOBILE_AREA", "1","5","KRIER","Eric","FILAFERRO ","Thomas"};
    rencontre->creerPartieSimple(infosTrame4);
    changerEtatPartie(5,"DEMARREE");
    afficherNET(5);
    actualiserScore(5,9,6,1,2);
    //changerEtatPartie(5,"TERMINEE");
    #endif
}

/**
 * @brief Destructeur de la classe IHMAfficheur
 *
 * @fn IHMAfficheur::~IHMAfficheur
 * @details Libère les ressources de l'application
 */
IHMAfficheur::~IHMAfficheur()
{
    if(rencontre != nullptr)
        delete rencontre;
    delete ui;
    qDebug() << Q_FUNC_INFO;
}

/**
 * @brief Initialise les widgets de l'IHM
 *
 * @fn IHMAfficheur::initialiserIHM
 */
void IHMAfficheur::initialiserIHM()
{
    qDebug() << Q_FUNC_INFO;
    ui->setupUi(this);

    initialiserCouleurs();

    // Mode debug
    QAction *quitter = new QAction(this);
    quitter->setShortcut(QKeySequence(QKeySequence(Qt::Key_Escape)));
    addAction(quitter);
    connect(quitter, SIGNAL(triggered()), this, SLOT(close()));
}

void IHMAfficheur::initialiserCouleurs()
{
    ui->LabelNetPartieGauche->setStyleSheet("*{color:#FF0000;}");
    ui->LabelNetPartieDroite->setStyleSheet("*{color:#FF0000;}");
    ui->centralWidget->setStyleSheet("QWidget#centralWidget{background-color:#1a2321;} QObject{color: #ffe43f;}");

    ui->nomEquipe1->setStyleSheet(COULEUR_EQUIPE1);
    ui->nomEquipe2->setStyleSheet(COULEUR_EQUIPE2);

    ui->LCD_PartieGauche_Joueur1->setStyleSheet(COULEUR_EQUIPE1);
    ui->Manches_Joueur1_PartieGauche->setStyleSheet(COULEUR_EQUIPE1);
    ui->Nom_Joueur1_Gauche->setStyleSheet(COULEUR_EQUIPE1);
    ui->Nom_Joueur3_Gauche->setStyleSheet(COULEUR_EQUIPE1);

    ui->LCD_PartieGauche_Joueur2->setStyleSheet(COULEUR_EQUIPE2);
    ui->Manches_Joueur2_PartieGauche->setStyleSheet(COULEUR_EQUIPE2);
    ui->Nom_Joueur2_Gauche->setStyleSheet(COULEUR_EQUIPE2);
    ui->Nom_Joueur4_Gauche->setStyleSheet(COULEUR_EQUIPE2);

    ui->LCD_PartieDroite_Joueur1->setStyleSheet(COULEUR_EQUIPE1);
    ui->Manches_Joueur1_PartieDroite->setStyleSheet(COULEUR_EQUIPE1);
    ui->Nom_Joueur1_Droite->setStyleSheet(COULEUR_EQUIPE1);
    ui->Nom_Joueur3_Droite->setStyleSheet(COULEUR_EQUIPE1);

    ui->LCD_PartieDroite_Joueur2->setStyleSheet(COULEUR_EQUIPE2);
    ui->Manches_Joueur2_PartieDroite->setStyleSheet(COULEUR_EQUIPE2);
    ui->Nom_Joueur2_Droite->setStyleSheet(COULEUR_EQUIPE2);
    ui->Nom_Joueur4_Droite->setStyleSheet(COULEUR_EQUIPE2);

    ui->ListeJoueursGauche->setStyleSheet(COULEUR_EQUIPE1);
    ui->ListeJoueursDroite->setStyleSheet(COULEUR_EQUIPE2);
}

void IHMAfficheur::initialiserFenetreAcceuil()
{
    qDebug() << Q_FUNC_INFO;
    fenetreAcceuil = new QWidget;
    fenetreAcceuil->showFullScreen();
    fenetreAcceuil->setStyleSheet("QWidget{background-color:#1a2321;} QObject{color:#ffe43f ;} QLabel {font-size: 90px; font-weight:bold}");

    timerHorodatage = new QTimer;
    labelHorodatage = new QLabel();
    labelHorodatage->setAlignment(Qt::AlignCenter);
    timerHorodatage->start(1000);
    connect(timerHorodatage,SIGNAL(timeout()), this, SLOT(actualiserHorodatage()));
}

void IHMAfficheur::actualiserHorodatage()
{
    QDateTime *horodatageActuel;
    labelHorodatage->setText(horodatageActuel->currentDateTime().toString("dddd d MMMM hh:mm:ss"));
}

/**
 * @brief Initialise la communication Bluetooth en mode serveur
 *
 * @fn IHMAfficheur::initialiserCommunication
 */
void IHMAfficheur::initialiserCommunication()
{
    communication = new Communication(this);

    connect(communication, SIGNAL(debutRencontre(QString,QString)), this, SLOT(commencerRencontre(QString,QString)));
    connect(communication, SIGNAL(socketConnectee()), this, SLOT(afficherEtatConnecte()));
    connect(communication, SIGNAL(socketDeconnectee()), this, SLOT(afficherEtatDeconnecte()));

    communication->demarrer();
}

/**
 * @brief Commence une rencontre entre deux clubs
 *
 * @fn IHMAfficheur::commencerRencontre
 * @param club1 Nom du premier club de la rencontre
 * @param club2 Nom du second club de la rencontre
 */
void IHMAfficheur::commencerRencontre(QString club1, QString club2)
{
    rencontre = new Rencontre(club1, club2);
    qDebug() << Q_FUNC_INFO << rencontre->getNomEquipe1() << "vs" << rencontre->getNomEquipe2();

    afficherRencontre();

    connect(communication, SIGNAL(creationPartieSimple(QStringList)), rencontre, SLOT(creerPartieSimple(QStringList)));
    connect(communication, SIGNAL(creationPartieDouble(QStringList)), rencontre, SLOT(creerPartieDouble(QStringList)));
    connect(communication, SIGNAL(nouveauScorePartie(int, int, int, int, int)), this, SLOT(actualiserScore(int, int, int, int, int)));
    connect(communication, SIGNAL(changementEtatPartie(int,QString)), this, SLOT(changerEtatPartie(int,QString)));
    connect(communication, SIGNAL(detectionNET(int)), this, SLOT(afficherNET(int)));
    connect(timerPartieGauche,SIGNAL(timeout()), this, SLOT(actualiserTimerGauche()));
    connect(timerPartieDroite,SIGNAL(timeout()), this, SLOT(actualiserTimerDroite()));
}

void IHMAfficheur::actualiserTimerGauche()
{
    *tempsPartieGauche=tempsPartieGauche->addMSecs(chronometrePartieGauche->elapsed());
    chronometrePartieGauche->restart();
    ui->labelTempsPartieGauche->setText(tempsPartieGauche->toString("mm:ss"));
}

void IHMAfficheur::actualiserTimerDroite()
{
    *tempsPartieDroite=tempsPartieDroite->addMSecs(chronometrePartieDroite->elapsed());
    chronometrePartieDroite->restart();
    ui->labelTempsPartieDroite->setText(tempsPartieDroite->toString("mm:ss"));
}

/**
 * @brief Affiche les noms des clubs qui s'affrontent durant la rencotre actuelle
 *
 * @fn IHMAfficheur::afficherRencontre
 */
void IHMAfficheur::afficherRencontre()
{
    qDebug() << Q_FUNC_INFO;

    QVBoxLayout *Vlayout = new QVBoxLayout(fenetreAcceuil);
    QHBoxLayout *Hlayout1 = new QHBoxLayout();
    QHBoxLayout *Hlayout2 = new QHBoxLayout();

    Vlayout->addSpacing(200);
    Hlayout1->addSpacing(100);
    Vlayout->addLayout(Hlayout1);
    Vlayout->addLayout(Hlayout2);
    QLabel *labelClub1Acceuil = new QLabel();
    Hlayout1->addWidget(labelClub1Acceuil);

    labelClub1Acceuil->setText(rencontre->getNomEquipe1());

    QLabel *labelVSAcceuil = new QLabel();
    Hlayout1->addWidget(labelVSAcceuil);
    labelVSAcceuil->setText("VS");
    labelVSAcceuil->setAlignment(Qt::AlignCenter);

    QLabel *labelClub2Acceuil = new QLabel();
    Hlayout1->addWidget(labelClub2Acceuil);
    labelClub2Acceuil->setText(rencontre->getNomEquipe2());
    labelClub2Acceuil->setAlignment(Qt::AlignRight);
    Hlayout1->setAlignment(Qt::AlignVCenter);

    Hlayout1->addSpacing(100);

    Hlayout2->addWidget(labelHorodatage);

    ui->nomEquipe1->setText(rencontre->getNomEquipe1());
    ui->nomEquipe1->setAlignment(Qt::AlignCenter);
    ui->nomEquipe2->setText(rencontre->getNomEquipe2());
    ui->nomEquipe2->setAlignment(Qt::AlignCenter);
}

/**
 * @brief Actualise les scores d'une partie
 *
 * @fn Communication::actualiserScore
 * @param idPartie Identitfiant de la partie dont les scores doivent etres mis a jour
 * @param scoreA Score du joueur de gauche
 * @param scoreB Score du joueur de droite
 * @param nbManchesJoueurA Nombre de manches remportées par le joueur de gauche
 * @param nbManchesJoueurB Nombre de manches remportées par le joueur de gauche
 */
void IHMAfficheur::actualiserScore(int idPartie, int scoreA, int scoreB, int nbManchesJoueurA, int nbManchesJoueurB)
{
    qDebug() << Q_FUNC_INFO << "scores recus : " << scoreA << scoreB;
    QVector<Partie> parties = rencontre->getParties();
    for(int i = 0; i < parties.size(); i++)
    {
        if(idPartie == parties[i].getIdPartie())
        {
            qDebug() << Q_FUNC_INFO << "affichage partie" << parties[i].getAffichagePartie();
            QVector<Joueur> joueurs = parties[i].getJoueurs();
            switch(parties[i].getAffichagePartie())
            {
                case 1:
                    ui->LCD_PartieGauche_Joueur1->display(QString::number(scoreA));
                    ui->LCD_PartieGauche_Joueur2->display(QString::number(scoreB));
                    afficherManchesGauche(nbManchesJoueurB, nbManchesJoueurA, joueurs[2].getNom());
                    break;
                case 2:
                    ui->LCD_PartieDroite_Joueur1->display(QString::number(scoreA));
                    ui->LCD_PartieDroite_Joueur2->display(QString::number(scoreB));
                    afficherManchesDroite(nbManchesJoueurB, nbManchesJoueurA, joueurs[2].getNom());
                    break;
            }
        }
    }
}

/**
 * @brief Affiche le nouveau score pour la partie gauche
 *
 * @fn IHMAfficheur::afficherManchesGauche
 * @param nbManchesJoueurA Nombre de manches remportées par le joueur de gauche
 * @param nbManchesJoueurB Nombre de manches remportées par le joueur de droite
 */
void IHMAfficheur::afficherManchesGauche(int nbManchesJoueurB, int nbManchesJoueurA ,QString nomJoueur3)
{
    switch(nbManchesJoueurA)
    {
        case 0:
            ui->Manches_Joueur1_PartieGauche->setText("○○○");
            break;
        case 1:
            ui->Manches_Joueur1_PartieGauche->setText("●○○");
            break;
        case 2:
            ui->Manches_Joueur1_PartieGauche->setText("●●○");
            break;
        case 3:
            ui->Manches_Joueur1_PartieGauche->setText("●●●");
            if(nomJoueur3 == " ")
                ui->ListeScores->setText(ui->ListeScores->text() + "1-0" + "\n\n");
            else
                ui->ListeScores->setText(ui->ListeScores->text() + "1-0" + "\n\n\n");
            break;
    }
    switch(nbManchesJoueurB)
    {
        case 0:
            ui->Manches_Joueur2_PartieGauche->setText("○○○");
            break;
        case 1:
            ui->Manches_Joueur2_PartieGauche->setText("○○●");
            break;
        case 2:
            ui->Manches_Joueur2_PartieGauche->setText("○●●");
            break;
        case 3:
            ui->Manches_Joueur2_PartieGauche->setText("●●●");
            if(nomJoueur3 == " ")
                ui->ListeScores->setText(ui->ListeScores->text() + "0-1" + "\n\n");
            else
                ui->ListeScores->setText(ui->ListeScores->text() + "0-1" + "\n\n\n");
            break;
    }
}

/**
 * @brief Affiche le nouveau score pour la partie droite
 *
 * @fn IHMAfficheur::afficherManchesDroite
 * @param nbManchesJoueurA Nombre de manches remportées par le joueur de gauche
 * @param nbManchesJoueurB Nombre de manches remportées par le joueur de droite
 */
void IHMAfficheur::afficherManchesDroite(int nbManchesJoueurB, int nbManchesJoueurA, QString nomJoueur3)
{
    switch(nbManchesJoueurA)
    {
        case 0:
            ui->Manches_Joueur1_PartieDroite->setText("○○○");
            break;
        case 1:
            ui->Manches_Joueur1_PartieDroite->setText("●○○");
            break;
        case 2:
            ui->Manches_Joueur1_PartieDroite->setText("●●○");
            break;
        case 3:
            ui->Manches_Joueur1_PartieDroite->setText("●●●");
            if(nomJoueur3 == " ")
                ui->ListeScores->setText(ui->ListeScores->text() + "1-0" + "\n\n");
            else
                ui->ListeScores->setText(ui->ListeScores->text() + "1-0" + "\n\n\n");
            break;
    }
    switch(nbManchesJoueurB)
    {
        case 0:
            ui->Manches_Joueur2_PartieDroite->setText("○○○");
            break;
        case 1:
            ui->Manches_Joueur2_PartieDroite->setText("○○●");
            break;
        case 2:
            ui->Manches_Joueur2_PartieDroite->setText("○●●");
            break;
        case 3:
            ui->Manches_Joueur2_PartieDroite->setText("●●●");
            if(nomJoueur3 == " ")
                ui->ListeScores->setText(ui->ListeScores->text() + "0-1" + "\n\n");
            else
                ui->ListeScores->setText(ui->ListeScores->text() + "0-1" + "\n\n\n");
            break;
    }
}

/**
 * @brief Débug de connexion d'un module area
 *
 * @fn IHMAfficheur::afficherEtatConnecte
 */
void IHMAfficheur::afficherEtatConnecte()
{
    qDebug() << Q_FUNC_INFO;
}

/**
 * @brief Débug de déconnexion d'un module area
 *
 * @fn IHMAfficheur::afficherEtatDeconnecte
 */
void IHMAfficheur::afficherEtatDeconnecte()
{
    qDebug() << Q_FUNC_INFO;
}

/**
 * @brief Démarre une partie ou met fin a une partie en cours
 *
 * @fn IHMAfficheur::changerEtatPartie
 * @param idPartie Identifiant de la partie a démarer/arréter
 * @param EtatPartie Etat a définir pour la partie
 */
void IHMAfficheur::changerEtatPartie(int idPartie, QString EtatPartie)
{

    QVector<Partie> parties = rencontre->getParties();
    qDebug() << Q_FUNC_INFO;

    for(int i = 0; i < parties.size(); i++)
    {
        if(idPartie == parties[i].getIdPartie())
        {
            QVector<Joueur> joueurs = parties[i].getJoueurs();
            if(EtatPartie == "DEMARREE")
            {
                demarrerPartie(i, joueurs, parties);
            }
            if(EtatPartie == "TERMINEE")
            {
                terminerPartie(i ,joueurs, parties);
            }
        }
    }
}

/**
 * @brief Démarre une partie
 *
 * @fn IHMAfficheur::demarrerPartie
 * @param i Index de la partie a démarrer
 * @param joueurs Vecteur de joueurs a afficher dans l'IHM
 * @param parties Vecteur de parties a démarrer et afficher
 */
void IHMAfficheur::demarrerPartie(int i, QVector<Joueur> joueurs, QVector<Partie> parties)
{
    qDebug() << Q_FUNC_INFO;
    this->showFullScreen();
    fenetreAcceuil->close();
    if(disponibiliteAffichages[0] == 0)
    {
        ui->Manches_Joueur1_PartieGauche->setText("○○○");
        ui->Manches_Joueur2_PartieGauche->setText("○○○");
        ui->LCD_PartieGauche_Joueur1->display(QString::number(0));
        ui->LCD_PartieGauche_Joueur2->display(QString::number(0));
        ui->labelTempsPartieGauche->setText("00:00");
        chronometrePartieGauche->restart();
        timerPartieGauche->start(1000);
        ui->Nom_Joueur1_Gauche->setText(joueurs[0].getNom() + "\n" + joueurs[0].getPrenom());
        ui->Nom_Joueur2_Gauche->setText(joueurs[1].getNom() + "\n" + joueurs[1].getPrenom());
        ui->Nom_Joueur3_Gauche->setText(joueurs[2].getNom() + "\n" + joueurs[2].getPrenom());
        ui->Nom_Joueur4_Gauche->setText(joueurs[3].getNom() + "\n" + joueurs[3].getPrenom());
        parties[i].setAffichagePartie(TypeAffichage::AFFICHAGE_GAUCHE);
        disponibiliteAffichages[0] = 1;
        rencontre->setParties(parties);
    }
    else
    {
        if(disponibiliteAffichages[1] == 0)
        {
            ui->Manches_Joueur1_PartieDroite->setText("○○○");
            ui->Manches_Joueur2_PartieDroite->setText("○○○");
            ui->LCD_PartieDroite_Joueur1->display(QString::number(0));
            ui->LCD_PartieDroite_Joueur2->display(QString::number(0));
            ui->labelTempsPartieDroite->setText("00:00");
            chronometrePartieDroite->restart();
            timerPartieDroite->start(1000);
            ui->Nom_Joueur1_Droite->setText(joueurs[0].getNom() + "\n" + joueurs[0].getPrenom());
            ui->Nom_Joueur2_Droite->setText(joueurs[1].getNom() + "\n" + joueurs[1].getPrenom());
            ui->Nom_Joueur3_Droite->setText(joueurs[2].getNom() + "\n" + joueurs[2].getPrenom());
            ui->Nom_Joueur4_Droite->setText(joueurs[3].getNom() + "\n" + joueurs[3].getPrenom());
            parties[i].setAffichagePartie(TypeAffichage::AFFICHAGE_DROITE);
            disponibiliteAffichages[1] = 1;
            rencontre->setParties(parties);
        }
    }
}

/**
 * @brief Termine une partie
 *
 * @fn IHMAfficheur::terminerPartie
 * @param i Index de la partie a démarrer
 * @param joueurs Vecteur de joueurs a afficher dans l'historique des parties
 * @param parties Vecteur de parties a terminer
 */
void IHMAfficheur::terminerPartie(int i,QVector<Joueur> joueurs, QVector<Partie> parties)
{
    qDebug() << Q_FUNC_INFO;
    //Q_UNUSED(joueurs);
    if(parties[i].getAffichagePartie() == TypeAffichage::AFFICHAGE_GAUCHE)
    {
        qDebug() << Q_FUNC_INFO << "fin partie gauche";
        if(joueurs[2].getNom() == " ")
        {
            ui->ListeJoueursGauche->setText(ui->ListeJoueursGauche->text() + joueurs[0].getNom() + "\n\n");
            ui->ListeJoueursDroite->setText(ui->ListeJoueursDroite->text() + joueurs[1].getNom() + "\n\n");
        }
        else
        {
            ui->ListeJoueursGauche->setText(ui->ListeJoueursGauche->text() + joueurs[0].getNom() + "\n" + joueurs[2].getNom() + "\n\n");
            ui->ListeJoueursDroite->setText(ui->ListeJoueursDroite->text() + joueurs[1].getNom() + "\n" + joueurs[3].getNom() + "\n\n");
        }
        tempsPartieGauche->setHMS(0,0,0);
        timerPartieGauche->stop();
        parties[i].setAffichagePartie(TypeAffichage::PAS_DAFFICHAGE);
        disponibiliteAffichages[0] = 0;
        rencontre->setParties(parties);
    }
    if(parties[i].getAffichagePartie() == TypeAffichage::AFFICHAGE_DROITE)
    {
        qDebug() << Q_FUNC_INFO << "fin partie droite";
        if(joueurs[2].getNom() == " ")
        {
            ui->ListeJoueursGauche->setText(ui->ListeJoueursGauche->text() + joueurs[0].getNom() + "\n\n");
            ui->ListeJoueursDroite->setText(ui->ListeJoueursDroite->text() + joueurs[1].getNom() + "\n\n");
        }
        else
        {
            ui->ListeJoueursGauche->setText(ui->ListeJoueursGauche->text() + joueurs[0].getNom() + "\n" + joueurs[2].getNom() + "\n\n");
            ui->ListeJoueursDroite->setText(ui->ListeJoueursDroite->text() + joueurs[1].getNom() + "\n" + joueurs[3].getNom() + "\n\n");
        }
        tempsPartieDroite->setHMS(0,0,0);
        timerPartieDroite->stop();
        parties[i].setAffichagePartie(TypeAffichage::PAS_DAFFICHAGE);
        disponibiliteAffichages[1] = 0;
        rencontre->setParties(parties);
    }
}

/**
 * @brief Affiche une faute dans l'IHM lors de la détection d'un NET
 *
 * @fn IHMAfficheur::afficherNET
 * @param idPartie Identifiant de la partie où la faute est détectée
 */
void IHMAfficheur::afficherNET(int idPartie)
{
    qDebug() << Q_FUNC_INFO;
    QVector<Partie> parties = rencontre->getParties();
    QTimer *timerNET;
    timerNET = new QTimer();
    timerNET->setSingleShot(true);

    for(int i = 0; i < parties.size(); i++)
    {
        if(idPartie == parties[i].getIdPartie())
        {
            if(parties[i].getAffichagePartie() == TypeAffichage::AFFICHAGE_GAUCHE)
            {
                ui->LabelNetPartieGauche->setText("NET");
                connect(timerNET,SIGNAL(timeout()), this, SLOT(cacherNETPartieGauche()));
                timerNET->start(5000);
            }
            if(parties[i].getAffichagePartie() == TypeAffichage::AFFICHAGE_DROITE)
            {
                ui->LabelNetPartieDroite->setText("NET");
                connect(timerNET,SIGNAL(timeout()), this, SLOT(cacherNETPartieDroite()));
                timerNET->start(5000);
            }
        }
    }
}

void IHMAfficheur::cacherNETPartieGauche()
{
    qDebug() << Q_FUNC_INFO;
    ui->LabelNetPartieGauche->setText("");
}

void IHMAfficheur::cacherNETPartieDroite()
{
    qDebug() << Q_FUNC_INFO;
    ui->LabelNetPartieDroite->setText("");
}
