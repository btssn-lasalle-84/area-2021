#ifndef IHMAFFICHEUR_H
#define IHMAFFICHEUR_H

/**
 * @file IHMAfficheur.h
 * @brief Déclaration de la classe IHMAfficheur
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

#include <QtWidgets>
#include <QThread>

/**
 * @def TEST_SANS_BLUETOOTH
 * @brief Pour tester le système sans le Bluetooth
 */
//#define TEST_SANS_BLUETOOTH

#define GAUCHE      1
#define DROITE      2

#define DEMARRER    0
#define TERMINER    1

#define COULEUR_EQUIPE1 "*{color:#fdff37;}"
#define COULEUR_EQUIPE2 "*{color:#fdff8b;}"

class Rencontre;
class Communication;
class Partie;
class Joueur;

namespace Ui
{
    class IHMAfficheur;
}

/**
 * @class IHMAfficheur
 * @brief Déclaration de la classe IHMAfficheur
 * @details Cette classe assure l'affichage de l'écran AREA
*/
class IHMAfficheur : public QMainWindow
{
    Q_OBJECT

private:
    Ui::IHMAfficheur *ui;                   //!< Association vers l'interface utilisateur réalisé avec Qt Designer
    Rencontre *rencontre;                   //!< Pointeur pour la relation avec la classe Rencontre
    Communication *communication;           //!< Pointeur pour la relation avec la classe Communication
    QVector<int> disponibiliteAffichages;   //!< Vecteur contenant la disponibilité des affichages (partie gauche et partie droite de l'ecran)
    QTime *tempsPartieGauche;
    QTime *tempsPartieDroite;
    QTimer *timerPartieGauche;
    QTimer *timerPartieDroite;
    QElapsedTimer *chronometrePartieGauche;
    QElapsedTimer *chronometrePartieDroite;
    QTime *horodatage;
    QTimer *timerHorodatage;
    QWidget *fenetreAcceuil;
    QLabel *labelHorodatage;

    void initialiserIHM();                  //!< Méthode qui initialise de L'interface Homme-Machine
    void initialiserCommunication();        //!< Méthode qui initialise la communication BLuetooth
    void afficherRencontre();               //!< Méthode qui affiche les deux clubs s'affrontant dans la rencontre
    void demarrerPartie(int i, QVector<Joueur> joueurs, QVector<Partie> parties);   //!< Méthode qui démarre une partie sur un affichage disponible
    void terminerPartie(int i,QVector<Joueur> joueurs, QVector<Partie> parties);    //!< Méthode qui met fin a une partie affichée a l'écran
    void afficherManchesGauche(int nbManchesJoueurB, int nbManchesJoueurA, QString nomJoueur3);         //!< Méthode qui actualise les manches remportées par les joueurs de la partie affichée a gauche
    void afficherManchesDroite(int nbManchesJoueurB, int nbManchesJoueurA, QString nomJoueur3);         //!< Méthode qui actualise les manches remportées par les joueurs de la partie affichée a droite

    void initialiserCouleurs();

public:
    explicit IHMAfficheur(QWidget *parent = 0);     //!< Constructeur de la classe IHMAfficheur
    ~IHMAfficheur();                                //!< Déstructeur de la classe IHMAfficheur

    void initialiserFenetreAcceuil();

public slots:
    void actualiserTimerGauche();
    void actualiserTimerDroite();
    void actualiserHorodatage();
    void commencerRencontre(QString club1, QString club2);                                                      //!< Méthode slot qui commence une rencontre entre deux clubs
    void afficherEtatConnecte();                                                                                //!< Méthode slot de débug qui notifie la connexion d'un module mobile
    void afficherEtatDeconnecte();                                                                              //!< Méthode slot de débug qui notifie la déconnexion d'un module mobile
    void actualiserScore(int idPartie, int scoreA, int scoreB, int nbManchesJoueurA, int nbManchesJoueurB);     //!< Méthode slot qui actualise les scores d'une partie
    void changerEtatPartie(int idPartie, QString EtatPartie);                                                   //!< Méthode slot qui démarre ou met fin a une partie
    void afficherNET(int idPartie);                                                                             //!< Méthode slot qui affiche une faute lors de la détection d'un net
    void cacherNETPartieGauche();
    void cacherNETPartieDroite();
};

#endif // IHMAFFICHEUR_H
