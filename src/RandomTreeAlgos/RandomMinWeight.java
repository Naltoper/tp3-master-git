package RandomTreeAlgos;

import Graph.Arc;
import Graph.Edge;
import Graph.Graph;
import Util.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RandomMinWeight {

    private Graph graph;
    private ArrayList<Arc> tree;
    private final static Random gen = new Random();
    private UnionFind unionFind;

    public RandomMinWeight(Graph graph) {
        this.graph = graph;
        this.tree = new ArrayList<Arc>();
        this.unionFind = new UnionFind(graph.upperBound);
    }

    public void assignRandomWeights() {
        // Pour chaque sommet on lui donne un poids aleatoire
        for (int v = 0; v < graph.upperBound; v++) {
            
            if (graph.isVertex(v)) {
                LinkedList<Edge> adjacencyList = graph.incidency.get(v);

                for (Edge e : adjacencyList) {
                    // On verifie que v est le sommet de numero min 
                    // pour ne pas passer 2 fois sur la meme edge
                    if (v == Math.min(e.source, e.dest)) {
                        e.weight = gen.nextInt();
                    }
                }
            }
        }
    }

    public ArrayList<Edge> getAllSortedEdges() {
        // Collecter toutes les arêtes sous forme d'objets Edge (pas encore Arc)
        ArrayList<Edge> allEdges = new ArrayList<>();
        for (int v = 0; v < graph.upperBound; v++) {
            if (graph.isVertex(v)) {
                LinkedList<Edge> adjacencyList = graph.incidency.get(v);

                // On s'assure de ne pas ajouter 2 fois le meme Edge
                for (Edge e : adjacencyList) {
                    if (v == Math.min(e.source, e.dest)) {
                        // On ajoute directement l'objet Edge
                        allEdges.add(e);
                    }
                }
            }
        }
        
        // Trier toutes les arêtes (Edge) par poids
        allEdges.sort((edge1, edge2) -> Double.compare(edge1.weight, edge2.weight));
        
        return allEdges;
    }


    // Algo de Kruskal, source YOUTUBE : FAST CODE DEVELOPER, et Gemini
    public void doKurskal() {
        ArrayList<Edge> allEdges = getAllSortedEdges();

        int numVertices = graph.order;

        // Parcourir les arêtes triées et construire le MST
        int edgesCount = 0;
        
        for (Edge edge : allEdges) {
            if (edgesCount == numVertices - 1) {
                break; 
            }
            
            int u = edge.source;
            int v = edge.dest;
            
            // Si l'union réussit (pas de cycle)
            if (unionFind.union(u, v)) {
                // On converti en Arc pour l'ajouter au tree
                Arc arc = new Arc(edge, false); 
                this.tree.add(arc);
                edgesCount++;
            }
        }
    }
  



public static ArrayList<Arc> generateTree(Graph graph, int root) {
        RandomMinWeight algo = new RandomMinWeight(graph);
        algo.assignRandomWeights();
        algo.doKurskal();
        return algo.tree;
    }
}    