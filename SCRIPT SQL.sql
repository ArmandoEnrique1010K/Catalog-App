SELECT * FROM db_catalog.brand;
INSERT INTO brand (id, name, status) VALUES
(1, 'Nike', 1),
(2, 'Adidas', 1),
(3, 'Puma', 0),
(4, 'Reebok', 1),
(5, 'Under Armour', 1),
(6, 'New Balance', 0),
(7, 'Asics', 1),
(8, 'Converse', 1),
(9, 'Fila', 0),
(10, 'Vans', 1);

INSERT INTO category (id, name, status) VALUES
(1, 'Electronics', true),
(2, 'Clothing', true),
(3, 'Home Appliances', false),
(4, 'Books', true),
(5, 'Toys', true),
(6, 'Sports', true),
(7, 'Automotive', false),
(8, 'Beauty', true),
(9, 'Furniture', true),
(10, 'Health', true);

INSERT INTO feature (id, name) VALUES
(1, 'Waterproof'),
(2, 'Bluetooth'),
(3, 'Eco-friendly'),
(4, 'Energy Efficient'),
(5, 'Handmade'),
(6, 'Limited Edition'),
(7, 'Recyclable'),
(8, 'Portable'),
(9, 'Lightweight'),
(10, 'Durable');

-- Inserta datos en la tabla "category_feature"
INSERT INTO category_feature (category_id, feature_id) VALUES
(1, 2), -- Electronics tiene Bluetooth
(1, 8), -- Electronics es Portable
(2, 5), -- Clothing es Handmade
(2, 3), -- Clothing es Eco-friendly
(3, 4), -- Home Appliances es Energy Efficient
(4, 6), -- Books es Limited Edition
(5, 7), -- Toys son Recyclable
(6, 9), -- Sports son Lightweight
(7, 10), -- Automotive es Durable
(8, 1), -- Beauty es Waterproof
(9, 8), -- Furniture es Portable
(10, 3); -- Health es Eco-friendly

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
TRUNCATE TABLE product;

-- Inserta datos en la tabla "product"
INSERT INTO product (id, code, name, in_offer, price, offer_price, status, description, updated_at, created_at, category_id, brand_id, image_id) VALUES
(1, 'P001', 'Smartphone', true, 599.99, 499.99, true, 'High-end smartphone with 128GB storage.', '2025-01-20 15:30:00', '2025-01-01 10:00:00', 1, 1, 1),
(2, 'P002', 'Sports Shoes', false, 79.99, NULL, true, 'Durable running shoes for all terrains.', '2025-01-15 18:45:00', '2025-01-03 12:00:00', 2, 2, 2),
(3, 'P003', 'Microwave Oven', true, 120.00, 99.99, true, 'Compact microwave oven with digital display.', '2025-01-10 14:20:00', '2025-01-05 09:00:00', 3, 3, 3),
(4, 'P004', 'Novel Book', false, 19.99, NULL, true, 'Bestselling novel of the year.', '2025-01-17 11:00:00', '2025-01-07 08:30:00', 4, 4, 4),
(5, 'P005', 'Action Figure', true, 29.99, 24.99, true, 'Limited edition action figure for collectors.', '2025-01-18 16:00:00', '2025-01-06 15:00:00', 5, 5, 5),
(6, 'P006', 'Tennis Racket', false, 89.99, NULL, true, 'Lightweight tennis racket for advanced players.', '2025-01-16 13:00:00', '2025-01-08 14:00:00', 6, 6, 6),
(7, 'P007', 'Car Cover', true, 49.99, 39.99, false, 'Waterproof car cover for all weather.', '2025-01-14 19:30:00', '2025-01-09 16:00:00', 7, 7, 7),
(8, 'P008', 'Lipstick', false, 14.99, NULL, true, 'Long-lasting lipstick in vibrant colors.', '2025-01-12 12:00:00', '2025-01-10 10:30:00', 8, 8, 8),
(9, 'P009', 'Sofa Set', true, 899.99, 799.99, true, 'Comfortable and modern 3-piece sofa set.', '2025-01-11 10:15:00', '2025-01-11 09:00:00', 9, 9, 9),
(10, 'P010', 'Vitamin Supplements', false, 24.99, NULL, true, 'Organic vitamins for daily health support.', '2025-01-19 15:00:00', '2025-01-12 11:00:00', 10, 10, 10);

-- Inserta más datos en la tabla "product"
INSERT INTO product (id, code, name, in_offer, price, offer_price, status, description, updated_at, created_at, category_id, brand_id, image_id) VALUES
(11, 'P011', 'Gaming Laptop', true, 1299.99, 1099.99, true, 'High-performance laptop for gaming and work.', '2025-01-20 16:00:00', '2025-01-01 12:00:00', 1, 1, 11),
(12, 'P012', 'Hoodie', false, 49.99, NULL, true, 'Comfortable hoodie for casual wear.', '2025-01-18 14:00:00', '2025-01-02 09:30:00', 2, 2, 12),
(13, 'P013', 'Blender', true, 79.99, 64.99, true, 'Powerful blender with multiple speed settings.', '2025-01-17 10:00:00', '2025-01-04 11:00:00', 3, 3, 13),
(14, 'P014', 'E-book Reader', true, 159.99, 129.99, true, 'Lightweight e-book reader with glare-free screen.', '2025-01-15 17:00:00', '2025-01-05 14:30:00', 4, 4, 14),
(15, 'P015', 'Puzzle Game', false, 24.99, NULL, true, 'Challenging puzzle game for all ages.', '2025-01-13 11:00:00', '2025-01-06 10:00:00', 5, 5, 15),
(16, 'P016', 'Basketball', true, 29.99, 19.99, true, 'Durable basketball for indoor and outdoor use.', '2025-01-12 15:00:00', '2025-01-07 09:00:00', 6, 6, 16),
(17, 'P017', 'Car Vacuum Cleaner', true, 69.99, 49.99, true, 'Portable vacuum cleaner for car interiors.', '2025-01-14 18:00:00', '2025-01-08 13:00:00', 7, 7, 17),
(18, 'P018', 'Face Cream', false, 34.99, NULL, true, 'Hydrating face cream for daily skincare.', '2025-01-16 09:00:00', '2025-01-09 12:00:00', 8, 8, 18),
(19, 'P019', 'Dining Table', true, 599.99, 499.99, true, 'Modern dining table for six people.', '2025-01-19 14:30:00', '2025-01-10 11:00:00', 9, 9, 19),
(20, 'P020', 'Protein Powder', true, 39.99, 29.99, true, 'High-quality protein powder for athletes.', '2025-01-11 13:00:00', '2025-01-11 09:30:00', 10, 10, 20);



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
