# Shared Gradle Scripts Guide

[English](02-gradle-scripts.md) | [Español](../es/02-gradle-scripts.md)

---

## Overview
Shared Gradle scripts are hosted at root level in `devops-templates` and are applied dynamically via HTTP URL in target projects.

## Scripts Description

### 1. `jacoco.gradle.kts`
- Applies JaCoCo plugin.
- Configures default exclusions (`dto`, `models`, `entities`, `config`, `exceptions`, `enums`, `support`, `migration`, `seeder`).
- Configures JaCoCo task dependencies and coverage verification rules.

### 2. `library-publish.gradle.kts`
- Applies `maven-publish` plugin.
- Configures GPR repository publishing (`https://maven.pkg.github.com/zentec-group/<repo>`).
- Configures pom metadata and credential resolution.

### 3. `client-deps.gradle.kts`
- Utility helper to configure GPR authentication credentials from project properties or environment variables (`ZENTEC_GPR_TOKEN`, `GPR_TOKEN`).
