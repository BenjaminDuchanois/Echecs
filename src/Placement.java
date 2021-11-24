public class Placement {
    public Piece[][] plateau;

    public Placement(){
        this.plateau = new Piece[7][8];
        initialiserPlateau();
    }

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

    public void misAJour(int xAv, int xAp, int yAv, int yAp){
        this.plateau[xAp][yAp] = this.plateau[xAv][yAv];
        this.plateau[xAv][yAv] = null;
    }

    public void afficher(){
        for (int x = 0; x < 7; x++) {
            System.out.print("|");
            for (int y = 0; y < 8; y++) {
                if(this.plateau[x][y] == null) System.out.print("  |");
                    else{
                        if(this.plateau[x][y].nom == "Pion" && this.plateau[x][y].blanc == false) System.out.print("Pb|");
                        if(this.plateau[x][y].nom == "Pion" && this.plateau[x][y].blanc == true) System.out.print("Pw|");

                        if(this.plateau[x][y].nom == "Tour" && this.plateau[x][y].blanc == false) System.out.print("Rb|");
                        if(this.plateau[x][y].nom == "Tour" && this.plateau[x][y].blanc == true) System.out.print("Rw|");

                        if(this.plateau[x][y].nom == "Cavalier" && this.plateau[x][y].blanc == false) System.out.print("Kb|");
                        if(this.plateau[x][y].nom == "Cavalier" && this.plateau[x][y].blanc == true) System.out.print("Kw|");

                        if(this.plateau[x][y].nom == "Fou" && this.plateau[x][y].blanc == false) System.out.print("Bb|");
                        if(this.plateau[x][y].nom == "Fou" && this.plateau[x][y].blanc == true) System.out.print("Bw|");

                        if(this.plateau[x][y].nom == "Roi" && this.plateau[x][y].blanc == false) System.out.print("Gb|");
                        if(this.plateau[x][y].nom == "Roi" && this.plateau[x][y].blanc == true) System.out.print("Gw|");

                        if(this.plateau[x][y].nom == "Reine" && this.plateau[x][y].blanc == false) System.out.print("Qb|");
                        if(this.plateau[x][y].nom == "Reine" && this.plateau[x][y].blanc == true) System.out.print("Qw|");
                    }
            }
            System.out.println();
        }
    }
}
