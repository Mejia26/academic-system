🚀 Backend - Sistema de Gestión Académica (Grupo 44)
Este es el repositorio del backend para el Proyecto Final del ITLA. Está construido con Java 21, Spring Boot 3 y PostgreSQL, implementando una arquitectura de API RESTful con seguridad basada en JWT mediante Cookies HttpOnly.

🛠️ Prerrequisitos
Para correr este proyecto NO necesitas instalar Java, Maven ni PostgreSQL en tu computadora. Solo necesitas tener instalado:

Docker Desktop (Asegúrate de que esté abierto y corriendo).

Git.

🏃‍♂️ ¿Cómo levantar el proyecto?
Sigue estos pasos cada vez que hagas un git pull de los últimos cambios:

Abre una terminal y ubícate en la raíz del proyecto (donde está el archivo docker-compose.yml).

Ejecuta el siguiente comando para construir y levantar tanto la Base de Datos como el Backend:

Bash
docker compose up --build -d
Nota: La primera vez que corras esto, Docker descargará las imágenes y ejecutará los scripts SQL (01-schema.sql y 02-data.sql) para crear las tablas y llenarlas con datos de prueba automáticamente.

Para apagar los contenedores cuando termines de trabajar:

Bash
docker compose down
(Si alguna vez necesitas resetear la base de datos por completo y borrar todos los datos, usa docker compose down -v y luego vuelve a levantar el proyecto).

📚 Documentación de la API (Swagger)
Una vez que los contenedores estén corriendo, puedes ver todos los endpoints, modelos de datos y probar la API directamente desde tu navegador entrando a:

👉 http://localhost:8080/swagger-ui.html

🔐 Credenciales de Prueba (Login)
La base de datos se inicializa automáticamente con un usuario docente de prueba. Puedes usar estas credenciales en el endpoint /api/v1/auth/login (o desde la interfaz de Swagger):

Email: teacher@example.com

Password: password123

⚠️ Notas muy importantes para el Desarrollo del Front-end (React)
Para proteger la aplicación contra ataques XSS, el token JWT NO se devuelve en el cuerpo de la respuesta JSON.

Cuando haces un login exitoso, el backend automáticamente adjuntará una cookie llamada token en el navegador con la bandera HttpOnly.

Para que tu aplicación de React (ya sea usando axios o fetch) pueda recibir esta cookie en el login y enviarla automáticamente en las siguientes peticiones (como al pedir la lista de estudiantes), debes configurar tu cliente HTTP para que incluya credenciales.

Si usas Axios (Recomendado):
Configúralo globalmente en tu archivo principal (App.jsx o main.jsx):

JavaScript
import axios from 'axios';

// Establece la URL base de tu backend
axios.defaults.baseURL = 'http://localhost:8080';

// ESTO ES CRUCIAL: Permite que Axios envíe y reciba cookies
axios.defaults.withCredentials = true; 
Si usas Fetch API:
Debes agregar la propiedad credentials: 'include' en cada petición:

JavaScript
fetch('http://localhost:8080/api/v1/classrooms', {
    method: 'GET',
    credentials: 'include' // Obligatorio para enviar la cookie JWT
})
¡Cualquier duda con la estructura de los datos que devuelve un endpoint, revisa el Swagger!
