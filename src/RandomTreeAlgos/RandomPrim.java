package RandomTreeAlgos;

import Graph.Arc;
import Graph.Graph;

import java.util.*;

public class RandomPrim {

    Graph graph;
    List<Arc> frontier; 
    ArrayList<Arc> tree;
    BitSet reached;
    Random random;

    /**
     * Ajoute toutes les arêtes sortantes du sommet 'vertex' à la liste des candidats 'frontier'.
     */
    private void push(int vertex) {
        for (Arc arc : graph.outEdges(vertex)) {
            frontier.add(arc);
        }
    }

    /**
     * Explore une arête candidate et l'ajoute à l'arbre si elle mène à un nouveau sommet.
     * Source : Internet youtube et Gemini
     */
    private void explore() {
        if (frontier.isEmpty()) return;

        // Choisir une arête au hasard dans la 'frontier'
        int randomIndex = random.nextInt(frontier.size());
        Arc nextArc = frontier.get(randomIndex);
        
        // Retirer l'arête choisie de la frontière
        frontier.remove(randomIndex);


        // Vérifier si l'arête mène à un nouveau sommet (vérification de cycle)
        if (reached.get(nextArc.getDest())) {
            // Si le sommet de destination est déjà atteint, nous ignorons cette arête et passons à la suivante.
            if (!frontier.isEmpty()) explore(); 
            return;
        }

        // Ajouter l'arête à l'arbre et marquer le nouveau sommet comme atteint
        reached.set(nextArc.getDest());
        tree.add(nextArc);
        
        // Mettre à jour la frontière avec les arêtes sortantes du nouveau sommet
        push(nextArc.getDest());
    }

    private void randomPrim(int startingVertex) {
        // Initialiser avec le sommet de départ
        reached.set(startingVertex);
        
        // Ajouter les arêtes sortantes du sommet de départ à la frontière
        push(startingVertex);
        
        // Tant qu'il y a des arêtes candidates dans la frontière
        while (!frontier.isEmpty() && tree.size() < graph.order - 1) {
            explore();
        }
    }

    private RandomPrim(Graph graph) {
        this.graph = graph;
        // On utilise une List pour la "frontier" car on doit choisir un élément au hasard par index.
        this.frontier = new ArrayList<>(); 
        this.tree = new ArrayList<>();
        this.reached = new BitSet(graph.order);
        this.random = new Random();
    }

    public static ArrayList<Arc> generateTree(Graph graph, int root) {
        RandomPrim algo = new RandomPrim(graph);
        algo.randomPrim(root);
        return algo.tree;
    }
}