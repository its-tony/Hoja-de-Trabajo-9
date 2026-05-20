# Hoja de Trabajo 10

Programa de grafos dirigidos con algoritmo de Floyd-Warshall para calcular rutas mas cortas y el centro del grafo.

## Archivos importantes

- `src/main/java`: codigo principal del programa
- `src/test/java`: pruebas unitarias con JUnit 5
- `guategrafo.txt`: grafo de ejemplo
- `UML_HojaTrabajo10.puml`: diagrama UML listo para PlantUML
- `python_networkx`: version opcional en Python con NetworkX

## Para probarlo rápido:
```powershell
.\build.bat
```
Para correr pruebas:
.\test.bat

## Ejecutar

```powershell
java -cp out Main guategrafo.txt
```

## Probar

Desde esta carpeta, usando el JUnit que esta en `..\lib`:

```powershell
javac -encoding UTF-8 -cp "..\lib\junit-platform-console-standalone-1.13.0-M3.jar;src\main\java" -d out-test src\main\java\*.java src\test\java\*.java
java -jar "..\lib\junit-platform-console-standalone-1.13.0-M3.jar" --class-path out-test --scan-class-path
```

## Extra Python

```powershell
python python_networkx\centro_grafo_networkx.py guategrafo.txt
python python_networkx\centro_grafo_networkx.py guategrafo.txt Mixco Flores
```
