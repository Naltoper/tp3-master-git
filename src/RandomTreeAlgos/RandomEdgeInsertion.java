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

        Collections.shuffle(allEdges);
        
        for (Arc e : allEdges) {
            int src = e.getSource();
            int dest = e.getDest();
            if (unionFind.union(src, dest)) {
                tree.add(e);
            }
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