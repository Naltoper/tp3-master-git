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

# Cible qui explique comment executer # TODO afficher la notice et demander au user de choisir les argv
exec: jar
	java -jar $(JARFILE).jar

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
# On s'attend (d'habitude) que pour claque classe MaClasse il y ait une
# classe TestMaClasse qui verifie le bon comportment de chaque methode de la classe
# sur au moins une entrée
# A vous de completer
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
