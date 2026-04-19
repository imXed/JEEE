INSERT INTO membres (id, nom, prenom, email, mot_de_passe) VALUES
    (1, 'Dupont', 'Claire', 'claire.dupont@club-escalade.fr', '$2b$10$UzErTfaJMTmcB6RRgfGpTOGwnM7tiY.mqLqy3p.VZSv96J76sm9GK'),
    (2, 'Martin', 'Julien', 'julien.martin@club-escalade.fr', '$2b$10$UzErTfaJMTmcB6RRgfGpTOGwnM7tiY.mqLqy3p.VZSv96J76sm9GK'),
    (3, 'Lefevre', 'Sophie', 'sophie.lefevre@club-escalade.fr', '$2b$10$UzErTfaJMTmcB6RRgfGpTOGwnM7tiY.mqLqy3p.VZSv96J76sm9GK');

INSERT INTO categories (id, nom) VALUES
    (1, 'Escalade'),
    (2, 'Randonnée'),
    (3, 'Alpinisme');

INSERT INTO sorties (id, nom, description, site_web, date_sortie, createur_id, categorie_id) VALUES
    (1, 'Falaise de Buoux', 'Session grande voie sur les secteurs classiques de Buoux.', 'https://www.buoux-escalade.fr', '2025-06-14', 1, 1),
    (2, 'Traversee du Vercors', 'Randonnee sur deux jours avec nuit en refuge.', NULL, '2026-05-10', 2, 2),
    (3, 'Initiation Mont Blanc', 'Sortie encadree pour apprendre les bases de l alpinisme.', 'https://www.chamonix-guides.com', '2026-07-20', 3, 3),
    (4, 'Bloc a Fontainebleau', 'Journee blocs sur circuits orange et bleu.', 'https://www.fontainebleau.fr', '2025-10-03', 2, 1),
    (5, 'Cretes du Jura', 'Randonnee sportive en crete avec denivele soutenu.', NULL, '2026-09-12', 1, 2),
    (6, 'Ecole de glace a La Grave', 'Progression sur glacier et ateliers de securite.', 'https://www.lagrave.com', '2027-01-18', 3, 3);
