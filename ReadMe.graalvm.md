# GraalVM

## ACHTUNG

_GraalVM_ funktioniert nicht mit dem _Preview Feature_ `StableValue<>` von Java 25.

## Installation

- Visual Studio 2022 (mind. 17.6.0) und Microsoft Visual C++ (MSVC) installieren,
  siehe https://visualstudio.microsoft.com/de/downloads und https://www.graalvm.org/latest/getting-started/windows
- In `C:\Program Files (x86)\Microsoft Visual Studio\Installer` ggf. `setup.exe` aufrufen, um z.B.
  _Visual Studio Community_ zu aktualisieren
- GraalVM von https://www.graalvm.org/downloads muss als JDK in JAVA_HOME installiert sein.
  Dazu muss man IntelliJ IDEA beenden und den Gradle-Daemon mit `gradle --stop` beenden.

## Projekt-Konfiguration für Gradle

- `build.gradle.kts`
  - beim Plugin `alias(libs.plugins.graalvm)` den Kommentar entfernen
  - bei der Task `java` die Konfiguration für `toolchain` auskommentieren
  - die Kommentare für die Task `processAot` entfernen
- `gradle.properties`: im Abschnitt für Spring bei der Property `nativeImage` den Kommentar entfernen und
  den Wert auf `true` setzen, damit
  - Umgebungsvariable wie z.B. `BP_NATIVE_IMAGE` gesetzt werden und
  - _Flyway_ deaktiviert wird

## Native Image erstellen

In einer _Eingabeaufforderung_ (**NICHT**: PowerShell) erstellt man das Native Image,
was bei 64 GB RAM und 16 Prozessoren ca. 3 Minuten dauert:

```cmd
    "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat"
    PATH
    .\gradlew nativeCompile
```

## Native Image aufrufen

```PowerShell
    # siehe "application.yml" beim Profile "native"
    cp src\main\resources\certificate.crt build\native\nativeCompile
    cp src\main\resources\private-key.pem build\native\nativeCompile

    cd build\native\nativeCompile
    .\kunde.exe --spring.profiles.active=dev,native
```
