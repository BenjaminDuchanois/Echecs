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
            if(placement.plateau[caseAvant.getY()/90][caseAvant.getX()/100]!=null)
            {
                System.out.println("Non null");
                selection = !selection;
                xAvant = caseAvant.getY()/90;
                yAvant = caseAvant.getX()/100;
            }
            else
                System.out.println("Null");
        }
        else
        {
            caseApres = (JButton) ae.getSource();
            xApres = caseApres.getY()/90;
            yApres = caseApres.getX()/100;
            if(placement.plateau[caseApres.getY()/90][caseApres.getX()/100]==null)
            {
                placement.misAJour(xAvant, xApres, yAvant, yApres);
                caseApres.setIcon(caseAvant.getIcon());
                caseAvant.setIcon(null);
                placement.afficher();
            }
            selection = !selection;
            
        }
    }
}
