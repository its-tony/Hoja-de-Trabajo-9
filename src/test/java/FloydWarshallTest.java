/*
 * Universidad del Valle de Guatemala
 * CC2003 Algoritmos y Estructuras de Datos
 * Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
 * Autor: Antony Portillo, Alejandro Rustrian
 * Descripcion: Pruebas unitarias para Floyd, rutas y centro del grafo
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class FloydWarshallTest {
    @Test
    public void encuentraDistanciaMasCortaConIntermedias() {
        GrafoDirigido grafo = crearGrafoDePrueba();
        FloydWarshall floyd = new FloydWarshall();

        ResultadoFloyd resultado = floyd.calcular(grafo);

        assertEquals(70, resultado.obtenerDistancia("Mixco", "SantaLucia"));
        assertIterableEquals(
                List.of("Mixco", "Antigua", "Escuintla", "SantaLucia"),
                resultado.obtenerRuta("Mixco", "SantaLucia"));
    }

    @Test
    public void reportaQueNoHayRutaCuandoNoExisteCamino() {
        GrafoDirigido grafo = new GrafoDirigido();
        grafo.agregarArco("A", "B", 5);
        grafo.agregarCiudad("C");

        ResultadoFloyd resultado = new FloydWarshall().calcular(grafo);

        assertTrue(Double.isInfinite(resultado.obtenerDistancia("C", "A")));
        assertTrue(resultado.obtenerRuta("C", "A").isEmpty());
    }

    @Test
    public void calculaCentroUsandoMaximoDeCadaColumna() {
        GrafoDirigido grafo = new GrafoDirigido();
        grafo.agregarArco("a", "b", 1);
        grafo.agregarArco("b", "c", 2);
        grafo.agregarArco("c", "d", 2);
        grafo.agregarArco("d", "b", 1);
        grafo.agregarArco("d", "c", 3);
        grafo.agregarArco("c", "e", 4);
        grafo.agregarArco("e", "d", 5);

        ResultadoFloyd resultado = new FloydWarshall().calcular(grafo);

        assertEquals("d", resultado.obtenerCentro());
        assertEquals(5, resultado.obtenerExcentricidad("d"));
    }

    private GrafoDirigido crearGrafoDePrueba() {
        GrafoDirigido grafo = new GrafoDirigido();
        grafo.agregarArco("Mixco", "Antigua", 30);
        grafo.agregarArco("Antigua", "Escuintla", 25);
        grafo.agregarArco("Escuintla", "SantaLucia", 15);
        grafo.agregarArco("Mixco", "SantaLucia", 100);
        return grafo;
    }
}
