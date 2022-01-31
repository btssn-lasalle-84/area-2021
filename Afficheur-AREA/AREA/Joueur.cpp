#include "Joueur.h"
#include <QDebug>

/**
 * @file Joueur.cpp
 * @brief Définition de la classe Joueur
 * @version 1.0
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 75 $
 * $LastChangedDate: 2021-05-28 14:01:52 +0200 (ven. 28 mai 2021) $
 */

/**
 * @brief Constructeur de la classe Joueur
 *
 * @fn Joueur::Joueur
 * @param nom Nom du joueur
 * @param prenom Prenom du joueur
 */
Joueur::Joueur(QString nom, QString prenom) : nom(nom), prenom(prenom), echangesRemportes(0)
{
    qDebug() << Q_FUNC_INFO << nom << prenom;
}

/**
 * @brief Destructeur de la classe Joueur
 *
 * @fn Joueur::~Joueur
 */
Joueur::~Joueur()
{
    qDebug() << Q_FUNC_INFO;
}

/**
 * @brief Retourne le nom du joueur
 *
 * @fn Joueur::getNom
 * @return Nom du joueur
 */
QString Joueur::getNom() const
{
    return this->nom;
}

/**
 * @brief Modifie le nom du joueur
 *
 * @fn Joueur::setNom
 * @param n Nouveau nom pour le joueur
 */
void Joueur::setNom(QString &n)
{
    nom = n;
}

/**
 * @brief Retourne le prénom du joueur
 *
 * @fn Joueur::getPrenom
 * @return Prénom du joueur
 */
QString Joueur::getPrenom() const
{
    return this->prenom;
}

/**
 * @brief Modifie le prénom du joueur
 *
 * @fn Joueur::setPrenom
 * @param Nouveau prénom pour le joueur
 */
void Joueur::setPrenom(QString &p)
{
    prenom = p;
}

/**
 * @brief Retourne le nombre d'échanges remportés par le joueur
 *
 * @fn Joueur::getEchangesRemportes
 * @return Nombre d'echange remportés
 */
int Joueur::getEchangesRemportes() const
{
    return echangesRemportes;
}
