public class Placement {
    //Gère l'emplacement des différentes pièces via un tableu
    public Piece[][] plateau;

    //Le constructeur initialise un tableau prédéfini
    public Placement(){
        this.plateau = new Piece[7][8];
        initialiserPlateau();
    }

    //On place les pions de la façon standart. La couleur des pièces est directement géré selon leur ligne.
    public void initialiserPlateau(){
        for (int i=0; i<7; i++){
            for (int j=0; j<8; j++){
                if ((i==1) || (i==5))
                    this.plateau[i][j] = new Pion(i, j);
                if ((i==0) || (i==6)){
                    if ((j==0) || (j==7))
                        this.plateau[i][j] = new Tour(i, j);
                    if ((j==1) || (j==6))
                        this.plateau[i][j] = new Cavalier(i, j);
                    if ((j==2) || (j==5))
                        this.plateau[i][j] = new Fou(i, j);
                    if (j==3)
                        this.plateau[i][j] = new Reine(i, j);
                    if (j==4)
                        this.plateau[i][j] = new Roi(i, j);
                }
            }
        }
    }

    //Met à jour le tableau avec les coordonnées d'avant et après déplacement. 
    //Change également les coordonées de la pièce pour qu'elle puisse se déplacer de nouveau.
    public void misAJour(int xAv, int xAp, int yAv, int yAp){
        this.plateau[xAp][yAp] = this.plateau[xAv][yAv];
        this.plateau[xAv][yAv] = null;
        this.plateau[xAp][yAp].x = xAp;
        this.plateau[xAp][yAp].y = yAp;
    }

    //Simple affichage pour gérer de potentielles erreurs
    public void afficher(){
        for (int x = 0; x < 7; x++) {
            System.out.print("|");
            for (int y = 0; y < 8; y++) {
                if(this.plateau[x][y] == null) System.out.print("  |");
                    else{
                        if(this.plateau[x][y].nom == "Pion" && this.plateau[x][y].blanc == false) System.out.print("Pn|");
                        if(this.plateau[x][y].nom == "Pion" && this.plateau[x][y].blanc == true) System.out.print("Pb|");

                        if(this.plateau[x][y].nom == "Tour" && this.plateau[x][y].blanc == false) System.out.print("Tn|");
                        if(this.plateau[x][y].nom == "Tour" && this.plateau[x][y].blanc == true) System.out.print("Tb|");

                        if(this.plateau[x][y].nom == "Cavalier" && this.plateau[x][y].blanc == false) System.out.print("Cn|");
                        if(this.plateau[x][y].nom == "Cavalier" && this.plateau[x][y].blanc == true) System.out.print("Cb|");

                        if(this.plateau[x][y].nom == "Fou" && this.plateau[x][y].blanc == false) System.out.print("Fn|");
                        if(this.plateau[x][y].nom == "Fou" && this.plateau[x][y].blanc == true) System.out.print("Fb|");

                        if(this.plateau[x][y].nom == "Roi" && this.plateau[x][y].blanc == false) System.out.print("Rn|");
                        if(this.plateau[x][y].nom == "Roi" && this.plateau[x][y].blanc == true) System.out.print("Rb|");

                        if(this.plateau[x][y].nom == "Reine" && this.plateau[x][y].blanc == false) System.out.print("Qn|");
                        if(this.plateau[x][y].nom == "Reine" && this.plateau[x][y].blanc == true) System.out.print("Qb|");
                    }
            }
            System.out.println();
        }
    }
}
