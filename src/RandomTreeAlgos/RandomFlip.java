package RandomTreeAlgos;

import Graph.Arc;
import Graph.Graph;

import java.util.*;

/**
 * Produit un arbre couvrant aléatoire par flips successifs
 */
public class RandomFlip {

    Graph graph;
    int[] parent;
    Arc[] parentArc;
    int root;
    Random random;
    private Map<Integer, Map<Integer, Arc>> arcMap;

    private Set<Integer> rootChildren;

    private RandomFlip(Graph graph) {
        this.graph = graph;
        this.parent = new int[graph.order];
        this.parentArc = new Arc[graph.order];
        this.random = new Random();
        this.rootChildren = new HashSet<>();
        this.arcMap = new HashMap<>();
        buildArcMap();
    }

    /**
     * Pré-calcule la table de hachage des arcs pour un accès O(1) par (source, destination).
     */
    private void buildArcMap() {
        for (int u = 0; u < graph.order; u++) {
            arcMap.put(u, new HashMap<>());
            for (Arc arc : graph.outEdges(u)) {
                arcMap.get(u).put(arc.getDest(), arc);
            }
        }
    }

    /**
     * Construit un arbre couvrant initial par DFS simple
     */
    private void buildInitialTree(int startingVertex) {
        BitSet visited = new BitSet(graph.order);
        Stack<Integer> stack = new Stack<>();
        
        root = startingVertex;
        visited.set(startingVertex);
        parent[startingVertex] = -1;
        parentArc[startingVertex] = null;
        stack.push(startingVertex);
        
        rootChildren.clear(); // Vider avant de construire
        
        while (!stack.isEmpty()) {
            int current = stack.pop();
            
            for (Arc arc : graph.outEdges(current)) {
                int dest = arc.getDest();
                if (!visited.get(dest)) {
                    visited.set(dest);
                    parent[dest] = current;
                    parentArc[dest] = arc;
                    stack.push(dest);

                    if (current == root) {
                        rootChildren.add(dest); 
                    }
                }
            }
        }
    }

    /**
     * Effectue un flip : choisit une arête e incidente à la racine,
     * puis l'arête sortante de u, et met à jour l'arbre
     */
    private void flip() {
        // Choisir une arête e = ru incidente à la racine
        List<Arc> incidentArcs = new ArrayList<>();
        
        // Arcs sortants de la racine qui ne sont pas dans l'arbre
        for (Arc arc : graph.outEdges(root)) {
            int u = arc.getDest();
            if (parent[u] != root) { 
                incidentArcs.add(arc);
            }
        }
        
        // Arcs entrants vers la racine
        for (int v : rootChildren) {
            // Trouver l'arc v -> root dans le graphe initial
            Arc arc = arcMap.get(v).get(root); 
            if (arc != null) {
                incidentArcs.add(arc);
            }
        }

        if (incidentArcs.isEmpty()) return;

        // Choisir aléatoirement une arête e
        Arc e = incidentArcs.get(random.nextInt(incidentArcs.size()));
        int u = e.getSource() == root ? e.getDest() : e.getSource();

        // Trouver l'arc sortant de u dans T (arc vers le père de u), ePrime, et la racine R.
        int R = root;
        if (parent[u] == -1) return; 

        // Mettre à jour T : Inversion du chemin de u vers l'ancienne racine R
        int current = u;
        int prev = -1;
        int nextParent;
        
        // Maintien des enfants de la racine
        rootChildren.remove(u); 

        // Le coût de cette boucle devient O(Longueur du chemin) = O(|V|)
        while (current != R) {
            nextParent = parent[current];
            
            // Inversion
            parent[current] = prev;
            
            // Mise à jour de parentArc en O(1) grâce à arcMap
            if (prev != -1) {
                parentArc[current] = arcMap.get(current).get(prev);
            } else {
                parentArc[current] = null; // Nouvelle racine
            }
            
            prev = current;
            current = nextParent;
        }

        //  Mettre à jour la liaison (ancienne racine -> u)
        // L'ancienne racine R est maintenant l'enfant de u (prev)
        parent[R] = u;
        
        // Trouver l'arc de R vers u en O(1)
        parentArc[R] = arcMap.get(R).get(u);
        
        // Maintien des enfants de la racine
        rootChildren.add(R);
        
        // u devient la nouvelle racine
        root = u;
    }
        
    /**
     * Effectue numFlips flips successifs
     */
    private void performFlips(int numFlips) {
        for (int i = 0; i < numFlips; i++) {
            flip();
        }
    }

    /**
     * Construit l'arbre final sous forme de liste d'arcs
     */
    private ArrayList<Arc> buildTree() {
        ArrayList<Arc> tree = new ArrayList<>();
        
        for (int v = 0; v < graph.order; v++) {
            if (parentArc[v] != null) {
                tree.add(parentArc[v]);
            }
        }
        
        return tree;
    }

    /**
     * Version principale : génère un arbre couvrant aléatoire par flips.
     */
    public static ArrayList<Arc> generateTree(Graph graph, int startRoot, int numFlips) {
        RandomFlip randomFlip = new RandomFlip(graph);
        randomFlip.buildInitialTree(startRoot);
        randomFlip.performFlips(numFlips);
        return randomFlip.buildTree();
    }
    
    /**
     * Version avec nombre de flips par défaut = |V|^3
     */
    public static ArrayList<Arc> generateTree(Graph graph, int startRoot) {
        int numFlips = graph.order * graph.order * graph.order;
        return generateTree(graph, startRoot, numFlips);
    }
}