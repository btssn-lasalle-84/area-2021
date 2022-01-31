#ifndef JOUEUR_H
#define JOUEUR_H

/**
 * @file Joueur.h
 * @brief Déclaration de la classe Joueur
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

#include <QString>

/**
 * @class Joueur
 * @brief Déclaration de la classe Joueur
 * @details Cette classe gère l'organisation des joueurs
*/
class Joueur
{
private:
    QString nom;            //!< Le nom d'un joueur
    QString prenom;         //!< Le prénom d'un joueur
    int echangesRemportes;  //!< Le nombre d'échagnes remportés par le joueur

public:
    Joueur(QString nom ="nomJoueur", QString prenom = "PrenomJoueur");  //!< Constructeur de la classe Joueur
    ~Joueur();                                                          //!< Déstructeur de la classe Joueur

    QString getNom() const;                                             //!< Méthode qui retourne le nom du joueur
    void setNom(QString &n);                                            //!< Méthode qui modifie le nom du joueur
    QString getPrenom() const;                                          //!< Méthode qui retourne le prénom du joueur
    void setPrenom(QString &p);                                         //!< Méthode qui modifie le prénom du joueur
    int getEchangesRemportes() const;                                   //!< Méthode qui retourne le nombre déchanges remportés par le joueur
};

#endif // JOUEUR_H
