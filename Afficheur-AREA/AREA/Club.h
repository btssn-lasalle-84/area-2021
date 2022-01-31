#ifndef CLUB_H
#define CLUB_H

/**
 * @file Club.h
 * @brief Déclaration de la classe Club
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

#include <QString>
#include "Joueur.h"

/**
 * @class Club
 * @brief Déclaration de la classe Club
 * @details Cette classe gère l'organisation des clubs
*/
class Club
{
private:
    QString nom;                        //!< Le nom du club
    int partiesRemportees;              //!< Le nombre de parties remortées par le club durant cette rencontre

public:
    Club(QString nom = "");             //!< Constructeur de la classe Club
    ~Club();                            //!< Déstructeur de la classe Club

    QString getNom()const;              //!< Méthode qui retourne le nom du club
    void setNom(QString &n);            //!< Méthode qui modifie le nom du club
    int getPartiesRemportes() const;    //!< Méthode qui retourne le nombre de parties remportées par le club
    void setPartiesRemportes(int &p);   //!< Méthode qui modifie le nombre de parties remportées par le club
};

#endif // CLUB_H
