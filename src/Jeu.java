class Jeu implements Runnable {
    public Placement placement;
    public Plateau plateau;
    public Controller controller;

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