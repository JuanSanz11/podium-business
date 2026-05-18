# TuApp - Backend Spring Boot

Este es un proyecto backend completo construido en Java con **Spring Boot 3**, diseñado para gestionar finanzas personales y un sistema de procesamiento asincrónico de pedidos.

El proyecto está dockerizado e incluye integración con bases de datos relacionales, mensajería, seguridad con JSON Web Tokens (JWT) y documentación automática de la API.

## 🚀 Características Principales

1. **Autenticación y Seguridad (JWT)**
   - Sistema de Login y Registro de usuarios usando **Spring Security**.
   - Generación y validación de JSON Web Tokens (JWT).
   - Endpoints protegidos, donde la entidad principal (`User`) está asociada a roles (`USER`, `ADMIN`).

2. **Control Financiero Personal**
   - **Facturas y Gastos**: Operaciones CRUD completas para la gestión financiera.
   - **Ahorros e Inversiones**: Clasificación de gastos mediante banderas especiales para identificar fondos destinados al ahorro o la inversión.
   - **Reportes Financieros**: Endpoint que proporciona un balance general consolidado agrupando los ingresos (basados en facturas), gastos totales por categoría y margen de ahorro.

3. **Sistema de Pedidos con RabbitMQ**
   - Integración nativa con **RabbitMQ** para mensajería asincrónica.
   - Cuando se genera un pedido (`POST /api/pedidos`), el controlador lo almacena y lanza un evento a una cola de RabbitMQ.
   - Un `RabbitListener` dedicado se encarga de escuchar los mensajes de la cola, simula un procesamiento en background y luego actualiza automáticamente el estado del pedido a `PROCESADO`.

4. **Documentación Automática**
   - La API se encuentra plenamente documentada a través de **Swagger UI** / **OpenAPI 3**.

## 🛠 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.5** (Web, Data JPA, Security, AMQP, Validation)
- **PostgreSQL 15** (Base de datos relacional)
- **RabbitMQ** (Message Broker)
- **MapStruct** y **Lombok** (Para el mapeo fluido de DTOs y reducción de código boilerplate)
- **Docker & Docker Compose** (Para la orquestación e infraestructura local)

## 🏗 Arquitectura del Proyecto

El código sigue un patrón limpio multicapa, estructurado de la siguiente forma:

```text
src/main/java/com/tuapp
 ├── config       # Configuraciones de Seguridad (JWT), RabbitMQ y Swagger
 ├── controller   # Controladores REST expuestos
 ├── dto          # Data Transfer Objects (Request y Response payloads)
 ├── entity       # Entidades JPA (User, Role, Factura, Gasto, Pedido)
 ├── exception    # Manejo global de excepciones (@ControllerAdvice)
 ├── mapper       # Interfaces de MapStruct para transformación DTO <-> Entity
 ├── repository   # Repositorios de Spring Data JPA
 ├── service      # Lógica de negocio y Listeners de RabbitMQ
 └── Application  # Clase principal de Spring Boot
```

## 🐳 Ejecución con Docker Compose

El proyecto está diseñado para funcionar "Out of the Box" usando Docker. No es necesario que tengas Maven, Java, Postgres ni RabbitMQ instalados de forma local, el `docker-compose` hace todo el trabajo mediante el sistema de **multi-stage build**.

1. Asegúrate de tener **Docker** y **Docker Compose** instalados en tu máquina.
2. Posiciónate en la carpeta raíz del proyecto y ejecuta el siguiente comando:

```bash
docker-compose up --build -d
```

Este comando:
- Construirá el `.jar` de la aplicación de forma aislada.
- Levantará un contenedor con **PostgreSQL**.
- Levantará un contenedor con **RabbitMQ**.
- Desplegará la aplicación Spring Boot exponiéndola en el puerto **8080**.

### Detener los servicios

```bash
docker-compose down
```

## 📚 Documentación de la API (Swagger)

Una vez que la aplicación esté corriendo, podrás explorar y probar directamente todos los endpoints desde tu navegador web:

🔗 **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Panel de Administración de RabbitMQ

🔗 **RabbitMQ UI**: [http://localhost:15672](http://localhost:15672)  
- **Usuario**: `guest`
- **Contraseña**: `guest`

## 🔐 Flujo de Pruebas Básico

1. Ve a `/api/auth/register` (vía Swagger o Postman) y crea un nuevo usuario.
2. Ve a `/api/auth/login` enviando las credenciales del usuario recién creado para obtener tu **JWT Token**.
3. Añade el Token en la cabecera `Authorization` con el formato `Bearer {tu_token}`. (Si usas Swagger, presiona el botón "Authorize" en la parte superior y pega el token).
4. Crea nuevas facturas y gastos para alimentar tu base de datos.
5. Llama a `GET /api/gastos/reporte` para observar tu consolidado financiero.
6. Crea un pedido en `POST /api/pedidos`, espera un par de segundos y consúltalo nuevamente (`GET /api/pedidos`); verás que su estado cambió de `PENDIENTE` a `PROCESADO` gracias al worker de RabbitMQ.

## 📦 Puesta en Producción en GitHub

Este repositorio ya se encuentra inicializado con `git` y un archivo `.gitignore` adaptado. Para subirlo a tu cuenta de GitHub, sólo debes correr los siguientes comandos:

```bash
git remote add origin https://github.com/TU_USUARIO/TU_REPOSITORIO.git
git branch -M main
git push -u origin main
```
