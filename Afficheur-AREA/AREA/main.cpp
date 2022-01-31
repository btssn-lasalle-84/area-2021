#include "IHMAfficheur.h"
#include <QApplication>

/**
 * @file main.cpp
 *
 * @brief Programme principal AREA
 * @details Crée et affiche en plein écran la fenêtre principale de l'application AREA
 * @author William GEROUVILLE <gerouwilliam@gmail.com>
 * @version 1.0
 *
 * @param argc
 * @param argv[]
 * @return int
 * $LastChangedRevision: 111 $
 * $LastChangedDate: 2021-06-08 15:54:37 +0200 (mar. 08 juin 2021) $
 *
 */
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    IHMAfficheur w;

    //w.showFullScreen();



    return a.exec();
}
