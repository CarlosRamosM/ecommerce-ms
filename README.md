# Proyecto de Microservicios de Ecommerce

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-brightgreen?logo=spring-boot)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.1.1-brightgreen?logo=spring)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apache-maven)
![MongoDB](https://img.shields.io/badge/MongoDB-8.2.6-47A248?logo=mongodb)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-4169E1?logo=postgresql)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.13-FF6600?logo=rabbitmq)
![Docker](https://img.shields.io/badge/Docker-27-2496ED?logo=docker)

Este repositorio contiene una plataforma de comercio electrónico basada en microservicios construida con Spring Boot 4.x y Java 25. El proyecto sigue una arquitectura moderna para una alta escalabilidad y modularidad.

## 🚀 Descripción General

El sistema está compuesto por varios servicios independientes que se comunican entre sí para gestionar productos, pedidos, inventario y notificaciones. Utiliza un patrón de descubrimiento de servicios y configuración centralizada.

### 🏗️ Arquitectura

Consulte el diagrama de Mermaid en `documentation/diagrams/ecommerce-ms.mmd` para una representación visual.

- **API Gateway**: Punto de entrada para todas las solicitudes de clientes (Puerto: 9000).
- **Descubrimiento de Servicios**: Servidor Eureka para el registro dinámico de servicios.
- **Servicio de Productos**: Gestiona el catálogo de productos (MongoDB).
- **Servicio de Pedidos**: Maneja los pedidos de los clientes (PostgreSQL).
- **Servicio de Inventario**: Rastrea los niveles de stock (MySQL).
- **Servicio de Notificaciones**: Envía alertas a través de RabbitMQ.
- **Infraestructura Transversal**: Servidor de Configuración, Keycloak (Autenticación) y gestores de mensajes.

---

## 🛠️ Stack Tecnológico

- **Lenguaje:** Java 25 (con Virtual Threads habilitados)
- **Framework:** Spring Boot 4.0.5, Spring Cloud 2025.1.1
- **Gestor de Paquetes:** Maven
- **Bases de Datos:** MongoDB, PostgreSQL, MySQL
- **Mensajería:** RabbitMQ
- **Contenerización:** Docker, Docker Compose
- **Otros:** Lombok, MapStruct

---

## 📁 Estructura del Proyecto

```text
.
├── discovery-server/      # Servidor de Descubrimiento Eureka
├── product-service/       # Microservicio de catálogo (MongoDB)
├── order-service/         # Microservicio de procesamiento de pedidos
├── inventory-service/     # Microservicio de gestión de inventario
├── notification-service/  # Microservicio de notificaciones orientado a eventos
├── documentation/         # Diagramas y documentos de diseño
└── README.md              # Documentación del proyecto
```

---

## ⚙️ Requisitos

Para ejecutar este proyecto, necesitas:

- **Java JDK 25**
- **Maven 3.9+**
- **Docker & Docker Compose**
- **IDE:** IntelliJ IDEA (recomendado) o VS Code

---

## 🚀 Configuración y Ejecución

### Ejecución con Docker (Ejemplo del Servicio de Productos)

1. Navega al directorio del servicio:
   ```bash
   cd product-service
   ```
2. Construye e inicia los contenedores:
   ```bash
   docker-compose up --build
   ```

### Ejecución Local (Desarrollo)

1. **Inicia primero el Servidor de Descubrimiento:**
   ```bash
   cd discovery-server
   ./mvnw spring-boot:run
   ```
2. **Inicia los demás servicios:**
   Abre una nueva terminal para cada servicio y ejecuta:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🔧 Variables de Entorno

Cada servicio puede ser configurado a través de variables de entorno. Los valores por defecto se proporcionan en `application.yaml`.

### Servicio de Productos
- `MONGO_HOST`: Host de MongoDB (por defecto: `localhost`)
- `MONGO_PORT`: Puerto de MongoDB (por defecto: `27017`)
- `MONGO_DATABASE`: Nombre de la base de datos (por defecto: `product-db`)
- `MONGO_USERNAME`: Usuario de MongoDB
- `MONGO_PASSWORD`: Contraseña de MongoDB
- `SERVER_PORT`: Puerto del servicio (por defecto: `8080`)

### TODO: Definir variables de entorno para otros servicios
- [ ] Servicio de Pedidos
- [ ] Servicio de Inventario
- [ ] Servicio de Notificaciones

---

## 📜 Scripts

La mayoría de los servicios incluyen el Maven Wrapper (`mvnw`). Utiliza estos comandos comunes:

- **Construir:** `./mvnw clean package`
- **Ejecutar:** `./mvnw spring-boot:run`
- **Probar:** `./mvnw test`
- **Buscar actualizaciones:** `./mvnw versions:display-dependency-updates` (si el plugin está configurado)

---

## 🧪 Pruebas

Las pruebas unitarias y de integración se encuentran en `src/test/java`.

- Para ejecutar todas las pruebas de un servicio:
  ```bash
  ./mvnw test
  ```
- El **Servicio de Productos** utiliza Testcontainers (configurado en `pom.xml` a través de `spring-boot-starter-data-mongodb-test`).

---

## ⚖️ Licencia

TODO: Agregar información de la licencia.
