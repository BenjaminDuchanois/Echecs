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

	public boolean deplace(int x, int y){
		if((this.x == x) || (this.y == y))
			return true;
			for(int i=0; i<7; i++)
		if((((this.x==x+i) && (this.y==y+i)) || ((this.x==x+i) && (this.y==y-i)))
		||(((this.x==x-i) && (this.y==y+i)) || ((this.x==x-i) && (this.y==y-i))))
			return true;
		return false;
	}
			
}
