import javax.swing.JButton ;
import java.applet.*;
import java.awt.Point.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Controller implements ActionListener{
    private Placement placement;
    private Plateau plateau;
    private int xAvant, xApres, yAvant, yApres;
    private boolean selection;
    private JButton caseAvant, caseApres;
    
    public Controller(Placement p){
        this.placement= p;
        selection = false;
    }

    public void actionPerformed(ActionEvent ae){
        if(!selection){
            caseAvant = (JButton) ae.getSource();
            
            xAvant = caseAvant.getY()/90;
            yAvant = caseAvant.getX()/98;
            if(placement.plateau[caseAvant.getY()/90][caseAvant.getX()/98]!=null)
            {
                selection = !selection;
                System.out.println(placement.plateau[caseAvant.getY()/90][caseAvant.getX()/98].nom + " selectionee " + xAvant + " " + yAvant);
            }
            else
                System.out.println("Case vide " + xAvant + " " + yAvant);
        }
        else
        {
            caseApres = (JButton) ae.getSource();
            xApres = caseApres.getY()/90;
            yApres = caseApres.getX()/98;
            if((placement.plateau[caseApres.getY()/90][caseApres.getX()/98]==null)
                &&(placement.plateau[xAvant][yAvant].deplace(xApres,yApres)))
            {
                placement.misAJour(xAvant, xApres, yAvant, yApres);
                caseApres.setIcon(caseAvant.getIcon());
                caseAvant.setIcon(null);
                placement.afficher();
                System.out.println("Deplacement fait " + xApres + " " + yApres);
            }
            else
                System.out.println("Deplacement impossible " + xApres + " " + yApres);
            selection = !selection; 
        }
    }
}
