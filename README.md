# TP3
FABRETTI Florent, FABRETTI Vincent

# TODO faire des stats sur les algo

## Membres du binôme
- FABRETTI Florent
- FABRETTI Vincent


## Avancement – Algorithmes implémentés (4)

 Algorithmes                          
|-----------------------------
- Random BFS                          
- Random Edge Insertion               
- Aldous-Broder
- Random Minimum Weight Tree


## Comment compiler et exécuter le projet
1.  make compile; 
2.  make install; 
3.  make exec;
-   OU make all (pour faire les trois d'un coup)

-   make exec montre une notice d'utilisation intuitive et propose interactivement de d'executer le programme avec les argv de votre choix.

-   make demo execute 2 algo sur 2 types de graph

-   make test execute tous les algo sur des graph de type Grid


### Options d'execution (argv)
1.  Options pour l'algorithme (1er argument) :
	-   -b   -> Random BFS (par défaut)
	-   -e   -> Random Edge Insertion
	-   -a   -> Aldous-Broder
	-   -m   -> Random Minimum Weight Spanning Tree

2.  Options  pour le type de graphe (2ème argument) :
	-   -C   -> Graphe Complete
	-   -L   -> Graphe Lollipop
	-   -E   -> Graphe Erdos-Renyi
	-   (rien) -> Grid (labyrinthe) <- choix par défaut

    Exemples :
	-   -b -C      -> BFS sur graphe Complete
	-   -m         -> Min-Weight sur la grille (labyrinthe coloré)
	-   -e -L      -> Edge Insertion sur lollipop
	-   (rien)     -> BFS sur la grille

