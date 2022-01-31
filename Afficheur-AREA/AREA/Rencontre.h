#ifndef RENCONTRE_H
#define RENCONTRE_H

/**
 * @file Rencontre.h
 * @brief Déclaration de la classe Rencontre
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

#include <QObject>
#include <QVector>
#include "Partie.h"

#define EQUIPE_A    0
#define EQUIPE_X    1

#define DEMARRER    0
#define TERMINER    1

class Club;
class Communication;

/**
 * @class Rencontre
 * @brief Déclaration de la classe Rencontre
 * @details Cette classe gère l'organisation de la Rencontre
*/
class Rencontre : public QObject
{
    Q_OBJECT

private:
    QVector<Club*> clubs;           //!< Pointeur pour la relation avec la classe Club
    QVector<Partie> parties;        //!< Conteneur pour la relation avec la classe Partie
    Communication *communication;   //!< Pointeur pour la relation avec la classe Communication

public:
    Rencontre(QString nomClub1 = "nomClub1", QString nomClub2 = "nomClub1");    //!< Constructeur de la classe Rencontre
    ~Rencontre();                                                               //!< déstructeur de la classe rencontre

    QString getNomEquipe1();                //!< Méthode qui retourne le nom du premier Club de la rencontre
    QString getNomEquipe2();                //!< Méthode qui retourne le nom du deuxieme Club de la rencontre
    QVector<Partie> getParties();           //!< Méthode qui retroune le vecteur de Parties
    void setParties(QVector<Partie> p);     //!< Méthode qui modifie le vecteur de Parties

public slots:
    void creerPartieSimple(QStringList infoTrame);      //!< Méthode slot qui créé une partie simple
    void creerPartieDouble(QStringList infoTrame);      //!< Méthode slot qui créé une partie double
};

#endif // RENCONTRE_H
