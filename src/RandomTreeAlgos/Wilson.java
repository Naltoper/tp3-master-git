package RandomTreeAlgos;

import Graph.Arc;
import Graph.Graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * Implémentation de l'algorithme de Wilson pour la génération d'arbres 
 * couvrants uniformes (Uniform Spanning Tree).
 * * Principe : Marche aléatoire à effacement de boucles (Loop-Erased Random Walk).
 */
public class Wilson {

    /**
     * Génère un arbre couvrant aléatoire uniforme.
     * * @param graph Le graphe sur lequel construire l'arbre.
     * @return La liste des arcs constituant l'arbre.
     */
    public static ArrayList<Arc> generateTree(Graph graph, int root) {
        ArrayList<Arc> tree = new ArrayList<>();
        BitSet inTree = new BitSet(graph.upperBound); // Marque les sommets déjà dans l'arbre
        
        // Tableau pour stocker les "marques" du chemin en cours.
        // nextArc[u] stocke l'arc emprunté par la marche aléatoire depuis u.
        // C'est ce qui permet l'effacement de cycle simple décrit dans l'énoncé.
        Arc[] nextArc = new Arc[graph.upperBound];
        
        Random random = new Random();
        
        // 1. Choisir un sommet initial v (de préférence de degré maximum pour de meilleurs performances)
        root = -1;
        int maxDegree = -1;
        
        // On parcourt tous les sommets valides pour trouver le max degré
        for (int i = 0; i < graph.upperBound; i++) {
            if (graph.isVertex(i)) {
                int degree = graph.outEdges(i).length;
                if (degree > maxDegree) {
                    maxDegree = degree;
                    root = i;
                }
            }
        }
        
        // Initialisation de l'arbre avec la racine
        if (root != -1) {
            inTree.set(root);
        }

        // Boucle principale : tant que l'arbre ne couvre pas tous les sommets
        // On itère sur tous les sommets possibles pour trouver des 'u' hors de l'arbre.
        // L'ordre n'importe pas car la marche est aléatoire.
        for (int u = 0; u < graph.upperBound; u++) {
            
            // Si le sommet n'est pas valide ou est déjà dans l'arbre, on passe
            if (!graph.isVertex(u) || inTree.get(u)) continue;

            // Étape 1 : Choisir un sommet u qui n'est pas dans l'arbre (ici 'curr')
            int curr = u;

            // Étape 2 : Faire une marche aléatoire jusqu'à atteindre l'arbre
            while (!inTree.get(curr)) {
                Arc[] neighbors = graph.outEdges(curr);
                
                // Précaution si le graphe n'est pas connexe ou sommet isolé
                if (neighbors.length == 0) break; 
                
                // Choix aléatoire du voisin
                Arc move = neighbors[random.nextInt(neighbors.length)];
                
                // Étape 3 (partielle) : Marquer le sommet avec l'arête.
                // Si on repasse par 'curr' plus tard dans cette marche, 
                // cette valeur sera écrasée, effaçant ainsi le cycle automatiquement.
                nextArc[curr] = move;
                
                // Avancer
                curr = move.getDest();
            }

            // Si on a atteint l'arbre (et non une impasse), on ajoute le chemin
            if (inTree.get(curr)) {
                // Étape 4 : Ajouter ce chemin (et ses sommets) à l'arbre.
                // On repart de u et on suit les marques jusqu'à toucher l'arbre.
                int pathCurr = u;
                while (!inTree.get(pathCurr)) {
                    Arc arcToAdd = nextArc[pathCurr];
                    
                    // Ajout à la solution
                    tree.add(arcToAdd);
                    
                    // Le sommet fait maintenant partie de l'arbre
                    inTree.set(pathCurr);
                    
                    // Avancer en suivant la marque
                    pathCurr = arcToAdd.getDest();
                }
            }
        }

        return tree;
    }
}