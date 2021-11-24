import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Pion extends Piece{

	public Pion(int x, int y){
		super(x, y);

		this.nom = "Pion";
		File input;

		try
		{
			if(this.blanc) 
				input = new File("../images/Pion_Blanc.png");
			else 
				input = new File("../images/Pion_Noir.png");
			this.img = ImageIO.read(input);
		} 
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}

	public boolean deplace(int x, int y){
		if (this.blanc){
			if (((this.y == y+1) && (this.x == x+1)) || ((this.y == y-1) && (this.x == x+1)))
				return true;}
		else
			if (((this.y == y+1) && (this.x == x-1)) || ((this.y == y-1) && (this.x == x-1)))
				return true;
		return false;
	}
}
