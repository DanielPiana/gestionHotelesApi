DROP DATABASE IF EXISTS hoteles;
CREATE DATABASE hoteles;
USE hoteles;

CREATE TABLE users (
                       id_usuario INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       token VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE hotel (
                       id_hotel INT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(255) NOT NULL,
                       descripcion TEXT,
                       categoria VARCHAR(50),
                       piscina BOOLEAN,
                       localidad VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE habitacion (
                            id_habitacion INT AUTO_INCREMENT PRIMARY KEY,
                            tamano INT NOT NULL,
                            precio_noche DECIMAL(10,2) NOT NULL,
                            desayuno BOOLEAN,
                            ocupada BOOLEAN,
                            id_hotel INT,
                            FOREIGN KEY (id_hotel) REFERENCES hotel(id_hotel) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO users (username, password, token)
VALUES
    ('juan', 'juan', 'token');

INSERT INTO hotel (nombre, descripcion, categoria, piscina, localidad)
VALUES
    ('Hotel Sol', 'Hotel en la playa', '5 estrellas', TRUE, 'Valladolid'),
    ('Hotel Montaña', 'Hotel en la montaña', '4 estrellas', FALSE, 'León'),
    ('Hotel Ciudad', 'Hotel en el centro de la ciudad', '3 estrellas', TRUE, 'Madrid');

INSERT INTO habitacion (tamano, precio_noche, desayuno, ocupada, id_hotel)
VALUES
    (30, 100.00, TRUE, FALSE, 1),
    (25, 80.00, FALSE, TRUE, 1),
    (20, 60.00, TRUE, FALSE, 2),
    (35, 120.00, TRUE, TRUE, 2),
    (40, 150.00, FALSE, FALSE, 3),
    (30, 90.00, TRUE, TRUE, 3);
