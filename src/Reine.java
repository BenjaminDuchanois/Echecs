import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Reine extends Piece{
			public Reine(int x, int y)
			{
				super(x, y);
				this.nom = "Reine";
				File input;
				try
				{
					if(this.blanc) input = new File("../images/Reine_Blanc.png");
					else input = new File("../images/Reine_Noir.png");
					this.img = ImageIO.read(input);
				} 
		
				catch(IOException e) 
				{
					e.printStackTrace();
				}
		
			}
			
}
