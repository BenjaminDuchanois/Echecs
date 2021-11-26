import javax.swing.*;
import java.awt.*;

public class Plateau extends JFrame{
	Jeu parent;
	//Gère la fenêtre et le plateau de base avec un echiquier composé de JButton
	public Plateau(Jeu parent){
		this.parent = parent;
		//On empêche la déformation car cela crée quelques problèmes sur le jeu
		this.setSize(800, 700);
		this.setTitle("Jeu d'Echecs");
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JPanel pn = new JPanel(); 

		pn.setPreferredSize(new Dimension(700,800));
		pn.setLayout(new GridLayout(7,8));
		boolean blanc=false;
		for (int x=0; x<7; x++) 
		{
			for(int y=0; y<8; y++) 
			{
				JButton carreau = new JButton();
				carreau.setPreferredSize(new Dimension(100,100));

				if(parent.placement.plateau[x][y] != null){
                    Image img = parent.placement.plateau[x][y].getImage();
                    carreau.setIcon(new ImageIcon(img));
                }

				//Alterne les couleurs pour faire un "damier"
				if(blanc) 
				{
					carreau.setBackground(new Color(255,255,255));
				}
				else 
				{
					carreau.setBackground(new Color(100,100,100));
				}
				carreau.addActionListener(parent.controller);
				pn.add(carreau);
				blanc=!blanc;
			}
			blanc=!blanc;
		}
		this.add(pn);
		this.setDefaultCloseOperation(3);
		this.setContentPane(pn);
		this.setVisible(true);
	}
}