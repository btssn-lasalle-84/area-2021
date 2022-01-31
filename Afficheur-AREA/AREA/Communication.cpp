#include "Communication.h"
#include "Rencontre.h"
#include <QtDebug>

/**

 * @file Communication.cpp
 * @brief Définition de la classe Communication
 * @version 1.1
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * $LastChangedRevision: 120 $
 * $LastChangedDate: 2021-06-11 08:15:18 +0200 (ven. 11 juin 2021) $
 */

/**
 * @brief Constructeur de la classe Communication
 *
 * @fn Communication::Communication
 * @param parent L'adresse de l'objet parent
 */
Communication::Communication(QObject *parent) : QObject(parent), serveur(nullptr), socket(nullptr)
{
    qDebug() << Q_FUNC_INFO;

    initialiser();
}

/**
 * @brief Destructeur de la classe Communication
 *
 * @fn Communication::~Communication
 */
Communication::~Communication()
{
    qDebug() << Q_FUNC_INFO;

    arreter();
}

/**
 * @brief Initialise la liaison Bluetooth en mode serveur
 *
 * @fn Communication::initialiser
 */
void Communication::initialiser()
{
    // vérifier la présence du Bluetooth
    if (peripheriqueLocal.isValid())
    {
        // activer le bluetooth
        peripheriqueLocal.powerOn();

        // récupérer le nom du périphérique local
        nomPeripheriqueLocal = peripheriqueLocal.name();
        qDebug() << Q_FUNC_INFO << nomPeripheriqueLocal;

        // rendre le périphérique local découvrable et jumelable
        peripheriqueLocal.setHostMode(QBluetoothLocalDevice::HostDiscoverable);

        // connecter les signaux et les slots
        connect(&peripheriqueLocal, SIGNAL(deviceConnected(QBluetoothAddress)), this, SLOT(connecterTerminalMobile(QBluetoothAddress)));
        connect(&peripheriqueLocal, SIGNAL(deviceDisconnected(QBluetoothAddress)), this, SLOT(deconnecterTerminalMobile(QBluetoothAddress)));
        connect(&peripheriqueLocal, SIGNAL(error(QBluetoothLocalDevice::Error)), this, SLOT(recevoirErreurBluetooth(QBluetoothLocalDevice::Error)));
    }
    else
        qDebug() << Q_FUNC_INFO << "Bluetooth non disponible !";
}

/**
 * @brief Démarre la communication Bluetooth en mode serveur
 *
 * @fn Communication::demarrer
 */
void Communication::demarrer()
{
    // vérifier la présence du Bluetooth
    if (peripheriqueLocal.isValid() && serveur == nullptr)
    {
        // créer une socket serveur
        serveur = new QBluetoothServer(QBluetoothServiceInfo::RfcommProtocol, this);

        // connecter les signaux et les slots
        connect(serveur, SIGNAL(newConnection()), this, SLOT(connecterSocket()));

        // placer le serveur en écoute
        QBluetoothUuid uuid = QBluetoothUuid(uuidService);
        serviceInfo = serveur->listen(uuid, nomService);

        qDebug() << Q_FUNC_INFO << "Attente de connexion";
    }
    else
        qDebug() << Q_FUNC_INFO << "Bluetooth non disponible !";
}

/**
 * @brief Arrête la communication Bluetooth en mode serveur
 *
 * @fn Communication::arreter
 */
void Communication::arreter()
{
    if (serveur == nullptr)
        return;

    if (socket != nullptr)
    {
        deconnecterSocket();
    }

    delete serveur;
    serveur = nullptr;
    qDebug() << Q_FUNC_INFO;

    peripheriqueLocal.setHostMode(QBluetoothLocalDevice::HostPoweredOff);
}

/**
 * @brief Initialise la connextion et connecte les signaux de la socket
 *
 * @fn Communication::connecterSocket
 */
void Communication::connecterSocket()
{
    socket = serveur->nextPendingConnection();
    if (!socket)
        return;

    qDebug() << Q_FUNC_INFO;
    socket->open(QIODevice::ReadOnly);
    connect(socket, SIGNAL(readyRead()), this, SLOT(lireTrame()));

    connect(socket, SIGNAL(readyRead()), this, SLOT(lireTrame()));
    connect(socket, SIGNAL(disconnected()), this, SLOT(deconnecterSocket()));
    connect(socket, SIGNAL(error(QBluetoothSocket::SocketError)), this, SLOT(recevoirErreurSocket(QBluetoothSocket::SocketError)));

    emit socketConnectee();
}

/**
 * @brief Ferme la connextion libere la mémoire de la socket
 *
 * @fn Communication::deconnecterSocket
 */
void Communication::deconnecterSocket()
{
    if (socket->isOpen())
       socket->close();
    delete socket;
    socket = nullptr;
    qDebug() << Q_FUNC_INFO;

    emit socketDeconnectee();
}

/**
 * @brief Débug les erreurs liées a la socket Bluetoth
 *
 * @fn Communication::recevoirErreurSocket
 * @param erreurSocket Erreur de la socket
 */
void Communication::recevoirErreurSocket(QBluetoothSocket::SocketError erreurSocket)
{
    qDebug() << Q_FUNC_INFO << erreurSocket;
}

/**
 * @brief Débug les nouvelles connexions a des terminaux mobiles
 *
 * @fn Communication::connecterTerminalMobile
 * @param adresse Adresse de l'appareil ayant initié la nouvelle connexion
 */
void Communication::connecterTerminalMobile(const QBluetoothAddress &adresse)
{
    if (peripheriqueLocal.pairingStatus(adresse) == QBluetoothLocalDevice::Paired || peripheriqueLocal.pairingStatus(adresse) == QBluetoothLocalDevice::AuthorizedPaired)
    {
        qDebug() << Q_FUNC_INFO << adresse.toString() << "appairé";
    }
    else
        qDebug() << Q_FUNC_INFO << adresse.toString() << "non appairé";
}

/**
 * @brief Débug les déconnexions des terminaux mobiles
 *
 * @fn Communication::deconnecterTerminalMobile
 * @param adresse Adresse de l'appareil ayant terminé la connexion
 */
void Communication::deconnecterTerminalMobile(const QBluetoothAddress &adresse)
{
    qDebug() << Q_FUNC_INFO << adresse;
}

/**
 * @brief Débug les erreurs bluetooth
 *
 * @fn Communication::recevoirErreurBluetooth
 * @param erreurBluetooth Erreur du bluetooth
 */
void Communication::recevoirErreurBluetooth(QBluetoothLocalDevice::Error erreurBluetooth)
{
    qDebug() << Q_FUNC_INFO << erreurBluetooth;
}


/**
 * @brief Lis les trames recues dans la socket et les sépare afin de les traiter une par une
 *
 * @fn Communication::lireTrame
 */
void Communication::lireTrame()
{
    QByteArray donnees;

    donnees = socket->readAll();
    qDebug() << Q_FUNC_INFO << donnees;

    trame += QString(donnees.data());
    qDebug() << Q_FUNC_INFO << trame;

    if(trame.startsWith(ENTETE_TRAME) && trame.endsWith("\r\n"))
    {
        QStringList trames = trame.split("\r\n", QString::SkipEmptyParts);
        qDebug() << Q_FUNC_INFO << trames;

        for(int i = 0; i < trames.count(); ++i)
        {
            qDebug() << Q_FUNC_INFO << i << trames[i];
            infosTrame = trames[i].split(";");
            traiterTrame(infosTrame);
        }

        trame.clear();
    }
}

/**
 * @brief Traite les trames recues et émet des signaux en fonction de la valeur du champ "CHAMP TYPE TRAME"
 *
 * @fn Communication::traiterTrame
 * @param infosTrame Liste des informations contenues dans la trame
 * @return Booléen qui renseigne si l'opération a été réalisée ou non
 */
bool Communication::traiterTrame(QStringList infosTrame)
{
    if(infosTrame.count() < CHAMP_TYPE_TRAME)
        return false;

    qDebug() << Q_FUNC_INFO << infosTrame;

    switch(infosTrame[CHAMP_TYPE_TRAME].toInt())
    {
        case TypeTrame::RENCONTRE:
            emit(debutRencontre(infosTrame[NOM_CLUB_1], infosTrame[NOM_CLUB_2]));
            break;

        case TypeTrame::INFO_PARTIE:
            if (infosTrame.count() == NB_CHAMPS_PARTIE_SIMPLE)
                emit(creationPartieSimple(infosTrame));
            else
                emit(creationPartieDouble(infosTrame));
            break;

        case TypeTrame::SCORE:
            emit(nouveauScorePartie(infosTrame[ID_PARTIE].toInt(), infosTrame[POINTS_JOUEUR_A].toInt(), infosTrame[POINTS_JOUEUR_B].toInt(), infosTrame[MANCHES_JOUEUR_A].toInt(), infosTrame[MANCHES_JOUEUR_B].toInt()));
            break;

        case TypeTrame::ETAT_PARTIE:
            emit(changementEtatPartie(infosTrame[ID_PARTIE].toInt(),infosTrame[ETAT]));
            break;

        case TypeTrame::NET:
            emit(detectionNET(infosTrame[ID_PARTIE].toInt()));
            break;

        case TypeTrame::TEMPS_MORT:
            emit(demandeTempsMort());
            break;

        default:
            qDebug() << Q_FUNC_INFO << "Type de trame inconnu !";
            return false;
    }

    return true;
}

/**
 * @brief Retourne la derniere trame traitée
 *
 * @fn Communication::getTrame
 * @return La derniere trame traitée
 */
QString Communication::getTrame() const
{
    return trame;
}
