# Packaging and Installers

This directory contains scripts to create native installers for different operating systems using `jpackage`.

## Prerequisites

- **JDK 17+** (JDK 21 recommended) must be installed and `jpackage` must be available in the PATH.
- The project must be built first using Maven:
  ```bash
  mvn clean package -DskipTests
  ```
- **Windows:** You may need [WiX Toolset v3](https://wixtoolset.org/releases/) installed to create MSI or EXE installers.
- **Linux:** `rpm-build` (for RPM) or `fakeroot` (for DEB) might be required depending on the distribution.
- **macOS:** No additional tools are usually required for DMG/PKG.

## Instructions

### Windows

Run the PowerShell script:
```powershell
.\packaging\create-installer-win.ps1
```
The installer will be created in the `dist/` directory.

### Linux / macOS

Run the shell script:
```bash
chmod +x packaging/create-installer-unix.sh
./packaging/create-installer-unix.sh
```
The installer (`.deb`, `.rpm`, or `.dmg`) will be created in the `dist/` directory.

## How it works

1. `mvn package` creates a fat JAR and copies all dependencies to `target/libs`.
2. The scripts copy the main application JAR into `target/libs`.
3. `jpackage` bundles the entire `libs` folder along with a minimal Java Runtime (JRE) into a native installer.
