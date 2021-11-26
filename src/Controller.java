import javax.swing.JButton ;
import java.applet.*;
import java.awt.Point.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

//Cette classe controle toutes les actions ayant lieu sur le plateau.

public class Controller implements ActionListener{
    //Prend en paramètres la disposition des pions, la fenetre, les coordonnées du premier clic et du 2ème.
    private Jeu parent;
    private Plateau plateau;
    private int xAvant, xApres, yAvant, yApres;
    //Prend également le bouton de correspondant à chaque clic, et un booléen pour différencier les 2 clics.
    private boolean selection;
    private JButton caseAvant, caseApres;
    
    //On initalise la selection d'une pièce à faux
    public Controller(Jeu parent){
        this.parent= parent;
        selection = false;
    }

    //Lorsqu'un clic a lieu
    public void actionPerformed(ActionEvent ae){
        Piece pieceAvant;
        Piece pieceApres;

        //Si aucune pièce n'est séléctionné, on est sur le premier clique
        if(!selection){
            //On regarde quel bouton a été cliqué et on garde ses coordonnées
            caseAvant = (JButton) ae.getSource();
            xAvant = caseAvant.getY()/90;
            yAvant = caseAvant.getX()/98;
            //Sert d'alias pour que le code soit plus lisible
            pieceAvant = this.parent.placement.plateau[xAvant][yAvant];
            //Si la case contient bien une pièce, on la selectionne
            if(pieceAvant!=null)
            {
                selection = !selection;
                System.out.println(pieceAvant.nom + " selectionee " + xAvant + " " + yAvant);
            }
            //Si la case est vide, on l'indique au cas où
            else
                System.out.println("Case vide x" + xAvant + " y" + yAvant);
        }
        //Si une pièce est déjà sélectionnée, on gère alors la destination
        else
        {
            //On enregistre les coordonnées de la case de destination
            caseApres = (JButton) ae.getSource();
            xApres = caseApres.getY()/90;
            yApres = caseApres.getX()/98;
            pieceApres = this.parent.placement.plateau[xApres][yApres];
            pieceAvant = this.parent.placement.plateau[xAvant][yAvant];

            //Si la pièce séléctionnée était un pion c'est spécial, son comportement peut différer
            if(pieceAvant.nom=="Pion")
            {
                //Si la destination est occupée, alors on vérifie le déplacement du pion pour "prendre"
                if(pieceApres!=null)

                    //Si les paramètres sont bons, alors on déplace le pion et prend la pièce adverse.
                    if((pieceAvant.deplace(xApres,yApres)) && (pieceApres.blanc != pieceAvant.blanc))
                        {
                            deplacement(pieceAvant, xApres, yApres);
                        }
                    else    
                        System.out.println("Deplacement impossible");
                    
                //Si la destination est vide, alors on autorise le déplacement uniquement si le pion a avancé d'une case
                else
                {
                    //Si c'est un pion blanc, c'est que x a incrémenté, sinon decrémenté
                    if(pieceAvant.blanc){
                        if ((yAvant == yApres) && (xAvant == xApres+1))
                        {
                            deplacement(pieceAvant, xApres, yApres);
                        }
                        else System.out.println("Deplacement impossible en " + xApres + " " + yApres);
                    }
                    else
                        if ((yAvant == yApres) && (xAvant == xApres-1))
                        {
                            deplacement(pieceAvant, xApres, yApres);
                        }
                        else System.out.println("Deplacement impossible en " + xApres + " " + yApres);
                }
            }
            //Si la pièce n'est pas un pion
            else{
                //On accepte son déplacement que si la destination est libre ou de la couleur adverse, et si le déplacement
                //Est bien réalisable par la pièce séléctionnée
                if((pieceApres == null)
                || (pieceApres.blanc != pieceAvant.blanc))
                {
                    if(pieceAvant.deplace(xApres,yApres))
                    {   
                        System.out.println("Deplacement autorise.");
                        if(obstacle(pieceAvant, xApres, yApres))
                            System.out.println("Mais il y a un obstacle..");
                        else
                        {
                            //Si tout est bon, on déplace la pièce
                            deplacement(pieceAvant, xApres, yApres);
                        }
                    }
                    else
                        //Sinon on indique que le déplacement n'est pas possible
                        System.out.println("Deplacement impossible " + xApres + " " + yApres);
                }
                else
                    System.out.println("Cette piece est a vous " + xApres + " " + yApres);
            }
            //On libère ensuite la selection
            selection = !selection;
        }
    }


    protected boolean obstacle(Piece p, int xAp, int yAp)
    {
        Placement placement = this.parent.placement;
        Piece[][] piece = this.parent.placement.plateau;
        Piece p2 = null;
        if (this.parent.placement.plateau[xAp][yAp] != null)
            p2 = this.parent.placement.plateau[xAp][yAp];
        int X = xAp;
        int Y = yAp;

        //Si la piece déplacé est une tour ou une reine, alors vérifie si il y a un
        //obstacle sur la ligne de déplacement
        if((p.nom == "Reine") || (p.nom == "Tour"))
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
        if(((p.nom == "Reine") || (p.nom == "Fou")) && (xAp - p.x != 0) && (yAp - p.y != 0))
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
 
    protected void deplacement(Piece p, int x, int y)
    {
        Piece piece = this.parent.placement.plateau[x][y];
        if(piece!=null)
            if(piece.nom=="Roi")
                Victoire(p.blanc);
            else
                System.out.println(p.nom + " mange " + piece.nom + " en " + x + " " + y);
        else
            System.out.println(p.nom + " va en " + x + " " + y);
        parent.placement.misAJour(p.x, x, p.y, y);
        caseApres.setIcon(caseAvant.getIcon());
        caseAvant.setIcon(null);
        parent.placement.afficher();
    }
    
    protected void Victoire(boolean blanc)
    {
        if(blanc)
            System.out.println("Victoire des blancs!");
        else
            System.out.println("Victoire des noirs !");
        this.parent.fin(blanc);
    }

}
