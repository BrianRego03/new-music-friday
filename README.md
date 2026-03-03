🎵 New Music Friday

A production-style, Dockerized REST API built with Java and Spring Boot that allows users to subscribe to their favorite artists and receive weekly updates of newly released music every Friday.

The application integrates with the Spotify API, persists release data in PostgreSQL, and automatically refreshes artist data using scheduled cron jobs.

🚀 Overview

New Music Friday is a backend-only REST API designed to demonstrate:

Secure JWT-based authentication

Third-party API integration (Spotify)

Scheduled background jobs

Clean layered architecture

Production-ready logging

Database versioning with Flyway

CI/CD deployment pipeline

Dockerized infrastructure


🏗 Architecture

The application follows a clean layered architecture:

Controller Layer
→ Service Layer
→ Persistence Layer (Hibernate/JPA)
→ PostgreSQL Database

External Integration:

Spotify API (artist & release data)

Infrastructure:

Docker

GitHub Actions CI/CD

VPS deployment

🛠 Tech Stack
Backend

Java 17+

Spring Boot

Spring Security

Hibernate / JPA

Maven

Database

PostgreSQL

FlywayDB (schema versioning & migrations)

DevOps

Docker & Docker Compose

GitHub Actions (CI/CD pipeline)

VPS Deployment

External APIs

Spotify Web API (music & release data)

