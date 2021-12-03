import javax.swing.*;
import java.awt.*;

public class Vue extends JFrame {

    private static final long serialVersionUID = 1111111;

    JPanel pn = new JPanel();
    Jeu parent;
    public int CASE_DIM = 75;
    

    
    protected Vue(Jeu parent) {

        this.parent = parent;

        //Propriétés de la fenêtre
        setTitle("Jeu d'echecs");
        setSize(CASE_DIM*10, CASE_DIM*9);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground( new Color(49,46,43) );

        AffichePlateau(parent.modele.plateau);
        
        setVisible(true);
    }





    protected void AffichePlateau(Piece[][] plateau){

        //On vide le panel pour le refaire à chaque déplacement
        pn.removeAll();
		pn.setLayout(new GridLayout(9,10));
        boolean blanc = false;
        //Les lettre pour le repérage des cases
        String[] lettre = {"A", "B", "C", "D", "E", "F", "G", "H"};

        //Les 7 lignes de l'échiquier et 2 pour le centrer
        for (int x=0; x<9; x++) 
		{
            //La première colonne sert pour les chiffres
            JLabel jl;
            jl = new JLabel(x + "");
            jl.setOpaque(true);
            jl.setHorizontalAlignment(JLabel.CENTER);
            jl.setVerticalAlignment(JLabel.CENTER);
            jl.setBackground(new Color(49,46,43));
            if((x!=8)&&(x!=0))
                jl.setForeground(new Color(255,255,255));
            else
                jl.setForeground(new Color(49,46,43));
            pn.add(jl);
            
            //Les 7 suivantes sont l'échiquier et la dernière sert de marge
			for(int y=0; y<8; y++) 
			{
                //Si ce n'est pas la colonne de droite ni celle de gauche
                if((x!=8)&&(x!=0)){

                    //On ajoute un bouton en tant que case de l'échiquier
                    JButton carreau = new MonBouton(x-1,y);
                    carreau.setSize(new Dimension(CASE_DIM,CASE_DIM));

                    //Alterne les couleurs pour faire un "damier"
                    if(blanc) 
                    {
                        carreau.setBackground(new Color(238,238,210));
                    }
                    else 
                    {
                        carreau.setBackground(new Color(118,150,86));
                    }

                    //Si le modèle indique qu'il y a une pièce, l'affiche
                    if(plateau[x-1][y] != null)
                        carreau.setIcon(new ImageIcon(plateau[x-1][y].img));
                    else 
                        carreau.setIcon(null);

                    //Lie la case au controller
                    carreau.addActionListener(parent.controller);

                    //Ajoute la case au panel
                    pn.add(carreau);
                    blanc=!blanc;

                }

                //Si c'est une colonne d'une extrémité, alors gère les lettres de repère
                else{
                    jl = new JLabel(lettre[y]);
                    jl.setOpaque(true);
                    jl.setHorizontalAlignment(JLabel.CENTER);
                    jl.setVerticalAlignment(JLabel.CENTER);
                    jl.setBackground(new Color(49,46,43));
                    if(x==0)
                        jl.setForeground(new Color(49,46,43));
                    else
                        jl.setForeground(new Color(255,255,255));
                    pn.add(jl);
                }
			}
			blanc=!blanc;

            //Gère les marges
            jl = new JLabel("");
            jl.setOpaque(true);
            jl.setHorizontalAlignment(JLabel.CENTER);
            jl.setVerticalAlignment(JLabel.CENTER);
            jl.setBackground(new Color(49,46,43));
            if(x!=7)
                jl.setForeground(new Color(255,255,255));
            else
                jl.setForeground(new Color(49,46,43));
            pn.add(jl);
		}        
        //Ajoute une bordure au panel
        pn.setBorder(BorderFactory.createLineBorder(Color.black));
        //Refresh le panel pour le mettre à jour à chaque déplacement
        pn.revalidate();
        //Ajoute le panel au contentpane
        add(pn,BorderLayout.CENTER);

		setDefaultCloseOperation(3);
    }





    public void Fin(boolean blanc){

        String msg, title;
        String[] reponses = {"Rejouer", "Quitter"};
        ImageIcon ic;

        if(blanc){
            msg = "Les blancs remportent la partie !";
            title = "Victoire !";
            ic = new ImageIcon("../images/Roi_Blanc.png");
        }
        else{
            msg = "Les noirs remportent la partie !";
            title = "Defaite.";
            ic = new ImageIcon("../images/Roi_Noir.png");
        }

        if(JOptionPane.showOptionDialog(this, msg, title, JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE, ic, reponses, 0) == 0) 
        {
            this.dispose();
            new Jeu();
        }
        else
            this.dispose();
    }



    protected void PromotionPopup(Piece p)
    {
        String[] piecesDispo;
        String piecePromue;

        if(p.blanc)
            piecesDispo = parent.modele.cimetiereB.toArray(new String[0]);
        else
            piecesDispo = parent.modele.cimetiereB.toArray(new String[0]);

        int pieceChoisie = JOptionPane.showOptionDialog(this, "Choisissez une piece !", "Promotion.", 
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, piecesDispo, piecesDispo[0]);
    
        piecePromue = piecesDispo[pieceChoisie];

        if(p.blanc)
            parent.modele.cimetiereB.remove(pieceChoisie);
        else
            parent.modele.cimetiereB.remove(pieceChoisie);

        parent.modele.ChangePiece(p, piecePromue);
    }

}
