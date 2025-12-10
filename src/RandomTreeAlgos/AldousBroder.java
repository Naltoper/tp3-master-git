package RandomTreeAlgos;

import Graph.Arc;
import Graph.Graph;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;


public class AldousBroder {

    private final Graph graph;
    private final ArrayList<Arc> tree;
    private final BitSet reached;
    private final Random random;


    private AldousBroder(Graph graph) {
        this.graph = graph;
        this.tree = new ArrayList<>();
        this.reached = new BitSet(graph.order);
        this.random = new Random();
    }

    private void generateTree(int startingVertex) {

        // La marche commence sur le sommet de départ. Il est considéré comme atteint.
        int currentVertex = startingVertex;
        reached.set(startingVertex);
        int verticesReachedCount = 1;


        // Marche aléatoire
        // On continue tant que tous les sommets n'ont pas été atteints.
        while (verticesReachedCount < graph.order) {
            // Récupérer tous les arcs sortants du sommet actuel pour choisir un voisin.
            Arc[] outEdges = graph.outEdges(currentVertex);
            if (outEdges.length == 0) {
                break;
            }

            // Choisir un arc sortant aléatoire
            Arc nextArc = outEdges[random.nextInt(outEdges.length)];
            int nextVertex = nextArc.getDest();

            // Ajout à l'arbre
            // Si le sommet de destination n'a jamais été atteint
            if (!reached.get(nextVertex)) {
                // ...alors l'arc qui y mène fait partie de l'arbre couvrant.
                tree.add(nextArc);
                reached.set(nextVertex);
                verticesReachedCount++;
            }

            // Mise à jour
            currentVertex = nextVertex;
        }
    }

    public static ArrayList<Arc> generateTree(Graph graph, int root) {
        AldousBroder algo = new AldousBroder(graph);
        algo.generateTree(root);
        return algo.tree;
    }
}