INSERT INTO membres (id, nom, prenom, email, mot_de_passe) VALUES
    (1, 'Dupont', 'Claire', 'claire.dupont@club-escalade.fr', '$2a$10$Dow1XzG1K7Qe9z9w9uQW5eWZz7e6b6Q6rXkzvYj7Y8W1XyYyZzYyG.mqLqy3p.VZSv96J76sm9GK'),
    (2, 'Martin', 'Julien', 'julien.martin@club-escalade.fr', '$2b$10$UzErTfaJMTmcB6RRgfGpTOGwnM7tiY.mqLqy3p.VZSv96J76sm9GK'),
    (3, 'Lefevre', 'Sophie', 'sophie.lefevre@club-escalade.fr', '$2b$10$UzErTfaJMTmcB6RRgfGpTOGwnM7tiY.mqLqy3p.VZSv96J76sm9GK');

INSERT INTO categories (id, nom) VALUES
    (1, 'Escalade'),
    (2, 'Randonnée'),
    (3, 'Alpinisme');

INSERT INTO sorties (id, nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES
    (1, 'Falaise de Buoux', 'Session grande voie sur les secteurs classiques de Buoux.', 'https://www.buoux-escalade.fr', '2025-06-14', 1, 1),
    (2, 'Traversée du Vercors', 'Randonnée sur deux jours avec nuit en refuge.', NULL, '2026-05-10', 2, 2),
    (3, 'Initiation Mont Blanc', 'Sortie encadrée pour apprendre les bases de l''alpinisme.', 'https://www.chamonix-guides.com', '2026-07-20', 3, 3),
    (4, 'Bloc à Fontainebleau', 'Journée blocs sur circuits orange et bleu.', 'https://www.fontainebleau.fr', '2025-10-03', 2, 1),
    (5, 'Crêtes du Jura', 'Randonnée sportive en crête avec dénivelé soutenu.', NULL, '2026-09-12', 1, 2),
    (6, 'École de glace à La Grave', 'Progression sur glacier et ateliers de sécurité.', 'https://www.lagrave.com', '2027-01-18', 3, 3);
