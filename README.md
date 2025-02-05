# Electronic-API

API REST para la gestion de productos de una tienda electronica y gestion de login y registro de usuarios hecho con Spring Boot, Spring Security y JWT.

Aplicacion catalogo de productos hecho con Spring Boot y React

Proyecto pendiente, fecha limite: 28/02/2025

Nota: Una vez que tenga todos los endpoints terminados, llega la hora de implementar el sistema de login y registro en la API con Spring Security y JWT.

## Endpoints de la API

Se consideran las entidades: Brand, Category, Image, Product, Feature, FeatureValues y CategoryFeatures

| Endpoint                      | Descripción                                                                                                                                                                           | Permitir a...   | Estado                                                                     |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------- | -------------------------------------------------------------------------- |
| GET /brands                   | Listar todas las marcas habilitadas                                                                                                                                                   | Sin restricción | LISTO, posiblemente no sea necesario                                       |
| POST /brands                  | Crear una nueva marca                                                                                                                                                                 | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| PUT /brands/{id}              | Actualizar una marca por su ID                                                                                                                                                        | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| PATCH /brands/{id}            | Inhabilitar una marca (eliminar)                                                                                                                                                      | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| GET /categories               | Listar todas las categorias habilitadas                                                                                                                                               | Sin restricción | LISTO                                                                      |
| POST /categories              | Crear una nueva categoria                                                                                                                                                             | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| PUT /categories/{id}          | Actualizar una categoria por su ID                                                                                                                                                    | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| PATCH /categories/{id}        | Inhabilitar una categoria (eliminar)                                                                                                                                                  | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| GET /products                 | Listar todos los productos habilitados, ademas de su categoria y marca habilitada                                                                                                     | Sin restricción | LISTO                                                                      |
| GET /products/offer           | Obtener una lista de productos que estan en oferta                                                                                                                                    | Sin restricción | LISTO                                                                      |
| GET /products/categories/{id} | Obtener una lista de productos que corresponden a la misma categoria por su ID                                                                                                        | Sin restricción | LISTO                                                                      |
| GET /products/{name}          | Obtener una lista de productos por su nombre (tipo barra de busqueda)                                                                                                                 | Sin restricción | LISTO                                                                      |
| GET /products/search/filters? | Obtener una lista de productos filtrados por los siguientes filtros: name (nombre), idCategory (id de categoria), idBrands (uno o varios id de marcas) y offer (1 si estan en oferta) | Sin restricción | LISTO                                                                      |
| GET /products/search/features | Obtener una lista de productos filtrados por los valores de sus caracteristicas, se tiene en cuenta el parametro featureValues (contiene una lista de ids de valores de categoria)    | Sin restricción | FALTA MEJORAR (posiblemente se utilice esto en una gran consulta  con JPA) |
| GET /products/{id}            | Obtener un producto por su ID                                                                                                                                                         | Sin restricción | LISTO, pero si el producto esta inhabilitado, no lo va a mostrar           |
| POST /products                | Crear un nuevo producto, incluyendo su imagen                                                                                                                                         | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| PUT /products/{id}            | Actualizar un producto, si se sube una imagen, se reemplaza la imagen existente                                                                                                       | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| PATCH products/{id}           | Inhabilitar un producto (eliminar)                                                                                                                                                    | ADMIN           | LISTO, pero falta restringir endpoint                                      |
| GET /images/{name}            | Obtener una imagen por su nombre                                                                                                                                                      | Sin restricción | LISTO                                                                      |
| GET /features                 | Obtener una lista de caracteristica (por ejemplo: color, tamaño de pantalla, procesador, etc.)                                                                                        | Sin restricción | LISTO                                                                      |
| POST /features                | Agregar una nueva caracteristica                                                                                                                                                      | ADMIN           | LISTO                                                                      |
| PUT /features                 | Actualizar el nombre una caracteristica existente                                                                                                                                     | ADMIN           | LISTO                                                                      |
| PATCH /features               | Inhabilitar una caracteristica                                                                                                                                                        | ADMIN           | LISTO, posiblemente sera eliminado este endpoint                           |
| GET /features/{id}/values     | Obtiene los valores que corresponden a una caracteristica por su ID (valores de ejemplo: 4GB, 8GB, 16GB)                                                                              | Sin restricción | LISTO                                                                      |
| POST /features/{id}/values    | Agrega un nuevo valor a una caracteristica por su ID                                                                                                                                  | ADMIN           | LISTO ???                                                                  |
| GET /categories/{id}/features | Obtiene una lista de caracteristicas que corresponden a una misma categoria por su ID, util para mostrar los filtros por una categoria                                                | Sin restricción | Falta mejorar                                                              |
| POST /categories/features     | Relaciona una caracteristica con una categoria, se utiliza para especificar que una categoria va a mostrar el filtro relacionado a la caracteristica                                  | ADMIN           | Falta mejorar                                                              |

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
