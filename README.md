# Echecs

Pour lancer le jeu :
    Se placer dans /class
    Compiler : javac *.java
    Executer : java Jeu

Modele :
    Tout ce qui concerne le placement des pieces, leur deplacement, tout calcul
    se trouve dans le modele.java ou dans les pieces.java pour leur deplacement
Vue : 
    Tout ce qui concerne l'affichage est dans vue.java
Controller :
    Toutes les écoutes de click sont dans controller.java
Main : 
    Le main se trouve dans jeu.java et permet de tout lancer à la foi
Sauvegarde :
    La sauvegarde et le chargement se font depuis modele.java
    La sauvegarde se fait dans save.data

Check : 
    Affiche le plateau et les diffèrentes pièces.
    Les déplacements fonctionnent et sont propres à chaque pièce.
    La prise de pièce adverse est prise en compte.
    Le comportement spécial du pion fonctionne.
    Gestion des collisions ok.
    Gestion des tours.
    Refonte du modèle MVC.
    Joueur virtuel ok.
    Promotion ok. dans une pop-up
    Refonte visuelle et ajout de marges autour du plateau.
    Timer ok.
    Ajout d'options et de sauvegarde avec serialisation.
    Correction de bugs, normalement tout est bon !
    Commentaires à jour
    IA Prend la meilleur pièce blanche quand elle peut.
    L'IA s'exerce sur un plateau virtuel
    L'algo MiniMax est fonctionnel
    L'algo AlphaBeta est fonctionnel