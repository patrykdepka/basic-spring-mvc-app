INSERT INTO `app_user` (`first_name`, `last_name`, `email`, `password`, `bio`, `city`, `enabled`, `account_non_locked`)
VALUES
    -- admin@example.com / admin
    ('Admin', 'Admin', 'admin@example.com', '{bcrypt}$2a$10$1rXMx0b4caUy/SN3Xg4j4u43gDqVJO/R.zXGCGWc/wr7bsmmSEk2C', 'Cześć! Jestem administratorem tego serwisu.', 'Serwerownia', 1, 1),
    -- jankowalski@example.com / user1
    ('Jan', 'Kowalski', 'jankowalski@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Jan Kowalski i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki na Politechnice Rzeszowskiej.', 'Rzeszów', 1, 1),
    -- patrykkowalski@example.com / user1
    ('Patryk', 'Kowalski', 'patrykkowalski@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Patryk Kowalski i jestem z Krakowa. Obecnie jestem na trzecim roku informatyki na Politechnice Krakowskiej.', 'Kraków', 1, 1),
    -- jannowak@example.com / user1
    ('Jan', 'Nowak', 'jannowak@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Jan Nowak i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki w Wyższej Szkole Informatyki i Zarządzania w Rzeszowie.', 'Rzeszów', 1, 1),
    -- patryknowak@example.com / user1
    ('Patryk', 'Nowak', 'patryknowak@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Patryk Nowak i jestem z Krakowa. Obecnie jestem na trzecim roku informatyki na Uniwersytecie Jagiellońskim.', 'Kraków', 1, 1),
    -- piotrwysocki@example.com / user1
    ('Piotr', 'Wysocki', 'piotrwysocki@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Piotr Wysocki i jestem z Rzeszowa. Obecnie jestem na czwartym roku informatyki na Politechnice Rzeszowskiej.', 'Rzeszów', 1, 1),
    -- dawidpolak@example.com / user1
    ('Dawid', 'Polak', 'dawidpolak@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Dawid Polak i jestem z Krakowa. Obecnie jestem na czwartym roku informatyki na Politechnice Krakowskiej.', 'Kraków', 1, 1),
    -- zuzannakowalska@example.com / user1
    ('Zuzanna', 'Kowalska', 'zuzannakowalska@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Zuzanna Kowalska i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki w Wyższej Szkole Informatyki i Zarządzania w Rzeszowie.', 'Rzeszów', 1, 1),
    -- piotrmichalik@example.com / user1
    ('Piotr', 'Michalik', 'piotrmichalik@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Piotr Michalik i jestem z Krakowa. Obecnie jestem na czwartym roku informatyki na Uniwersytecie Jagiellońskim.', 'Kraków', 1, 1),
    -- dawiddabrowski@example.com / user1
    ('Dawid', 'Dąbrowski', 'dawiddabrowski@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Dawid Dąbrowski i jestem z Rzeszowa. Obecnie jestem na piątym roku informatyki na Politechnice Rzeszowskiej.', 'Rzeszów', 1, 1),
    -- danieldabrowski@example.com / user1
    ('Daniel', 'Dąbrowski', 'danieldabrowski@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Daniel Dąbrowski i jestem z Krakowa. Obecnie jestem na piątym roku informatyki na Politechnice Krakowskiej.', 'Kraków', 1, 1),
    -- marianowak@example.com / user1
    ('Maria', 'Nowak', 'marianowak@example.com', '{bcrypt}$2a$10$GeDFCwTN8AbMbu/2JwcNlO4p/SikMfMBmoYh9e/lma/2zD7aACMbG', 'Cześć! Nazywam się Maria Nowak i jestem z Rzeszowa. Obecnie jestem na trzecim roku informatyki w Wyższej Szkole Informatyki i Zarządzania w Rzeszowie.', 'Rzeszów', 1, 1);