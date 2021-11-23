public class Tour extends Piece{
			public Tour(int x, int y, boolean blanc)
			{
				super();
				this.name = "Tour";
				File input;
				try
				{
					if(this.blanc) input = new File("images/Tour_Blanc.png");
					else input = new File("images/Tour_Noir.png");
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
