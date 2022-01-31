--- Supprime les tables

DROP TABLE IF EXISTS Score;
DROP TABLE IF EXISTS Partie;
DROP TABLE IF EXISTS Rencontre;
DROP TABLE IF EXISTS Joueur;
DROP TABLE IF EXISTS Equipe;

--- Table Equipe

CREATE TABLE IF NOT EXISTS Equipe(idEquipe INTEGER PRIMARY KEY AUTOINCREMENT, nomClub VARCHAR);

INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'PPC Avignon');
INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'PPC Pernes');
INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'PPC Sorgues');
INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'AS Cavaillon TT');
INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'C.S. Pertuis');
INSERT INTO Equipe (idEquipe, nomClub) VALUES (NULL,'T.T. Morières');

--- Table Joueur

CREATE TABLE IF NOT EXISTS Joueur(numeroLicence INTEGER PRIMARY KEY, idEquipe INTEGER NOT NULL, nom VARCHAR, prenom VARCHAR, CONSTRAINT fk_idEquipe_1 FOREIGN KEY (idEquipe) REFERENCES Equipe(idEquipe), UNIQUE(nom,prenom));

--- Autres :
--- Catgérorie -> V1/-50, S/-40, J1/-16, etc...
--- Classement/Points -> 17/1745, 11/1129, etc...

INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (9419829,1,'BOUDRIMIL','Kamal');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (844443,1,'REDOR','Simon');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843368,1,'KRIER','Eric');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (139328,1,'RUIZ','Jean michel');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (841827,1,'GUIDARELLI','Nicolas');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (9420542,2,'RUAULT','Nicolas');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845682,2,'CROUZET','Lionel');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (847650,2,'FLORES','Fabien');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845423,2,'BRESSON','Thibault');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (844549,2,'FAGOO','Damien');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843944,3,'BEAUMONT','Jérôme');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (303504,3,'SAULNIER','Christian');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (842353,3,'COMTE','Emmanuel');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (842363,3,'LEVRARD','Mickael');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (645758,3,'FILAFERRO','Thomas');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (846543,3,'DUBOURG ','Dylan');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (841424,4,'ALBERT','Pierre-david');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843634,4,'MANGIN','Frederic');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (9110467,4,'ZENATY','Julien');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (843871,4,'MANGIN','Thierry');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (842471,4,'DESPRES','Gregory');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (3330232,5,'BADRE','Corentin');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845470,5,'CHOUISNARD','Enzo');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (261769,5,'LASCOMBE','Nicolas');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (845078,5,'BOINARD','Thomas');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (84326,5,'SOLER','Michel');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (305731,6,'OPEZZO','Mathieu');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (1310341,6,'AMBROSINO','Stephane');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (136310,6,'MALET','Sebastien');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (349167,6,'ROUTTIER','Julien');
INSERT INTO Joueur(numeroLicence, idEquipe, nom, prenom) VALUES (43041,6,'JOULLIE','Arnaud');

--- Table Rencontre

CREATE TABLE IF NOT EXISTS Rencontre(idRencontre INTEGER PRIMARY KEY AUTOINCREMENT, idEquipeA INTEGER NOT NULL, idEquipeB INTEGER NOT NULL, nbPartiesGagnantes INTEGER DEFAULT 0, estFinie INTEGER DEFAULT 0, horodatage DATETIME NOT NULL, CONSTRAINT fk_idEquipe_A FOREIGN KEY (idEquipeA) REFERENCES Equipe(idEquipe), CONSTRAINT fk_idEquipe_B FOREIGN KEY (idEquipeB) REFERENCES Equipe(idEquipe));

--- Table Partie

--- CREATE TABLE IF NOT EXISTS Partie(idPartie INTEGER PRIMARY KEY AUTOINCREMENT, idRencontre INTEGER NOT NULL, idJoueurA INTEGER NOT NULL, idJoueurB INTEGER NOT NULL, nbManchesGagnantes INTEGER DEFAULT 0, nbPointsParManche INTEGER DEFAULT 0, debut DATETIME NOT NULL, fin DATETIME, CONSTRAINT fk_idRencontre_1 FOREIGN KEY (idRencontre) REFERENCES Rencontre(idRencontre), CONSTRAINT fk_idJoueur_A FOREIGN KEY (idJoueurA) REFERENCES Joueur(numeroLicence), CONSTRAINT fk_idJoueur_B FOREIGN KEY (idJoueurB) REFERENCES Joueur(numeroLicence));

CREATE TABLE IF NOT EXISTS Partie(idPartie INTEGER PRIMARY KEY AUTOINCREMENT, idRencontre INTEGER NOT NULL, idJoueurA INTEGER NOT NULL, idJoueurB INTEGER NOT NULL, idJoueurW INTEGER NOT NULL, idJoueurX INTEGER NOT NULL, nbManchesGagnantes INTEGER DEFAULT 0, nbPointsParManche INTEGER DEFAULT 0, typePartie INTEGER DEFAULT 1, debut DATETIME NOT NULL, fin DATETIME, CONSTRAINT fk_idRencontre_1 FOREIGN KEY (idRencontre) REFERENCES Rencontre(idRencontre), CONSTRAINT fk_idJoueur_A FOREIGN KEY (idJoueurA) REFERENCES Joueur(numeroLicence), CONSTRAINT fk_idJoueur_B FOREIGN KEY (idJoueurB) REFERENCES Joueur(numeroLicence), CONSTRAINT fk_idJoueur_W FOREIGN KEY (idJoueurW) REFERENCES Joueur(numeroLicence), CONSTRAINT fk_idJoueur_X FOREIGN KEY (idJoueurX) REFERENCES Joueur(numeroLicence));

--- Table Score

CREATE TABLE IF NOT EXISTS Score(idPartie INTEGER NOT NULL, numeroSet INTEGER NOT NULL, pointsJoueurEquipeA INTEGER DEFAULT 0, pointsJoueurEquipeB INTEGER DEFAULT 0, debut DATETIME NOT NULL, fin DATETIME, CONSTRAINT pk_score PRIMARY KEY (idPartie,numeroSet), CONSTRAINT fk_idPartie_1 FOREIGN KEY (idPartie) REFERENCES Partie(idPartie));

--- Table Club
CREATE TABLE IF NOT EXISTS Club(idClub INTEGER PRIMARY KEY AUTOINCREMENT, nom VARCHAR);

--- Voir aussi :
--- ON DELETE CASCADE
