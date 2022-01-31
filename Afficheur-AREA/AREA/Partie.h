#ifndef PARTIE_H
#define PARTIE_H

/**
 * @file Partie.h
 * @brief Déclaration de la classe Partie
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

#include <QVector>
#include "Joueur.h"

/**
 * @enum TypeAffichage
 * @brief Les types d'affichages pour une partie
 */
enum TypeAffichage
{
    PAS_DAFFICHAGE,
    AFFICHAGE_GAUCHE,
    AFFICHAGE_DROITE,
};

/**
 * @class Partie
 * @brief Déclaration de la classe Partie
 * @details Cette classe gère l'organisation des parties
*/
class Partie
{
private:
    QVector<Joueur> joueurs;    //!< Conteneur pour la relation avec la classe Joueur
    int idPartie;               //!< Identifiant de la partie
    int affichagePartie;        //!< Attribut contenant l'etat de l'affichage de la partie

public:
    Partie(Joueur joueurA = Joueur("nomJoueur1", "prenomJoueur1"), Joueur joueurB = Joueur("nomJoueur2", "prenomJoueur2"),Joueur joueurC = Joueur(" ", " "), Joueur joueurD = Joueur(" ", " "), int idPartie=0);    //!< Constructeur de la classe Partie
    ~Partie();                                                      //!< Déstructeur de la classe Partie
    QVector<Joueur> getJoueurs();                                   //!< Méthode qui retourne Le vecteur de joueurs
    int getIdPartie() const;                                        //!< Méthode qui retourne l'identifiant de la partie
    void setIdPartie(int &id);                                      //!< Méthode qui modifie l'identifiant de la partie
    int getAffichagePartie() const;                                 //!< Méthode qui retourne l'affichage de la partie
    void setAffichagePartie(int a);                                 //!< Méthode qui modifie l'affichage de la partie
    void ajouterJoueur(QString &nomJoueur, QString &prenomJoueur);  //!< Méthode qui ajoute un joueur a la partie
};

#endif // PARTIE_H
