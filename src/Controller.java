import javax.swing.JButton ;
import java.applet.*;
import java.awt.Point.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

//Cette classe controle toutes les actions ayant lieu sur le plateau.

public class Controller implements ActionListener{
    //Prend en paramètres la disposition des pions, la fenetre, les coordonnées du premier clic et du 2ème.
    private Placement placement;
    private Plateau plateau;
    private int xAvant, xApres, yAvant, yApres;
    //Prend également le bouton de correspondant à chaque clic, et un booléen pour différencier les 2 clics.
    private boolean selection;
    private JButton caseAvant, caseApres;
    
    //On initalise la selection d'une pièce à faux
    public Controller(Placement p){
        this.placement= p;
        selection = false;
    }

    //Lorsqu'un clic a lieu
    public void actionPerformed(ActionEvent ae){
        //Si aucune pièce n'est séléctionné, on est sur le premier clique
        if(!selection){
            //On regarde quel bouton a été cliqué et on garde ses coordonnées
            caseAvant = (JButton) ae.getSource();
            xAvant = caseAvant.getY()/90;
            yAvant = caseAvant.getX()/98;
            //Si la case contient bien une pièce, on la selectionne
            if(placement.plateau[caseAvant.getY()/90][caseAvant.getX()/98]!=null)
            {
                selection = !selection;
                System.out.println(placement.plateau[caseAvant.getY()/90][caseAvant.getX()/98].nom + " selectionee " + xAvant + " " + yAvant);
            }
            //Si la case est vide, on l'indique au cas où
            else
                System.out.println("Case vide " + xAvant + " " + yAvant);
        }
        //Si une pièce est déjà sélectionnée, on gère alors la destination
        else
        {
            //On enregistre les coordonnées de la case de destination
            caseApres = (JButton) ae.getSource();
            xApres = caseApres.getY()/90;
            yApres = caseApres.getX()/98;
            //Si la pièce séléctionnée était un pion c'est spécial, son comportement peut différer
            if(placement.plateau[caseAvant.getY()/90][caseAvant.getX()/98].nom=="Pion")
            {
                //Si la destination est occupée, alors on vérifie le déplacement du pion pour "prendre"
                if(placement.plateau[caseApres.getY()/90][caseApres.getX()/98]!=null)
                    {
                    //Si les paramètres sont bons, alors on déplace le pion et prend la pièce adverse.
                    if(placement.plateau[xAvant][yAvant].deplace(xApres,yApres))
                        {
                            placement.misAJour(xAvant, xApres, yAvant, yApres);
                            caseApres.setIcon(caseAvant.getIcon());
                            caseAvant.setIcon(null);
                        }
                    }
                //Si la destination est vide, alors on autorise le déplacement uniquement si le pion a avancé d'une case
                else
                    //Si c'est un pion blanc, c'est que x a incrémenté, sinon decrémenté
                    if(placement.plateau[xAvant][yAvant].blanc){
                        if ((yAvant == yApres) && (xAvant == xApres+1))
                        {
                            placement.misAJour(xAvant, xApres, yAvant, yApres);
                            caseApres.setIcon(caseAvant.getIcon());
                            caseAvant.setIcon(null);
                        }
                    }
                    else
                        if ((yAvant == yApres) && (xAvant == xApres-1))
                        {
                            placement.misAJour(xAvant, xApres, yAvant, yApres);
                            caseApres.setIcon(caseAvant.getIcon());
                            caseAvant.setIcon(null);
                        }
                //Une fois le deuxième clique prit en compte, on libère la selection
                selection = !selection;
            }
            //Si la pièce n'est pas un pion
            else{
                //On accepte son déplacement que si la destination est libre ou de la couleur adverse, et si le déplacement
                //Est bien réalisable par la pièce séléctionnée
                if(((placement.plateau[caseApres.getY()/90][caseApres.getX()/98]==null) 
                    || (placement.plateau[caseApres.getY()/90][caseApres.getX()/98].blanc != placement.plateau[caseAvant.getY()/90][caseAvant.getX()/98].blanc))
                    && (placement.plateau[xAvant][yAvant].deplace(xApres,yApres)))
                {
                    //Si tout est bon, on déplace la pièce
                    placement.misAJour(xAvant, xApres, yAvant, yApres);
                    caseApres.setIcon(caseAvant.getIcon());
                    caseAvant.setIcon(null);
                    placement.afficher();
                    System.out.println("Deplacement fait " + xApres + " " + yApres);
                }
                else
                    //Sinon on indique que le déplacement n'est pas possible
                    System.out.println("Deplacement impossible " + xApres + " " + yApres);

                //On libère ensuite la selection
                selection = !selection; 
            }
        }
    }
}
