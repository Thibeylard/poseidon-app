[![shields](https://img.shields.io/badge/project%20status-validated-deepgreen)](https://shields.io/)
[![shields](https://img.shields.io/badge/made%20with-java-orange)](https://shields.io/)
[![shields](https://img.shields.io/badge/powered%20by-spring-green)](https://shields.io/)
____________________

> Ce README est basé sur les conclusions évoquées dans la présentation réalisée à la fin du projet.

# Transformez votre backend en API pour rendre votre application plus flexible

## Dans le cadre de la formation OpenClassrooms "Développeur d'application Java"

### Objectif du projet
À partir d'un projet existant, implémenter une API REST sécurisée.

### Progression
Ce projet m'a permis de pratiquer le développement de l'architecture moderne API REST, notamment à l'aide de Swagger. J'ai exploré encore un peu plus le framework Spring, avec une deuxième utilisation de Spring Security, et la découverte de Spring Data JPA, Spring Data Rest, Thymeleaf, et de la validation Hibernate, que je n'avais pas encore exploités. Plusieurs difficultés à gérer les dépendances ont aussi développé mes compétences à l'investigation des erreurs infra.

### Réalisation
Les objectifs du projets ont été accomplis avec les technologies demandées, et plus encore :
* Génération des API avec Swagger.io
* Deux type d'implémentation parallèles des controllers avec Spring MVC ou Spring Data JPA sur des base path différent ('/html' et '/api') 
* Utilisation de Spring Data Rest pour éviter la redondance de code
* Des validateurs et des annotations personnalisés, avec des DTO dédiés
* Emploi de FlywayDB avec H2 pour les migrations de la base de donnée et maîtriser les tests
* Utilisation d'aspects autour des controllers

### Axes d'améliorations
Les logs sont peu précis sur Spring Data Rest : il y aurait possibilité de développer un aspect sur les classes générées par Spring. Par ailleurs, UserRepository devrait avoir un accès plus limité pour éviter les modifications illégales. @PreAuthorize est une piste, mais elle empêche pour le moment les utilisateurs de se connecter.