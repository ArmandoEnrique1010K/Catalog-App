SELECT * FROM db_electronic.brand;
INSERT INTO brand (id, name, status) VALUES
(1, 'HP', 1),
(2, 'LG', 1),
(3, 'Toshiba', 0),
(4, 'Lenovo', 1),
(5, 'Sony', 1),
(6, 'Epson', 1),
(7, 'Samsung', 1);

INSERT INTO category (id, name, status) VALUES
(1, 'Laptops', true),
(2, 'TV', true),
(3, 'Smartphones', true),
(4, 'Impresoras', true),
(5, 'Camaras', true),
(6, 'Relojes', true);

INSERT INTO feature (id, name, status) VALUES
(1, 'Color', true),
(2, 'Tamaño de pantalla', true),
(3, 'Tipo de pantalla', true),
(4, 'Procesador', true),
(5, 'Memoria RAM', true),
(6, 'Tecnologia de impresion', true),
(7, 'Puertos USB', true),
(8, 'Bluetooth integrado', true),
(9, 'Resistencia al agua', true),
(10, 'Sistema operativo', true);
-- Inserta datos en la tabla "category_feature"
INSERT INTO category_feature (category_id, feature_id) VALUES
(1, 3),
(1, 4),
(1, 5),
(1, 7),
(1, 10),
(2, 2),
(2, 3),
(2, 7),
(2, 8),
(3, 1),
(3, 2),
(3, 5),
(3, 8),
(3, 9),
(3, 10),
(4, 6),
(4, 8),
(5, 8),
(5, 9),
(6, 1),
(6, 8),
(6, 9);


INSERT INTO image (id, name) VALUES
(1, 'https://example.com/images/product1.jpg'),
(2, 'https://example.com/images/product2.jpg'),
(3, 'https://example.com/images/product3.jpg'),
(4, 'https://example.com/images/product4.jpg'),
(5, 'https://example.com/images/product5.jpg'),
(6, 'https://example.com/images/product6.jpg'),
(7, 'https://example.com/images/product7.jpg'),
(8, 'https://example.com/images/product8.jpg'),
(9, 'https://example.com/images/product9.jpg'),
(10, 'https://example.com/images/product10.jpg');


INSERT INTO image (id, name) VALUES
(11, 'https://example.com/images/product11.jpg'),
(12, 'https://example.com/images/product12.jpg'),
(13, 'https://example.com/images/product13.jpg'),
(14, 'https://example.com/images/product14.jpg'),
(15, 'https://example.com/images/product15.jpg'),
(16, 'https://example.com/images/product16.jpg'),
(17, 'https://example.com/images/product17.jpg'),
(18, 'https://example.com/images/product18.jpg'),
(19, 'https://example.com/images/product19.jpg'),
(20, 'https://example.com/images/product20.jpg');



-- Limpia la tabla antes de insertar nuevos datos (opcional)
-- TRUNCATE TABLE product;

-- Inserta datos en la tabla "product"
INSERT INTO product (id, code, name, in_offer, price, offer_price, status, description, updated_at, created_at, brand_id, category_id, image_id) VALUES
(1, 'P001', 'HP Spectre x360', true, 1599.99, 1499.99, true, 'Laptop convertible con pantalla táctil de 13.5" y procesador Intel Core i7', '2025-01-20 15:30:00', '2025-01-01 10:00:00',  1, 1, 1),
(2, 'P002', 'LG OLED TV', false, 1299.99, NULL, true, 'Televisor OLED 4K de 55" con tecnología AI ThinQ.', '2025-01-15 18:45:00', '2025-01-03 12:00:00', 2, 2, 2),
(3, 'P003', 'Samsung Galaxy S23', true, 999.00, 920.99, true, 'Smartphone con pantalla AMOLED de 6.1" y resistencia al agua IP68.', '2025-01-10 14:20:00', '2025-01-05 09:00:00', 7, 3, 3),
(4, 'P004', 'Epson EcoTank L3250', false, 199.99, NULL, true, 'Impresora multifuncional con sistema de tanque de tinta.', '2025-01-17 11:00:00', '2025-01-07 08:30:00', 6, 4, 4),
(5, 'P005', 'Sony Alpha a7 III', true, 1999.99, 1899.99, true, 'Cámara mirrorless con sensor de fotograma completo y 24 MP.', '2025-01-18 16:00:00', '2025-01-06 15:00:00', 5, 5, 5),
(6, 'P006', 'Samsung Galaxy Watch 6', false, 299.99, NULL, true, 'Reloj inteligente con seguimiento de salud y GPS integrado.', '2025-01-16 13:00:00', '2025-01-08 14:00:00', 7, 6, 6),
(7, 'P007', 'Lenovo ThinkPad X1 Carbon', true, 1799.99, 1599.99, false, 'Ultrabook empresarial con pantalla de 14" y procesador Intel Core i7.', '2025-01-14 19:30:00', '2025-01-09 16:00:00', 4, 1, 7),
(8, 'P008', 'LG NanoCell TV', false, 1599.99, NULL, true, 'Televisor NanoCell 4K de 65" con inteligencia artificial.', '2025-01-12 12:00:00', '2025-01-10 10:30:00', 2, 2, 8),
(9, 'P009', 'iPhone 14', true, 1099.99, 899.99, true, 'Smartphone de Apple con pantalla Super Retina XDR de 6.1".', '2025-01-11 10:15:00', '2025-01-11 09:00:00', 3, 3, 9),
(10, 'P010', 'HP DeskJet 2722', false, 89.99, NULL, true, 'Impresora todo-en-uno con conectividad inalámbrica.', '2025-01-19 15:00:00', '2025-01-12 11:00:00', 1, 4, 10),
(11, 'P011', 'Sony ZV-1', true, 799.99, 699.99, true, 'Cámara compacta diseñada para creadores de contenido.', '2025-01-20 16:00:00', '2025-01-01 12:00:00', 5, 5, 11),
(12, 'P012', 'Garmin Forerunner 945', false, 599.99, NULL, true, 'Reloj inteligente para atletas con mapas y música.', '2025-01-18 14:00:00', '2025-01-02 09:30:00', 6, 6, 12),
(13, 'P013', 'Toshiba Satellite Pro', true, 749.99, 704.99, true, 'Laptop de 15.6" con procesador AMD Ryzen 5.', '2025-01-17 10:00:00', '2025-01-04 11:00:00', 3, 1, 13),
(14, 'P014', 'Samsung QLED TV', true, 2999.99, 2799.99, true, 'Televisor QLED 8K de 75" con tecnología Quantum HDR.', '2025-01-15 17:00:00', '2025-01-05 14:30:00', 7, 2, 14),
(15, 'P015', 'Google Pixel 7', false, 899.99, NULL, true, 'Smartphone con cámara avanzada y software exclusivo de Google.', '2025-01-13 11:00:00', '2025-01-06 10:00:00', 7, 3, 15),
(16, 'P016', 'Canon EOS R50', true, 999.99, 949.99, true, 'Cámara mirrorless compacta con lente intercambiable.', '2025-01-12 15:00:00', '2025-01-07 09:00:00', 5, 5, 16),
(17, 'P017', 'Fitbit Versa 4', true, 229.99, 199.99, true, 'Reloj inteligente con monitor de actividad física y sueño.', '2025-01-14 18:00:00', '2025-01-08 13:00:00', 6, 6, 17),
(18, 'P018', 'Dell XPS 13', false, 1299.99, NULL, true, 'Laptop ultraportátil con pantalla InfinityEdge de 13.3.', '2025-01-16 09:00:00', '2025-01-09 12:00:00', 1, 1, 18),
(19, 'P019', 'Sony Bravia XR', true, 1799.99, 1599.99, true, 'Televisor inteligente 4K con sonido envolvente.', '2025-01-19 14:30:00', '2025-01-10 11:00:00', 5, 2, 19),
(20, 'P020', 'Huawei Watch GT 3', true, 249.99, 219.99, true, 'Reloj inteligente con diseño elegante y larga duración de batería.', '2025-01-11 13:00:00', '2025-01-11 09:30:00', 6, 6, 20);


# Selecciona todos los productos
SELECT 
    p.id AS product_id,
    p.code AS product_code,
    p.name AS product_name,
    p.in_offer AS product_in_offer,
    p.price AS product_price,
    p.offer_price AS product_offer_price,
    p.status AS product_status,
    p.description AS product_description,
    p.updated_at AS product_updated_at,
    p.created_at AS product_created_at,
    c.id AS category_id,
    c.name AS category_name,
    c.status AS category_status,
    b.id AS brand_id,
    b.name AS brand_name,
    b.status AS brand_status,
    i.id AS image_id,
    i.name AS image_name
FROM product p
LEFT JOIN category c ON p.category_id = c.id
LEFT JOIN brand b ON p.brand_id = b.id
LEFT JOIN image i ON p.image_id = i.id;



SELECT 
    p.id AS product_id,
    p.code AS product_code,
    p.name AS product_name,
    p.in_offer AS product_in_offer,
    p.price AS product_price,
    p.offer_price AS product_offer_price,
    p.status AS product_status,
    p.description AS product_description,
    p.updated_at AS product_updated_at,
    p.created_at AS product_created_at,
    c.id AS category_id,
    c.name AS category_name,
    c.status AS category_status,
    f.id AS feature_id,
    f.name AS feature_name,
    fv.id AS feature_value_id,
    fv.value AS feature_value_value
FROM 
    product p
JOIN 
    category c ON p.category_id = c.id
LEFT JOIN 
    category_feature cf ON c.id = cf.category_id
LEFT JOIN 
    feature f ON cf.feature_id = f.id
LEFT JOIN 
    feature_value fv ON f.id = fv.feature_id
ORDER BY 
    p.id, f.id;





# (1, 'Laptops'),
# (2, 'TV'),
# (3, 'Smartphones'),
# (4, 'Impresoras'),
# (5, 'Camaras'),
# (6, 'Relojes');

# Obtiene todos los filtros y sus valores para una categoría específica:
SELECT 
    f.id AS feature_id,
    f.name AS feature_name,
    fv.id AS value_id,
    fv.value AS value_name
FROM 
    category_feature cf
JOIN 
    feature f ON cf.feature_id = f.id
LEFT JOIN 
    feature_value fv ON f.id = fv.feature_id
WHERE 
    cf.category_id = 1;


# Obtiene la ficha técnica de un producto:
SELECT 
    pf.product_id,
    f.name AS feature_name,
    fv.value AS feature_value
FROM 
    product_feature pf
JOIN 
    feature f ON pf.feature_id = f.id
JOIN 
    feature_value fv ON pf.feature_value_id = fv.id
WHERE 
    pf.product_id = 84;
    
    

# (1, 'Color'),
# (2, 'Tamaño de pantalla'),
# (3, 'Tipo de pantalla'),
# (4, 'Procesador'),
# (5, 'Memoria RAM'),
# (6, 'Tecnologia de impresion'),
# (7, 'Puertos USB'),
# (8, 'Bluetooth integrado'),
# (9, 'Resistencia al agua'),
# (10, 'Sistema operativo');

-- Valores para la característica "Color" (id: 1)
INSERT INTO feature_value (id, value, feature_id) VALUES 
(1, 'Rojo', 1),
(2, 'Azul', 1),
(3, 'Negro', 1),
(4, 'Blanco', 1);

-- Valores para la característica "Tamaño" (id: 2)
INSERT INTO feature_value (id, value, feature_id) VALUES 
(5, '32 pulgadas', 2),
(6, '40 pulgadas', 2),
(7, '50 pulgadas', 2),
(8, '65 pulgadas', 2);

-- Valores para la característica "Tipo de pantalla" (id: 3)
INSERT INTO feature_value (id, value, feature_id) VALUES 
(9, 'LED', 3),
(10, 'OLED', 3),
(11, 'QLED', 3),
(12, 'Plasma', 3);

-- Valores para la característica "Procesador" (id: 4)
INSERT INTO feature_value (id, value, feature_id) VALUES 
(13, 'Intel', 4),
(14, 'AMD', 4);

-- Valores para la característica "Memoria RAM" (id: 5)
INSERT INTO feature_value (id, value, feature_id) VALUES 
(15, '8 GB', 5),
(16, '12 GB', 5),
(17, '16 GB', 5),
(18, '32 GB', 5);

-- Valores para la característica "Tecnologia de impresion" (id: 6)
INSERT INTO feature_value (id, value, feature_id) VALUES 
(19, 'Laser', 6),
(20, 'Inyeccion de tinta', 6);

-- Valores para la característica "Puertos USB" (id: 7)
INSERT INTO feature_value (id, value, feature_id) VALUES 
(21, '2', 7),
(22, '3', 7),
(23, '4', 7);

INSERT INTO feature_value (id, value, feature_id) VALUES 
(24, 'Si', 8),
(25, 'No', 8);

INSERT INTO feature_value (id, value, feature_id) VALUES 
(26, 'Si', 9),
(27, 'No', 9);

INSERT INTO feature_value (id, value, feature_id) VALUES 
(28, 'Windows', 10),
(29, 'Linux', 10);







INSERT INTO product_feature (id, product_id, feature_value_id, feature_id) VALUES
(1, 1, 1, 1), -- HP Spectre x360, Color: Rojo
(2, 1, 3, 2), -- HP Spectre x360, Tamaño de pantalla: 13.5"
(3, 1, 10, 3), -- HP Spectre x360, Tipo de pantalla: LED
(4, 1, 16, 5), -- HP Spectre x360, Memoria RAM: 12 GB

(5, 2, 5, 2), -- LG OLED TV, Tamaño de pantalla: 55 pulgadas
(6, 2, 10, 3), -- LG OLED TV, Tipo de pantalla: OLED
(7, 2, 19, 6), -- LG OLED TV, Tecnología de impresión: Inyección de tinta
(8, 2, 1, 1); -- LG OLED TV, Color: Rojo

# NUEVAS CONSULTAS
# FICHA TECNICA DE UN PRODUCTO (SE INCLUYEN CARACTERSITICAS)

# OBTIENE LOS VALORES DE UNA CARACTERISTICA
SELECT * FROM db_electronic.feature_value WHERE feature_id = 2;

# OBTIENE LOS VALORES DISTINTOS QUE CORRESPONDEN A UNA CARACTERISTICA POR SU CATEGORIA
SELECT DISTINCT fv.id, fv.value
FROM feature_value fv
JOIN product_feature pf ON fv.id = pf.feature_value_id
JOIN product p ON pf.product_id = p.id
WHERE p.category_id = 1  -- ID de la categoría
AND fv.feature_id = 1;  -- ID de la característica



# CREAR UNA CONSULTA PARA SELECCIONAR TODOS LOS PRODUCTOS CUYO VALOR DE LA CARACTERISTICA (feature_value_id) COINCIDA
SELECT * FROM db_electronic.product_feature;

# Tabla: id - feature_id - feature_value_id - product_id


SELECT p.* 
FROM product p
JOIN product_feature pf ON p.id = pf.product_id
WHERE pf.feature_value_id = 1;

SELECT p.* 
FROM product p
JOIN product_feature pf ON p.id = pf.product_id
WHERE pf.feature_value_id = 3;

SELECT p.*
FROM product p
JOIN product_feature pf ON p.id = pf.product_id
WHERE pf.feature_value_id IN (1, 2, 3);  -- Lista de valores de características


SELECT DISTINCT p.*
FROM product p
JOIN product_feature pf ON p.id = pf.product_id
WHERE pf.feature_value_id IN (1, 2, 3);  -- Lista de valores de características


SELECT p.*
FROM product p
JOIN product_feature pf ON p.id = pf.product_id
WHERE pf.feature_value_id IN (1, 2, 3)  -- Lista de valores de características
GROUP BY p.id;


# MOSTRAR LA FICHA TECNICA DE UN PRODUCTO
SELECT f.name AS feature, fv.value AS value
FROM product_feature pf
JOIN feature_value fv ON pf.feature_value_id = fv.id
JOIN feature f ON fv.feature_id = f.id
WHERE pf.product_id = 1;




