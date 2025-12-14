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
	-   -w   -> Wilson

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


#### Comparaisons des algorithmes
Les tests ont été effectués sur 10 échantillons pour chaque algorithme.

Résultats :

- Random BFS (-b)
  Excentricité moyenne : 87.26
  Indice de Wiener       : 21 898 474 120
  Diamètre               : 358
  Feuilles               : 4326
  Degré 2                : 8464
  Temps                  : 26 ms

- Random Edge Insertion (-e)
  Excentricité moyenne : 88.50
  Indice de Wiener       : 21 925 036 707
  Diamètre               : 366
  Feuilles               : 4337
  Degré 2                : 8446
  Temps                  : 27 ms

- Aldous-Broder (-a)
  Excentricité moyenne : 277.38
  Indice de Wiener       : 58 023 909 847
  Diamètre               : 1086
  Feuilles               : 5011
  Degré 2                : 7635
  Temps                  : 51 ms

- Random Min Weight (-m)
  Excentricité moyenne : 213.64
  Indice de Wiener       : 46 157 032 238
  Diamètre               : 787
  Feuilles               : 5219
  Degré 2                : 7293
  Temps                  : 40 ms

- Wilson (-w)
  Excentricité moyenne : 261.62
  Indice de Wiener       : 55 262 033 072
  Diamètre               : 1015
  Feuilles               : 5023
  Degré 2                : 7619
  Temps                  : 38 ms

- Random Successive Flip (-f) (Avec un nombre redui de flip car trop trop long sinon)
  Excentricité moyenne : 1438.63
  Indice de Wiener       : 253 294 805 511
  Diamètre               : 5187
  Feuilles               : 8100
  Degré 2                : 936
  Temps                  : 224 ms

Analyse :

Les algorithmes Random BFS et Random Edge Insertion produisent des arbres très similaires, avec les valeurs les plus basses pour l'excentricité, le diamètre et l'indice de Wiener. Ces arbres sont compacts et proches de l'arbre couvrant de distance minimale.

Aldous-Broder et Wilson génèrent des arbres plus étirés (diamètre et Wiener environ 2-3 fois plus grands), avec davantage de sommets de degré intermédiaire.

Random Min Weight se situe entre ces deux groupes.

Random Successive Flip produit des arbres extrêmement allongés, avec un diamètre et un indice de Wiener très élevés, beaucoup plus de feuilles et très peu de sommets de degré 2. Le temps de calcul est nettement supérieur.

Les algorithmes BFS et Edge Insertion sont les plus rapides, suivis de Wilson et Random Min Weight. Aldous-Broder est légèrement plus lent, et Successive Flip est significativement plus coûteux.
