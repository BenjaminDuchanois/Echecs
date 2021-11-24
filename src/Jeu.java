class Jeu implements Runnable {
    //Classe principal lançant le jeu
    public Placement placement;
    public Plateau plateau;
    public Controller controller;

    //Initialise, la fenetre, le controlleur et le placement des pièces.
    public Jeu(){
        this.placement = new Placement();
        this.controller = new Controller(placement);
        this.plateau = new Plateau(placement, controller);
    }

    public void run(){
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
    }
}