/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Calcula caminos minimos entre todos los pares de ciudades
 */

import java.util.List;

public class FloydWarshall {
    public ResultadoFloyd calcular(GrafoDirigido grafo) {
        double[][] distancias = copiarMatriz(grafo.obtenerMatrizAdyacencia());
        int[][] siguiente = inicializarSiguiente(distancias);
        int cantidad = grafo.cantidadCiudades();

        for (int intermedia = 0; intermedia < cantidad; intermedia++) {
            for (int origen = 0; origen < cantidad; origen++) {
                for (int destino = 0; destino < cantidad; destino++) {
                    if (!Double.isFinite(distancias[origen][intermedia])
                            || !Double.isFinite(distancias[intermedia][destino])) {
                        continue;
                    }

                    double posibleDistancia = distancias[origen][intermedia] + distancias[intermedia][destino];
                    if (posibleDistancia < distancias[origen][destino]) {
                        distancias[origen][destino] = posibleDistancia;
                        siguiente[origen][destino] = siguiente[origen][intermedia];
                    }
                }
            }
        }

        List<String> ciudades = grafo.obtenerCiudades();
        return new ResultadoFloyd(distancias, siguiente, ciudades);
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

    private int[][] inicializarSiguiente(double[][] matriz) {
        int[][] siguiente = new int[matriz.length][matriz.length];

        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[fila].length; columna++) {
                if (fila == columna) {
                    siguiente[fila][columna] = columna;
                } else if (Double.isFinite(matriz[fila][columna])) {
                    siguiente[fila][columna] = columna;
                } else {
                    siguiente[fila][columna] = -1;
                }
            }
        }

        return siguiente;
    }
}
