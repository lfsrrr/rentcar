# Hinweise zum Programmierbeispiel

<!--
  Copyright (C) 2020 - present Juergen Zimmermann, Hochschule Karlsruhe

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->

[Juergen Zimmermann](mailto:Juergen.Zimmermann@h-ka.de)

> Diese Datei ist in Markdown geschrieben und kann z.B. mit IntelliJ IDEA
> gelesen werden. Näheres zu Markdown gibt es z.B. bei
> [Markdown Guide](https://www.markdownguide.org) oder
> [JetBrains](https://www.jetbrains.com/help/hub/Markdown-Syntax.html)

Inhalt

- [Powershell bei Windows](#powershell-bei-windows)
- [Speichereinstellung für Gradle](#speichereinstellung-für-gradle)
- [Postman](#postman)
  - [Registrieren und Installieren](#registrieren-und-installieren)
  - [Workspace anlegen](#workspace-anlegen)
  - [Environments](#environments)
  - [Collections und Folders](#collections-und-folders)
  - [Requests](#requests)
  - [Variable](#variable)
- [Übersetzung und lokale Ausführung](#übersetzung-und-lokale-ausführung)
  - [Ausführung in IntelliJ IDEA](#ausführung-in-intellij-idea)
  - [Kommandozeile](#kommandozeile)
  - [Port 8080 oder 8443](#port-8080-oder-8443)
  - [Aufrufe mit Postman oder mit der (Power-) Shell](#aufrufe-mit-postman-oder-mit-der-power--shell)
  - [OpenAPI-mit-Swagger](#openapi-mit-swagger)
  - [Liveness und Readiness](#liveness-und-readiness)
- [Image, Container und Docker Dashboard](#image-container-und-docker-dashboard)
  - [Docker-Daemon und Benutzerkennung](#docker-daemon-und-benutzerkennung)
  - [Image erstellen mit Dockerfile](#image-erstellen-mit-dockerfile)
  - [Cloud Native Buildpacks durch Spring Boot](#cloud-native-buildpacks-durch-spring-boot)
  - [Image inspizieren](#image-inspizieren)
  - [Container starten](#container-starten)
  - [Docker Compose](#docker-compose)
  - [Image kopieren](#image-kopieren)
- [Kubernetes, Helm und Terraform](#kubernetes-helm-und-terraform)
  - [WICHTIG: Schreibrechte für die Log-Datei](#wichtig-schreibrechte-für-die-logdatei)
  - [Rechnername in der Datei hosts](#rechnername-in-der-datei-hosts)
  - [CLI durch kubectl](#cli-durch-kubectl)
  - [Eigener Namespace und Defaults](#eigener-namespace-und-defaults)
  - [Image für Kubernetes erstellen](#image-für-kubernetes-erstellen)
  - [Installation in Kubernetes: Deployment, Service und Configmap](#installation-in-kubernetes-deployment-service-und-configmap)
  - [Deinstallieren des Microservice](#deinstallieren-des-microservice)
  - [Helm als Package Manager für Kubernetes](#helm-als-package-manager-für-kubernetes)
  - [Bereitstellung mit Terraform und Port Forwarding](#bereitstellung-mit-terraform-und-port-forwarding)
  - [kubectl top](#kubectl-top)
  - [Validierung der Installation](#validierung-der-installation)
  - [Administration des Kubernetes-Clusters](#administration-des-kubernetes-clusters)
- [Statische Codeanalyse](#statische-codeanalyse)
  - [Checkstyle und SpotBugs](#checkstyle-und-spotbugs)
  - [SonarQube](#SonarQube)
- [Analyse von Sicherheitslücken](#analyse-von-sicherheitslücken)
  - [OWASP Security Check](#owasp-security-check)
  - [Docker Scout](#docker-scout)
- [Dokumentation](#dokumentation)
  - [Dokumentation durch AsciiDoctor und PlantUML](#dokumentation-durch-asciidoctor-und-plantuml)
  - [API Dokumentation durch javadoc](#api-dokumentation-durch-javadoc)


## Powershell bei Windows

Überprüfung, ob sich Powershell-Skripte starten lassen:

```powershell
    Get-ExecutionPolicy -list
```

`CurrentUser` muss _zumindest_ das Recht `RemoteSigned` haben. Ggf. muss dieses
Ausführungsrecht gesetzt werden:

```powershell
    Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

Ggf. genügt `RemoteSigned` nicht und man muss `Bypass` verwenden, sodass
keine Ausführung blockiert wird und dabei keine Warnings ausgegeben werden.
Das hängt von der eigenen Windows-Installation ab. Details siehe
https://docs.microsoft.com/en-us/powershell/module/microsoft.powershell.security/set-executionpolicy?view=powershell-7.2

---

## Speichereinstellung für Gradle

Falls die Speichereinstellung für Gradle zu großzügig ist, kann man in
`gradle.properties` bei `org.gradle.jvmargs` den voreingestellten Wert
(2 GB) ggf. reduzieren.

---

## Postman

### Registrieren und Installieren

Zunächst muss man sich bei https://www.postman.com registrieren und kann danach
die Desktop-Application _Postman_ von https://www.postman.com/downloads
herunterladen und installieren. Die Installation erfolgt dabei im Verzeichnis
`${LOCALAPPDATA}\Postman\app-VERSION`, z.B. `C:\Users\MeineKennung\AppData\Local\Postman\app-VERSION`.

### Workspace anlegen

Über die Desktop-Applikation legt man sich folgendermaßen einen _Workspace_ an:

- Den Menüpunkt _Workspaces_ anklicken
- Im Drop-Down Menü den Button _Create Workspace_ anklicken
- Danach den Button _Next_ anklicken
- Im Eingabefeld _Name_ `kunde` und im Eingabefeld _Summary_ z.B.
  `REST- und GraphQL-Requests für den Appserver.`
- Abschließend den Button _Create_ anklicken.

### Environments

Zunächst legt man ein _Environment_ mit Variablen an. Dazu wählt man am
linken Rand den Menüpunkt _Environments_, klickt auf den Button `Import`
und wählt aus dem Verzeichnis `.extras\postman` die Datei `kunde.postman_environment.json`
aus. Jetzt hat man die Umgebung `kunde` mit der Variablen `base_url` und dem
Wert `https://localhost:8443` angelegt.

### Collections und Folders

Als nächstes wählt man den Menüpunkt _Collections_ aus und importiert der Reihe
nach _Collections_ aus dem Verzeichnis `extras\postman`, indem man den Button
`Import` anklickt. Collections sind zusammengehörige Gruppierungen von Requests
und können zur besseren Strukturierung in _Folder_ unterteilt werden.
Beispielsweise gibt es die Collection _REST_ mit untergeordneten Folder, wie
z.B. _Suche mit ID_ und _Neuanlegen_. Im Folder _Suche mit ID_ gibt es dann z.B.
den Eintrag _GET vorhandene ID 0...1_, um einen GET-Request mit dem Pfadparameter
`:id` und dem Wert `00000000-0000-0000-0000-000000000001` abzusetzen.

Eine neue Collection legt man mit dem Button _+_ an und einen untergeordneten
Folder mit dem Overflow-Menü sowie dem Menüpunkt _Add folder_.

### Requests

Im Overflow-Menü eines Folders oder einer Collection kann man durch den Menüpunkt
_Add request_ einen neuen Eintrag für Requests erstellen, wobei man dann z.B.
folgendes festlegt:

- Bezeichnung des Eintrags
- GET, POST, PUT, PATCH, DELETE
- URL mit ggf. Pfadparameter, z.B. :id
- Im Karteireiter _Params_ sieht man dann die Pfadparameter und kann auch
  Query-Parameter spezifizieren.
- Im Karteireiter _Body_ kann man z.B. JSON-Daten für einen POST-Request oder
  Daten für GraphQL-Queries oder -Mutations eintragen. Dazu wählt man dann
  unterhalb von _Body_ den Radiobutton _raw_ mit _JSON_ aus, wenn man einen
  POST- oder PUT-Request spezifiziert bzw. den Radiobutton _GraphQL_ für
  Queries oder Mutations aus.
- Wenn man GraphQL-Requests spezifiziert, d.h. im Request-Body _GraphQL_
  festlegt, dann lädt Postman aufgrund der Request-URL das zugehörige GraphQL-Schema
  herunter, falls man die Vorbelegung _Auto-fetch_ beibehält. Dadurch hat man
  Autovervollständigen beim Formulieren von Queries und Mutations.

> Beachte: Wenn man gebündelte Requests von Collections oder Folders abschickt,
> hat man bis zu 50 "Runs" pro Monat frei.

### Variable

Um bei der URL für die diversen Requests nicht ständig wiederholen zu müssen,
kann man in einer Collection auch _Variable_ definieren, indem man die Collection
auswählt und dann den Karteireiter _Variables_, z.B. `rest_url` als Variablenname
und `https://localhost:8443/rest` als zugehöriger Wert.

---

## Übersetzung und lokale Ausführung

### Ausführung in IntelliJ IDEA

Bei Gradle: Am rechten Rand auf den Button _Gradle_ klicken und in _Tasks_ > _application_
durch einen Doppelklick auf _bootRun_ starten.

Bei Maven: Am rechten Rand auf den Button _Maven_ klicken und innerhalb vom Projekt
_Plugins_ > _spring-boot_ durch einen Doppelklick auf _spring-boot:run_ starten.

Danach gibt es bei Gradle in der Titelleiste am oberen Rand den Eintrag _kunde [bootRun]_
im Auswahlmenü und man kann von nun an den Server auch damit (neu-) starten,
stoppen und ggf. debuggen.

### Kommandozeile

In einer Powershell wird der Server mit dem Spring-_Profile_ `dev` gestartet. Bei Windows:

```powershell
    # Gradle:
    .\gradlew bootRun

    # Maven:
    .\mvnw spring-boot:run
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew bootRun

    # Maven:
    ./mvnw spring-boot:run
```

Mit `<Strg>C` kann man den Server herunterfahren, weil in der Datei `application.yml`
im Verzeichnis `src\main\resources` bei Windows bzw. `src/main//resources` bei macOS
_graceful shutdown_ konfiguriert ist.

Falls der Server nicht gestartet werden kann, weil der Port `8443` belegt ist,
kann man bei Windows in der Powershell zunächst die ID vom Betriebssystem-Prozess ermitteln,
der den Port belegt und danach diesen Prozess beenden:

```powershell
    netstat -ano | findstr ':8443'
    taskkill /F /PID <Prozess-ID>
```

Bei macOS:

```shell
    ps -af
    kill <Prozess-ID>
    # ggf.
    kill -9 <Prozess-ID>
```

### Port 8080 oder 8443

Normalerweise nimmt man beim Entwickeln mit _Java_ für HTTP den Port `8080` und für HTTPS
den Port `8443`.

### Aufrufe mit Postman oder mit der (Power-) Shell

Im Verzeichnis `extras\postman` gibt es Dateien für den Import in Postman.
Zuerst importiert man die Datei `*_environment.json`, um Umgebungsvariable
anzulegen, und danach die Dateien `*_collection.json`, um Collections für Requests
anzulegen.

Bei Windows kann man in einer PowerShell mit _Invoke-WebRequest_  eine Sequenz von z.B.
5 Requests folgendermaßen absetzen und beobachten:

```powershell
    for ($i = 1; $i -le 5; $i++) {
        Write-Output ''
        $response = Invoke-WebRequest https://localhost:8443/rest/00000000-0000-0000-0000-00000000000$i `
            -HttpVersion 2 -SslProtocol Tls13 -SkipCertificateCheck -Headers @{Accept = 'application/json'}
        $statuscode = $response.StatusCode
        Write-Output "${i}: ${statuscode}"
    }
    Write-Output $response.RawContent
```

Bei macOS verwendet man _curl_.

### OpenAPI mit Swagger

Mit der URL `https://localhost:8443/swagger-ui.html` kann man in einem
Webbrowser den RESTful Web Service über eine Weboberfläche nutzen, die
von _Swagger_ auf der Basis von der Spezifikation _OpenAPI_ generiert wurde.
Die _Swagger JSON Datei_ kann man mit `https://localhost:8443/v3/api-docs`
abrufen.

### Liveness und Readiness

Mit der URL `https://localhost:8443/actuator/health/liveness` kann die
_Liveness_ des gestarteten Servers überprüft werden, d.h. ob der Server an
sich gestartet ist. Dazu gehört _nicht_ die Prüfung, ob evtl. externe Server
wie z.B. DB-Server oder Mailserver erreichbar sind.

Mit der URL `https://localhost:8443/actuator/health/readiness` kann die
_Readiness_ des gestarteten Servers überprüft werden, d.h. ob der Server bereit
ist, Requests entgegenzunehmen.

Diese URLs für Liveness und Readiness können später für die Lastbalanzierung
z.B. in der Cloud verwendet werden.

---

## Image, Container und Docker Dashboard

### Docker-Daemon und Benutzerkennung

Die Kommunikation mit dem _Docker-Daemon_, d.h. _Dienst_ bei Windows, sollte
mit der Benutzerkennung erfolgen, mit der man _Docker Desktop_ installiert hat,
weil diese Benutzerkennung bei der Installation zur Windows-Gruppe
`docker-users` hinzugefügt wurde. Deshalb sollte man auch _Docker Desktop_
stets mit dieser Benutzerkennung starten.

### Image erstellen mit Dockerfile

Durch die (Konfigurations-) Datei `Dockerfile` kann man ein Docker-Image
erstellen und z.B. durch ein _Multi-stage Build_ optimieren. Ob das
`Dockerfile` gemäß _Best Practices_ (https://docs.docker.com/develop/develop-images/dockerfile_best-practices)
erstellt wurde, kann man in einer PowerShell mit folgendem Kommando überprüfen:

```shell
    # Windows:
    Get-Content Dockerfile | docker run --rm --interactive hadolint/hadolint:v2.14.0-debian

    # macOS:
    cat Dockerfile | docker run --rm --interactive hadolint/hadolint:v2.14.0-debian
    # evtl.:
    sudo chown -R $(whoami) ~/.docker
	  chmod -R u+rw ~/.docker
```

Eine weitverbreitete Namenskonvention für ein Docker-Image ist
`<registry-name>/<username>/<image-name>:<image-tag>`. Damit kann man dann ein
Docker-Image mit folgendem Kommando erstellen.

```powershell
    # Eclipse Temurin mit Ubuntu Noble (2024.04) als Basis-Image
    docker build --tag=juergenzimmermann/kunde:2025.10.1-eclipse-noble .
    # Eclipse Temurin mit Alpine als Basis-Image
    docker build --tag=juergenzimmermann/kunde:2025.10.1-eclipse-alpine --file=Dockerfile.alpine .
    # Azul Zulu mit Ubuntu Jammy (2022.04) als Basis-Image
    docker build --tag=juergenzimmermann/kunde:2025.10.1-azul  --file=Dockerfile.azul .
```

Wenn das Image gebaut wird, kann ggf. noch die Option `--no-cache` angegeben
werden, um zu unterbinden, dass Daten aus dem (Docker-) Cache verwendet werden.

Statt der diversen Optionen für `docker build` kann man auch eine Konfigurationsdatei
für Docker `Bake` erstellen, z.B. docker-bake.hcl (im Format HCL = HashiCorp Configuration Language).
Dann lauten die entsprechenden einfacheren Aufrufe für die obigen Beispiele:

```
    docker buildx bake
    docker buildx bake alpine
    docker buildx bake azul
```

Nachdem das Docker-Image erstellt ist, kann man es im _Docker Dashboard_
inspizieren. Falls das Docker Dashboard geschlossen wurde, kann man es
folgendermaßen wieder öffnen:

- Im _System Tray_ (rechts unten in der _Taskleiste_) ist das Docker-Icon
  (_Whale_).
- Wenn man das Whale-Icon anklickt, wird das _Docker Dashboard_ aufgerufen.

Mit der PowerShell kann man Docker-Images folgendermaßen auflisten und löschen,
wobei das Unterkommando `rmi` die Kurzform für `image rm` ist:

```shell
    docker images | sort
    docker rmi myImage:myTag
```

Im Laufe der Zeit kann es immer wieder Images geben, bei denen der Name
und/oder das Tag `<none>` ist, sodass das Image nicht mehr verwendbar und
deshalb nutzlos ist. Solche Images kann man mit dem nachfolgenden Kommando
herausfiltern und dann unter Verwendung ihrer Image-ID, z.B. `9dd7541706f0`
löschen:

```shell
    # Windows:
    docker images | Select-String -Pattern '<none>'

    # macOS:
    docker images | grep "<none>"

    docker rmi 9dd7541706f0
```

### Cloud Native Buildpacks durch Spring Boot

Spring Boot bstellt für Gradle die Task `bootBuildImage` bereit und für Maven
`spring-boot:build-image`. Damit kann man unter Verwendung von _Cloud Native Buildpacks_
(cnb) ein optimiertes und geschichtetes ("Layer") Docker-Image erstellen, ohne
dass ein `Dockerfile` notwendig ist.

Bei Windows:

```powershell
    # Gradle:
    .\gradlew bootBuildImage

    # Maven:
    .\mvnw spring-boot:build-image -D'maven.test.skip=true'
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew bootBuildImage

    # Maven:
    ./mvnw spring-boot:build-image -D'maven.test.skip=true'
```

Während der Ausführung werden für die "Buildpacks" von Paketo
Docker-Images und auch einige Archive von Github heruntergeladen.

### Image inspizieren

#### docker inspect

Mit dem Unterkommando `inspect` von `docker` kann man die Metadaten, z.B. Labels, zu einem
Image inspizieren:

```shell
    docker inspect juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
```

#### docker sbom

Mit dem Unterkommando `sbom` (Software Bill of Materials) von `docker` kann man inspizieren,
welche Bestandteilen in einem Docker-Images enthalten sind, z.B. Java-Archive oder Debian-Packages.

```shell
    docker sbom juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
```

#### docker history

Mit dem Unterkommando `history` von `docker` kann man ein Docker-Image und die einzelnen Layer inspizieren, z.B.:

```shell
    docker history juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
```

#### Tool Window "Services" von IntelliJ IDEA

Im Tool Window _Services_ von IntelliJ IDEA gibt es den Eintrag _Docker_ mit
dem Unterpunkt _Images_, wo man ähnlich wie mit _dive_ ein Image und dessen
Layers inspizieren kann.

### Container starten

Einen Docker-Container kann man mit einem Docker-Image starten, indem man das
nachfolgende Kommando aufruft.

Bei Windows:

```powershell
    docker run --publish 8443:8443 `
      --env TZ=Europe/Berlin `
      --env SPRING_PROFILES_ACTIVE=dev `
      --env APPLICATION_LOGLEVEL=trace `
      --mount 'type=bind,source=C:\Zimmermann\volumes\kunde-v1,destination=/tmp' `
      --memory 1024m --cpus 1 --hostname kunde `
      --name kunde --rm juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
```

Bei macOS (statt `/var/volumes` kann auch ein anderer sinnvoller Pfadverwendet werden):

```shell
    docker run --publish 8443:8443 \
      --env TZ=Europe/Berlin \
      --env SPRING_PROFILES_ACTIVE=dev \
      --env APPLICATION_LOGLEVEL=trace \
      --mount 'type=bind,source=/var/volumes/kunde-v1,destination=/tmp' \
      --memory 1024m --cpus 1 --hostname kunde \
      --name kunde --rm juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
```

Jetzt läuft der Microservice als Docker-Container mit `HTTPS`, wobei auch der
Container-interne Port 8443 des Microservice "kunde" als Port 8443 für
localhost freigegeben wurde. Die Log-Datei `application.log` befindet sich im
Container im Verzeichnis `/tmp`, das durch Mounting im
Windows-Verzeichnis `C:\Zimmermann\volumes\kunde-v1` bzw. im macOS-Verzeichnis
`/var/volumes/kunde-v1` zugreifbar ist.

Mit _Postman_ können nun HTTP-Requests (GET, POST, PUT, PATCH, DELETE) abgeschickt werden.

Den gestarteten Docker-Container kann man im Docker Dashboard sehen und später
auch beenden. Dabei wird der im Container enthaltene Server für den Microservice
mit "graceful shutdown" heruntergefahren.

### Docker Compose

Statt direkt `docker run` mit all den Optionen aufzurufen, kann man das neue
_Docker Compose V2_, das in Go statt in Python implementiert ist, als
Docker-Plugin verwenden. Dazu ist die (Default-) Konfigurationsdatei
`compose.yml` notwendig, die im Verzeichnis `extras\compose` bzw. `extras/compose`
gespeichert ist. Bei macOS müssen in `compose.yml` die Windows-Pfade zu `private-key.pem`
und `certificate.crt` angepasst werden, d.h. `\` muss durch `/` ersetzt werden.

Dann lässt sich der Container folgendermaßen starten und später in einer
weiteren PowerShell herunterfahren:

```shell
    # Windows:
    cd extras\compose\kunde

    # macOS:
    cd extras/compose/kunde

    # kunde mit z.B. Cloud-Native Buildpacks und Bellsoft Liberica (siehe compose.yml)
    docker compose up

    # Nur zur Fehlersuche: weitere (Power-) Shell für bash
    # Bei Buildpacks den Builder "paketobuildpacks/builder-jammy-base:latest" verwenden (s. gradle.properties)
    # Windows:
    cd extras\compose\kunde
    # macOS:
    cd extras/compose/kunde

    docker compose exec kunde bash
        id
        ps -ef
        env
        ls -l /layers
        ls -l /layers/paketo-buildpacks_adoptium/jre
        #ls -l /layers/paketo-buildpacks_azul-zulu/jre
        #ls -l /layers/paketo-buildpacks_bellsoft-liberica/jre
        pwd
        hostname
        cat /etc/os-release
        exit

    docker compose down
```

Statt eine PowerShell zu verwenden, kann man Docker Compose auch direkt in
IntelliJ aufrufen, indem man über das Kontextmenü ("rechte Maustaste") den
Unterpunkt _Run 'compose/compose.yml...'_ für die Datei `compose.yml` auswählt.
Im Tool-Window _Services_ sieht man dann unterhalb von _Docker_ den Eintrag
_Docker-compose: compose_ mit dem Service _kunde_. Durch Anklicken von
`compose-kunde-1` kann man _Log_ und _Dashboard_ (Environment Variables,
Port, Volumes) inspizieren. Im Kontextmenü von `compose-kunde-1` gibt es u.a.
den Menüpunkt _Stop Container_, um den Container zu beenden.

### Image kopieren

Mit `docker save` kann man ein Docker Image im Format `tar` abspeichern und
dann ggf. kopieren:

```shell
    docker save juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft > kunde.tar
```

Mit `docker load` kann man anschließend ein Image aus dem Format `tar`
wiederherstellen:

```shell
    docker load < kunde.tar
```

---

## Kubernetes, Helm und Terraform

### WICHTIG: Schreibrechte für die Logdatei

Wenn die Anwendung in Kubernetes läuft, ist die Log-Datei `application.log` im
Verzeichnis `C:\Zimmermann\volumes\kunde-v1`. Das bedeutet auch zwangsläufig,
dass diese Datei durch den _Linux-User_ vom (Kubernetes-) Pod angelegt und
geschrieben wird, wozu die erforderlichen Berechtigungen in Windows gegeben
sein müssen.

### Rechnername in der Datei hosts

Wenn man mit Kubernetes arbeitet, bedeutet das auch, dass man i.a. über TCP
kommuniziert. Deshalb sollte man überprüfen, ob in der Datei
`C:\Windows\System32\drivers\etc\hosts` der eigene Rechnername mit seiner
IP-Adresse eingetragen ist. Zum Editieren dieser Datei sind Administrator-Rechte
notwendig.

### CLI durch kubectl

`kubectl` ist ein _CLI_ (= Command Line Interface) für Kubernetes und bietet
etliche Unterkommandos, wie z.B. `kubectl apply`, `kubectl create`, `kubectl get`,
`kubectl describe` oder `kubectl delete`.

### Eigener Namespace und Defaults

In Kubernetes gibt es Namespaces ("Namensräume") ähnlich wie in

- Betriebssystemen durch Verzeichnisse, z.B. in Windows oder Unix
- Programmiersprachen, z.B. durch `package` in Java
- Datenbanksystemen, z.B. durch ein Schema in Oracle und PostgreSQL.

Genauso wie in Datenbanksystemen gibt es in Kubernetes _keine_ untergeordneten
Namespaces. Vor allem ist es in Kubernetes empfehlenswert für die eigene
Software einen _neuen_ Namespace anzulegen und **NICHT** den Default-Namespace
zu benutzen. Das wurde bei der Installation von Kubernetes durch den eigenen
Namespace `acme` bereits erledigt. Außerdem wurde aus Sicherheitsgründen beim
defaultmäßigen Service-Account das Feature _Automounting_ deaktiviert und der
Kubernetes-Cluster wurde intern defaultmäßig so abgesichert, dass

- über das Ingress-Gateway keine Requests von anderen Kubernetes-Services zulässig sind
- über das Egress-Gateway keine Requests an andere Kubernetes-Services zulässig sind.

### Image für Kubernetes erstellen

Zunächst wird ein Docker-Image benötigt, das z.B. mit dem
[Gradle- oder Maven-Plugin von Spring Boot](#Cloud-Native-Buildpacks-durch-Spring-Boot)
als optimiertes und geschichtetes Image erstellt wird.

Bei Verwendung der Buildpacks werden ggf. einige Archive von Github heruntergeladen,
wofür es leider kein Caching gibt. Ein solches Image kann mit dem Linux-User `cnb`
gestartet werden.

Bei Windows:

```shell
    # Gradle:
    .\gradlew bootBuildImage

    # Maven:
    .\mvnw spring-boot:build-image -D'maven.test.skip=true'
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew bootBuildImage

    # Maven:
    ./mvnw spring-boot:build-image -D'maven.test.skip=true'
```

### Installation in Kubernetes: Deployment, Service und Configmap

Damit ein Docker-Image in Kubernetes als Docker-Container laufen kann, sind
zusätzliche Einstellungen z.B. durch Umgebungsvariable notwendig. Solche
Key-Value-Paare kann man in Kubernetes durch eine _Configmap_ bereitstellen.

Das Image wird innerhalb von Kubernetes durch ein _Deployment_ konfiguriert
und kann mit einer _Configmap_ für die Umgebungsvariable verknüpft werden.
Außerdem wird ein Deployment i.a. mit einem _Service_ verbunden, um externen
Zugriff von z.B. anderen Deployments zu ermöglichen. Die zugehörige Manifest-
bzw. Konfigurationsdatei für _Configmap_, _Service_ und _Deployment_ ist
`kubernetes.yaml` im Unterverzeichnis `kubernetes`.

Die Installation in Kubernetes erfolgt durch das nachfolgende Kommando, sodass
der Microservice dann innerhalb von Kubernetes mit `HTTPS` läuft. Dabei wird
die Logdatei im internen Verzeichnis `/var/log/spring` angelegt, welches durch
_Mounting_ dem Windows-Verzeichnis `C:\Zimmermann\volumes\kunde-v1` bzw. dem
macOS-Verzeichnis `/var/volumes/kunde-v1` entspricht und mit _Schreibberechtigung_
existieren muss, z.B. bei Windows für die Benutzergruppen `Authentifizierte Benutzer`
und `Benutzer`.

Bei Windows:
```powershell
    # Image mit Spring Boot und Cloud Native Buildpacks
    # Gradle:
    .\gradlew bootBuildImage

    # Maven:
    .\mvnw spring-boot:build-image -D'maven.test.skip=true'

    kubectl create -f extras\kubernetes\kubernetes.yaml --namespace acme
```

Bei macOS:

```powershell
    # Image mit Spring Boot und Cloud Native Buildpacks
    # Gradle:
    ./gradlew bootBuildImage

    # Maven:
    ./mvnw spring-boot:build-image -D'maven.test.skip=true'

    kubectl create -f extras/kubernetes/kubernetes.yaml --namespace acme
```

Die Datei `kubernetes.yaml` wird übrigens im 2. Beispiel bzw. für die 2. Abgabe
_nicht_ mehr benötigt.

### Deinstallieren des Microservice

Um den Microservice vollständig zu deinstallieren, müssen _Configmap_,
_Deployment_ und _Service_ aus Kubernetes entfernt werden:

```powershell
    kubectl delete -f extras\kubernetes\kubernetes.yaml --namespace acme
```

### Helm als Package Manager für Kubernetes

_Helm_ ist ein _Package Manager_ für Kubernetes mit einem _Template_-Mechanismus
auf der Basis von _Go_.

Zunächst muss man mit dem
[Gradle- oder Maven-Plugin von Spring Boot](#Cloud-Native-Buildpacks-durch-Spring-Boot)
ein Docker-Image erstellen.

Bei Windows:

```powershell
    # Gradle:
    .\gradlew bootBuildImage

    # Maven:
    .\mvnw spring-boot:build-image -D'maven.test.skip=true'
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew bootBuildImage

    # Maven:
    ./mvnw spring-boot:build-image -D'maven.test.skip=true'
```

Das _Helm-Chart_ heißt `kunde` und ist deshalb in einem gleichnamigen Verzeichnis,
das zur besseren Strukturierung unterhalb von `extras\helm` ist. Die Metadaten sind
in der Datei `Chart.yaml` und die einzelnen Manifest-Dateien sind im
Unterverzeichis `templates` im Format YAML. In diesen Dateien gibt es Platzhalter
("templates") mit der Syntax der Programmiersprache Go. Die Defaultwerte für diese
Platzhalter sind in der Datei `values.yaml` und können beim Installieren mit z.B.
_Terraform_ oder _Pulumi_ durch weitere YAML-Dateien überschrieben werden.

Mit den nachfolgenden Kommandos kann man ein Helm-Chart überprüfen ("lint")
und eine Markdown-Datei zur Dokumentation der Defaultwerte für das Helm-Chart
generieren.

```shell
    # Windows:
    cd extras\helm\kunde

    # macOS:
    cd extras/helm/kunde

    # Ueberprüfung des Helm-Charts
    helm lint --strict .

    # Markdown-Datei mit den Defaultwerten generieren
    helm-docs
```

### Bereitstellung mit Terraform und Port Forwarding

Siehe `extras\terraform\ReadMe.md` bzw. `extras/terraform/ReadMe.md`.

### Bereitstellung mit Pulumi und Port Forwarding

Siehe `extras\pulumi\ReadMe.md` bzw. `extras/pulumi/ReadMe.md`.

### kubectl top

Mit `kubectl top pods -n acme` kann man sich die CPU- und RAM-Belegung der Pods
anzeigen lassen. Ausgehend von diesen Werten kann man `resources.requests` und
`resources.limits` in `dev.yaml` ggf. anpassen.

Voraussetzung für `kubectl top` ist, dass der `metrics-server` für Kubernetes
im Namespace `kube-system` installiert wurde.
https://kubernetes.io/docs/tasks/debug/debug-cluster/resource-metrics-pipeline

---

### Validierung der Installation

#### Polaris

Ob _Best Practices_ bei der Installation eingehalten wurden, kann man mit
_Polaris_ überprüfen. Um den Aufruf zu vereinfachen, gibt es im Unterverzeichnis
`extras\kubernetes` das Skript `polaris.ps1`:

```shell
    # Windows:
    cd extras\kubernetes
    .\polaris.ps1

    # macOS:
    cd extras/kubernetes
    ./polaris.sh
```

Nun kann Polaris in einem Webbrowser mit der URL `http://localhost:8008`
aufgerufen werden.

#### kubescape

Ob _Best Practices_ bei den _Manifest-Dateien_ eingehalten wurden, kann man mit
_kubescape_ überprüfen. Um den Aufruf zu vereinfachen, gibt es im
Unterverzeichnis `extras\kubernetes` das Skript `kubescape.ps1`:

```shell
    # Windows:
    cd extras\kubernetes
    .\kubescape.ps1

    # macOS:
    cd extras/kubernetes
    ./kubescape.sh
```

#### Pluto

Ob _deprecated_ APIs bei den _Manifest-Dateien_ verwendet wurden, kann man mit
_Pluto_ überprüfen. Um den Aufruf zu vereinfachen, gibt es im
Unterverzeichnis `extras\kubernetes` das Skript `pluto.ps1`:

```shell
    # Windows:
    cd extras\kubernetes
    .\pluto.ps1

    # macOS:
    cd extras/kubernetes
    ./pluto.sh
```

### Administration des Kubernetes Clusters

#### Services Tool Window von Intellij IDEA

Über den Menüpunkt _View_ mit den Unterpunkten _Tool Windows_ und _Services_
kann man das _Services Tool Window_ öffnen. Dort sieht man den Eintrag für
_docker-desktop_ und kann über das Icon _Namespace_ am linken Rand vom
Default-Namespace auf den Namespace "acme" umschalten. Danach kann man über
die Unterpunkte _Workloads_ und _Pods_ zu einem laufenden Pod, z.B.
`kunde-1234567890-12345`, navigieren und diesen mit der linken Maustaste
selektieren. Nun kann man z.B. über die Icons _Download Log_ oder _Run Shell_
die Log-Einträge inspizieren oder eine Shell öffnen. Alternativ kann man beim
Pod auch das Kontextmenü bzw. die rechte Maustaste benutzen.

#### Lens

Lens von Mirantis https://k8slens.dev bietet eine grafische Oberfläche, um die
Log-Ausgaben zu inspizieren oder eine Shell zu benutzen. Darüberhinaus gibt es
Monitoring-Möglichkeiten für z.B. die CPU.

In der Navigationsleiste am linken Rand gibt es Menüpunkte für:

- _Workloads_: Pods, Deployments, Stateful Sets
- _Configuration_: ConfigMaps und Secrets
- _Network_: Services

Bei z.B. den Pods kann man rechts oben einen bestimmten Namespace auswählen,
damit man eine bessere Übersicht hat. Nun kann man über das Overflow-Menü am
rechten Rand direkt zugreifen auf

- die Logging-Ausgaben in der Konsole
- ein Terminal mit einer Linux-Shell, falls das zugrundeliegende Docker-Image
  eine Shell enthält. Dabei werden folgende Shells in dieser Reihenfolge
  unterstützt:
  - bash
  - ash
  - sh

#### Alternative Werkzeuge

Statt _Lens_ kann man auch andere Werkzeuge verwenden:

_Kubernetes Dashboard_ <https://github.com/kubernetes/dashboard> ist vor allem
für IT-Administratoren geeignet und gedacht. Für Entwickler/inn/en ist m.E.
[Lens](#Lens) besser geeignet.

_Kui_ von IBM ist eine interessante Kombination von grafischer Benutzungsoberfläche
und _anklickbarer_ Kommandozeile in Verbindung mit der Möglichkeit, mehrere
Tabs zu verwenden.

_k9s_ ist ein CLI (= Command Line Interface), mit dem effizient auf Kubernetes
zugegriffen werden kann und das bei denjenigen Entwickler*innen sehr beliebt
ist, die ständig mit Kubernetes arbeiten

Für _VS Code_ (statt _IntelliJ IDEA_) gibt es die Extension _Kubernetes_ von
Microsoft. Diese Extension ist ähnlich wie _Lens_ auf die Bedürfnisse der
Entwickler/inn/en zugeschnitten und ermöglicht den einfachen Zugriff auf ein
Terminal oder die Logs.

---

## Statische Codeanalyse

### Checkstyle und SpotBugs

Eine statische Codeanalyse ist durch die beiden Werkzeuge _Checkstyle_, _SpotBugs_,
_Spotless_ und _Modernizer_ möglich, indem man die jeweiligen Tasks aufruft:

Bei Windows:

```powershell
    # Gradle:
    .\gradlew checkstyleMain spotbugsMain checkstyleTest spotbugsTest spotlessApply modernizer

    # Maven:
    .\mvnw checkstyle:checkstyle spotbugs:check spotless:check modernizer:modernizer jxr:jxr
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew checkstyleMain spotbugsMain checkstyleTest spotbugsTest spotlessApply modernizer

    # Maven:
    ./mvnw checkstyle:checkstyle spotbugs:check spotless:check modernizer:modernizer jxr:jxr
```

### SonarQube

Für eine statische Codeanalyse durch _SonarQube_ muss zunächst der
SonarQube-Server mit _Docker Compose_ als Docker-Container gestartet werden.
Zur Konfiguration des Servers siehe `extras\compose\sonarqube\ReadMe.md`.

```powershell
    # Windows:
    cd extras\compose\sonarqube

    # macOS:
    cd extras/compose/sonarqube

    docker compose up
```

Nachdem der Server gestartet ist, wird der SonarQube-Scanner in einer zweiten
PowerShell mit `.\gradlew sonar` bzw. `.\mvnw sonar:sonar` gestartet.
Das Resultat kann dann in der Webseite des zuvor gestarteten Servers über die
URL `http://localhost:9000` inspiziert werden.

Abschließend wird der oben gestartete Server heruntergefahren.

```shell
    # Windows:
    cd extras\compose\sonarqube

    # macOS:
    cd extras/compose/sonarqube

    docker compose down
```

---

## Analyse von Sicherheitslücken

### OWASP Security Check

In `build.gradle.kts` bzw. `pom.xml` sind _dependencies_ konfiguriert, um
Java Archive, d.h. .jar-Dateien, von Drittanbietern zu verwenden, z.B. die
JARs für Spring oder für Jackson. Diese Abhängigkeiten lassen sich mit
_OWASP Dependency Check_ analysieren:

Bei Windows:

```powershell
    # Gradle:
    .\gradlew dependencyCheckAnalyze --info

    # Maven:
    .\mvnw dependency-check:check
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew dependencyCheckAnalyze --info

    # Maven:
    ./mvnw dependency-check:check
```

### Docker Scout

Mit dem Unterkommando `quickview` von _Scout_ kann man sich zunächst einen
groben Überblick verschaffen, wieviele Sicherheitslücken in den Bibliotheken im
Image enthalten sind:

```shell
    docker scout quickview juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
```

Dabei bedeutet:

* C ritical
* H igh
* M edium
* L ow

Sicherheitslücken sind als _CVE-Records_ (CVE = Common Vulnerabilities and Exposures)
katalogisiert: https://www.cve.org (ursprünglich: https://cve.mitre.org/cve).
Übrigens bedeutet _CPE_ in diesem Zusammenhang _Common Platform Enumeration_.
Die Details zu den CVE-Records im Image kann man durch das Unterkommando `cves`
von _Scout_ auflisten:

```shell
    docker scout cves juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
    docker scout cves --format only-packages juergenzimmermann/kunde:2025.10.1-buildpacks-bellsoft
````

Statt der Kommandozeile kann man auch den Menüpunkt "Docker Scout" im
_Docker Dashboard_ verwenden.

---

## Dokumentation

### Dokumentation durch AsciiDoctor und PlantUML

Eine HTML- und PDF-Dokumentation aus AsciiDoctor-Dateien, die ggf. UML-Diagramme
mit PlantUML enthalten, wird durch folgende Tasks erstellt:

Bei Windows:

```powershell
    # Gradle:
    .\gradlew asciidoctor asciidoctorPdf

    # Maven:
    .\mvnw asciidoctor:process-asciidoc asciidoctor:process-asciidoc@pdf -Pasciidoctor
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew asciidoctor asciidoctorPdf

    # Maven:
    ./mvnw asciidoctor:process-asciidoc asciidoctor:process-asciidoc@pdf -Pasciidoctor
```

### API Dokumentation durch javadoc

Eine API-Dokumentation in Form von HTML-Seiten kann man durch das Gradle- bzw.
Maven-Plugin erstellen:

Bei Windows:

```powershell
    # Gradle:
    .\gradlew javadoc

    # Maven:
    .\mvnw javadoc:javadoc
```

Bei macOS:

```shell
    # Gradle:
    ./gradlew javadoc

    # Maven:
    ./mvnw javadoc:javadoc
```

---
