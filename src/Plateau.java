import javax.swing.*;
import java.awt.*;

public class Plateau extends JFrame{
	public Plateau(Placement p, Controller c){

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

				if(p.plateau[x][y] != null){
                    Image img = p.plateau[x][y].getImage();
                    carreau.setIcon(new ImageIcon(img));
                }

				if(blanc) 
				{
					carreau.setBackground(new Color(255,255,255));
				}
				else 
				{
					carreau.setBackground(new Color(100,100,100));
				}
				carreau.addActionListener(c);
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