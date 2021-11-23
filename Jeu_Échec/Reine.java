public class Reine extends Piece{
			public Reine(int x, int y, boolean blanc)
			{
				super();
				this.name = "Reine";
				File input;
				try
				{
					if(this.blanc) input = new File("images/Reine_Blanc.png");
					else input = new File("images/Reine_Noir.png");
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
