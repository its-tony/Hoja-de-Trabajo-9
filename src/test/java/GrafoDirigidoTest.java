/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Pruebas unitarias para operaciones basicas del grafo
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GrafoDirigidoTest {
    @Test
    public void agregaCiudadesSinDuplicarlas() {
        GrafoDirigido grafo = new GrafoDirigido();

        grafo.agregarCiudad("Mixco");
        grafo.agregarCiudad("Mixco");

        assertEquals(1, grafo.cantidadCiudades());
        assertTrue(grafo.existeCiudad("Mixco"));
    }

    @Test
    public void agregaArcoYGuardaDistancia() {
        GrafoDirigido grafo = new GrafoDirigido();

        grafo.agregarArco("Mixco", "Antigua", 30);

        double[][] matriz = grafo.obtenerMatrizAdyacencia();
        assertEquals(30, matriz[grafo.obtenerIndice("Mixco")][grafo.obtenerIndice("Antigua")]);
        assertTrue(grafo.existeArco("Mixco", "Antigua"));
    }

    @Test
    public void eliminaArcoExistente() {
        GrafoDirigido grafo = new GrafoDirigido();

        grafo.agregarArco("Mixco", "Antigua", 30);
        grafo.eliminarArco("Mixco", "Antigua");

        assertFalse(grafo.existeArco("Mixco", "Antigua"));
    }

    @Test
    public void rechazaDistanciasNegativas() {
        GrafoDirigido grafo = new GrafoDirigido();

        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArco("A", "B", -1));
    }
}
