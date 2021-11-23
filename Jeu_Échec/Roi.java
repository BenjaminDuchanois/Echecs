public class Roi extends Piece{
			public Roi(int x, int y, boolean blanc)
			{
				super();
				this.name = "Roi";
				File input;
				try
				{
					if(this.blanc) input = new File("images/Roi_Blanc.png");
					else input = new File("images/Roi_Noir.png");
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
