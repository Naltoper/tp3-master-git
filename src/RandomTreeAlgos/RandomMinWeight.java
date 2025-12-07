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

    // TODO faire un parcour sur les poids et donner l'arbre couvrant
    // Note: The second step (finding the MST) would require an implementation 
    // of Kruskal's or Prim's algorithm, which is not present in the provided code.
    // For Kruskal's, you would sort all edges by their new weight [cite: 3] 
    // and then use a Disjoint Set Union (DSU) structure to build the tree.
  
public static ArrayList<Arc> generateTree(Graph graph, int root) {
        RandomMinWeight algo = new RandomMinWeight(graph);
        algo.assignRandomWeights();
        return algo.tree;
    }
}    