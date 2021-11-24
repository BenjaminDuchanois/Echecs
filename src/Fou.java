import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Fou extends Piece{
			public Fou(int x, int y)
			{
				super(x, y);
				this.nom = "Fou";
				File input;
				try
				{
					if(this.blanc) input = new File("../images/Fou_Blanc.png");
					else input = new File("../images/Fou_Noir.png");
					this.img = ImageIO.read(input);
				} 
		
				catch(IOException e) 
				{
					e.printStackTrace();
				}
		
			}
			
}
