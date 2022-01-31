#include "Partie.h"
#include <QtDebug>

/**
 * @file Partie.cpp
 * @brief Définition de la classe Partie
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

/**
 * @brief Constructeur de la classe Partie
 *
 * @fn Partie::Partie
 * @param joueurA Informations sur le joueur A
 * @param joueurB Informations sur le joueur B
 * @param joueurC Informations sur le joueur C
 * @param joueurD Informations sur le joueur D
 * @param idPartie Identifiant de la partie
 */
Partie::Partie(Joueur joueurA, Joueur joueurB, Joueur joueurC, Joueur joueurD, int idPartie) : joueurs({joueurA, joueurB, joueurC, joueurD}), idPartie(idPartie), affichagePartie(0)
{
    qDebug() << Q_FUNC_INFO << joueurA.getNom() << joueurA.getPrenom() << "vs" << joueurB.getNom() << joueurB.getPrenom() << "id partie :" << idPartie;
}

/**
 * @brief Destructeur de la classe Partie
 *
 * @fn Partie::~Partie
 */
Partie::~Partie()
{
    qDebug() << Q_FUNC_INFO;
}

/**
 * @brief Retourne le vecteur de joueurs de la partie
 *
 * @fn Partie::getJoueurs
 * @return Vecteur de joueurs
 */
QVector<Joueur> Partie::getJoueurs()
{
    return joueurs;
}

/**
 * @brief Ajoute un joueur à la partie
 *
 * @fn Partie::ajouterJoueur
 * @param nomJoueur Nom du joueur à ajouter
 * @param prenomJoueur Prénom du joueur à ajouter
 */
void Partie::ajouterJoueur(QString &nomJoueur, QString &prenomJoueur)
{
    qDebug() << Q_FUNC_INFO << nomJoueur << prenomJoueur;
    Joueur joueur(nomJoueur,prenomJoueur);
    joueurs.push_back(joueur);
}

/**
 * @brief Retourne l'identifiant de la partie
 *
 * @fn Partie::getIdPartie
 * @return L'identifiant de la partie
 */
int Partie::getIdPartie() const
{
    return idPartie;
}

/**
 * @brief Modifie l'identifiant de la partie
 *
 * @fn Partie::setIdPartie
 * @param id Nouvel identifiant de la partie
 */
void Partie::setIdPartie(int &id)
{
    this->idPartie = id;
}

/**
 * @brief Retourne l'affichage de la partie
 *
 * @fn Partie::getAffichagePartie
 * @return L'affichage de la partie
 */
int Partie::getAffichagePartie() const
{
    return affichagePartie;
}

/**
 * @brief Modifie l'affichage de la partie
 *
 * @fn Partie::setAffichagePartie
 * @param a Nouvel affichage de la partie
 */
void Partie::setAffichagePartie(int a)
{
    this->affichagePartie = a;
}
