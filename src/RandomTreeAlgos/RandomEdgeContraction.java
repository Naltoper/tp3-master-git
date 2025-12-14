package RandomTreeAlgos;
import Graph.Graph;
import Graph.Edge;
import Graph.Arc;
import java.util.*;

public class RandomEdgeContraction {
    private static Random random = new Random();

    private static class UnionFind {
        int[] parent;
        int[] rank;
        int components;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
            components = size;
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px == py) return;
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else {
                parent[py] = px;
                if (rank[px] == rank[py]) rank[px]++;
            }
            components--;
        }
    }

    public static ArrayList<Arc> generateTree(Graph graph, int root) {
        UnionFind uf = new UnionFind(graph.upperBound);
        ArrayList<Edge> treeEdges = new ArrayList<>();
        ArrayList<Edge> allEdges = graph.getAllEdges(); // Assumes getAllEdges() is implemented in Graph as shown below

        while (uf.components > 1) {
            ArrayList<Edge> crossing = new ArrayList<>();
            for (Edge e : allEdges) {
                if (e.source != e.dest && uf.find(e.source) != uf.find(e.dest)) {
                    crossing.add(e);
                }
            }
            if (crossing.isEmpty()) {
                throw new RuntimeException("Graph is not connected");
            }
            int index = random.nextInt(crossing.size());
            Edge chosen = crossing.get(index);
            treeEdges.add(chosen);
            uf.union(chosen.source, chosen.dest);
        }

        // Build a new graph with the spanning tree edges
        Graph treeGraph = new Graph(graph.upperBound);
        treeGraph.order = graph.order;
        for (Edge e : treeEdges) {
            treeGraph.addEdge(e);
        }

        // Use BreadthFirstSearch to get oriented arcs from the root
        return BreadthFirstSearch.generateTree(treeGraph, root);
    }
}

