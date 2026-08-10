# devops-templates

[English](README.md) | [Español](README.es.md)

---

## Description
`devops-templates` is the centralized DevOps repository for the Zentec platform (`zentec-group`). It hosts reusable GitHub Actions CI/CD workflows and shared Gradle scripts to standardize build, testing, code coverage, artifact publishing, and release automation across all libraries and microservices.

## Shared Infrastructure Components

### 1. Reusable GitHub Actions Workflows (`.github/workflows/`)
- `library-ci-cd.yml`: Reusable CI/CD pipeline for Maven libraries. Features PR title validation, JDK 21 build & test, JaCoCo code coverage reporting, dynamic PR snapshot publishing, and automated Semantic Release for `develop` and `main` branches.

### 2. Shared Gradle Scripts (`.`)
- `jacoco.gradle.kts`: Configures JaCoCo code coverage plugin, task dependencies, exclusions, and verification rules.
- `library-publish.gradle.kts`: Configures Maven Publish plugin for GitHub Packages publishing (`maven.pkg.github.com/zentec-group`).
- `client-deps.gradle.kts`: Utility script for resolving GPR repository credentials across projects.

## Integration in Libraries

To integrate this pipeline in any Zentec library:

### 1. Configure `.github/workflows/ci-cd.yml` in target library:
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

### 2. Apply shared scripts in `build.gradle.kts`:
```kotlin
apply(from = "https://raw.githubusercontent.com/zentec-group/devops-templates/main/jacoco.gradle.kts")
apply(from = "https://raw.githubusercontent.com/zentec-group/devops-templates/main/library-publish.gradle.kts")
```

## Documentation
- [01-workflows.md](doc/en/01-workflows.md): Reusable workflow architecture and configuration options.
- [02-gradle-scripts.md](doc/en/02-gradle-scripts.md): Shared Gradle scripts and publication rules.
