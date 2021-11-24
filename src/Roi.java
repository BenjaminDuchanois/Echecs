import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Roi extends Piece{
	public Roi(int x, int y)
	{
		super(x, y);
		this.nom = "Roi";
		File input;
		try
		{
			if(this.blanc) input = new File("../images/Roi_Blanc.png");
			else input = new File("../images/Roi_Noir.png");
			this.img = ImageIO.read(input);
		} 

		catch(IOException e) 
		{
			e.printStackTrace();
		}

	}

	public boolean deplace(int x, int y){
		if((x-this.x > 1) || (x-this.x < -1))
			return false;
		if((y-this.y > 1) || (y-this.y < -1))
			return false;
		return true;
	}
			
}
