package RandomTreeAlgos;
import Graph.Arc;
import Graph.Graph;
import Util.UnionFind;

import java.util.*;


public class RandomEdgeInsertion {
    Graph graph;
    ArrayList<Arc> tree;
    int[] parent;
    int[] rank;
    private UnionFind unionFind;


    private RandomEdgeInsertion(Graph graph) {
        this.graph = graph;
        this.tree = new ArrayList<>();
        parent = new int[graph.order];
        rank = new int[graph.order];
        for (int i = 0; i < graph.order; i++) {
            parent[i] = i;
        }
        this.unionFind = new UnionFind(graph.upperBound);
    }

    /*
    * Algo de Kurskal,
    * Source : cp-algorithms.com et Gemini
    */
    private void generate() {
        // recuperer toutes les arretes uniques
        ArrayList<Arc> allEdges = new ArrayList<>();
        for (int u = 0; u < graph.order; u++) {
            for (Arc a : graph.outEdges(u)) {
                if (a.getDest() > u) {
                    allEdges.add(a);
                }
            }
        }

        // Mélanger aléatoirement la liste des arêtes
        Collections.shuffle(allEdges);
        
        // Parcourir les arêtes dans l'ordre aléatoire
        for (Arc e : allEdges) {
            int src = e.getSource();
            int dest = e.getDest();
            // Ajouter l'arête à l'arbre si elle relie deux composantes connexes différentes
            if (unionFind.union(src, dest)) {
                tree.add(e);
            }
            // Arrêter dès que l'arbre contient n-1 arêtes
            if (tree.size() == graph.order - 1) {
                break;
            }
        }
    }

    public static ArrayList<Arc> generateTree(Graph graph, int root) {
        RandomEdgeInsertion algo = new RandomEdgeInsertion(graph);
        algo.generate();
        return algo.tree;
    }
}