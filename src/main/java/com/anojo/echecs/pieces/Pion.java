package com.anojo.echecs.pieces;
public class Pion extends Piece{
			public Pion(int x, int y, boolean blanc)
			{
				super();
				this.name = "Pion";
				File input;
				try
				{
					if(this.blanc) input = new File("images/Pion_Blanc.png");
					else input = new File("images/Pion_Noir.png");
					this.img = ImageIO.read(input);
				} 
		
				catch(IOException e) 
				{
					e.printStackTrace();
				}
		
			}
			
			public boolean move(int x, int y)
			{
				
				
				
				
			}
}
