# Electronic-API

API REST para la gestion de productos de una tienda electronica y gestion de login y registro de usuarios hecho con Spring Boot, Spring Security y JWT.

Aplicacion catalogo de productos hecho con Spring Boot y React

Proyecto pendiente, fecha limite: 28/02/2025

Nota: Una vez que tenga todos los endpoints terminados, llega la hora de implementar el sistema de login y registro en la API con Spring Security y JWT.

## Endpoints de la API

Se consideran las entidades: Brand, Category, Image, Product

| Endpoint                      | Descripción                                                                       | Permitir a...   | Estado                                |
| ----------------------------- | --------------------------------------------------------------------------------- | --------------- | ------------------------------------- |
| GET /brands                   | Listar todas las marcas habilitadas                                               | Sin restricción | LISTO, posiblemente no sea necesario  |
| POST /brands                  | Crear una nueva marca                                                             | ADMIN           | LISTO, pero falta restringir endpoint |
| PUT /brands/{id}              | Actualizar una marca por su ID                                                    | ADMIN           | LISTO, pero falta restringir endpoint |
| PATCH /brands/{id}            | Inhabilitar una marca (eliminar)                                                  | ADMIN           | LISTO, pero falta restringir endpoint |
| GET /categories               | Listar todas las categorias habilitadas                                           | Sin restricción | LISTO                                 |
| POST /categories              | Crear una nueva categoria                                                         | ADMIN           | LISTO, pero falta restringir endpoint |
| PUT /categories/{id}          | Actualizar una categoria por su ID                                                | ADMIN           | LISTO, pero falta restringir endpoint |
| PATCH /categories/{id}        | Inhabilitar una categoria (eliminar)                                              | ADMIN           | LISTO, pero falta restringir endpoint |
| GET /images/{name}            | Obtener una imagen por su nombre                                                  | Sin restricción | LISTO                                 |
| GET /products                 | Listar todos los productos habilitados, ademas de su categoria y marca habilitada | Sin restricción | LISTO                                 |
| GET /products/{id}            | Obtener un producto por su ID                                                     | Sin restricción | LISTO                                 |
| GET /products/{name}          | Obtener una lista de productos por su nombre (tipo barra de busqueda)             | Sin restricción | LISTO                                 |
| GET /products/categories/{id} | Obtener una lista de productos que corresponden a la misma categoria por su ID    | Sin restricción | LISTO                                 |
| POST /products                | Crear un nuevo producto                                                           | ADMIN           | LISTO, pero falta restringir endpoint |
| PUT /products/{id}            | Actualizar un producto                                                            | ADMIN           | LISTO, pero falta restringir endpoint |
| PATCH products/{id}           | Inhabilitar un producto (eliminar)                                                | ADMIN           | LISTO, pero falta restringir endpoint |

**Agregar otros endpoints relacionados con caracteristicas de un producto y ficha tecnica de un producto, además de filtros de busqueda**

USUARIO

- Ver todos los productos en el catalogo incluyendo su imagen

- Ver un producto por su ID

- Buscar productos por su nombre

- Filtrar productos

- Registrarse en la aplicación

- Iniciar sesión en la aplicación

- Mostrar la lista de categorias

ADMINISTRADOR

- Agregar, editar y eliminar un producto

- Agregar, editar y eliminar marcas y categorias

- 
