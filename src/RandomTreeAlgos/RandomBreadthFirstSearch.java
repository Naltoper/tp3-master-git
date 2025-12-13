package RandomTreeAlgos;

import Graph.Arc;
import Graph.Graph;

import java.util.*;


/**
 * Produit un arbre couvrant aleatoire par un parcours
 * en profondeur randomisé par niveaux.
 */
public class RandomBreadthFirstSearch {

    Graph graph;
    ArrayList<Arc> tree;
    BitSet reached;
	List<Arc> currentFrontier = new ArrayList<>();
    List<Arc> nextFrontier = new ArrayList<>();

	private RandomBreadthFirstSearch (Graph graph) {
        this.graph = graph;
        this.tree = new ArrayList<>();
        this.reached = new BitSet(graph.order);
    }

	private void pushFrontier1(int vertex) {
		// Ajoute les arcs dans frontier
        for (Arc arc : graph.outEdges(vertex)) currentFrontier.add(arc);
	}

	private void pushFrontier2(int vertex) {
		// Ajoute les arcs dans frontier2
        for (Arc arc : graph.outEdges(vertex)) nextFrontier.add(arc);
	}
    
    private void explore(Arc nextArc) {
        if (reached.get(nextArc.getDest())) return; // Le bit et à 0 si le sommet et inconnu
        reached.set(nextArc.getDest()); // On met le bit a 1 pour dire qu'on l'a atteint
        tree.add(nextArc);
        pushFrontier2(nextArc.getDest()); // On push dans F2 pour ne pas melanger les niveaux
    }

    /*
    * Source : Nous meme a partir de la description donner du tp
     */
    private void bfs(int startingVertex) {
        reached.set(startingVertex);
		pushFrontier1(startingVertex);
		
		// Tant que les F1 et F2 ne sont pas vide en meme temps
		while (!currentFrontier.isEmpty() || !nextFrontier.isEmpty()){

            Collections.shuffle(currentFrontier); // On melange le niveau courrant

			while (!currentFrontier.isEmpty()) {
                // Met les arcs de niveau suivant dans F2
				explore(currentFrontier.getLast());
				currentFrontier.removeLast();	 
        	}
			currentFrontier.addAll(nextFrontier);    // Met F2 dans F1
			nextFrontier.clear();
		}
    }
    
    
	
    public static ArrayList<Arc> generateTree(Graph graph, int root) {
        RandomBreadthFirstSearch algo = new RandomBreadthFirstSearch(graph);
        algo.bfs(root);
        return algo.tree;
    }

}