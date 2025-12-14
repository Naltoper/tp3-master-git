package Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class Graph {
    // classe de graphe non orientés permettant de manipuler
    // en même temps des arcs (orientés)
    // pour pouvoir stocker un arbre couvrant, en plus du graphe

    public int order;
    public int upperBound;
    int edgeCardinality;

    public ArrayList<LinkedList<Edge>> incidency;
    public ArrayList<LinkedList<Arc>> inIncidency;
    public ArrayList<LinkedList<Arc>> outIncidency;

    public Graph(int upperBound) {
        // Au début, upperBound==order
        // Ensuite, on pourrait retirer des sommets du graphe.
        // Ainsi, on pourrait avoir upperBound > order
        // Cette modification de la classe devient nécessaire
        // si vous implémentez
        // ou l'algorithme de génération d'arbre couvrant
        // par suppression de sommet, ou l'opération de contraction d’arête.
        // Autrement, on pourra asssumer que upperBound==order.

        // à compléter
        this.upperBound = upperBound;
        this.order = upperBound;
        this.edgeCardinality = 0;

        // Initialisation des listes d'adjacences pour tous les sommets initiaux
        incidency = new ArrayList<>(upperBound);
        inIncidency = new ArrayList<>(upperBound);
        outIncidency = new ArrayList<>(upperBound);

        for (int i = 0; i < upperBound; i++) {
            incidency.add(new LinkedList<>());
            inIncidency.add(new LinkedList<>());
            outIncidency.add(new LinkedList<>());
        }
    }

    public boolean isVertex(int vertex) {
        // Après avori supprimé certains sommets
        // pas tous le sommets numerotés 0,...,n-1 sont 'vivant'.

        // à compléter

        // Un sommet est valide si il est dans la plage initialisée
        return vertex >= 0 && vertex < upperBound;
    }

    public void addVertex(int vertex) {
        // à compléter
        // modifier order et upperbound (nombre de sommets ajouté depuis le debut)

        // Verif que les listes sont assez grandes
        ensureVertex(vertex);

        this.order = Math.max(this.order, vertex + 1);
    }

   public void deleteVertex(int vertex) {
        if (!isVertex(vertex)) {
            return; // Vertex doesn't exist or already deleted
        }
        
        // Remove all incident edges (undirected)
        LinkedList<Edge> edgesToRemove = new LinkedList<>(incidency.get(vertex));
        for (Edge edge : edgesToRemove) {
            // Remove edge from the other endpoint's incidency list
            int otherVertex = edge.oppositeExtremity(vertex);
            incidency.get(otherVertex).remove(edge);
            edgeCardinality--;
        }
        
        // Clear all adjacency lists for this vertex
        incidency.get(vertex).clear();
        inIncidency.get(vertex).clear();
        outIncidency.get(vertex).clear();
        
        // Decrement order (number of active vertices)
        order--;
    }

    public void ensureVertex(int vertex) {
        // Synonyme de addVertex ?
        // à compléter
        // Assure que les listes d'adjacences sont assez grandes pour l'indice 'vertex'
        while (incidency.size() <= vertex) {
            incidency.add(new LinkedList<>());
            inIncidency.add(new LinkedList<>());
            outIncidency.add(new LinkedList<>());
            this.upperBound = incidency.size();
        }
        
    }

    public void addArc(Arc arc) {
        // à compléter
        // ajoute a in et/ou ou et ajoute a edje
        ensureVertex(arc.getSource());
        ensureVertex(arc.getDest());

        outIncidency.get(arc.getSource()).add(arc);
        inIncidency.get(arc.getDest()).add(arc);

    }

    public void addEdge(Edge edge) {
        // NOUVEAU: Assurer que les sommets source et dest sont dans la plage valide
        ensureVertex(edge.source);
        ensureVertex(edge.dest);
        
        // Ajouter à la liste d'incidency non orientée
        incidency.get(edge.source).add(edge);
        incidency.get(edge.dest).add(edge);

        // Creer et ajouter deux arces orientés
        Arc arc1 = new Arc(edge, false);
        addArc(arc1);
        
        Arc arc2 = new Arc(edge, true);
        addArc(arc2);

        // Mise a jour de la cardinalité
        edgeCardinality++;

    }

    public Arc[] outEdges(int vertex) {
        // à modifier, si nécessaire

        // Pour la prochaine ligne voir
        // https://www.baeldung.com/java-collection-toarray-methods
        return outIncidency.get(vertex).toArray(new Arc[0]);
   }



    public ArrayList<Edge> getAllEdges() {
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < upperBound; i++) {
            if (isVertex(i)) {
                for (Edge e : incidency.get(i)) {
                    edges.add(e);
                }
            }
        }
        return new ArrayList<>(edges);
}

}