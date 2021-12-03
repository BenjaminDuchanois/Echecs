import java.util.*;

public class Modele {
    //Gère l'emplacement des différentes pièces via un tableau
    public Piece[][] plateau;
    private Jeu parent;
    public List<String> cimetiereB, cimetiereN;




    //Le constructeur initialise un tableau prédéfini
    public Modele(Jeu parent){
        this.parent = parent;
        this.plateau = new Piece[7][8];
        this.cimetiereB = new ArrayList<String>();
        this.cimetiereN = new ArrayList<String>();
        initialiserPlateau();
    }




    //On place les pions de la façon standart. La couleur des pièces est directement géré selon leur ligne.
    public void initialiserPlateau(){
        boolean blanc = true;
        for (int i=0; i<7; i++){
            if(i == 5 || i == 6) blanc = true;
		    else if (i == 0 || i == 1) blanc = false;
            for (int j=0; j<8; j++){
                if ((i==1) || (i==5))
                    this.plateau[i][j] = new Pion(i, j, blanc, this.parent);
                if ((i==0) || (i==6)){
                    if ((j==0) || (j==7))
                        this.plateau[i][j] = new Tour(i, j, blanc);
                    if ((j==1) || (j==6))
                        this.plateau[i][j] = new Cavalier(i, j, blanc);
                    if ((j==2) || (j==5))
                        this.plateau[i][j] = new Fou(i, j, blanc);
                    if (j==3)
                        this.plateau[i][j] = new Reine(i, j, blanc);
                    if (j==4)
                        this.plateau[i][j] = new Roi(i, j, blanc);
                }
            }
        }
    }




    //Met à jour le tableau avec les coordonnées d'avant et après déplacement. 
    //Change également les coordonées de la pièce pour qu'elle puisse se déplacer de nouveau.
    protected void misAJour(int xAv, int xAp, int yAv, int yAp){
        this.plateau[xAp][yAp] = this.plateau[xAv][yAv];
        this.plateau[xAv][yAv] = null;
        this.plateau[xAp][yAp].x = xAp;
        this.plateau[xAp][yAp].y = yAp;
    }




    protected void testDeplacement(Piece pieceAvant, int x, int y)
    {
        //On regarde la case de destination
        Piece pieceApres = this.plateau[x][y];

        //Si elle est vide ou de la couleur de l'adversaire
        if((pieceApres == null)
            || (pieceApres.blanc != pieceAvant.blanc))
        {
            //Alors, si le déplacement est autorisé par la pièce séléctionnée
            if(pieceAvant.deplace(x,y))
            {   
                System.out.println("Deplacement autorise.");
                //Et qu'il n'y a pas d'obstacle sur le chemin
                if(obstacle(pieceAvant, x, y))
                    System.out.println("Mais il y a un obstacle..");
                else
                {
                    if((pieceApres!=null)&&(pieceApres.nom!="Pion"))
                        if(pieceApres.blanc)
                            cimetiereB.add(pieceApres.nom);
                        else
                            cimetiereN.add(pieceApres.nom);

                    //Alors on accepte le déplacement
                    deplacement(pieceAvant, x, y);
                }
            }
            else
                //Sinon on indique que le déplacement n'est pas possible
                System.out.println("Deplacement impossible " + x + " " + y);
        }
        else    //Sinon on indique une erreur
                System.out.println("Cette piece est a vous " + x + " " + y);
    }




    protected void deplacement(Piece p, int x, int y)
    {
        Piece piece = this.plateau[x][y];
        
        //On met à jour le tableau, la piece selectionnée va au coordonnées ciblées
        misAJour(p.x, x, p.y, y);

        //On affiche alors le plateau modifié
        afficher(plateau);
        parent.vue.AffichePlateau(plateau);

        //On change le tour, c'est à l'autre joueur de jouer
        parent.controller.tour = !parent.controller.tour; 

        //Si la piece de destination etait un roi, alors annonce la victoire
        if(piece!=null)
            if(piece.nom=="Roi")
                Victoire(p.blanc);
            else
                System.out.println(p.nom + " mange " + piece.nom + " en " + x + " " + y);
        else
            System.out.println(p.nom + " va en " + x + " " + y);

        if(((x==0)||(x==6))&&(p.nom=="Pion"))
            Promotion(p);

        //Si c'est les blancs qui viennent de jouer, alors c'est au tour
        //Du joueur virtuel
        if(!parent.controller.tour)
            JoueurVirtuel(this.plateau);
    }





    protected void Promotion(Piece p){

        if(((p.blanc)&&(!cimetiereB.isEmpty()))
        ||((!p.blanc)&&(!cimetiereN.isEmpty()))) 
            parent.vue.PromotionPopup(p);
    }
    



    protected void ChangePiece(Piece p, String nom)
    {
        switch(nom) {
            case "Tour":
              this.plateau[p.x][p.y] = new Tour(p.x, p.y, p.blanc);
              break;
            case "Cavalier":
              this.plateau[p.x][p.y] = new Cavalier(p.x, p.y, p.blanc);
              break;
            case "Fou":
              this.plateau[p.x][p.y] = new Fou(p.x, p.y, p.blanc);
              break;
            case "Roi":
              this.plateau[p.x][p.y] = new Roi(p.x, p.y, p.blanc);
              break;
            case "Reine":
              this.plateau[p.x][p.y] = new Reine(p.x, p.y, p.blanc);
              break;
            default:
              this.plateau[p.x][p.y] = new Pion(p.x, p.y, p.blanc, parent);
          }

          parent.vue.AffichePlateau(this.plateau);
    }



    public void JoueurVirtuel(Piece[][] plateau){
        int xCible, yCible;
        Random random = new Random();
        int xNb, yNb, xNbApres, yNbApres;
        while(!parent.controller.tour)
        {
            xNb = random.nextInt(7);
            yNb = random.nextInt(6);
            if((plateau[xNb][yNb] != null)&&(!plateau[xNb][yNb].blanc))
                {
                    xNbApres = random.nextInt(7);
                    yNbApres = random.nextInt(6);
                    testDeplacement(plateau[xNb][yNb], xNbApres, yNbApres);
                }
        }
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
    public void afficher(Piece[][] plateau){
        for (int x = 0; x < 7; x++) {
            System.out.print("|");
            for (int y = 0; y < 8; y++) {
                if(this.plateau[x][y] == null) System.out.print("  |");
                    else{
                        if(plateau[x][y].nom == "Pion" && plateau[x][y].blanc == false) System.out.print("Pn|");
                        if(plateau[x][y].nom == "Pion" && plateau[x][y].blanc == true) System.out.print("Pb|");

                        if(plateau[x][y].nom == "Tour" && plateau[x][y].blanc == false) System.out.print("Tn|");
                        if(plateau[x][y].nom == "Tour" && plateau[x][y].blanc == true) System.out.print("Tb|");

                        if(plateau[x][y].nom == "Cavalier" && plateau[x][y].blanc == false) System.out.print("Cn|");
                        if(plateau[x][y].nom == "Cavalier" && plateau[x][y].blanc == true) System.out.print("Cb|");

                        if(plateau[x][y].nom == "Fou" && plateau[x][y].blanc == false) System.out.print("Fn|");
                        if(plateau[x][y].nom == "Fou" && plateau[x][y].blanc == true) System.out.print("Fb|");

                        if(plateau[x][y].nom == "Roi" && plateau[x][y].blanc == false) System.out.print("Rn|");
                        if(plateau[x][y].nom == "Roi" && plateau[x][y].blanc == true) System.out.print("Rb|");

                        if(plateau[x][y].nom == "Reine" && plateau[x][y].blanc == false) System.out.print("Qn|");
                        if(plateau[x][y].nom == "Reine" && plateau[x][y].blanc == true) System.out.print("Qb|");
                    }
            }
            System.out.println();
        }
    }




     //Vérifie si il y a des obstacle sur le chemin d'une pièce
     protected boolean obstacle(Piece p, int xAp, int yAp)
     {
         Piece[][] piece = this.plateau;
         Piece p2 = null;
         if (piece[xAp][yAp] != null)
             p2 = piece[xAp][yAp];
         int X = xAp;
         int Y = yAp;
 
         //Si la piece déplacé est une tour ou une reine, alors vérifie si il y a un
         //obstacle sur la ligne de déplacement
         if((p.nom == "Reine") || (p.nom == "Tour"))
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
         if(((p.nom == "Reine") || (p.nom == "Fou")) && (xAp - p.x != 0) && (yAp - p.y != 0))
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

}
