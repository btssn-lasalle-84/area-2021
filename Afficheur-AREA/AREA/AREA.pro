#-------------------------------------------------
#
# Project created by QtCreator 2021-03-18T10:31:24
#
#-------------------------------------------------

QT       += core gui widgets bluetooth
CONFIG += c++11

TARGET = AREA
TEMPLATE = app

SOURCES += main.cpp\
        IHMAfficheur.cpp \
    Communication.cpp \
    Rencontre.cpp \
    Partie.cpp \
    Club.cpp \
    Joueur.cpp

HEADERS  += IHMAfficheur.h \
    Communication.h \
    Rencontre.h \
    Partie.h \
    Club.h \
    Joueur.h

FORMS    += IHMAfficheur.ui

CONFIG(release, debug|release):DEFINES+=QT_NO_DEBUG_OUTPUT
