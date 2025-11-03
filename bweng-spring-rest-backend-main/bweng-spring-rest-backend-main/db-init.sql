-- Datenbank erstellen
CREATE DATABASE IF NOT EXISTS `oeffi-db`;

-- Zur erstellten Datenbank wechseln
USE `oeffi-db`;

-- Tabelle 'users' erstellen
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    salutation VARCHAR(10) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('administrator', 'user') NOT NULL DEFAULT 'user',
    status ENUM('active', 'inactive') NOT NULL DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabelle 'categories' erstellen
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabelle 'products' erstellen
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    rating VARCHAR(255) DEFAULT NULL,
    image_path VARCHAR(255),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Tabelle 'orders' erstellen
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'completed', 'canceled') NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	payment_method VARCHAR(50) DEFAULT NULL, -- Zahlungsmethode
	invoice_number VARCHAR(20) DEFAULT NULL, -- Rechnungsnummer
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabelle 'order_items' erstellen
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Tabelle 'cart' erstellen
CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
/**
-- Tabelle 'coupons' erstellen
CREATE TABLE coupons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(5) NOT NULL UNIQUE,
    value DECIMAL(10, 2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME NOT NULL,
	cashed TINYINT(1) DEFAULT 0, -- 0 = nicht eingelöst, 1 = eingelöst
    residual_value DECIMAL(10, 2) DEFAULT NULL -- Restwert des Gutscheins
);

-- Tabelle 'payment_methods' erstellen
CREATE TABLE payment_methods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    method ENUM('Kreditkarte', 'Paypal', 'Klarna', 'Kauf auf Rechnung', 'Apple Pay') NOT NULL,
    card_number VARCHAR(16), -- Für Kreditkarte
    expiry_date VARCHAR(5), -- MM/YY für Kreditkarte
    cvv VARCHAR(3), -- Für Kreditkarte
    paypal_email VARCHAR(255), -- Für Paypal
    klarna_account VARCHAR(255), -- Für Klarna
    billing_address VARCHAR(255), -- Für Kauf auf Rechnung
    apple_pay_token VARCHAR(255), -- Für Apple Pay
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
**/
-- Kategorien einfügen
INSERT INTO categories (name) VALUES
('Einzelfahrkarten'),
('Tageskarten'),
('Wochenkarten'),
('Monatskarten'),
('Jahreskarten'),
('Senioren & Studenten'),
('Gruppentickets');

-- Beispiel-Nutzer hinzufügen
INSERT INTO users (salutation, first_name, last_name, address, zip, city, email, username, password, role, status)
VALUES
('Mr.', 'Max', 'Mustermann', 'Musterstraße 1', '10115', 'Berlin', 'max.mustermann@example.com', 'maxm', '$2y$10$examplehash1', 'user', 'active'),
('Ms.', 'Julia', 'Schmidt', 'Hauptstraße 12', '80331', 'München', 'julia.schmidt@example.com', 'julias', '$2y$10$examplehash2', 'user', 'active'),
('Mr.', 'Lukas', 'Meier', 'Bahnhofstraße 5', '50667', 'Köln', 'lukas.meier@example.com', 'lukasm', '$2y$10$examplehash3', 'user', 'active'),
('Ms.', 'Anna', 'Fischer', 'Kirchweg 8', '20095', 'Hamburg', 'anna.fischer@example.com', 'annaf', '$2y$10$examplehash4', 'user', 'active'),
('Mr.', 'Tom', 'Becker', 'Parkallee 22', '28195', 'Bremen', 'tom.becker@example.com', 'tomb', '$2y$10$examplehash5', 'user', 'active'),
('Ms.', 'Laura', 'Klein', 'Schulstraße 3', '70173', 'Stuttgart', 'laura.klein@example.com', 'laurak', '$2y$10$examplehash6', 'user', 'active'),
('Mr.', 'Jan', 'Wagner', 'Marktweg 7', '04109', 'Leipzig', 'jan.wagner@example.com', 'janw', '$2y$10$examplehash7', 'user', 'active');

-- Beispiel-Produkte hinzufügen
-- Beispiel-Tickets einfügen
INSERT INTO products (name, description, price, rating, image_path, category_id) VALUES
('Einzelfahrkarte Innenstadt', 'Fahrkarte gültig für eine Fahrt innerhalb der Innenstadtzone.', 2.90, 'Sehr praktisch für kurze Wege', 'einzelfahrt.jpg', 1),
('Tageskarte Stadtgebiet', 'Unbegrenzte Fahrten innerhalb des Stadtgebiets für einen Tag.', 7.50, 'Ideal für Touristen und Pendler', 'tageskarte.jpg', 2),
('Wochenkarte Region', 'Gültig für alle öffentlichen Verkehrsmittel in der Region für 7 Tage.', 28.00, 'Perfekt für Berufspendler', 'wochenkarte.jpg', 3),
('Monatskarte Stadtgebiet', 'Unbegrenzte Fahrten innerhalb des Stadtgebiets für einen Monat.', 85.00, 'Sehr praktisch für Vielnutzer', 'monatskarte.jpg', 4),
('Jahreskarte Stadt & Region', '365 Tage unbegrenzte Nutzung von allen Verkehrsmitteln in Stadt und Region.', 950.00, 'Optimal für tägliche Pendler', 'jahreskarte.jpg', 5),
('Senioren-Monatskarte', 'Monatskarte für Senioren mit Ermäßigung, gültig für alle Verkehrsmittel in der Stadt.', 50.00, 'Günstig und flexibel', 'seniorenkarte.jpg', 6),
('Gruppenticket 5 Personen', 'Fahrkarte gültig für 5 Personen gleichzeitig, ideal für Familien oder Freunde.', 20.00, 'Sehr praktisch für Gruppen', 'gruppenticket.jpg', 7),
('Abendkarte', 'Unbegrenzte Fahrten nach 18:00 Uhr für das gesamte Stadtgebiet.', 4.50, 'Perfekt für Freizeitaktivitäten', 'abendkarte.jpg', 2),
('Airport Shuttle Ticket', 'Direktfahrt zum Flughafen, gültig für Hin- und Rückfahrt.', 12.00, 'Schnell und bequem zum Flughafen', 'airport.jpg', 1),
('Studenten-Monatskarte', 'Ermäßigtes Monatsticket für Studierende, gültig innerhalb des Stadtgebiets.', 40.00, 'Ideal für Studenten', 'studentenkarte.jpg', 6),
('Wochenendticket Stadt & Region', 'Gültig für unbegrenzte Fahrten am Wochenende innerhalb der Stadt und Region.', 15.00, 'Ideal für Ausflüge am Wochenende', 'wochenendticket.jpg', 3),
('Einzelfahrkarte Zone 2', 'Fahrkarte gültig für eine Fahrt in Zone 2.', 3.50, 'Praktisch für kurze Strecken außerhalb der Innenstadt', 'einzelfahrt_zone2.jpg', 1),
('Monatskarte Abo', 'Monatsticket als Abonnement, automatisch erneuerbar.', 80.00, 'Komfortabel für regelmäßige Fahrten', 'monatsabo.jpg', 4),
('Familien-Tageskarte', 'Gültig für 2 Erwachsene und 2 Kinder, einen Tag lang.', 18.00, 'Perfekt für Familienausflüge', 'familientageskarte.jpg', 7),
('Nachtfahrkarte', 'Gültig für alle Fahrten zwischen 22:00 und 06:00 Uhr.', 5.00, 'Sicher unterwegs in der Nacht', 'nachtfahrkarte.jpg', 2),
('Senioren-Einzelfahrkarte', 'Einzelfahrkarte für Senioren, gültig innerhalb der Stadt.', 2.00, 'Günstig für kurze Wege', 'senior_einzelfahrt.jpg', 6),
('Studenten-Tageskarte', 'Gültig für einen Tag, für Studierende mit Ausweis.', 5.50, 'Flexibel für Uni & Freizeit', 'studententageskarte.jpg', 6),
('Gruppenticket 10 Personen', 'Fahrkarte gültig für 10 Personen gleichzeitig.', 35.00, 'Super für Vereinsausflüge', 'gruppenticket10.jpg', 7),
('Airport Express Ticket', 'Direktfahrt zum Flughafen in nur 30 Minuten.', 14.00, 'Schnell und bequem', 'airport_express.jpg', 1),
('Flex-Ticket 5 Fahrten', 'Ticket für 5 Fahrten innerhalb eines Monats frei wählbar.', 12.50, 'Flexibel und günstig', 'flex5.jpg', 2);

/**
-- Beispiel-Gutscheine einfügen
INSERT INTO coupons (code, value, created_at, expires_at) VALUES
('A1B2C', 10.00, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
('D3E4F', 15.00, NOW(), DATE_ADD(NOW(), INTERVAL 60 DAY)),
('G5H6I', 20.00, NOW(), DATE_ADD(NOW(), INTERVAL 90 DAY)),
('J7K8L', 25.00, NOW(), DATE_ADD(NOW(), INTERVAL 120 DAY)),
('M9N0O', 30.00, NOW(), DATE_ADD(NOW(), INTERVAL 150 DAY)),
('P1Q2R', 35.00, NOW(), DATE_ADD(NOW(), INTERVAL 180 DAY)),
('S3T4U', 40.00, NOW(), DATE_ADD(NOW(), INTERVAL 210 DAY)),
('V5W6X', 45.00, NOW(), DATE_ADD(NOW(), INTERVAL 240 DAY)),
('Y7Z8A', 50.00, NOW(), DATE_ADD(NOW(), INTERVAL 270 DAY)),
('B9C0D', 55.00, NOW(), DATE_ADD(NOW(), INTERVAL 300 DAY));
**/
-- Rechte für einen Benutzer erstellen
CREATE USER IF NOT EXISTS 'oeffi-db-user'@'%' IDENTIFIED BY 'xSnsN)F3!wg[vbPk';

GRANT ALL PRIVILEGES ON `oeffi-db`.* TO 'oeffi-db-user'@'%';

FLUSH PRIVILEGES;