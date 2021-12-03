class Jeu {

    //Classe principal lançant le jeu
    public Modele modele;
    public Vue vue;
    public Controller controller;

    //Initialise, la fenetre, le controlleur et le placement des pièces.
    public Jeu(){
        this.controller = new Controller(this);
        this.modele = new Modele(this);
        this.vue = new Vue(this);
    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
    }

}