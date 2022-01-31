#ifndef COMMUNICATION_H
#define COMMUNICATION_H

#include <QObject>
#include <QString>
#include <QBluetoothLocalDevice>
#include <QBluetoothServer>
#include <QBluetoothSocket>

/**
 * @file Communication.h
 * @brief Déclaration de la classe Communication
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

#define ENTETE_TRAME                "MOBILE_AREA"
#define CHAMP_TYPE_TRAME            1

#define NOM_CLUB_1                  2
#define NOM_CLUB_2                  3

#define ID_PARTIE                   2

#define POINTS_JOUEUR_A             3
#define POINTS_JOUEUR_B             4
#define MANCHES_JOUEUR_A            5
#define MANCHES_JOUEUR_B            6

#define ETAT                        3

#define NB_CHAMPS_PARTIE_SIMPLE     7


/**
 * @enum TypeTrame
 * @brief Les différents types de trame
 */
enum TypeTrame
{
    RENCONTRE, //!< ...
    INFO_PARTIE,
    SCORE,
    ETAT_PARTIE,
    NET,
    TEMPS_MORT,
};

static const QString uuidService(QStringLiteral("0000110a-0000-1000-8000-00805f9b34fb"));
static const QString nomService(QStringLiteral("Afficheur_AREA"));

class Rencontre;

/**
 * @class Communication
 * @brief Déclaration de la classe Communication
 * @details Cette classe gère la communication Bluetooth
*/
class Communication : public QObject
{
    Q_OBJECT
private:
    Rencontre *rencontre;                       //!< Pointeur pour la relation avec la classe Rencontre
    QBluetoothServer *serveur;                  //!< Le serveur Bluetooth
    QBluetoothSocket *socket;                   //!< La socket de communication Bluetooth
    QBluetoothLocalDevice peripheriqueLocal;    //!< L'interface Bluetooth de la Raspberry Pi
    QBluetoothServiceInfo serviceInfo;          //!< Informations sur le service bluetooth
    QString nomPeripheriqueLocal;               //!< Nom du périphérique local
    QString trame;                              //!< Une trame extraite
    QString trames;                             //!< Les trames recues
    QStringList infosTrame;                     //!< Les informations transmises dans la trame

    bool traiterTrame(QStringList infosTrame);  //!< Méthode qui raite la trame recue selon le champ TypeTrame

public:
    Communication(QObject *parent = nullptr);   //!< Constructeur de la classe Communication
    ~Communication();                           //!< Déstructeur de la classe Communication

    void initialiser();                         //!< Méthode qui prépare la connexion Bluetooth en mode serveur
    void demarrer();                            //!< Méthode qui démarre le serveur Bluetooth
    void arreter();                             //!< Méthode qui arrête le serveur Bluetooth
    QString getTrame() const;                   //!< Méthode qui retourne la derniere trame recue

private slots:
    void connecterSocket();                                                     //!< Méthode slot qui permet la connection au socket du serveur et connecte les signaux et slots
    void deconnecterSocket();                                                   //!< Méthode slot de déconnexion du socket
    void recevoirErreurSocket(QBluetoothSocket::SocketError erreurSocket);      //!< Méthode slot de débug qui signale une erreur du socket
    void connecterTerminalMobile(const QBluetoothAddress &adresse);             //!< Méthode slot de débug qui signale la connextion d'un teminal mobile
    void deconnecterTerminalMobile(const QBluetoothAddress &adresse);           //!< Méthode slot de débug qui signale la déconnexion d'un terminal mobile
    void recevoirErreurBluetooth(QBluetoothLocalDevice::Error erreurBluetooth); //!< Méthode slot de débug qui signale une erreur de Bluetooth
    void lireTrame();                                                           //!< Méthode slot qui lit les trames recues et les séparent en plusieurs trames afin de les traiter

signals:
    void debutRencontre(QString club1, QString club2);                          //!< Signal de début de rencontre
    void creationPartieSimple(QStringList infoTrame);                           //!< Signal de création d'une partie simple
    void creationPartieDouble(QStringList infoTrame);                           //!< Signal de création d'une partie double
    void nouveauScorePartie(int idPartie, int scoreA, int scoreB, int nbManchesJoueurA, int nbManchesJoueurB);  //!< Signal de rafraichissement du score d'une partie
    void changementEtatPartie(int idPartie, QString etatPartie);                                                //!< Signal de début ou de fin de partie
    void detectionNET(int idPartie);                                            //!< Signal de détection d'une sequence de net
    void demandeTempsMort();                                                    //!< Signal de demande de temps mort
    void socketConnectee();                                                     //!< Signal de connextion de socket
    void socketDeconnectee();                                                   //!< Signal de déconnextion de socket
};

#endif // COMMUNICATION_H
