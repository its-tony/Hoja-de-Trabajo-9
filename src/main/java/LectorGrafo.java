/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Lee el archivo guategrafo y construye el grafo
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LectorGrafo {
    public GrafoDirigido leerArchivo(String rutaArchivo) throws IOException {
        GrafoDirigido grafo = new GrafoDirigido();
        Path ruta = Path.of(rutaArchivo);

        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            int numeroLinea = 1;

            while ((linea = lector.readLine()) != null) {
                procesarLinea(linea, grafo, numeroLinea);
                numeroLinea++;
            }
        }

        return grafo;
    }

    private void procesarLinea(String linea, GrafoDirigido grafo, int numeroLinea) {
        String lineaLimpia = linea.trim();

        if (lineaLimpia.isEmpty() || lineaLimpia.startsWith("#")) {
            return;
        }

        String[] partes = lineaLimpia.split("\\s+");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Linea " + numeroLinea + " invalida: " + linea);
        }

        String origen = partes[0];
        String destino = partes[1];
        double distancia = convertirDistancia(partes[2], numeroLinea);
        grafo.agregarArco(origen, destino, distancia);
    }

    private double convertirDistancia(String texto, int numeroLinea) {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Distancia invalida en linea " + numeroLinea + ": " + texto);
        }
    }
}
