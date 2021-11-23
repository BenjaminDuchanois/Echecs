public class Cavalier extends Piece{
			public Cavalier(int x, int y, boolean blanc)
			{
				super();
				this.name = "Cavalier";
				File input;
				try
				{
					if(this.blanc) input = new File("images/Cavalier_Blanc.png");
					else input = new File("images/Cavalier_Noir.png");
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
