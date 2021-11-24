package com.anojo.echecs.pieces;
public class Fou extends Piece{
			public Fou(int x, int y, boolean blanc)
			{
				//CECI EST UN GROS TEST
				super();
				this.name = "Fou";
				File input;
				try
				{
					if(this.blanc) input = new File("images/Fou_Blanc.png");
					else input = new File("images/Fou_Noir.png");
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
