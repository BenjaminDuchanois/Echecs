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
    protected void testDeplacement(Piece pieceAvant, int x, int y)
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
                System.out.println("Deplacement autorise.");
                //Et qu'il n'y a pas d'obstacle sur le chemin
                if(obstacle(pieceAvant, x, y))
                    System.out.println("Mais il y a un obstacle..");
                else
                {
                    if((pieceApres!=null)&&(!pieceApres.nom.equals("Pion")))
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



    //Quand toutes les conditions sont remplies, effectue le déplacement de la piece
    protected void deplacement(Piece p, int x, int y)
    {
        Piece piece = plateau[x][y];
        
        //On met à jour le tableau, la piece selectionnée va au coordonnées ciblées
        misAJour(p.x, x, p.y, y);

        //On affiche alors le plateau modifié
        afficher(plateau);
        parent.vue.AffichePlateau(plateau);

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
            JoueurVirtuel(plateau);

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

          parent.vue.AffichePlateau(plateau);
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
        parent.vue.AffichePlateau(plateau);
     }
     



}
