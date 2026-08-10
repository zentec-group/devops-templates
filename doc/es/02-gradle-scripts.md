# Guía de Scripts Compartidos de Gradle

[English](../en/02-gradle-scripts.md) | [Español](02-gradle-scripts.md)

---

## Resumen
Los scripts compartidos de Gradle están alojados en la raíz de `devops-templates` y se aplican dinámicamente mediante URL HTTP en los proyectos destino.

## Descripción de Scripts

### 1. `jacoco.gradle.kts`
- Aplica el plugin de JaCoCo.
- Configura las exclusiones por defecto (`dto`, `models`, `entities`, `config`, `exceptions`, `enums`, `support`, `migration`, `seeder`).
- Configura las dependencias de tareas JaCoCo y reglas de verificación de cobertura.

### 2. `library-publish.gradle.kts`
- Aplica el plugin `maven-publish`.
- Configura la publicación en el repositorio GPR (`https://maven.pkg.github.com/zentec-group/<repo>`).
- Configura la metadata del POM y la resolución de credenciales.

### 3. `client-deps.gradle.kts`
- Script de utilidad para configurar las credenciales de autenticación GPR a partir de propiedades del proyecto o variables de entorno (`ZENTEC_GPR_TOKEN`, `GPR_TOKEN`).
