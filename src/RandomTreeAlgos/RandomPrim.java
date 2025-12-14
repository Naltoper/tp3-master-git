package RandomTreeAlgos;

import Graph.Arc;
import Graph.Graph;

import java.util.*;

public class RandomPrim {

    Graph graph;
    // Ensemble de toutes les Arcs candidates (sortant des sommets déjà atteints)
    List<Arc> frontier; 
    ArrayList<Arc> tree;
    BitSet reached;
    Random random;

    /**
     * Ajoute toutes les arêtes sortantes du sommet 'vertex' à la liste des candidats 'frontier'.
     */
    private void push(int vertex) {
        for (Arc arc : graph.outEdges(vertex)) {
            // Nous n'ajoutons que les arêtes menant à un sommet non encore atteint.
            // Cependant, pour simplifier, on peut les ajouter toutes et les filtrer lors de l'exploration
            // ou juste avant de les choisir. Pour le Prim Aléatoire, on maintient généralement un ensemble
            // de toutes les arêtes candidates, donc ajoutons-les toutes si la destination n'est pas dans l'arbre.
            // Une implémentation plus stricte (pour éviter les doublons/inutiles) vérifie le 'reached' ici.
            // Simplifions pour la cohérence de la structure avec votre BFS :
            frontier.add(arc);
        }
    }

    /**
     * Explore une arête candidate et l'ajoute à l'arbre si elle mène à un nouveau sommet.
     * Cette méthode est l'étape principale où l'on retire une arête au hasard du 'frontier'.
     */
    private void explore() {
        if (frontier.isEmpty()) return;

        // 1. Choisir une arête au hasard dans la 'frontier'
        int randomIndex = random.nextInt(frontier.size());
        Arc nextArc = frontier.get(randomIndex);
        
        // 2. Retirer l'arête choisie de la frontière
        // Cette étape est cruciale pour garantir l'uniformité du choix.
        // Remarque : Le retrait d'un élément au milieu d'une ArrayList est coûteux (O(n)).
        // Pour de meilleures performances, une structure comme un HashSet (pour les arêtes) et
        // un Iterator (pour un retrait rapide après le choix) serait meilleure, mais restons
        // sur l'ArrayList pour la structure.
        frontier.remove(randomIndex);


        // 3. Vérifier si l'arête mène à un nouveau sommet (vérification de cycle)
        if (reached.get(nextArc.getDest())) {
            // Si le sommet de destination est déjà atteint, nous ignorons cette arête et passons à la suivante.
            // Note : Pour garantir que l'arête *choisie* soit bien celle qui permet de progresser,
            // il est plus propre de relancer l'exploration jusqu'à ce que l'on trouve une arête
            // valide, ou de filtrer les arêtes dans la 'frontier' avant le choix.
            // Pour l'implémentation, simplifions par récursion (attention aux boucles infinies sur graphe non connexe).
            // Sur un graphe connexe, cela va converger :
            if (!frontier.isEmpty()) explore(); 
            return;
        }

        // 4. Ajouter l'arête à l'arbre et marquer le nouveau sommet comme atteint
        reached.set(nextArc.getDest());
        tree.add(nextArc);
        
        // 5. Mettre à jour la frontière avec les arêtes sortantes du nouveau sommet
        push(nextArc.getDest());
    }

    private void randomPrim(int startingVertex) {
        // 1. Initialiser avec le sommet de départ
        reached.set(startingVertex);
        
        // 2. Ajouter les arêtes sortantes du sommet de départ à la frontière
        push(startingVertex);
        
        // 3. Tant qu'il y a des arêtes candidates dans la frontière
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