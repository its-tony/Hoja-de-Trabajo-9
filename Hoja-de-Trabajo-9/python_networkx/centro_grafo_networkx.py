"""
Universidad del Valle de Guatemala
CC2003 Algoritmos y Estructuras de Datos
Hoja de Trabajo 10 Grafos dirigidos y algoritmo de Floyd
Autor: Entrega individual
Descripcion: Version opcional en Python usando NetworkX
"""

from __future__ import annotations

import math
import sys
from pathlib import Path

try:
    import networkx as nx
    from networkx.algorithms.shortest_paths.dense import reconstruct_path
except ImportError:
    print("NetworkX no esta instalado")
    print("Instalalo con: python -m pip install networkx")
    sys.exit(1)


def leer_grafo(ruta_archivo: str) -> nx.DiGraph:
    grafo = nx.DiGraph()
    ruta = Path(ruta_archivo)

    with ruta.open("r", encoding="utf-8") as archivo:
        for numero_linea, linea in enumerate(archivo, start=1):
            linea = linea.strip()

            if not linea or linea.startswith("#"):
                continue

            partes = linea.split()
            if len(partes) != 3:
                raise ValueError(f"Linea {numero_linea} invalida: {linea}")

            origen, destino, distancia_texto = partes
            distancia = float(distancia_texto)
            grafo.add_edge(origen, destino, weight=distancia)

    return grafo


def calcular_centro(grafo: nx.DiGraph, distancias: dict) -> str:
    mejor_ciudad = None
    mejor_excentricidad = math.inf

    for ciudad_destino in grafo.nodes:
        excentricidad = 0
        for ciudad_origen in grafo.nodes:
            distancia = distancias.get(ciudad_origen, {}).get(ciudad_destino, math.inf)
            excentricidad = max(excentricidad, distancia)

        if excentricidad < mejor_excentricidad:
            mejor_excentricidad = excentricidad
            mejor_ciudad = ciudad_destino

    return mejor_ciudad or "No hay ciudades en el grafo"


def mostrar_ruta(predecesores: dict, distancias: dict, origen: str, destino: str) -> None:
    distancia = distancias.get(origen, {}).get(destino, math.inf)

    if math.isinf(distancia):
        print(f"No hay ruta entre {origen} y {destino}")
        return

    ruta = reconstruct_path(origen, destino, predecesores)
    print(f"Distancia mas corta: {distancia:g} km")
    print("Ruta:", " -> ".join(ruta))


def main() -> None:
    ruta_archivo = sys.argv[1] if len(sys.argv) > 1 else "guategrafo.txt"
    grafo = leer_grafo(ruta_archivo)
    predecesores, distancias = nx.floyd_warshall_predecessor_and_distance(grafo, weight="weight")
    centro = calcular_centro(grafo, distancias)

    print(f"Ciudades cargadas: {grafo.number_of_nodes()}")
    print(f"Arcos cargados: {grafo.number_of_edges()}")
    print(f"Centro del grafo: {centro}")

    if len(sys.argv) == 4:
        mostrar_ruta(predecesores, distancias, sys.argv[2], sys.argv[3])


if __name__ == "__main__":
    main()
