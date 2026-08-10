# devops-templates

[English](README.md) | [Español](README.es.md)

---

## Descripción
`devops-templates` es el repositorio centralizado de DevOps para la plataforma Zentec (`zentec-group`). Alberga workflows reutilizables de GitHub Actions y scripts compartidos de Gradle para estandarizar la compilación, pruebas, cobertura de código, publicación de artefactos y automatización de releases en todas las librerías y microservicios.

## Componentes de Infraestructura Compartidos

### 1. Workflows Reutilizables de GitHub Actions (`.github/workflows/`)
- `library-ci-cd.yml`: Pipeline reutilizable para librerías Maven. Incluye validación de títulos semánticos de PR, compilación y pruebas en JDK 21, reporte de cobertura de código JaCoCo, publicación dinámica de paquetes snapshot por PR, y Semantic Release automatizado para las ramas `develop` y `main`.

### 2. Scripts Compartidos de Gradle (`.`)
- `jacoco.gradle.kts`: Configura el plugin de cobertura JaCoCo, dependencias de tareas, exclusiones y reglas de verificación.
- `library-publish.gradle.kts`: Configura el plugin Maven Publish para la publicación en GitHub Packages (`maven.pkg.github.com/zentec-group`).
- `client-deps.gradle.kts`: Script de utilidad para resolver credenciales del repositorio GPR entre proyectos.

## Integración en Librerías

Para integrar este pipeline en cualquier librería de Zentec:

### 1. Configurar `.github/workflows/ci-cd.yml` en la librería destino:
```yaml
name: CI/CD Pipeline

on:
  pull_request:
    types: [opened, synchronize, reopened, edited]
  push:
    branches: [develop, main]

permissions:
  contents: write
  packages: write
  pull-requests: write
  statuses: write

jobs:
  library-pipeline:
    uses: zentec-group/devops-templates/.github/workflows/library-ci-cd.yml@main
    secrets: inherit
```

### 2. Aplicar scripts compartidos en `build.gradle.kts`:
```kotlin
apply(from = "https://raw.githubusercontent.com/zentec-group/devops-templates/main/jacoco.gradle.kts")
apply(from = "https://raw.githubusercontent.com/zentec-group/devops-templates/main/library-publish.gradle.kts")
```

## Documentación Detallada
- [01-workflows.md](doc/es/01-workflows.md): Arquitectura de workflows reutilizables y opciones de configuración.
- [02-gradle-scripts.md](doc/es/02-gradle-scripts.md): Scripts compartidos de Gradle y reglas de publicación.
