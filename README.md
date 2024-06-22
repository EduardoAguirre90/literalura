# Proyecto LiterAlura Challenge de ALURA que realiza Consulta de Libros

Este proyecto Java utiliza Spring Boot para consultar libros desde una API y almacenarlos en una base de datos. Proporciona funcionalidades como búsqueda de libros por título, consulta de autores vivos en un año específico, estadísticas de libros por idioma y consulta de los 10 libros más descargados desde la API.

## Requisitos

- Java 11 o superior
- Maven
- Base de datos MySQL (en este caso se uso pgAdmin 4 compatible con Spring Boot)

## Configuración

1. **Clonar el repositorio:**

   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd nombre-del-repositorio
   ```

2. **Configuración de la base de datos:**

   - Configura tu base de datos MySQL en `src/main/resources/application.properties`:

     ```properties
     spring.datasource.url=jdbc:postgresql://${DB_HOST}/consulta_libros
     spring.datasource..username=${DB_USER}
     spring.datasource.password=${DB_PASSWORD}
     spring.datasource.driver-class-name=org.postgresql.Driver
     hibernate.dialect=org.hibernate.dialect.HSQLDialect
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Ejecución:**

   Desde la raíz del proyecto, puedes ejecutarlo usando Maven:

   ```bash
   mvn spring-boot:run
   ```

   La aplicación se ejecutará en `localhost:8080`.

## Funcionalidades

1. **Buscar libros por título:**

   - Busca un libro por su título en la base de datos y en la API de Gutendex.

2. **Mostrar libros buscados:**

   - Muestra los libros que se han buscado y almacenado en la base de datos.

3. **Mostrar autores de los libros consultados:**

   - Muestra los autores de los libros consultados y almacenados.

4. **Buscar autores vivos en determinado año:**

   - Busca autores vivos en un año específico utilizando la base de datos.

5. **Consultar cantidad de libros por idioma:**

   - Consulta la cantidad de libros almacenados en la base de datos por idioma.

6. **Consultar los 10 libros más consultados desde la API:**

   - Consulta y muestra los 10 libros más consultados desde la API de Gutendex.



## Estructura del Proyecto

- **`src/main/java/`**: Contiene los archivos Java.
- **`src/main/resources/`**: Contiene los archivos de recursos como propiedades de aplicación y archivos estáticos.

## Tecnologías Utilizadas

- Spring Boot
- Hibernate/JPA
- MySQL/PostgeSQL
- RestTemplate para consumir la API de Gutendex

## Autor

- Nombre: [Eduardo Aguirre Rosales]
- Contacto: [yolalo66@hotmail.com]
```
