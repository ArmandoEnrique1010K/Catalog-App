-- INICIALMENTE ESTE ARCHIVO SE PUEDE UTILIZAR PARA INSERTAR LOS DATOS DE PRUEBA EN LA BASE DE DATOS, PERO SOLAMENTE FUNCIONA SI SE TIENE EN EL ARCHIVO PROPERTIES:
-- spring.jpa.hibernate.ddl-auto = create

-- Inserta datos en la tabla "brand"
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

-- FUENTES BIBLIOGRAFICAS DE AYUDA
-- https://www.baeldung.com/mapstruct
-- https://github.com/ArmandoEnrique1010K/DesarrolloWebEntornosServidor-02-2023-2024/blob/master/07-SpringDataJPASQL.md#autoincremental-vs-uuid
-- https://www.baeldung.com/spring-request-param