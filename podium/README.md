# Podium - Frontend Angular

Podium es una aplicación web moderna construida con **Angular 17+** (usando Standalone Components). Es la interfaz gráfica compañera del backend Spring Boot "TuApp" y se encarga de proveer a los usuarios de una experiencia visual e interactiva para la gestión de sus finanzas personales y el procesamiento de sus pedidos.

## 🚀 Características y Módulos

1. **Autenticación (JWT):**
   - El proyecto cuenta con un interceptor HTTP (`JwtInterceptor`) que de forma automática inyecta el token en la cabecera `Authorization`.
   - Se dispone de un `AuthGuard` basado en funciones para proteger el acceso a las rutas `/dashboard`, `/facturas`, `/gastos`, y `/pedidos`.
   - Lógica de autenticación centralizada en `AuthService`.

2. **Dashboard de Finanzas:**
   - La vista principal utiliza **Chart.js** y **ng2-charts** para renderizar gráficos de los ingresos (facturas), egresos (gastos) e inversiones.

3. **Módulos Funcionales (CRUD):**
   - Tablas y formularios impulsados por **Angular Material**.
   - Se incluyen vistas para interactuar con las rutas `/api/facturas` y `/api/gastos`.

4. **Sistema de Pedidos Asíncrono:**
   - Formularios para solicitar pedidos al backend.
   - Polling/Actualización para evidenciar cuando RabbitMQ procesa el estado y este cambia de `PENDIENTE` a `PROCESADO`.

5. **UI / UX (Podium Branding):**
   - El tema está desarrollado utilizando **Angular Material** con colores base índigo y rosa, definidos globalmente en `src/styles.scss`.
   - Componentes estructurales (`Navbar`, `Footer`) y diseño responsivo estándar.

## 🛠 Requisitos Previos

- Node.js (v18 o superior recomendado)
- NPM

## ⚙️ Instalación y Configuración

1. Moverse al directorio del frontend:
   ```bash
   cd /Users/zodde/Desktop/javaecositem/podium
   ```

2. Instalar dependencias (ya deben estar instaladas si usaste el CLI, pero por si acaso):
   ```bash
   npm install
   ```

3. Asegurarse que el backend de Spring Boot está corriendo en `http://localhost:8080`. Puedes verificar las variables de entorno para los llamados REST en `src/environments/environment.ts`.

4. Ejecutar el servidor de desarrollo:
   ```bash
   npm run start
   ```
   *El proyecto estará disponible en `http://localhost:4200`.*

## 📦 Despliegue y Repositorio

Este entorno ha sido inicializado sin repositorio de Git inicialmente. Para subir el código a GitHub y continuar el desarrollo colaborativo:

```bash
git init
git add .
git commit -m "feat: Initial Podium Frontend setup with Angular 17"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/podium-frontend.git
git push -u origin main
```
