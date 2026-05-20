/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Grafo dirigido con pesos usando matriz de adyacencia
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GrafoDirigido {
    private static final double INF = Double.POSITIVE_INFINITY;
    private static final int CAPACIDAD_INICIAL = 4;

    private final List<String> ciudades;
    private final Map<String, Integer> indices;
    private double[][] matrizAdyacencia;

    public GrafoDirigido() {
        this.ciudades = new ArrayList<>();
        this.indices = new LinkedHashMap<>();
        this.matrizAdyacencia = new double[CAPACIDAD_INICIAL][CAPACIDAD_INICIAL];
        llenarConInfinito(this.matrizAdyacencia);
    }

    public void agregarCiudad(String nombre) {
        String ciudad = normalizarCiudad(nombre);
        if (indices.containsKey(ciudad)) {
            return;
        }

        asegurarCapacidad(ciudades.size() + 1);
        int indice = ciudades.size();
        ciudades.add(ciudad);
        indices.put(ciudad, indice);

        for (int i = 0; i < matrizAdyacencia.length; i++) {
            matrizAdyacencia[indice][i] = INF;
            matrizAdyacencia[i][indice] = INF;
        }
        matrizAdyacencia[indice][indice] = 0;
    }

    public void agregarArco(String origen, String destino, double distancia) {
        if (distancia < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        }

        agregarCiudad(origen);
        agregarCiudad(destino);

        int indiceOrigen = obtenerIndice(origen);
        int indiceDestino = obtenerIndice(destino);
        matrizAdyacencia[indiceOrigen][indiceDestino] = distancia;
    }

    public void eliminarArco(String origen, String destino) {
        int indiceOrigen = obtenerIndice(origen);
        int indiceDestino = obtenerIndice(destino);

        if (indiceOrigen == indiceDestino) {
            matrizAdyacencia[indiceOrigen][indiceDestino] = 0;
            return;
        }

        matrizAdyacencia[indiceOrigen][indiceDestino] = INF;
    }

    public boolean existeCiudad(String nombre) {
        return indices.containsKey(normalizarCiudad(nombre));
    }

    public boolean existeArco(String origen, String destino) {
        int indiceOrigen = obtenerIndice(origen);
        int indiceDestino = obtenerIndice(destino);
        return Double.isFinite(matrizAdyacencia[indiceOrigen][indiceDestino]);
    }

    public int obtenerIndice(String nombre) {
        String ciudad = normalizarCiudad(nombre);
        Integer indice = indices.get(ciudad);
        if (indice == null) {
            throw new IllegalArgumentException("La ciudad no existe en el grafo: " + ciudad);
        }
        return indice;
    }

    public String obtenerCiudad(int indice) {
        if (indice < 0 || indice >= ciudades.size()) {
            throw new IllegalArgumentException("El indice de ciudad no es valido: " + indice);
        }
        return ciudades.get(indice);
    }

    public List<String> obtenerCiudades() {
        return Collections.unmodifiableList(new ArrayList<>(ciudades));
    }

    public double[][] obtenerMatrizAdyacencia() {
        int cantidad = cantidadCiudades();
        double[][] copia = new double[cantidad][cantidad];

        for (int fila = 0; fila < cantidad; fila++) {
            for (int columna = 0; columna < cantidad; columna++) {
                copia[fila][columna] = matrizAdyacencia[fila][columna];
            }
        }

        return copia;
    }

    public int cantidadCiudades() {
        return ciudades.size();
    }

    public double obtenerInfinito() {
        return INF;
    }

    private void asegurarCapacidad(int capacidadNecesaria) {
        if (capacidadNecesaria <= matrizAdyacencia.length) {
            return;
        }

        int nuevaCapacidad = matrizAdyacencia.length * 2;
        while (nuevaCapacidad < capacidadNecesaria) {
            nuevaCapacidad *= 2;
        }

        double[][] nuevaMatriz = new double[nuevaCapacidad][nuevaCapacidad];
        llenarConInfinito(nuevaMatriz);

        for (int fila = 0; fila < ciudades.size(); fila++) {
            for (int columna = 0; columna < ciudades.size(); columna++) {
                nuevaMatriz[fila][columna] = matrizAdyacencia[fila][columna];
            }
        }

        this.matrizAdyacencia = nuevaMatriz;
    }

    private void llenarConInfinito(double[][] matriz) {
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                matriz[fila][columna] = INF;
            }
        }
    }

    private String normalizarCiudad(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la ciudad no puede estar vacio");
        }
        return nombre.trim();
    }
}
