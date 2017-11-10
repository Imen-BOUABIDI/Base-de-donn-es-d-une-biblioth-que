-- Titre             : Script SQL (PostgreSQL) de création de la base de données du projet bibliothèque
-- Version           : 1.0
-- Date création     : 07 mars 2006
-- Date modification : 9 avril 2017
-- Auteur            : Philippe TANGUY
-- Description       : Ce script est une ébauche, à compléter, qui permet la création de la table
--                     "livre" pour la réalisation de la fonctionnalité "liste de tous les livres".

-- +----------------------------------------------------------------------------------------------+
-- | Suppression des tables                                                                       |
-- +----------------------------------------------------------------------------------------------+

drop table if exists "livre" cascade;
drop table if exists Abonne cascade;
drop table if exists Exemplaire cascade;
drop table if exists Emprunt;

-- +----------------------------------------------------------------------------------------------+
-- |                                  Table livre                                                 |
-- +----------------------------------------------------------------------------------------------+

create table livre
(
  id     serial primary key,
  isbn10 varchar(25) unique,
  isbn13 varchar(25) unique,
  titre  varchar(50) not null,
  auteur varchar(30)
);

-------------------- Insertion de quelques données de pour les tests -------------------------------                                            

insert into livre values(nextval('livre_id_seq'), '2-84177-042-7', NULL,                'JDBC et JAVA',                            'George REESE');    -- id = 1
insert into livre values(nextval('livre_id_seq'), NULL,            '978-2-7440-7222-2', 'Sociologie des organisations',            'Michel FOUDRIAT'); -- id = 2
insert into livre values(nextval('livre_id_seq'), '2-212-11600-4', '978-2-212-11600-7', 'Le data warehouse',                       'Ralph KIMBALL');   -- id = 3
insert into livre values(nextval('livre_id_seq'), '2-7117-4811-1', NULL,                'Entrepots de données',                    'Ralph KIMBALL');   -- id = 4
insert into livre values(nextval('livre_id_seq'), '2012250564',    '978-2012250567',    'Oui-Oui et le nouveau taxi',              'Enid BLYTON');     -- id = 5
insert into livre values(nextval('livre_id_seq'), '2203001011',    '978-2203001015',    'Tintin au Congo',                         'HERGÉ');           -- id = 6
insert into livre values(nextval('livre_id_seq'), '2012011373',    '978-2012011373',    'Le Club des Cinq et le trésor de l''île', 'Enid BLYTON');     -- id = 7

-- +----------------------------------------------------------------------------------------------+
-- |                                  Table Abonne                                                |
-- +----------------------------------------------------------------------------------------------+

create table Abonne
(
  idAbonne serial primary key,
  nom      varchar(20),
  prenom   varchar(20),
  statut   varchar(20),
  email    varchar(50)
);

-------------------- Insertion de quelques données de pour les tests -------------------------------       
insert into Abonne values (nextval('abonne_idabonne_seq'),'DUPONT','Marcel','Etudiant','marcel.dupont@imt-atlantique.fr');
insert into Abonne values (nextval('abonne_idabonne_seq'),'DUPOND','Jean','Enseignant','jean.dupond@imt-atlantique.fr');
insert into Abonne values (nextval('abonne_idabonne_seq'),'DURAND','Robert','Enseignant','robert.durand@imt-atlantique.fr');
insert into Abonne values (nextval('abonne_idabonne_seq'),'DUCSHMOLL','Hans','Etudiant','hans.duschmoll@imt-atlantique.fr');
insert into Abonne values (nextval('abonne_idabonne_seq'),'LASALAS','Paula','Etudiant','paula.lasalas@gmail.com');

-- +----------------------------------------------------------------------------------------------+
-- |                                  Table Exemplaire                                            |
-- +----------------------------------------------------------------------------------------------+

create table Exemplaire
(
  idExemplaire serial primary key,
  idLivre      integer not null references livre)
);

-------------------- Insertion de quelques données de pour les tests -------------------------------   

insert into Exemplaire values (nextval('exemplaire_idexemplaire_seq'),1);
insert into Exemplaire values (nextval('exemplaire_idexemplaire_seq'),2);
-- +----------------------------------------------------------------------------------------------+
-- |                                  Table Emprunt                                               |
-- +----------------------------------------------------------------------------------------------+
create table Emprunt
(
  idEmprunt     serial primary key,
  dateEmprunt   timestamp,
  dateRetour    timestamp,
  idA           integer not null references Abonne,
  idE           integer not null references Exemplaire
);
// check dateRetour>dateEmprunt
