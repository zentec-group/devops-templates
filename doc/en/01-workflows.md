# Reusable Workflows Architecture

[English](01-workflows.md) | [Español](../es/01-workflows.md)

---

## Overview
This document describes the structure and execution steps of the reusable GitHub Actions workflows hosted in `devops-templates`.

## `library-ci-cd.yml`

This workflow standardizes the continuous integration and release pipeline for Maven libraries.

### Jobs & Pipeline Flow

1. **`pr-title` (PR Title Validation)**
   - Validates that Pull Request titles adhere to Conventional Commits standard using `amannn/action-semantic-pull-request@v5`.
   - Posts technical guidance comments if validation fails.

2. **`build-and-test` (Build, Test & JaCoCo Coverage)**
   - Compiles Kotlin/Java code under JDK 21.
   - Runs unit tests and computes code coverage.
   - Generates and posts a visual JaCoCo coverage summary table on PRs via `madrapps/jacoco-report`.

3. **`publish-snapshot` (Dynamic PR Snapshot)**
   - Triggers on PR updates.
   - Publishes temporary snapshot packages `1.0.0-PR<NUMBER>-SNAPSHOT` to GitHub Packages for integration testing.

4. **`semantic-release` (Automated Release)**
   - Triggers on merges to `develop` or `main`.
   - **`develop`**: Generates release candidate versions (`v1.0.0-rc.1`) and publishes pre-release packages.
   - **`main`**: Promotes release candidate to official production version (`v1.0.0`), updates `CHANGELOG.md`, and publishes release package.
