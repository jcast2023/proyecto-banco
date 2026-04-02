# 💳 Ecosistema Bancario de Microservicios Reactivos
**Alumno:** Julio Castillo  
**Curso:** Desarrollo de Aplicaciones Web II (DAWII)  
**Ciclo:** 6to Ciclo - Cibertec

## 📖 Descripción del Proyecto
Este proyecto implementa una arquitectura de microservicios para la gestión integral de clientes bancarios. Utiliza un patrón **BFF (Backend For Frontend)** para orquestar la comunicación entre servicios internos, garantizando seguridad, trazabilidad y alta disponibilidad mediante programación reactiva.

## 🛠️ Stack Tecnológico
* **Java 17 & Spring Boot 3.2.0**
* **Spring WebFlux:** Programación reactiva no bloqueante.
* **Spring Data R2DBC:** Acceso a base de datos reactivo (PostgreSQL).
* **Keycloak 25:** Servidor de Identidad y Acceso (OAuth2/OIDC).
* **Docker & Docker Compose:** Containerización y orquestación.
* **MapStruct:** Mapeo de DTOs y Entidades.
* **Logback & AOP:** Gestión de logs y trazabilidad con `X-Tracking-Id`.

---

## 🚀 Guía de Despliegue Rápido

### 1. Requisitos Previos
* Docker Desktop instalado y corriendo.
* Postman (para pruebas de endpoints).

### 2. Pasos para levantar el entorno
Desde la terminal en la raíz del proyecto, ejecuta:
```bash
docker-compose up -d --build
```
### 3. Configuración de Keycloak
El servidor corre en: http://localhost:8090 (Admin: admin / admin).
Realm utilizado: banco-realm.
Client utilizado: backend-client.
Usuario de prueba: julio / 123456.
🔗 Endpoints Principales
BFF Orquestador 8080 GET /api/bff/cliente-integral/{codigo_encriptado}
MS Clientes     8081 GET /api/clientes/codigo/{codigo}
MS Productos    8082 GET /api/productos/cliente/{codigo}

📄 Documentación Swagger (OpenAPI)
Acceso a la interfaz de pruebas: http://localhost:8080/swagger-ui.html
🛡️ Trazabilidad y Logs
Se utiliza AOP (Aspectos) para interceptar las peticiones y registrar el X-Tracking-Id. Los logs se generan automáticamente en la carpeta /logs de cada microservicio.
Ejemplo de log:
[DEBUG] c.b.bff.aspect.LogAspect - Tracking ID: [REQ-123] - Iniciando orquestación para cliente.
🧪 Pruebas con Postman
1.Importar la colección: postman/Proyecto-Banco-Julio-Castillo-BFF.postman_collection.json.
2.Obtener el Token en la pestaña Auth (OAuth 2.0).
3.Ejecutar el request. Código de éxito: Q0xJLTAwMQ== (CLI-001).
📂 Estructura de la EntregaPlaintextEF_DAWII_Castillo_Julio/
├── bff-orquestador/        # Orquestador Reactivo (Netty)
├── ms-clientes/            # Microservicio de Clientes (R2DBC)
├── ms-productos/           # Microservicio de Productos (R2DBC)
├── postman/                # Colección de Postman exportada
├── docker-compose.yml      # Orquestación de contenedores
└── README.md               # Documentación del proyecto