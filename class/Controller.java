import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Controller implements ActionListener {

    //Prend en paramètres la disposition des pions, la fenetre, les coordonnées du premier clic et du 2ème.
    private Jeu parent;
    private int xAvant, xApres, yAvant, yApres;

    //Prend également le bouton de correspondant à chaque clic, et un booléen pour différencier les 2 clics.
    protected boolean selection, tour;
    private MonBouton caseAvant, caseApres;
    


    
    //On initalise la selection d'une pièce à faux
    public Controller(Jeu parent){
        this.parent= parent;
        selection = false;
        tour = true;
    }




    //Lorsqu'un clic a lieu
    public void actionPerformed(ActionEvent ae){

        Piece pieceAvant;
        Piece pieceApres;

        MonBouton caseClique = (MonBouton) ae.getSource();
        if(caseClique.getMonY()>7){
            switch(caseClique.getMonX())
            {
                case 2:
                    parent.vue.ConfirmationRelance();
                    break;
                case 3:
                    parent.modele.Sauvegarder();
                    break;
                case 4:
                    parent.modele.Charger();
                    break;
                default:
                    System.out.println("Option non reconue");
                    break;
            }
        }
        else{
            //Si aucune pièce n'est séléctionné, on est sur le premier clique
            if(!selection){
                //On regarde quel bouton a été cliqué et on garde ses coordonnées
                caseAvant = caseClique;
                xAvant = caseAvant.getMonX();
                yAvant = caseAvant.getMonY();

                //Sert d'alias pour que le code soit plus lisible
                pieceAvant = this.parent.modele.plateau[xAvant][yAvant];

                //Si la case contient bien une pièce, on la selectionne
                if(pieceAvant!=null)
                {
                    if((!tour)&&(pieceAvant.blanc))
                        System.out.println("C'est aux noirs de jouer");
                    else if((tour)&&(!pieceAvant.blanc))
                            System.out.println("C'est aux blancs de jouer");
                        else
                            {selection = !selection;
                            System.out.println(pieceAvant.nom + " selectionee " + xAvant + " " + yAvant);}
                }

                //Si la case est vide, on l'indique au cas où
                else
                    System.out.println("Case vide x" + xAvant + " y" + yAvant);
            }

            //Si une pièce est déjà sélectionnée, on gère alors la destination
            else
            {
                //On enregistre les coordonnées de la case de destination
                caseApres = caseClique;
                xApres = caseApres.getMonX();
                yApres = caseApres.getMonY();
                pieceAvant = this.parent.modele.plateau[xAvant][yAvant];

                parent.modele.testDeplacement(pieceAvant, xApres, yApres);

                selection = !selection;

            }
        }
    }
    

}
