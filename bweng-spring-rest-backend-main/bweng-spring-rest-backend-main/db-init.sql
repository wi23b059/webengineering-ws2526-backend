-- Datenbank erstellen
CREATE DATABASE IF NOT EXISTS `oeffi-db`;

-- Zur erstellten Datenbank wechseln
USE `oeffi-db`;

-- Tabelle 'users' erstellen
CREATE TABLE users (
    id CHAR(36) PRIMARY KEY,
    salutation ENUM('MR', 'MS', 'MRS', 'MX') NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
	country_code VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
	profile_picture_path VARCHAR(255),
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    status ENUM('ACTIVE', 'INACTIVE', 'BANNED', 'DELETED') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Trigger für automatische User-UUID
DELIMITER //
CREATE TRIGGER before_insert_users
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    IF NEW.id IS NULL OR NEW.id = '' THEN
        SET NEW.id = UUID();
    END IF;
END;
//
DELIMITER ;

-- Tabelle 'categories' erstellen
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
    FOREIGN KEY (category_id) REFERENCES categories(id),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabelle 'orders' erstellen
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'completed', 'canceled') NOT NULL DEFAULT 'pending',
	payment_method VARCHAR(50) DEFAULT NULL, -- Zahlungsmethode
	invoice_number VARCHAR(20) DEFAULT NULL, -- Rechnungsnummer
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabelle 'order_items' erstellen
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabelle 'cart' erstellen
CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
('Single Tickets'),
('Day Passes'),
('Weekly Passes'),
('Monthly Passes'),
('Annual Passes'),
('Seniors & Students'),
('Group Tickets');

-- Beispiel-Nutzer hinzufügen
INSERT INTO users (salutation, first_name, last_name, country_code, address, zip, city, email, username, password, profile_picture_path, role, status)
VALUES
('MR', 'Max', 'Mustermann', 'AT', 'Musterstraße 1', '10115', 'Berlin', 'max.mustermann@example.com', 'maxm', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_1.png', 'USER', 'ACTIVE'),
('MS', 'Julia', 'Schmidt', 'AT', 'Hauptstraße 12', '80331', 'München', 'julia.schmidt@example.com', 'julias', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_2.png', 'USER', 'ACTIVE'),
('MR', 'Lukas', 'Meier', 'AT', 'Bahnhofstraße 5', '50667', 'Köln', 'lukas.meier@example.com', 'lukasm', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_3.png', 'USER', 'ACTIVE'),
('MR', 'Anna', 'Fischer', 'AT', 'Kirchweg 8', '20095', 'Hamburg', 'anna.fischer@example.com', 'annaf', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_4.png', 'USER', 'ACTIVE'),
('MR', 'Tom', 'Becker', 'AT', 'Parkallee 22', '28195', 'Bremen', 'tom.becker@example.com', 'tomb', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_1.png', 'USER', 'ACTIVE'),
('MS', 'Laura', 'Klein', 'AT', 'Schulstraße 3', '70173', 'Stuttgart', 'laura.klein@example.com', 'laurak', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_2.png', 'USER', 'ACTIVE'),
('MR', 'Jan', 'Wagner', 'AT', 'Marktweg 7', '04109', 'Leipzig', 'jan.wagner@example.com', 'janw', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_3.png', 'USER', 'ACTIVE'),
('MR', 'Admin', 'Administrator', 'AT', 'Höchstädtplatz 1', '1200', 'Wien', 'admin.administrator@oeffi.at', 'admin', '$2a$10$bEquV67MRaMDsSzaUZ8cFe8v9AXLP9IlPgqW9LS3qhdwhsWlAVicm', 'test_4.png', 'ADMIN', 'ACTIVE');

-- Beispiel-Produkte hinzufügen
-- Beispiel-Tickets einfügen
INSERT INTO products (name, description, price, rating, image_path, category_id) VALUES
('Single Ticket City Center', 'Valid for one trip within the city center zone.', 2.90, 'Perfect for short trips', 'tram_1.png', 1),
('Day Pass City Area', 'Unlimited rides throughout the city area for one day.', 7.50, 'Ideal for tourists and commuters', 'tram_2.png', 2),
('Weekly Pass Region', 'Unlimited use of all public transport in the region for 7 days.', 28.00, 'Great for daily commuters', 'tram_1.png', 3),
('Monthly Pass City Area', 'Unlimited rides in the city area for a full month.', 85.00, 'Excellent for frequent travelers', 'tram_2.png', 4),
('Annual Pass City & Region', 'Unlimited access to all city and regional transport for a full year.', 950.00, 'Perfect for daily commuters', 'tram_1.png', 5),
('Senior Monthly Pass', 'Discounted monthly pass for seniors, valid on all city transport.', 50.00, 'Affordable and flexible', 'tram_2.png', 6),
('Group Ticket 5 People', 'Ticket for up to 5 people, ideal for families or friends.', 20.00, 'Convenient for groups', 'tram_1.png', 7),
('Evening Pass', 'Unlimited rides after 6:00 PM in the entire city area.', 4.50, 'Great for evening outings', 'tram_2.png', 2),
('Airport Shuttle Ticket', 'Direct round-trip to the airport.', 12.00, 'Quick and convenient', 'tram_1.png', 1),
('Student Monthly Pass', 'Discounted monthly pass for students, valid in the city area.', 40.00, 'Perfect for students', 'tram_2.png', 6),
('Weekend Ticket City & Region', 'Unlimited rides during the weekend across city and region.', 15.00, 'Ideal for weekend trips', 'tram_1.png', 3),
('Single Ticket Zone 2', 'Valid for one trip in Zone 2.', 3.50, 'Convenient for trips outside the city center', 'tram_2.png', 1),
('Monthly Subscription Pass', 'Automatically renewing monthly ticket for regular travel.', 80.00, 'Comfortable for frequent rides', 'tram_1.png', 4),
('Family Day Pass', 'Valid for 2 adults and 2 children for one day.', 18.00, 'Perfect for family adventures', 'tram_2.png', 7),
('Night Pass', 'Unlimited rides from 10:00 PM to 6:00 AM.', 5.00, 'Safe and convenient at night', 'tram_1.png', 2),
('Senior Single Ticket', 'Single ride ticket for seniors, valid in the city.', 2.00, 'Affordable for short trips', 'tram_2.png', 6),
('Student Day Pass', 'One-day ticket for students with valid ID.', 5.50, 'Flexible for school or leisure', 'tram_1.png', 6),
('Group Ticket 10 People', 'Ticket valid for up to 10 people at once.', 35.00, 'Great for clubs or large groups', 'tram_2.png', 7),
('Airport Express Ticket', 'Direct ride to the airport in 30 minutes.', 14.00, 'Fast and convenient', 'tram_1.png', 1),
('Flex Ticket 5 Rides', 'Five rides to use anytime within a month.', 12.50, 'Flexible and budget-friendly', 'tram_2.png', 2);

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