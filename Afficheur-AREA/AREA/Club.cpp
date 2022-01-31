#include "Club.h"
#include <QDebug>

/**
 * @file Club.cpp
 * @brief Définition de la classe Club
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

/**
 * @brief Constructeur de la classe Club
 *
 * @fn Club::Club
 * @param nom Nom du club
 */
Club::Club(QString nom) : nom(nom), partiesRemportees(0)
{
    qDebug() << Q_FUNC_INFO << nom << this;
}

/**
 * @brief Destructeur de la classe Club
 *
 * @fn Club::Club
 */
Club::~Club()
{
    qDebug() << Q_FUNC_INFO << this;
}

/**
 * @brief Retourne le nom du club
 *
 * @fn Club::getNom
 * @return Nom du club
 */
QString Club::getNom() const
{
    return this->nom;
}

/**
 * @brief Modifie le nom du club
 *
 * @fn Club::setNom
 * @param Nouveau nom pour le club
 */
void Club::setNom(QString &n)
{
    nom = n;
}

/**
 * @brief Retourne le nombre de parties remportées par le club depuis le début de la rencontre
 *
 * @fn Club::getPartiesRemportes
 * @return le nombre de parties remportées
 */
int Club::getPartiesRemportes() const
{
    return this->partiesRemportees;
}

/**
 * @brief Modifie le nombre de parties remportées par le club depuis le début de la rencontre
 *
 * @fn Club::setPartiesRemportes
 * @param le nombre de partie remportées
 */
void Club::setPartiesRemportes(int &p)
{
    partiesRemportees = p;
}

