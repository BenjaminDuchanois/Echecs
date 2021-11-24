import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Cavalier extends Piece{
	public Cavalier(int x, int y)
	{
		super(x, y);
		this.nom = "Cavalier";
		File input;
		try
		{
			if(this.blanc) input = new File("../images/Cavalier_Blanc.png");
			else input = new File("../images/Cavalier_Noir.png");
			this.img = ImageIO.read(input);
		} 

		catch(IOException e) 
		{
			e.printStackTrace();
		}

	}

	public boolean deplace(int x, int y){
		return true;
	}
			
}
