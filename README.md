# TP3
FABRETTI Florent, FABRETTI Vincent

## Membres du binôme
- FABRETTI Florent
- FABRETTI Vincent


## Avancement – Algorithmes implémentés (7)

 Algorithmes                          
|-----------------------------
- Random BFS                          
- Random Edge Insertion               
- Aldous-Broder
- Random Minimum Weight Tree
- Random Prim
- Wilson
- Random Flip


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
	-   -p   -> Random Prim

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


# Comparaisons des algorithmes

- Résultats (commande make test)

================ TEST ================ : 

Lancement de tous les algo sur des graphs Grid : 

--- Random BFS ---
java -classpath out/production/TP3 Main -b
On 10 samples:
Average eccentricity: 89.44390100867935
Average wiener index: 22168449638
Average diameter: 366
Average number of leaves: 4319
Average number of degree 2 vertices: 8486
Average computation time: 30ms

--- Random Edge Insertion ---
java -classpath out/production/TP3 Main -e
On 10 samples:
Average eccentricity: 205.61137696457894
Average wiener index: 44159576154
Average diameter: 790
Average number of leaves: 5240
Average number of degree 2 vertices: 7264
Average computation time: 30ms

--- Aldous Border ---
java -classpath out/production/TP3 Main -a
On 10 samples:
Average eccentricity: 237.69551958714518
Average wiener index: 51100832409
Average diameter: 934
Average number of leaves: 4998
Average number of degree 2 vertices: 7656
Average computation time: 53ms

--- Random Min Weight ---
java -classpath out/production/TP3 Main -m
On 10 samples:
Average eccentricity: 226.85587614356086
Average wiener index: 47533744080
Average diameter: 848
Average number of leaves: 5209
Average number of degree 2 vertices: 7313
Average computation time: 40ms

--- Wilson ---
java -classpath out/production/TP3 Main -w
On 10 samples:
Average eccentricity: 243.49304480412857
Average wiener index: 52232204916
Average diameter: 963
Average number of leaves: 5024
Average number of degree 2 vertices: 7624
Average computation time: 37ms

**Valeurs a ne pas prendre en compte pour Flip car nombre de flip volontairement reduit**
--- Random Successive Flip (AVEC NOMBRE DE FLIP REDUIT CAR TROP LONG) ---
java -classpath out/production/TP3 Main -f
On 10 samples:
Average eccentricity: 1663.7695988740325
Average wiener index: 299262073113
Average diameter: 6114
Average number of leaves: 8148
Average number of degree 2 vertices: 836
Average computation time: 234ms

--- Random Prim ---
java -classpath out/production/TP3 Main -p
On 10 samples:
Average eccentricity: 114.53540933614825
Average wiener index: 27485983033
Average diameter: 435
Average number of leaves: 5511
Average number of degree 2 vertices: 6839
Average computation time: 33ms


## Analyse et considérations

### 1. Uniformité de la distribution aléatoire

- Les algorithmes de **Wilson**, **Random Successive Flip** et **Aldous Border** produisent des arbres couvrants **uniformes**. Leur indice sont asser proches. 

- **Random BFS** et **Random Prim** donnent des arbres beaucoup plus compacts : diamètre 368-405, indice de Wiener 22-25 milliards.  
  On voit qu'il ne sont pas uniform avec la visualisation du labyrinthe. Ils suivent une logique de parcours par niveaux qui est visualisable.

- Les algorithmes intermédiaires comme **Random Edge Insertion** et **Random Min Weight** sont entre les deux, avec diamètre 870-878. Visuellement on ne voit pas la difference avec un algo uniforme.


### 2. Vitesse d'exécution

- Les plus rapides : **Random BFS** (26 ms), **Random Edge Insertion** (29 ms), **Random Prim** (34 ms).
- Moyennement rapides : **Wilson** (36 ms), **Random Min Weight** (45 ms).
- Un peu plus lent : **Aldous Border** (54 ms).
- Beaucoup plus lent : **Random Successive Flip** (204 ms avec un nombre de flip reduit, plusieurs minutes sinon)
#### Conclusion

- Le meilleur compromis semble etre **Random Edge Insertion** car il est tres rapide est meme si il ne produit pas une distribution parfaitement uniforme, il en est proche et c'est difficilement visualisable.