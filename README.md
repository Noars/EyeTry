# Description du projet

EyeTry est une application JavaFx qui permet de tester l'exactitude et la précision de différentes Eye Trackers.<br>
Ce projet est sous license GNU GPL3.

Readme mise à jour le 22 septembre 2023.

# Structure du projet

## SDK

- SDK Azul Zulu Community 17.0.8+7
- JavaFx 20.0.2+100
- Language level 17 (Celui par défaut du SDK - Types scellés, sémantique à virgule flottante toujours stricte)

Date de la release -> Août 2023

## Gradle

- Gradle version 8.3 -> release le 17 Août 2023

### Gradle Plugins

- "org.openjfx.javafxplugin" version "0.1.0" -> release le 04 Septembre 2023
- "de.undercouch.download" version "5.5.0" -> release le 27 Août 2023
- "io.freefair.lombok" version "8.3" -> release le 28 Août 2023
- "com.github.spotbugs" version "5.1.3" -> release le 15 Août 2023
- "com.github.hierynomus.license-report" version "0.16.1" -> release le 04 Mai 2021
- "net.researchgate.release" version "3.0.2" -> release le 09 Septembre 2022

### Dependencies

- "com.github.GazePlay:TobiiStreamEngineForJava" version "5.0" -> release le 20 Mars 2020
- "org.projectlombok:lombok" version "1.18.30" -> release le 20 Septembre 2023
- "org.slf4j:slf4j-simple" version "2.0.9" -> release le 03 Septembre 2023
- "org.json:json" version "20230618" -> release le 18 Juin 2023
- "com.google.code.gson:gson" version "2.10.1" -> release le 06 Janvier 2023

## GitHub Action

- actions/checkout@v4 -> version actuelle 4.0.0
- actions/setup-java@v3 -> version actuelle 3.12.0
- actions/upload-artifact@v3 -> version actuelle 3.1.3
- ncipollo/release-action@v1 -> version actuelle 1.13.0
