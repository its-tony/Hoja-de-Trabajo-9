/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Guarda distancias, rutas y centro del grafo despues de Floyd
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultadoFloyd {
    private final double[][] distancias;
    private final int[][] siguiente;
    private final List<String> ciudades;
    private final Map<String, Integer> indices;

    public ResultadoFloyd(double[][] distancias, int[][] siguiente, List<String> ciudades) {
        this.distancias = copiarMatriz(distancias);
        this.siguiente = copiarMatriz(siguiente);
        this.ciudades = new ArrayList<>(ciudades);
        this.indices = new LinkedHashMap<>();

        for (int i = 0; i < this.ciudades.size(); i++) {
            this.indices.put(this.ciudades.get(i), i);
        }
    }

    public double obtenerDistancia(String origen, String destino) {
        int indiceOrigen = obtenerIndice(origen);
        int indiceDestino = obtenerIndice(destino);
        return distancias[indiceOrigen][indiceDestino];
    }

    public List<String> obtenerRuta(String origen, String destino) {
        int indiceOrigen = obtenerIndice(origen);
        int indiceDestino = obtenerIndice(destino);
        List<String> ruta = new ArrayList<>();

        if (siguiente[indiceOrigen][indiceDestino] == -1) {
            return ruta;
        }

        ruta.add(ciudades.get(indiceOrigen));
        int actual = indiceOrigen;
        int pasosMaximos = ciudades.size() * ciudades.size();

        while (actual != indiceDestino && pasosMaximos > 0) {
            actual = siguiente[actual][indiceDestino];
            if (actual == -1) {
                return new ArrayList<>();
            }
            ruta.add(ciudades.get(actual));
            pasosMaximos--;
        }

        if (pasosMaximos == 0) {
            throw new IllegalStateException("No fue posible reconstruir la ruta");
        }

        return ruta;
    }

    public String obtenerCentro() {
        if (ciudades.isEmpty()) {
            return "No hay ciudades en el grafo";
        }

        int mejorIndice = 0;
        double mejorExcentricidad = Double.POSITIVE_INFINITY;

        for (int columna = 0; columna < ciudades.size(); columna++) {
            double excentricidad = calcularExcentricidadColumna(columna);
            if (excentricidad < mejorExcentricidad) {
                mejorExcentricidad = excentricidad;
                mejorIndice = columna;
            }
        }

        return ciudades.get(mejorIndice);
    }

    public boolean hayRuta(String origen, String destino) {
        return Double.isFinite(obtenerDistancia(origen, destino));
    }

    public double obtenerExcentricidad(String ciudad) {
        int indice = obtenerIndice(ciudad);
        return calcularExcentricidadColumna(indice);
    }

    public double[][] obtenerMatrizDistancias() {
        return copiarMatriz(distancias);
    }

    private int obtenerIndice(String ciudad) {
        Integer indice = indices.get(ciudad);
        if (indice == null) {
            throw new IllegalArgumentException("La ciudad no existe en el resultado: " + ciudad);
        }
        return indice;
    }

    private double calcularExcentricidadColumna(int columna) {
        double maximo = 0;

        for (int fila = 0; fila < ciudades.size(); fila++) {
            if (distancias[fila][columna] > maximo) {
                maximo = distancias[fila][columna];
            }
        }

        return maximo;
    }

    private double[][] copiarMatriz(double[][] matriz) {
        double[][] copia = new double[matriz.length][matriz.length];

        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                copia[fila][columna] = matriz[fila][columna];
            }
        }

        return copia;
    }

    private int[][] copiarMatriz(int[][] matriz) {
        int[][] copia = new int[matriz.length][matriz.length];

        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                copia[fila][columna] = matriz[fila][columna];
            }
        }

        return copia;
    }
}
