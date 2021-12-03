import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Vue extends JFrame {

    private static final long serialVersionUID = 1111111;

    //Contenu de la fenetre
    JPanel pn = new JPanel();
    Jeu parent;
    //Taille d'une case (Tout est basé dessus et peut être modifié au choix)
    public int CASE_DIM = 75;

    protected ActionListener tache_timer;
    protected Timer timer;
    protected int minute, seconde;
    protected int delais=1000;
    

    
    protected Vue(Jeu parent) {

        this.parent = parent;

        //Propriétés de base de la fenêtre
        setTitle("Jeu d'echecs");
        setSize(CASE_DIM*10, CASE_DIM*9);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground( new Color(49,46,43) );

        AffichePlateau(Modele.plateau);
        
        setVisible(true);

        Chrono();
    }




    //Affichage du plateau dans son état
    protected void AffichePlateau(Piece[][] plateau){

        //On vide le panel pour le refaire à chaque déplacement
        pn.removeAll();
		pn.setLayout(new GridLayout(9,10));
        boolean blanc = true;
        //Les lettre pour le repérage des cases
        String[] lettre = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String couleur;

        //Les 7 lignes de l'échiquier et 2 pour le centrer
        for (int x=0; x<9; x++) 
		{
            //La première colonne sert pour les chiffres
            JLabel jl;
            jl = new JLabel(8-x + "");
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
                    {
                        //Copie sa couleur dans un text, pour retrouver le lien de son image
                        if(plateau[x-1][y].blanc)
                            couleur = "Blanc";
                        else
                            couleur = "Noir";
                        //Trouve l'image correspondant grace à la couleur et au nom de la pièce
                        carreau.setIcon(new ImageIcon(
                            "../images/" + plateau[x-1][y].nom + "_" + couleur + ".png"));
                    }
                    else 
                        //Si il n'y a pas de pièce, n'affiche rien
                        carreau.setIcon(null);

                    //Lie la case au controller
                    carreau.addActionListener(parent.controller);

                    //Ajoute la case au panel
                    pn.add(carreau);
                    blanc=!blanc;

                }

                //Marge au dessus du plateau
                else{
                    //Affichage du chrono en haut (y 3 et 4)
                    if((x==0)&&((y==4)||(y==3)))
                    {
                        if(y==3){
                            jl = new JLabel(minute + " min");
                            jl.setHorizontalAlignment(JLabel.RIGHT);}
                        else{
                            jl = new JLabel("  " + seconde + " s");
                            jl.setHorizontalAlignment(JLabel.LEFT);}
                        jl.setOpaque(true);
                        jl.setVerticalAlignment(JLabel.CENTER);
                        jl.setBackground(new Color(49,46,43));
                        jl.setForeground(new Color(255,255,255));
                        pn.add(jl);
                    }
                    //Reste de la marge du haut
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
			}
			blanc=!blanc;

            //Marge de droite
            jl = new JLabel("");
            MonBouton jb = new MonBouton(x-1,8);
            jl.setOpaque(true);
            jl.setHorizontalAlignment(JLabel.CENTER);
            jl.setVerticalAlignment(JLabel.CENTER);
            jl.setBackground(new Color(49,46,43));
            if(x!=7)
                jl.setForeground(new Color(255,255,255));
            else
                jl.setForeground(new Color(49,46,43));

            //Gère les options à droite du plateau
            if((x>2)&&(x<6)){
                switch(x){
                    case 3:
                        jb.setText("Rejouer");
                        break;
                    case 4:
                        jb.setText("Sauver");
                        break;
                    case 5:
                        jb.setText("Charger");
                        break;
                    default:
                        break;
                }

                jb.addActionListener(parent.controller);
                jb.setBackground(new Color(49,46,43));
                jb.setForeground(new Color(255,255,255));
                jb.setBorder(BorderFactory.createLineBorder(new Color(49,46,43)));
                pn.add(jb);
            }
            else
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


    //Gère le chronomètre
    protected void Chrono()
    {
        ResetChrono();
        
        //Créer le chronomètre
        tache_timer = new ActionListener()  {
            public void actionPerformed(ActionEvent e1)  {
               seconde--;
               if(seconde==-1)  {
                    seconde=59;
                    minute--;
                }
            //Refresh la vue
            AffichePlateau(Modele.plateau);

            //Si le temps tombe à zéro, donne la victoire à l'adversaire
            if((minute==0)&&(seconde==0))
                Fin(!parent.controller.tour);
   
           }
       };

        //Action et temps d'execution de la tache
        timer = new Timer(delais, tache_timer);
        
        //Demarrer le chrono
        timer.start();
    }



    //Remet le chrono au temps de base
    protected void ResetChrono(){
        minute = 3;
        seconde = 1;
    }



    //Met le chrono à un temps défini (Pratique pour la sauvegarde/charge)
    protected void SetChrono(int m, int s){
        minute = m;
        seconde = s;
    }



    //Ecran de fin de partie
    public void Fin(boolean blanc){

        timer.stop();

        String msg, title;
        String[] reponses = {"Rejouer", "Quitter"};
        ImageIcon ic;

        //Si les blancs ont gagné
        if(blanc){
            msg = "Les blancs remportent la partie !";
            title = "Victoire !";
            ic = new ImageIcon("../images/Roi_Blanc.png");
        }
        //Si les noirs ont gagné
        else{
            msg = "Les noirs remportent la partie !";
            title = "Defaite.";
            ic = new ImageIcon("../images/Roi_Noir.png");
        }

        //Ouvre une pop-up qui propose de fermer ou relancer le jeu
        if(JOptionPane.showOptionDialog(this, msg, title, JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE, ic, reponses, 0) == 0) 
        {
            this.dispose();
            new Jeu();
        }
        else
            this.dispose();
    }



    //Demande si on est sur de vouloir quitter
    protected void ConfirmationQuitter(){
        String[] reponses = { "Quitter", "Retour au jeu" };
        if(JOptionPane.showOptionDialog(this, "Voulez-vous vraiment quitter sans sauvegarder ?", "Quitter", JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE, null, reponses, 0) == 0) 
        {
            this.dispose();
        }
    }



    //Demande si on est sur de vouloir relancer la partie
    protected void ConfirmationRelance(){
        String[] reponses = { "Nouvelle partie", "Retour" };
        if(JOptionPane.showOptionDialog(this, "Voulez-vous vraiment recommencer une partie ?", "Rejouer", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, reponses, 0) == 0) 
        {
            this.dispose();
            new Jeu();
        }
    }



    //Interface de choix de pion pour la promotion
    protected void PromotionPopup(Piece p)
    {
        String[] piecesDispo;
        String piecePromue;

        //Si le pion est blanc, alors affiche toutes les pièces mortes dans une pop-up
        //Et propose au joueur d'en choisir une à récuperer
        if(p.blanc){
            piecesDispo = parent.modele.cimetiereB.toArray(new String[0]);

            int pieceChoisie = JOptionPane.showOptionDialog(this, "Choisissez une piece !", "Promotion.", 
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, piecesDispo, piecesDispo[0]);
            piecePromue = piecesDispo[pieceChoisie];
            parent.modele.cimetiereB.remove(pieceChoisie);
        }
        //Si le pion est noir, le joueur virtuel en selectionne une par defaut
        else{
            piecesDispo = parent.modele.cimetiereN.toArray(new String[0]);
            piecePromue = piecesDispo[0];
            parent.modele.cimetiereN.remove(0);
        }

        //Remplace le pion par la piece choisie
        parent.modele.ChangePiece(p, piecePromue);
    }

}
