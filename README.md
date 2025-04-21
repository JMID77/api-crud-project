# Clean Architecture Spring Boot – API Java

Ce projet implémente une API REST en Java avec Spring Boot, en suivant les principes de la **Clean Architecture**. Il vise à garantir une séparation stricte des responsabilités, une testabilité accrue et une bonne maintenabilité du code.

---

## 🗂️ Structure du projet

Le code source est organisé selon quatre couches principales :

```
src/
├── application/        # Logique métier applicative (DTOs, Services, Mappers)
├── domain/             # Entités métier pures et interfaces (Ports)
├── infrastructure/     # Configuration Spring, persistance, exceptions
└── interaction/        # Contrôleurs REST (Entrées utilisateur)
```

### 📦 Détail des sous-packages

- `application.dto` : Objets de transfert (Request/Response), avec annotations de validation.  
- `application.mapper` : Conversion entre entités métier et DTOs.  
- `application.service` : Cas d’usage et logique applicative.  
- `domain.entity` : Modèles métier purs (POJOs sans dépendance Spring).  
- `domain.repository` : Interfaces représentant les ports de persistance.  
- `infrastructure.persistence` : Adaptateurs techniques pour accéder aux données.  
- `infrastructure.config` : Configuration Spring Boot.  
- `infrastructure.exception` : Gestion des exceptions centralisée.  
- `interaction.controller` : Endpoints REST via Spring MVC.  

---

## ✨ Exemple de DTO

```java
public record UserRequest(
    @NotBlank(message = "{username.notblank.message}")
    @Size(min = 3, max = 50, message = "{username.size.message}")
    String userName,

    @NotBlank(message = "{email.notblank.message}")
    @Email(message = "{email.size.message}")
    String email,

    @NotBlank(message = "{password.notblank.message}")
    @Size(min = 6, message = "{password.size.message}")
    String password
) {}
```

```java
public record UserResponse(Long id, String userName, String email) {}
```

---

## ▶️ Lancer le projet

### ✅ Prérequis

- Java 17+  
- Maven 3.8+  
- Docker (optionnel pour la base de données)  

### 🚀 Commandes

```bash
# Compilation + tests
mvn clean install

# Lancement de l'application
mvn spring-boot:run
```

---

## 🎯 Objectifs pédagogiques

- Appliquer les principes de Clean Architecture avec Spring Boot  
- Isoler les dépendances techniques du cœur métier  
- Faciliter les tests, l’évolution et la lisibilité du code  

---

## 📚 Références

- [Clean Architecture - Robert C. Martin](https://www.oreilly.com/library/view/clean-architecture/9780134494272/)  
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)

## 📘 Documentation complémentaire

Pour plus de détails sur l’architecture, les choix techniques et l’évolution du projet, consulte le wiki associé :

➡️ [Schéma global du projet – Notion](https://polite-payment-f71.notion.site/Sch-ma-global-du-projet-1d59b8a90bd9807fa706d5d9c579ad33)
