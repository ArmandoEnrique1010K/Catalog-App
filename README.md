# Electronic-API

API REST para la gestion de productos de una tienda electronica y gestion de login y registro de usuarios hecho con Spring Boot, Spring Security y JWT.

Aplicacion catalogo de productos hecho con Spring Boot y React

Proyecto pendiente, fecha limite: 28/02/2025

Nota: Una vez que tenga todos los endpoints terminados, llega la hora de implementar el sistema de login y registro en la API con Spring Security y JWT.

## Endpoints de la API

Se consideran las entidades: Brand, Category, Image, Product, Feature, FeatureValues y CategoryFeatures

| Controlador / Endpoints                                | Descripción                                                                                                                                                                           | Permitir a...   | Estado  |
|:------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------- | ------- |
| **Brand (Marca)**                                      |                                                                                                                                                                                       |                 |         |
| GET /brands                                            | Listar todas las marcas habilitadas, solamente se visualiza para administradores                                                                                                      | ADMIN           | ✅ 👤 ⚠️ |
| POST /brands                                           | Crear una nueva marca                                                                                                                                                                 | ADMIN           | ✅ 👤    |
| PUT /brands/{id}                                       | Actualizar una marca por su ID                                                                                                                                                        | ADMIN           | ✅ 👤    |
| PATCH /brands/{id}                                     | Inhabilitar una marca (eliminar)                                                                                                                                                      | ADMIN           | ✅ 👤    |
| **Category (Categoria)**                               |                                                                                                                                                                                       |                 |         |
| GET /categories                                        | Listar todas las categorias habilitadas                                                                                                                                               | Sin restricción | ✅       |
| POST /categories                                       | Crear una nueva categoria                                                                                                                                                             | ADMIN           | ✅👤     |
| PUT /categories/{id}                                   | Actualizar una categoria por su ID                                                                                                                                                    | ADMIN           | ✅👤     |
| PATCH /categories/{id}                                 | Inhabilitar una categoria (eliminar)                                                                                                                                                  | ADMIN           | ✅👤     |
| **Product (Producto)**                                 |                                                                                                                                                                                       |                 |         |
| GET /products                                          | Listar todos los productos habilitados, ademas de su categoria y marca habilitada                                                                                                     | Sin restricción | ✅       |
| GET /products/offer                                    | Obtener una lista de productos habilitados que estan en oferta                                                                                                                        | Sin restricción | ✅       |
| GET /products/categories/{id}                          | Obtener una lista de productos habilitados que corresponden a la misma categoria por su ID                                                                                            | Sin restricción | ✅       |
| GET /products/{name}                                   | Obtener una lista de productos habilitados por su nombre (tipo barra de busqueda)                                                                                                     | Sin restricción | ✅⚠️     |
| GET /products/search/filters?                          | Obtener una lista de productos filtrados por los siguientes filtros: name (nombre), idCategory (id de categoria), idBrands (uno o varios id de marcas) y offer (1 si estan en oferta) | Sin restricción | ✅⚠️     |
| GET /products/search/features                          | Obtener una lista de productos filtrados por los valores de sus caracteristicas, se tiene en cuenta el parametro featureValues (contiene una lista de ids de valores de categoria)    | Sin restricción | ✅⚠️     |
| GET /products/search?                                  | Obtener una lista de productos habilitados filtrados por los filtros: name, idCategory, idBrands, offer y featureValues                                                               | Sin restricción | ❌       |
| GET /products/{id}                                     | Obtener un producto habilitado por su ID, de lo contrario no se va a mostrar.                                                                                                         | Sin restricción | ✅       |
| POST /products                                         | Crear un nuevo producto, incluyendo su imagen                                                                                                                                         | ADMIN           | ✅👤⚠️   |
| POST /products/new                                     | Crear un nuevo producto, incluyendo su imagen y ficha tecnica                                                                                                                         | ADMIN           | ❌       |
| PUT /products/{id}                                     | Actualizar un producto, si se sube una imagen, se reemplaza la imagen existente                                                                                                       | ADMIN           | ✅👤⚠️   |
| PATCH products/{id}                                    | Inhabilitar un producto (eliminar)                                                                                                                                                    | ADMIN           | ✅ 👤    |
| **Image (Imagen de producto)**                         |                                                                                                                                                                                       |                 |         |
| GET /images/{name}                                     | Obtener una imagen por su nombre                                                                                                                                                      | Sin restricción | ✅       |
| **Feature (Caracteristica)**                           |                                                                                                                                                                                       |                 |         |
| GET /features                                          | Obtener una lista de caracteristicas habilitadas (por ejemplo: color, tamaño de pantalla, procesador, etc.)                                                                           | Sin restricción | ✅⚠️     |
| POST /features                                         | Agregar una nueva caracteristica                                                                                                                                                      | ADMIN           | ✅👤     |
| PUT /features                                          | Actualizar el nombre una caracteristica existente                                                                                                                                     | ADMIN           | ✅👤     |
| PATCH /features                                        | Inhabilitar una caracteristica                                                                                                                                                        | ADMIN           | ✅👤⚠️   |
| **FeatureValue (Valor de una caracteristica)**         |                                                                                                                                                                                       |                 |         |
| GET /features/{id}/values                              | Obtiene los valores que corresponden a una caracteristica por su ID (valores de ejemplo: 4GB, 8GB, 16GB)                                                                              | ADMIN           | ✅👤⚠️   |
| POST /features/{id}/values                             | Agrega un nuevo valor a una caracteristica por su ID                                                                                                                                  | ADMIN           | ✅👤     |
| GET /features/{idFeature}/values/category/{idCategory} | INVESTIGAR ESTE ENDPOINT                                                                                                                                                              |                 | ⚠️⚠️    |
| **CategoryFeature (Caracteristica de una categoria)**  |                                                                                                                                                                                       |                 |         |
| GET /categories/{id}/features                          | Obtiene una lista de caracteristicas que corresponden a una misma categoria por su ID, util para mostrar los filtros por una categoria                                                | Sin restricción | ⚠️⚠️    |
| POST /categories/features                              | Relaciona una caracteristica con una categoria, se utiliza para especificar que una categoria va a mostrar el filtro relacionado a la caracteristica                                  | ADMIN           | ✅👤     |
| **ProductFeature (Caracteristica de un producto)**     |                                                                                                                                                                                       |                 |         |
| GET /product/{id}/feature/tech-sheet                   | Obtiene la ficha tecnica de un producto, contiene una lista de objetos en el que incluye la caracteristica y su valor                                                                 | Sin restricción | ✅⚠️     |
| POST /product/{id}/feature/tech-sheet                  | Endpoint experimental para guardar la ficha tecnica de un producto "mediante una entidad"                                                                                             |                 | ⚠️⚠️    |
| POST /product/{id}/feature/tech-sheet/test2            | Endpoint experimental para guardar la ficha tecnica de un producto "mediante un DTO"                                                                                                  |                 |         |

Listar marcas por categoria

Filtros en productos

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
