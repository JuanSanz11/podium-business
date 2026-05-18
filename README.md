# 🏆 Podium & TuApp - Ecosistema de Control Financiero y Pedidos Asíncronos

Este repositorio contiene la integración completa de una solución de software de nivel profesional ("Full-Stack") estructurada en dos grandes proyectos independientes: un robusto servidor Backend en **Java (Spring Boot)** y una interfaz web Frontend interactiva y moderna en **Angular 17+ (Podium)**.

El sistema permite la administración de finanzas personales (ingresos, egresos, deudas y ahorros), generación de reportes automáticos con gráficos interactivos, y cuenta con un sistema de procesamiento de pedidos asíncrono utilizando mensajería distribuida con **RabbitMQ**.

<img width="1203" height="638" alt="Captura de Tela 2026-05-18 às 02 29 13" src="https://github.com/user-attachments/assets/9f5ba657-847a-45d3-a356-22858dae7481" />

---

## 📂 Estructura General del Proyecto

El ecosistema está dividido de la siguiente forma en tu workspace:
```bash
javaecositem/
├── tuapp/        # Backend Server & Docker Infrastructure (Java Spring Boot 3)
└── podium/       # Client Web Application (Angular 17+ & Angular Material)
```

---

## 🚀 Tecnologías y Arquitecturas Utilizadas

### 💻 Backend: `tuapp` (Spring Boot 3)
Construido bajo una arquitectura limpia y desacoplada en capas (Controladores, Servicios, Repositorios, DTOs y Mappers).
*   **Java 17** & **Spring Boot 3.2.5**
*   **Spring Security & JWT (JSON Web Tokens)**: Implementación de seguridad sin estado (*stateless*) con interceptor y filtro personalizado para autorizar recursos de forma segura.
  
<img width="464" height="508" alt="Captura de Tela 2026-05-18 às 02 30 25" src="https://github.com/user-attachments/assets/040189a4-07fd-4549-b74f-36b700897015" />

*   **Spring Data JPA / Hibernate**: Persistencia y mapeo objeto-relacional automático hacia la base de datos.
*   **PostgreSQL**: Base de datos relacional para guardar usuarios, facturas, gastos, deudas y pedidos.
*   **RabbitMQ**: Middleware de mensajería asíncrona para la publicación y el consumo desacoplado del procesamiento de pedidos en segundo plano.
*   **Lombok**: Reducción drástica de código repetitivo (Getters, Setters, Builders, etc.).
*   **MapStruct**: Mapeo eficiente y estructurado de Entidades a DTOs.
*   **Docker & Docker Compose**: Orquestación y empaquetamiento total del entorno local (PostgreSQL, RabbitMQ, app compilada).
*   **OpenAPI 3 / Swagger**: Documentación automatizada de toda la API en `/swagger-ui.html`.

### 🎨 Frontend: `podium` (Angular 17+)
Diseñado bajo las mejores prácticas modernas de Angular con un branding interactivo y creativo llamado **Podium**.
*   **Angular 17+ (Componentes Standalone)**: Estructura ligera y modular sin necesidad de `NgModule`.
*   **Angular Material**: Interfaz responsiva y pulida con componentes como Tablas de datos, Formularios reactivos, Diálogos, Desplegables, etc.
*   **Chart.js / ng2-charts**: Visualizaciones y gráficas dinámicas de donas (Doughnut) que leen el balance financiero del usuario.
*   **Interceptor HTTP de JWT**: Inyección automática del header `Authorization: Bearer <token>` en todas las peticiones salientes.
*   **Guards de Ruta**: Bloqueo de navegación a páginas del panel si el usuario no cuenta con un token JWT válido.
*   **RxJS (forkJoin, etc.)**: Programación reactiva para cargar reportes complejos de la API de forma concurrente.
*   **Polling en tiempo real**: Monitoreo dinámico del estado de los pedidos que son consumidos y procesados a través de RabbitMQ.

---

## ⚙️ Guía de Inicio Rápido

Para levantar el ecosistema completo en tu máquina, sigue estos dos sencillos pasos:

### 1. Iniciar el Servidor y la Infraestructura (Docker)
1. Abre tu terminal y ve a la carpeta del backend:
   ```bash
   cd tuapp
   ```
2. Levanta PostgreSQL, RabbitMQ y el Servidor compilado en Spring Boot:
   ```bash
   docker-compose up --build -d
   ```
3. *Opcional:* Puedes monitorear los logs del backend usando:
   ```bash
   docker logs -f tuapp-service
   ```
4. **Verificar API**: Entra a [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) para explorar los endpoints disponibles.
5. **Verificar RabbitMQ**: Ingresa a [http://localhost:15672](http://localhost:15672) (Usuario/Clave: `guest`/`guest`) para ver las colas activas.

### 2. Iniciar el Interfaz Web (Angular)
1. Abre una **nueva terminal** y ve a la carpeta de Angular:
   ```bash
   cd podium
   ```
2. Ejecuta el servidor de desarrollo en caliente:
   ```bash
   npm run start
   ```
3. Abre tu navegador en **[http://localhost:4200](http://localhost:4200)**. El sistema te dirigirá al login interactivo sin mostrar cabeceras. ¡Regístrate para empezar a usar Podium!

---

## 🧹 Limpieza del Código & Mejoras

Durante la integración y despliegue del proyecto, se aplicaron las siguientes medidas de optimización:
*   **Eliminación de dependencias circulares**: Se resolvió el ciclo de inyección que impedía a Spring Boot levantar el contexto en Docker al independizar el `JwtFilter` de `UserDetailsService` (inyectando `UserRepository` directamente).
*   **Corrección de CORS**: Se configuraron filtros CORS explícitos en Spring Security para evitar bloqueos del navegador hacia peticiones desde el puerto de Angular (`4200`).
*   **Limpieza de código Angular**: Se eliminaron selectores o HTMLs de prueba genéricos ("works!"), asegurando imports estrictos en los componentes Standalone para mantener el bundle final lo más ligero y limpio posible.
*   **Branding Moderno**: Ocultación dinámica del Navbar y del Footer en la pantalla de autenticación y registro para brindar una experiencia de usuario limpia y profesional.

---

*Desarrollado y optimizado con 💙 para Juan Sanz.*
