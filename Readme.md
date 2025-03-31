# Spring Academia

## Descripción del proyecto

Este proyecto es una aplicación web desarrollada con Spring Boot que permite gestionar la información de una academia. Los usuarios pueden registrarse, iniciar sesión y realizar diversas operaciones relacionadas con la gestión de alumnos, profesores y asignaturas.

## Tecnologías usadas

- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven

## Características

- Registro y autenticación de usuarios
- Gestión de alumnos, profesores y asignaturas
- Asignación de roles a los usuarios
- Gestión de teléfonos asociados a los usuarios
- Páginas de ayuda y acerca de

## Rutas

- `/` - Página de inicio
- `/login` - Página de inicio de sesión
- `/usuarios` - Gestión de usuarios
- `/alumnos` - Gestión de alumnos
- `/profesores` - Gestión de profesores
- `/asignaturas` - Gestión de asignaturas
- `/ayuda` - Página de ayuda
- `/acerca` - Página acerca de
- `/denegado` - Página de acceso denegado

## Instalación e iniciar el servidor

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/josego16/spring-academia.git
   cd spring-academia
   ```

2. Configurar la base de datos en el archivo `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:33306/academia
   spring.datasource.username=root
   spring.datasource.password=zx76wbz7FG89k
   ```

3. Ejecutar la aplicación con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

## Realizar peticiones HTTP usando Postman

Para realizar peticiones HTTP a la API de la aplicación, se pueden utilizar herramientas como Postman. A continuación se muestra un ejemplo de cómo realizar una petición GET a la ruta `/alumnos`:

1. Abrir Postman y crear una nueva petición.
2. Seleccionar el método GET y la URL `http://localhost:8080/alumnos`.
3. Hacer clic en el botón "Send" para enviar la petición y ver la respuesta.

Para realizar peticiones HTTP con otros verbos (POST, PUT, DELETE), se pueden seguir los mismos pasos, cambiando el método y añadiendo el cuerpo de la petición si es necesario.
