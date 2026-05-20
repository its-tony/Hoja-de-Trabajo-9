/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Punto de entrada del programa
 */

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String rutaArchivo = args.length > 0 ? args[0] : "guategrafo.txt";
        LectorGrafo lector = new LectorGrafo();

        try {
            GrafoDirigido grafo = lector.leerArchivo(rutaArchivo);
            MenuPrincipal menu = new MenuPrincipal(grafo);
            menu.iniciar();
        } catch (IOException error) {
            System.out.println("No se pudo leer el archivo: " + rutaArchivo);
            System.out.println("Detalle: " + error.getMessage());
        } catch (IllegalArgumentException error) {
            System.out.println("El archivo tiene datos invalidos");
            System.out.println("Detalle: " + error.getMessage());
        }
    }
}
