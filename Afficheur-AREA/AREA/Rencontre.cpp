#include "Rencontre.h"
#include "Club.h"
#include "Communication.h"
#include <QDebug>

/**
 * @file Rencontre.cpp
 * @brief Définition de la classe Rencontre
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

/**
 * @brief Constructeur de la classe Rencontre
 *
 * @fn Rencontre::Rencontre
 * @param nomClub1 Le nom du club 1 de la rencontre
 * @param nomClub2 Le nom du club 2 de la rencontre
 */
Rencontre::Rencontre(QString nomClub1, QString nomClub2)
{
    qDebug() << Q_FUNC_INFO << nomClub1 << nomClub2;
    clubs.push_back(new Club(nomClub1));
    clubs.push_back(new Club(nomClub2));
}

/**
 * @brief Destructeur de la classe Rencontre
 *
 * @fn Rencontre::~Rencontre
 */
Rencontre::~Rencontre()
{
    for(int i=0; i<clubs.size(); i++)
    {
        delete clubs[i];
    }
    qDebug() << Q_FUNC_INFO;
}

/**
 * @brief Retourne le nom de la premiere équipe de la rencontre
 *
 * @fn Rencontre::getNomEquipe1
 * @return Nom de la premiere équipe
 */
QString Rencontre::getNomEquipe1()
{
    if(clubs.count() > 0)
        return clubs[EQUIPE_A]->getNom();
    else
        return QString();
}

/**
 * @brief Retourne le nom de la deuxieme équipe de la rencontre
 *
 * @fn Rencontre::getNomEquipe2
 * @return Nom de la deuxieme équipe
 */
QString Rencontre::getNomEquipe2()
{
    if(clubs.count() > 0)
        return clubs[EQUIPE_X]->getNom();
    else
        return QString();
}

/**
 * @brief Retourne le vecteur contenant toute les parties
 *
 * @fn Rencontre::getParties
 * @return Vecteur de parties
 */
QVector<Partie> Rencontre::getParties()
{
    return parties;
}

/**
 * @brief Modifie le vecteur contenant toute les parties
 *
 * @fn Rencontre::setParties
 * @param p Nouveau vecteur de parties
 */
void Rencontre::setParties(QVector<Partie> p)
{
   this->parties = p;
}

/**
 * @brief Créé une nouvelle partie simple
 *
 * @fn Rencontre::creerPartieSimple
 * @param infoTrame Informations contenues dans la trame
 */
void Rencontre::creerPartieSimple(QStringList infoTrame)
{
    qDebug() << Q_FUNC_INFO;
    parties.push_back(Partie(Joueur(infoTrame[3],infoTrame[4]), Joueur(infoTrame[5], infoTrame[6]),Joueur(" ", " "),Joueur(" ", " "), infoTrame[2].toInt()));

    #ifdef DEBUG_PARTIE
    parties[0].debugPartie();
    #endif
}

/**
 * @brief Créé une nouvelle partie double
 *
 * @fn Rencontre::creerPartieDouble
 * @param infoTrame Informations contenues dans la trame
 */
void Rencontre::creerPartieDouble(QStringList infoTrame)
{
    qDebug() << Q_FUNC_INFO;
    parties.push_back(Partie(Joueur(infoTrame[3],infoTrame[4]), Joueur(infoTrame[7], infoTrame[8]),Joueur(infoTrame[5],infoTrame[6]), Joueur(infoTrame[9], infoTrame[10]), infoTrame[2].toInt()));

    #ifdef DEBUG_PARTIE
    parties[0].debugPartie();
    #endif
}


