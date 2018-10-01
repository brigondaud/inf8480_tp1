# INF8480 - TP1 - Appels de méthodes à distance

Ce README détaille les étapes pour pouvoir exécuter la partie 2 du TP qui porte sur le système de fichier à distance.

## Prise en main

### Prérequis

Installer [Apache Ant](https://ant.apache.org/).

### Installation

Pour compiler le projet et les exécutables pour lancer le serveur d'authentification, le serveur de fichier et le client:
```
ant
```

## Exécution des tests

Pour exécuter les tests:
```
ant test
```

## Utilisation

Avancer de pouvoir lancer les serveurs, lancer Java RMI:
```
registry -J-Djava.rmi.server.codebase=file:shared.jar &
```

Dans un premier temps, lancer le serveur d'authentification:
```
./authentication.sh
```

Puis lancer le serveur de fichier:
```
./server.sh
```

Pour utiliser le client:
```
./client.sh <command> [options...]
```

### Arborescence générée

Le serveur d'authentification génère un fichier de méta-données pour pouvoir sauvegarder les identifiants générés par les clients:

    .
    ├── authFiles
    │   ├── auth.metadata   # Sauvegarde des identifiants

Le serveur de fichier génère un fichier de méta-données pour sauvegarder les verrous, ainsi qu'un dossier contenant le système de fichiers:

    .
    ├── server.metadata
    ├── serverFiles
    │   ├── ...     # Les fichiers sont stockés ici

Le client génère un fichier de méta-données pour stocker les derniers identifiants créés, ainsi qu'un dossier pour le système de fichiers:

    .
    ├── .credentials    # Identifiants générés
    ├── clientFiles
    │   ├── ...         # Les fichiers sont stockés ici

## Développé avec

* [Apache Ant](https://ant.apache.org/)
* [JUnit](https://junit.org/junit4/)

## Auteurs

* **Baptiste Rigondaud (1973586)**
* **Loïc Poncet (1973621)**

