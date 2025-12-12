# Le nom de votre classe principale
# Renommez si nécessaire
MAINCLASS=Main
## Le chemin vers où votre classe compilée est installée
# Renommez si nécessaire
INSTALLDIR=out/production/TP3
JARFILE=TP3RandomTrees

all: compile install exec

# Cible pour compiler
compile:
	cd src ; make compile

jar: compile
	cd $(INSTALLDIR); \
	echo Main-Class: $(subst /,.,$(MAINCLASS)) > manifest.txt ; \
	jar cvfm $(JARFILE).jar manifest.txt ./
	mv $(INSTALLDIR)/$(JARFILE).jar ./

install:
	cd src ; make install

clean:
	cd src ; make clean ; make cleanInstall
	rm *.zip *.jar manifest.*

# Cible qui explique comment executer et demande au user de choisir les argv
exec: jar
	@echo "==================================================================="
	@echo "                  Lancement du générateur d'arbres aléatoires"
	@echo "==================================================================="
	@echo ""
	@echo "Options pour l'algorithme (1er argument) :"
	@echo "   -b   -> Random BFS (par défaut)"
	@echo "   -e   -> Random Edge Insertion"
	@echo "   -a   -> Aldous-Broder"
	@echo "   -m   -> Random Minimum Weight Spanning Tree"
	@echo ""
	@echo "Options  pour le type de graphe (2ème argument) :"
	@echo "   -C   -> Graphe Complete"
	@echo "   -L   -> Graphe Lollipop"
	@echo "   -E   -> Graphe Erdos-Renyi"
	@echo "   (rien) -> Grid (labyrinthe) <- choix par défaut"
	@echo ""
	@echo "Exemples :"
	@echo "   -b -C      -> BFS sur graphe Complete"
	@echo "   -m         -> Min-Weight sur la grille (labyrinthe coloré)"
	@echo "   -e -L      -> Edge Insertion sur lollipop"
	@echo "   (rien)     -> BFS sur la grille"
	@echo ""
	@echo "==================================================================="
	@while true; do \
	    read -p "Vos arguments (ex: -m -C) ou Entrée pour défaut [-b] : " input; \
	    args=$$input; \
	    [ -z "$$args" ] && args=""; \
	    echo ""; \
	    echo "→ Lancement avec : java -jar $(JARFILE).jar $$args"; \
	    java -jar $(JARFILE).jar $$args; \
	    echo ""; \
	    read -p "Relancer avec d'autres paramètres ? (y/n) : " rerun; \
	    echo ""; \
	    case "$$rerun" in [Nn]*) break;; esac; \
	done

# Ou autrement
#exec:
#	java -classpath $(INSTALLDIR) $(MAINCLASS)

# Demarre automatiquement une demonstration de votre programme
# Il faut que cette demo soit convaincante
demo:
	@echo -e "\n================ DEMO ================ : "
	@echo -e "\nRandom trees sur graph de type Grid avec Labyrinth : "
	@echo -e "\n--- Random Edge Insertion ---"
	java -classpath $(INSTALLDIR) $(MAINCLASS) -e
	@echo -e "\nRandom trees sur graph de type Complete"
	@echo -e "\n--- Random BFS sur graph Complete ---"
	java -classpath $(INSTALLDIR) $(MAINCLASS) -b -C

# Executer automatiquent les test
test:
	@echo -e "\n================ TEST ================ : "
	@echo -e "\nLancement de tous les algo sur des graphs Grid : "
	@echo -e "\n--- Random BFS ---"
	java -classpath $(INSTALLDIR) $(MAINCLASS) -b
	@echo -e "\n--- Random Edge Insertion ---"
	java -classpath $(INSTALLDIR) $(MAINCLASS) -e
	@echo -e "\n--- Aldous Border ---"
	java -classpath $(INSTALLDIR) $(MAINCLASS) -a
	@echo -e "\n--- Random Min Weight ---"
	java -classpath $(INSTALLDIR) $(MAINCLASS) -m

# Cible pour créer son rendu de tp 
zip:
	moi=$$(whoami) ; zip -r $${moi}_renduTP2.zip *


# Cible pour vérifier le contenu de son rendu de tp 
zipVerify:
	moi=$$(whoami) ; unzip -l $${moi}_renduTP2.zip
