# TP3
FABRETTI Florent, FABRETTI Vincent

# TODO faire des stats sur les algov

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


## Comparaisons des algorithmes
Algorithme,Option,Excentricité moyenne,Indice de Wiener moyen,Diamètre moyen,Nb. feuilles moyen,Nb. degré 2 moyen,Temps moyen (ms)
Random BFS,-b,83.13,20 256 059 000,335,4345,8431,27
Random Edge Insertion,-e,216.63,45 851 000 000,791,5193,7349,32
Aldous-Broder,-a,234.55,50 050 000 000,921,5018,7634,66
Random Min Weight,-m,202.33,43 924 000 000,802,5226,7285,45 # TODO
