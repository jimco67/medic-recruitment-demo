# Plateforme de Recrutement Médical (Démo DDD)

Ce projet est une application web full-stack servant de démonstration pour une plateforme de recrutement médical. Elle est conçue en suivant les principes du **Domain-Driven Design (DDD)** pour garantir une architecture modulaire, évolutive et alignée sur le métier.

L'application est composée d'un backend en Java/Spring Boot et d'un frontend en Angular, communiquant via une API REST. Elle intègre également **Apache Kafka** pour gérer la communication asynchrone entre les services via une architecture événementielle.

## Concepts Architecturaux

- **Domain-Driven Design (DDD)** : Le code est organisé par domaines métier (ex: `job`), avec une séparation claire entre les couches `domain`, `application` (use cases), et `infrastructure`.
- **Architecture Événementielle** : Des événements, comme la création d'une offre d'emploi (`CreateJobEvent`), sont publiés sur des topics Kafka pour découpler les composants du système.
- **API REST** : Le backend expose des points d'accès REST pour que le frontend puisse interagir avec l'application.
- **Découplage Frontend/Backend** : Le frontend (Angular) est une Single Page Application (SPA) complètement indépendante qui consomme les services du backend.

## Stack Technologique

- **Backend**:
  - Java 17+
  - Spring Boot
  - Apache Maven (avec wrapper `mvnw`)
  - Apache Kafka (pour la messagerie)
- **Frontend**:
  - Angular
  - TypeScript
  - SCSS
- **Tests**:
  - JUnit 5
  - Cucumber (pour les tests BDD)
  - Testcontainers (pour les tests d'intégration)
- **Conteneurisation**:
  - Docker
  - Docker Compose

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les outils suivants :
- [Git](https://git-scm.com/)
- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou une version supérieure
- [Node.js et npm](https://nodejs.org/) (pour le frontend)
- [Docker et Docker Compose](https://www.docker.com/products/docker-desktop/)

## Démarrage Rapide (Recommandé)

La manière la plus simple de lancer l'ensemble de l'application (backend, frontend, Kafka) est d'utiliser Docker Compose.

1.  **Clonez le dépôt :**
    ```bash
    git clone <URL_DU_REPO>
    cd medic-recruitment-demo-DDD
    ```

2.  **Lancez l'application avec Docker Compose :**
    ```bash
    docker-compose up --build
    ```
    Cette commande va construire les images Docker pour le backend et le frontend, et démarrer tous les services nécessaires.

3.  **Accédez à l'application :**
    - Frontend : [http://localhost:4200](http://localhost:4200)
    - Backend API : [http://localhost:8080](http://localhost:8080)

## Développement Local

Si vous préférez lancer les services manuellement pour le développement.

### Lancer le Backend

1.  **Démarrez Kafka :**
    Assurez-vous d'avoir une instance de Kafka accessible. Vous pouvez utiliser le `docker-compose.yml` pour ne démarrer que Kafka :
    ```bash
    docker-compose up -d kafka
    ```

2.  **Lancez l'application Spring Boot :**
    Depuis la racine du projet, exécutez la commande suivante :
    ```bash
    ./mvnw spring-boot:run
    ```

### Lancer le Frontend

1.  **Naviguez vers le dossier du frontend :**
    ```bash
    cd frontend
    ```

2.  **Installez les dépendances :**
    ```bash
    npm install
    ```

3.  **Démarrez le serveur de développement Angular :**
    ```bash
    npm start
    ```
    L'application sera disponible sur [http://localhost:4200](http://localhost:4200).

## Exécuter les Tests

Pour lancer la suite de tests du backend (unitaires et intégration), utilisez la commande Maven :

```bash
./mvnw clean test
```
