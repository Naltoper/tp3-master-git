package RandomTreeAlgos;

import Graph.Arc;
import Graph.Edge;
import Graph.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RandomMinWeight {

    private Graph graph;
    private ArrayList<Arc> tree;
    private final static Random gen = new Random();

    public RandomMinWeight(Graph graph) {
        this.graph = graph;
        this.tree = new ArrayList<Arc>();
    }

    // Union-Find pour detecter les cycles
    private int find(int[] parent, int i) {
        if (parent[i] == i) {
            return i;
        }
        parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    /**
     * @return true si une union a eu lieu (pas de cycle), false sinon.
     */
    private boolean union(int[] parent, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);

        if (rootX != rootY) {
            parent[rootX] = rootY;
            return true;
        }
        return false;
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
        // 1. Collecter toutes les arêtes sous forme d'objets Edge (pas encore Arc)
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
        
        // 2. Trier toutes les arêtes (Edge) par poids
        // On trie toujours sur les poids aléatoires
        allEdges.sort((edge1, edge2) -> Double.compare(edge1.weight, edge2.weight)); // TODO peut etre utiliser des int pour les poids
        
        return allEdges;
    }

    // TODO faire un parcour sur les poids et donner l'arbre couvrant
    // Algo de Kruskal, source YOUTUBE : FAST CODE DEVELOPER, et Gemini
    public void doKurskal() {
        ArrayList<Edge> allEdges = getAllSortedEdges();

        // 3. Initialiser la structure DSU (Union-Find)
        int numVertices = graph.order;
        int[] parent = new int[graph.upperBound];
        for (int i = 0; i < graph.upperBound; i++) {
            parent[i] = i; 
        }

        // 4. Parcourir les arêtes triées et construire le MST
        int edgesCount = 0;
        
        for (Edge edge : allEdges) {
            if (edgesCount == numVertices - 1) {
                break; 
            }
            
            int u = edge.source;
            int v = edge.dest;
            
            // Si l'union réussit (pas de cycle)
            if (union(parent, u, v)) {
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