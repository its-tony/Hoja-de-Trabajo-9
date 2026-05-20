/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Menu de consola para consultar rutas, centro y cambios del grafo
 */

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private final GrafoDirigido grafo;
    private final FloydWarshall floyd;
    private final Scanner scanner;
    private ResultadoFloyd resultado;

    public MenuPrincipal(GrafoDirigido grafo) {
        this.grafo = grafo;
        this.floyd = new FloydWarshall();
        this.scanner = new Scanner(System.in);
        recalcularFloyd();
    }

    public void iniciar() {
        System.out.println("Bienvenido al sistema de rutas del Centro de Respuesta");
        System.out.println("Este grafo tiene " + grafo.cantidadCiudades() + " ciudades cargadas");
        mostrarMatrizAdyacencia();

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    consultarRuta();
                    break;
                case "2":
                    mostrarCentro();
                    break;
                case "3":
                    modificarGrafo();
                    break;
                case "4":
                    continuar = false;
                    System.out.println("Programa finalizado");
                    break;
                default:
                    System.out.println("Opcion no valida, intenta de nuevo");
                    break;
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("1. Consultar ruta mas corta");
        System.out.println("2. Mostrar centro del grafo");
        System.out.println("3. Modificar conexion entre ciudades");
        System.out.println("4. Finalizar");
        System.out.print("Elige una opcion: ");
    }

    private void consultarRuta() {
        System.out.print("Ciudad origen: ");
        String origen = scanner.nextLine().trim();
        System.out.print("Ciudad destino: ");
        String destino = scanner.nextLine().trim();

        try {
            if (!resultado.hayRuta(origen, destino)) {
                System.out.println("No existe una ruta disponible entre esas ciudades");
                return;
            }

            double distancia = resultado.obtenerDistancia(origen, destino);
            List<String> ruta = resultado.obtenerRuta(origen, destino);

            System.out.println("Distancia mas corta: " + formatearDistancia(distancia) + " km");
            System.out.println("Ruta: " + String.join(" -> ", ruta));
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }
    }

    private void mostrarCentro() {
        String centro = resultado.obtenerCentro();
        double excentricidad = resultado.obtenerExcentricidad(centro);

        System.out.println("Centro del grafo: " + centro);
        if (Double.isFinite(excentricidad)) {
            System.out.println("Excentricidad: " + formatearDistancia(excentricidad) + " km");
        } else {
            System.out.println("Excentricidad: infinito");
        }
    }

    private void modificarGrafo() {
        System.out.println("1. Hay interrupcion de trafico");
        System.out.println("2. Se establece una nueva conexion");
        System.out.print("Elige una opcion: ");
        String opcion = scanner.nextLine().trim();

        System.out.print("Ciudad origen: ");
        String origen = scanner.nextLine().trim();
        System.out.print("Ciudad destino: ");
        String destino = scanner.nextLine().trim();

        try {
            if ("1".equals(opcion)) {
                grafo.eliminarArco(origen, destino);
                System.out.println("Conexion eliminada entre " + origen + " y " + destino);
            } else if ("2".equals(opcion)) {
                System.out.print("Distancia en km: ");
                double distancia = Double.parseDouble(scanner.nextLine().trim());
                grafo.agregarArco(origen, destino, distancia);
                System.out.println("Conexion agregada entre " + origen + " y " + destino);
            } else {
                System.out.println("Opcion no valida");
                return;
            }

            recalcularFloyd();
            mostrarMatrizAdyacencia();
            mostrarCentro();
        } catch (IllegalArgumentException error) {
            System.out.println("No se pudo modificar el grafo: " + error.getMessage());
        }
    }

    private void recalcularFloyd() {
        this.resultado = floyd.calcular(grafo);
    }

    private void mostrarMatrizAdyacencia() {
        List<String> ciudades = grafo.obtenerCiudades();
        double[][] matriz = grafo.obtenerMatrizAdyacencia();

        System.out.println();
        System.out.println("Matriz de adyacencia");
        System.out.printf("%15s", "");
        for (String ciudad : ciudades) {
            System.out.printf("%15s", ciudad);
        }
        System.out.println();

        for (int fila = 0; fila < ciudades.size(); fila++) {
            System.out.printf("%15s", ciudades.get(fila));
            for (int columna = 0; columna < ciudades.size(); columna++) {
                if (Double.isFinite(matriz[fila][columna])) {
                    System.out.printf("%15s", formatearDistancia(matriz[fila][columna]));
                } else {
                    System.out.printf("%15s", "INF");
                }
            }
            System.out.println();
        }
    }

    private String formatearDistancia(double distancia) {
        if (distancia == Math.rint(distancia)) {
            return String.valueOf((int) distancia);
        }
        return String.format("%.2f", distancia);
    }
}
