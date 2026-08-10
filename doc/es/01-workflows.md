# Arquitectura de Workflows Reutilizables

[English](../en/01-workflows.md) | [Español](01-workflows.md)

---

## Resumen
Este documento describe la estructura y los pasos de ejecución de los workflows reutilizables de GitHub Actions alojados en `devops-templates`.

## `library-ci-cd.yml`

Este workflow estandariza el pipeline de integración continua y publicación para librerías Maven.

### Tareas y Flujo del Pipeline

1. **`pr-title` (Validación de Título de PR)**
   - Valida que los títulos de los Pull Requests cumplan con el estándar Conventional Commits utilizando `amannn/action-semantic-pull-request@v5`.
   - Publica comentarios con guías técnicas si la validación falla.

2. **`build-and-test` (Compilación, Pruebas y Cobertura JaCoCo)**
   - Compila el código Kotlin/Java bajo JDK 21.
   - Ejecuta las pruebas unitarias y calcula la cobertura de código.
   - Genera y publica una tabla resumen visual de cobertura JaCoCo en los PRs mediante `madrapps/jacoco-report`.

3. **`publish-snapshot` (Snapshot Dinámico por PR)**
   - Se activa al actualizar un PR.
   - Publica paquetes snapshot temporales `1.0.0-PR<NUMERO>-SNAPSHOT` en GitHub Packages para pruebas de integración.

4. **`semantic-release` (Release Automatizado)**
   - Se activa al hacer merge en `develop` o `main`.
   - **`develop`**: Genera versiones pre-release candidate (`v1.0.0-rc.1`) y publica paquetes pre-release.
   - **`main`**: Promueve la versión candidate a versión oficial de producción (`v1.0.0`), actualiza `CHANGELOG.md` y publica el paquete oficial.
