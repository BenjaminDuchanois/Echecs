class Jeu implements Runnable {
    //Classe principal lançant le jeu
    public Placement placement;
    public Plateau plateau;
    public Controller controller;

    //Initialise, la fenetre, le controlleur et le placement des pièces.
    public Jeu(){
        this.placement = new Placement(this);
        this.controller = new Controller(this);
        this.plateau = new Plateau(this);
    }

    public void run(){}
    public int runn(){
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        int xCible, yCible;
        System.out.println("Run lance !");
        //while(true)
            if (!(this.controller.tour))
            for (int i=0; i<7; i++){
                System.out.println("Boucle i !");
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                for (int j=0; j<8; j++){
                    System.out.println("Boucle j !");
                    if ((this.placement.plateau[i][j] == null) || (this.placement.plateau[i][j].blanc))
                    {
                        System.out.println("Cible trouvee !");
                        xCible = i;
                        yCible = j;
                        for (int k=0; k<7; k++){
                            for (int l=0; l<8; l++){
                                if((this.placement.plateau[k][l] != null) && (!(this.placement.plateau[k][l].blanc)))
                                    if((this.placement.plateau[k][l].deplace(xCible, yCible))
                                    &&(!(this.controller.obstacle(this.placement.plateau[k][l], xCible, yCible))))
                                        {
                                            this.controller.deplacement(this.placement.plateau[k][l], xCible, yCible);
                                            return 0;
                                        }
                            }
                        }
                    }
                    
                }
            }
            return 1;
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
    }

    protected void fin(boolean blanc){
		EcranFin ec = new EcranFin(this, blanc);
	}

    protected void rejouer(){
        new Jeu();
    }
}