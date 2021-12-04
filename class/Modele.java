import java.util.*;
import java.io.*;

public class Modele implements Serializable {

    private static final long serialVersionUID = 1L;

    //Gère l'emplacement des différentes pièces via un tableau
    public static Piece[][] plateau;
    private Jeu parent;
    public ArrayList<String> cimetiereB, cimetiereN;




    //Le constructeur initialise un tableau prédéfini
    public Modele(Jeu parent){
        this.parent = parent;
        plateau = new Piece[7][8];
        this.cimetiereB = new ArrayList<String>();
        this.cimetiereN = new ArrayList<String>();
        initialiserPlateau();
    }




    //On place les pions de la façon standart.
    public void initialiserPlateau(){
        boolean blanc = true;
        for (int i=0; i<7; i++){
            if(i == 5 || i == 6) blanc = true;
		    else if (i == 0 || i == 1) blanc = false;
            for (int j=0; j<8; j++){
                if ((i==1) || (i==5))
                    plateau[i][j] = new Pion(i, j, blanc);
                if ((i==0) || (i==6)){
                    if ((j==0) || (j==7))
                        plateau[i][j] = new Tour(i, j, blanc);
                    if ((j==1) || (j==6))
                        plateau[i][j] = new Cavalier(i, j, blanc);
                    if ((j==2) || (j==5))
                        plateau[i][j] = new Fou(i, j, blanc);
                    if (j==3)
                        plateau[i][j] = new Reine(i, j, blanc);
                    if (j==4)
                        plateau[i][j] = new Roi(i, j, blanc);
                }
            }
        }
    }




    //Met à jour le tableau avec les coordonnées d'avant et après déplacement. 
    //Change également les coordonées de la pièce pour qu'elle puisse se déplacer de nouveau.
    protected void misAJour(int xAv, int xAp, int yAv, int yAp){
        plateau[xAp][yAp] = plateau[xAv][yAv];
        plateau[xAv][yAv] = null;
        plateau[xAp][yAp].x = xAp;
        plateau[xAp][yAp].y = yAp;
    }



    //Test si le déplacement est possible par la piece
    protected boolean testDeplacement(Piece pieceAvant, int x, int y)
    {
        //On regarde la case de destination
        Piece pieceApres = plateau[x][y];

        //Si elle est vide ou de la couleur de l'adversaire
        if((pieceApres == null)
            || (pieceApres.blanc != pieceAvant.blanc))
        {
            //Alors, si le déplacement est autorisé par la pièce séléctionnée
            if(pieceAvant.deplace(x,y))
            {   
                //Et qu'il n'y a pas d'obstacle sur le chemin
                if(!obstacle(pieceAvant, x, y))
                {
                    //Alors on accepte le déplacement
                    return true;
                    //deplacement(pieceAvant, x, y);
                }
            }
        }
        return false;
    }



    //Quand toutes les conditions sont remplies, effectue le déplacement de la piece
    protected void deplacement(Piece p, int x, int y)
    {
        Piece piece = plateau[x][y];

        if((piece!=null)&&(!piece.nom.equals("Pion")))
            if(piece.blanc)
                cimetiereB.add(piece.nom);
            else
                cimetiereN.add(piece.nom);
        
        //On met à jour le tableau, la piece selectionnée va au coordonnées ciblées
        misAJour(p.x, x, p.y, y);

        //On affiche alors le plateau modifié
        afficher();
        parent.vue.AffichePlateau();

        //On change le tour, c'est à l'autre joueur de jouer
        parent.controller.tour = !parent.controller.tour; 

        //Si la piece de destination etait un roi, alors annonce la victoire
        if(piece!=null)
            if(piece.nom.equals("Roi"))
                Victoire(p.blanc);
            else
                System.out.println(p.nom + " mange " + piece.nom + " en " + x + " " + y);
        else
            System.out.println(p.nom + " va en " + x + " " + y);

        if(((x==0)||(x==6))&&(p.nom.equals("Pion")))
            Promotion(p);

        //Si c'est les blancs qui viennent de jouer, alors c'est au tour
        //Du joueur virtuel
        if(!parent.controller.tour)
            JoueurVirtuel();

        int tempsMax = 180;
        int tempsRest = 60*parent.vue.minute + parent.vue.seconde - 1;
        String couleur;
        if(!p.blanc)
            couleur="blanc";
        else
            couleur="noir";
        tempsRest = 180 - tempsRest;
        System.out.println("Temps de jeu du joueur " + couleur + " : " + tempsRest + " sec");

        //Remet le chrono à 3 minutes
        parent.vue.ResetChrono();
    }




    //Si un pion arrive sur la bonne ligne, alors test si une promotion est possible
    protected void Promotion(Piece p){

        if(((p.blanc)&&(!cimetiereB.isEmpty()))
        ||((!p.blanc)&&(!cimetiereN.isEmpty()))) 
            parent.vue.PromotionPopup(p);
    }
    


    //En cas de promotion, change le pion en la pièce choisie
    protected void ChangePiece(Piece p, String nom)
    {
        switch(nom) {
            case "Tour":
              plateau[p.x][p.y] = new Tour(p.x, p.y, p.blanc);
              break;
            case "Cavalier":
              plateau[p.x][p.y] = new Cavalier(p.x, p.y, p.blanc);
              break;
            case "Fou":
              plateau[p.x][p.y] = new Fou(p.x, p.y, p.blanc);
              break;
            case "Roi":
              plateau[p.x][p.y] = new Roi(p.x, p.y, p.blanc);
              break;
            case "Reine":
              plateau[p.x][p.y] = new Reine(p.x, p.y, p.blanc);
              break;
            default:
              plateau[p.x][p.y] = new Pion(p.x, p.y, p.blanc);
          }

          parent.vue.AffichePlateau();
    }



    //Déplace des pièces sur un plateau non visible
    protected Piece deplaceVirtuellement(Coup c, Piece[][] p){
        //On stock le contenu de la case cible
        Piece pieceStock = p[c.x][c.y];
        //On echange les cases de départ et d'arrivée
        p[c.x][c.y] = p[c.p.x][c.p.y];
        p[c.p.x][c.p.y] = null;
        //On retourne le contenu pour pouvoir annuler le déplacement
        return pieceStock;
    }

    //Annule un coup sur un plateau non visible
    protected void defaitVirtuellement(Coup c, Piece[][] p, Piece pieceStock){
        //On remet la piece à sa place initiale
        p[c.p.x][c.p.y] = p[c.x][c.y];
        //On remet la piece stockée à sa place
        p[c.x][c.y] = pieceStock;
    }


    //Calcul la valeur d'un plateau donné
    protected int calculValeur(Piece[][] p){
        int valTot = 0, val = 0;
        //Regarde toutes les cases du plateau
        for(int x=0; x<7; x++)
            for(int y=0; y<8; y++)
                //Si il y a une piece, donne sa valeur
                if(p[x][y]!=null){
                    switch(p[x][y].nom){
                        case "Pion":
                            val = 10;
                            break;
                        case "Cavalier":
                            val = 30;
                            break;
                        case "Fou":
                            val = 30;
                            break;
                        case "Tour":
                            val = 50;
                            break;
                        case "Reine":
                            val = 100;
                            break;
                        case "Roi":
                            val = 5000;
                            break;
                        default:
                            val = 0;
                            break;
                    }
                    //Si c'est une piece blanche, la soustrait au total
                    if(p[x][y].blanc)
                        valTot -= val;
                    //Si c'est une piece noir, l'ajoute
                    else
                        valTot += val;
                }
        return valTot;
    }


    //Algo MiniMax
    protected IntCoup MiniMax(Piece [][] plateauTest, int profondeur, boolean tourNoir, Coup cp){
        Piece pieceStock;
        IntCoup val;
        int valMax;
        ArrayList<Coup> listeCoup = new ArrayList<Coup>();

        //Au plus profond on calcul la valeur du plateau obtenu et on la remonte.
        if(profondeur == 0)
            return new IntCoup(calculValeur(plateauTest),cp);

        //Regarde la liste des coups possible pour le joueur dont c'est le tour
        listeCoup = getCoupsPossibles(plateauTest, tourNoir);

        //Si c'est l'IA, fixe la valeur très bas par défaut puis descend en profondeur en regardant le joueur
        if(tourNoir)
        {
            //Fixe la valeur très bas
            val = new IntCoup(-10000,cp);
            valMax = -10000;
            //Pour chaque coup possible
            for(Coup coupNoir : listeCoup)
            {
                //Effectue le coup, puis descend en profondeur
                pieceStock = deplaceVirtuellement(coupNoir, plateauTest);
                valMax = Math.max(val.i, MiniMax(plateauTest, profondeur-1, false, coupNoir).i);
                //stock la valeur max trouvée et le coup correspondant
                if(valMax>val.i){
                    val.coup = coupNoir;
                    val.i = valMax;
                }
                //Defait le coup testé
                defaitVirtuellement(coupNoir, plateauTest, pieceStock);
            } 
            //Retourne la valeur et le coup trouvé au niveau au dessus
            return val;
        }
        //Si c'est le joueur blanc, fixe la valeur très haut puis descend en regardant les coups de l'IA
        else
        {
            //Fixe la valeur très haut
            val = new IntCoup(10000,cp);
            valMax = 10000;
            //Pour chaque coup possible
            for(Coup coupBlanc : listeCoup)
            {
                //Effectue le coup, puis descend en profondeur
                pieceStock = deplaceVirtuellement(coupBlanc, plateauTest);
                valMax = Math.min(val.i, MiniMax(plateauTest, profondeur-1, true, coupBlanc).i);
                //stock la valeur minimum trouvée et le coup correspondant
                if(valMax<val.i){
                    val.coup = coupBlanc;
                    val.i = valMax;
                }
                //Defait le coup
                defaitVirtuellement(coupBlanc, plateauTest, pieceStock);
            } 
            //Retourne la valeur et le coup trouvé au niveau au dessus
            return val;
        }
    }


    //Algo AlphaBeta, même principe que MiniMax mais en évitant quelques test inutiles
    protected IntCoup AlphaBeta(Piece[][] plateauTest, int profondeur, int alpha, int beta, boolean tourNoir, Coup cp){
        Piece pieceStock;
        IntCoup val;
        int valMax;
        ArrayList<Coup> listeCoup = new ArrayList<Coup>();

        //Au plus profond on calcul la valeur du plateau obtenu et on la remonte.
        if(profondeur == 0)
            return new IntCoup(calculValeur(plateauTest),cp);

        //Regarde la liste des coups possible pour le joueur dont c'est le tour
        listeCoup = getCoupsPossibles(plateauTest, tourNoir);

        //Si c'est l'IA, fixe la valeur très bas par défaut puis descend en profondeur en regardant le joueur
        if(tourNoir)
        {
            //Fixe la valeur très bas
            val = new IntCoup(-10000,cp);
            valMax = -10000;
            //Pour chaque coup possible
            for(Coup coupNoir : listeCoup)
            {
                //Effectue le coup, puis descend en profondeur
                pieceStock = deplaceVirtuellement(coupNoir, plateauTest);
                valMax = Math.max(val.i, AlphaBeta(plateauTest, profondeur-1, alpha, beta, false, coupNoir).i);
                //stock la valeur max trouvée et le coup correspondant
                if(valMax>val.i){
                    val.coup = coupNoir;
                    val.i = valMax;
                }
                //Defait le coup testé
                defaitVirtuellement(coupNoir, plateauTest, pieceStock);
                alpha = Math.max(alpha,val.i);
                if(val.i>=beta)
                    break;
            } 
            //Retourne la valeur et le coup trouvé au niveau au dessus
            return val;
        }
        //Si c'est le joueur blanc, fixe la valeur très haut puis descend en regardant les coups de l'IA
        else
        {
            //Fixe la valeur très haut
            val = new IntCoup(10000,cp);
            valMax = 10000;
            //Pour chaque coup possible
            for(Coup coupBlanc : listeCoup)
            {
                //Effectue le coup, puis descend en profondeur
                pieceStock = deplaceVirtuellement(coupBlanc, plateauTest);
                valMax = Math.min(val.i, AlphaBeta(plateauTest, profondeur-1, alpha, beta, true, coupBlanc).i);
                //stock la valeur minimum trouvée et le coup correspondant
                if(valMax<val.i){
                    val.coup = coupBlanc;
                    val.i = valMax;
                }
                //Defait le coup
                defaitVirtuellement(coupBlanc, plateauTest, pieceStock);
                beta = Math.min(beta,val.i);
                if(val.i<=alpha)
                    break;
            } 
            //Retourne la valeur et le coup trouvé au niveau au dessus
            return val;
        }
    }



    public void JoueurVirtuel(){

        //Initialise la couleur à celle de l'IA
        boolean couleur = true;
        //Crée une copie du plateau sur laquelle l'IA va faire des tests
        Piece[][] plateauTest = plateau;
        //Contiendra la valeur max du plateau possible après les coups
        //Ainsi que les coups correspondants
        IntCoup valMax;

        //Définit la profondeur, plus ce sera grand, plus l'IA sera intelligente
        //Mais fera de calculs (De façon exponentielle)
        int profondeur = 5;


        //Lance l'algo MiniMax et retient le meilleur coup possible
        //valMax = MiniMax(plateauTest, profondeur, couleur, null);
        valMax = AlphaBeta(plateauTest, profondeur, -10000, 10000, couleur, null);

        //Joue le meilleur coup trouvé
        deplacement(valMax.coup.p, valMax.coup.x, valMax.coup.y);
    }


    //Retourne la liste des coups possible selon la couleur du joueur sous forme de liste
    protected ArrayList<Coup> getCoupsPossibles(Piece[][] p, boolean IA){
        ArrayList<Coup> coups = new ArrayList<Coup>();
        for (int xP=0; xP<7; xP++)
            for (int yP=0; yP<8; yP++)
                //Si c'est une piece noir, examine tout ses coups
                if ((p[xP][yP]!=null)&&(!p[xP][yP].blanc)&&(IA)){
                    for (int xC=0; xC<7; xC++)
                        for (int yC=0; yC<8; yC++) 
                            //Si le coup est légal
                            if(testDeplacement(p[xP][yP], xC, yC))
                                //L'ajoute à la liste
                                coups.add(new Coup(p[xP][yP], xC, yC));}
                else
                    if ((p[xP][yP]!=null)&&(p[xP][yP].blanc)&&(!IA)){
                        for (int xC=0; xC<7; xC++)
                            for (int yC=0; yC<8; yC++) 
                                //Si le coup est légal
                                if(testDeplacement(p[xP][yP], xC, yC))
                                    //L'ajoute à la liste
                                    coups.add(new Coup(p[xP][yP], xC, yC));}
        //Retourne la liste
        return coups;
    }


    protected void Victoire(boolean blanc)
    {
        if(blanc)
            System.out.println("Victoire des blancs!");
        else
            System.out.println("Victoire des noirs !");
        parent.vue.Fin(blanc);
    }




    //Simple affichage pour gérer de potentielles erreurs
    public void afficher(){
        for (int x = 0; x < 7; x++) {
            System.out.print("|");
            for (int y = 0; y < 8; y++) {
                if(plateau[x][y] == null) System.out.print("  |");
                    else{
                        if(plateau[x][y].nom.equals("Pion") && plateau[x][y].blanc == false) System.out.print("Pn|");
                        if(plateau[x][y].nom.equals("Pion") && plateau[x][y].blanc == true) System.out.print("Pb|");

                        if(plateau[x][y].nom.equals("Tour") && plateau[x][y].blanc == false) System.out.print("Tn|");
                        if(plateau[x][y].nom.equals("Tour") && plateau[x][y].blanc == true) System.out.print("Tb|");

                        if(plateau[x][y].nom.equals("Cavalier") && plateau[x][y].blanc == false) System.out.print("Cn|");
                        if(plateau[x][y].nom.equals("Cavalier") && plateau[x][y].blanc == true) System.out.print("Cb|");

                        if(plateau[x][y].nom.equals("Fou") && plateau[x][y].blanc == false) System.out.print("Fn|");
                        if(plateau[x][y].nom.equals("Fou") && plateau[x][y].blanc == true) System.out.print("Fb|");

                        if(plateau[x][y].nom.equals("Roi") && plateau[x][y].blanc == false) System.out.print("Rn|");
                        if(plateau[x][y].nom.equals("Roi") && plateau[x][y].blanc == true) System.out.print("Rb|");

                        if(plateau[x][y].nom.equals("Reine") && plateau[x][y].blanc == false) System.out.print("Qn|");
                        if(plateau[x][y].nom.equals("Reine") && plateau[x][y].blanc == true) System.out.print("Qb|");
                    }
            }
            System.out.println();
        }
    }




     //Vérifie si il y a des obstacle sur le chemin d'une pièce
     protected boolean obstacle(Piece p, int xAp, int yAp)
     {
         Piece[][] piece = plateau;
         Piece p2 = null;
         if (piece[xAp][yAp] != null)
             p2 = piece[xAp][yAp];
         int X = xAp;
         int Y = yAp;
 
         //Si la piece déplacé est une tour ou une reine, alors vérifie si il y a un
         //obstacle sur la ligne de déplacement
         if((p.nom.equals("Reine")) || (p.nom.equals("Tour")))
             //Déplacement vertical
             if(p.y == Y)
             {
                 //Si se déplace vers le haut
                 while(X > p.x){
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else X--;}
                 X = xAp;
                 //Si se déplace vers le bas
                 while(X < p.x){
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else X++;}
             }
             //Déplacement horizontal
             else if(p.x == X)
             {
                 //Si se déplace vers la droite
                 while(Y > p.y){
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else Y--;}
                 Y = yAp;
                 while(Y < p.y){
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else Y++;}
             }
 
         //Si la piece déplacé est un fou ou une reine, alors vérifie si il y a un
         //obstacle sur la diagonal de déplacement
         //On vérifie également que les x et y sont bien différents, pour ne pas créer de bug avec la 
         //Reine qui essaierait d'aller en ligne droite.
         if(((p.nom.equals("Reine")) || (p.nom.equals("Fou"))) && (xAp - p.x != 0) && (yAp - p.y != 0))
         {
             while(X>p.x){
                 while(Y>p.y)
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else {X--; Y--;}
                 while(Y<p.y)
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else {X--; Y++;}
             }
             while(X<p.x){
                 while(Y>p.y)
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else {X++; Y--;}
                 while(Y<p.y)
                     if((piece[X][Y] != null)&&(piece[X][Y] != p2)) return true;
                     else {X++; Y++;}
             }
         }
         return false;
     }




     //Serialisation pour la sauvegarde
     protected void Sauvegarder(){
         try{
             //On stock tout dans un fichier save.data
             FileOutputStream fos = new FileOutputStream("save.data");

             ObjectOutputStream oos = new ObjectOutputStream(fos);

             //On y met l'état actuel du plateau et du chrono
             //Ainsi que des cimetières pour la promotion
             oos.writeObject(plateau);
             oos.writeObject(this.cimetiereB);
             oos.writeObject(this.cimetiereN);
             oos.writeInt(parent.vue.minute);
             oos.writeInt(parent.vue.seconde);

             oos.close();

         } catch (IOException e) {
             e.printStackTrace();
         }
         
     }
     



     //On déserialise les données pour reprendre une partie
     protected void Charger(){
        try{
            FileInputStream fis = new FileInputStream("save.data");

            ObjectInputStream ois = new ObjectInputStream(fis);

            plateau = (Piece[][]) ois.readObject();
            @SuppressWarnings("unchecked")
            ArrayList<String> cB = (ArrayList<String>) ois.readObject();
            @SuppressWarnings("unchecked")
            ArrayList<String> cN = (ArrayList<String>) ois.readObject();
            int min = ois.readInt();
            int sec = ois.readInt();

            //On remet le chrono à son état avant sauvegarde
            parent.vue.SetChrono(min, sec);
            this.cimetiereB = cB;
            this.cimetiereN = cN;

            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //On réaffiche le plateau
        parent.vue.AffichePlateau();
     }
     



}
