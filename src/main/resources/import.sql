INSERT INTO membres (nom, prenom, email, mot_de_passe) VALUES ('Dupont', 'Claire', 'claire.dupont@club-escalade.fr', '$2a$10$Dow1XzG1K7Qe9z9w9uQW5eWZz7e6b6Q6rXkzvYj7Y8W1XyYyZzYyG.mqLqy3p.VZSv96J76sm9GK');
INSERT INTO membres (nom, prenom, email, mot_de_passe) VALUES ('Martin', 'Julien', 'julien.martin@club-escalade.fr', '$2b$10$UzErTfaJMTmcB6RRgfGpTOGwnM7tiY.mqLqy3p.VZSv96J76sm9GK');
INSERT INTO membres (nom, prenom, email, mot_de_passe) VALUES ('Lefevre', 'Sophie', 'sophie.lefevre@club-escalade.fr', '$2b$10$UzErTfaJMTmcB6RRgfGpTOGwnM7tiY.mqLqy3p.VZSv96J76sm9GK');

INSERT INTO categories (nom) VALUES ('Escalade');
INSERT INTO categories (nom) VALUES ('Randonnée');
INSERT INTO categories (nom) VALUES ('Alpinisme');

INSERT INTO sorties (nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES ('Falaise de Buoux', 'Session grande voie sur les secteurs classiques de Buoux.', 'https://www.buoux-escalade.fr', '2025-06-14', (SELECT id FROM membres WHERE email = 'claire.dupont@club-escalade.fr'), (SELECT id FROM categories WHERE nom = 'Escalade'));
INSERT INTO sorties (nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES ('Traversée du Vercors', 'Randonnée sur deux jours avec nuit en refuge.', NULL, '2026-05-10', (SELECT id FROM membres WHERE email = 'julien.martin@club-escalade.fr'), (SELECT id FROM categories WHERE nom = 'Randonnée'));
INSERT INTO sorties (nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES ('Initiation Mont Blanc', 'Sortie encadrée pour apprendre les bases de l''alpinisme.', 'https://www.chamonix-guides.com', '2026-07-20', (SELECT id FROM membres WHERE email = 'sophie.lefevre@club-escalade.fr'), (SELECT id FROM categories WHERE nom = 'Alpinisme'));
INSERT INTO sorties (nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES ('Bloc à Fontainebleau', 'Journée blocs sur circuits orange et bleu.', 'https://www.fontainebleau.fr', '2025-10-03', (SELECT id FROM membres WHERE email = 'julien.martin@club-escalade.fr'), (SELECT id FROM categories WHERE nom = 'Escalade'));
INSERT INTO sorties (nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES ('Crêtes du Jura', 'Randonnée sportive en crête avec dénivelé soutenu.', NULL, '2026-09-12', (SELECT id FROM membres WHERE email = 'claire.dupont@club-escalade.fr'), (SELECT id FROM categories WHERE nom = 'Randonnée'));
INSERT INTO sorties (nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES ('École de glace à La Grave', 'Progression sur glacier et ateliers de sécurité.', 'https://www.lagrave.com', '2027-01-18', (SELECT id FROM membres WHERE email = 'sophie.lefevre@club-escalade.fr'), (SELECT id FROM categories WHERE nom = 'Alpinisme'));
