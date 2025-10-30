# Hinweise zu Pulumi

## Voraussetzung

- Namespace `acme` in Kubernetes existiert gemäß der allgemeinen Installationsanleitung
- Registrierung bei https://app.pulumi.com/signin wurde gemacht
- _Pulumi_ ist installiert und über die Umgebungsvariable `PATH` auch aufrufbar
- Installation von _Node_, z.B. bei Windows in `C:\Zimmermann\node`
- Umgebungsvariable `PATH` enthält den Pfad zur Node-Installation
- In `extras/helm/rentcar/templates/deployment.yaml` müssen die Pfade bei `spec.template.spec.volumes[].hostpath`
  angepasst werden

Ob die Voraussetzungen erfüllt sind, kann mit folgenden Kommandos überprüft werden:

```shell
    kubectl describe namespace acme

    # PowerShell
    Get-Command pulumi
    # macOS
    which pulumi

    pulumi version
    node --version
    npm --version
```

## Installation von pnpm

_pnpm_ ("performant node package manager") wird genutzt, um die Node-Packages für
Pulumi, Kubernetes und TypeScript zu installieren. Dazu werden zunächst evtl. vorhandene
Installationen von _pnpm_ und _yarn_ entfernt. Danach wird _corepack_ installiert,
womit anschließen _pnpm_ installiert und aktiviert wird.

```shell
    npm r -g pnpm yarn
    npm i -g corepack
    corepack enable pnpm
    corepack prepare pnpm@latest-10 --activate
    pnpm --version
```

## Installation von Pulumi für TypeScript und Kubernetes

Um _Pulumi_ mit _TypeScript_ als Konfigurationssprache für Kubernetes nutzen zu
können, werden die Node-Packages aus `package.json` durch _pnpm_ installiert:

```shell
    pnpm i
```

Danach existieren u.a. die Verzeichnisse `node_modules\@pulumi\kubernetes` und
`node_modules\@pulumi\pulumi`, welche bei _pnpm_ als Link realisiert sind. Das
kann man auch folgendermaßen überprüfen:

```shell
    pnpm why typescript
```

Nun kann man sich die vollständige Information zur Pulumi-Umgebung anzeigen lassen:

```shell
    pulumi about
```

## Dateien für ein Pulumi-Projekt

- `Pulumi.yaml` zur Project-Definition, dass z.B. _Node_ und _Helm-Charts_ genutzt werden.
- `Pulumi.dev.yaml` Konfigurations-Daten für den Pulumi-Stack, z.B. der Kubernetes-Namespache.
- `index.ts` TypeScript-Datei für die Bereitstellung im Kubernetes-Cluster mit
  z.B. einem Helm-Chart.

## Helm-Chart bereitstellen und deinstallieren

```shell
    # Preview fuer eine potentielle Bereitstellung, d.h. ohne echte Ausfuehrung
    pulumi preview

    # Helm-Chart im Kubernetes-Cluster bereitstellen
    pulumi up --yes
    .\port-forward.ps1

    # Deinstallieren
    pulumi destroy --yes
```
