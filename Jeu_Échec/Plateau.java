import javax.swing.*;
import java.awt.*;

public class Plateau extends JFrame{
	public Plateau(){
		this.setSize(800, 700);
		JPanel pn = new JPanel(); 
		pn.setPreferredSize(new Dimension(700,800));
		pn.setLayout(new GridLayout(7,8));
		boolean blanc=true;
		for (int y=0; y<7; y++) 
		{
			for(int x=0; x<8; x++) 
			{
				JButton carreau = new JButton();
				carreau.setPreferredSize(new Dimension(100,100));
				if(blanc) 
				{
					carreau.setBackground(new Color(0,0,0));
				}else 
				{
					carreau.setBackground(new Color(255,255,255));
				}
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
	public static void main(String [] args) 
	{
		new Plateau();
	}
}