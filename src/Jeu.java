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

    public void run(){
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