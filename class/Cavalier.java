import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Cavalier extends Piece{

	//Constructeur de la pièce cavalier, gérant ses coordonnées et son image
	public Cavalier(int x, int y, boolean blanc)
	{
		super(x, y, blanc);
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

	//Son déplacement est possible que si il se déplace de 2 cases dans une direction
	//Et de 1 case dans l'autre
	public boolean deplace(int x, int y){
		if( 
			(
			((this.y == y-2) || (this.y == y+2))
		 && ((this.x == x+1) || (this.x == x-1))
		    )
		||  
			(
			((this.y == y-1) || (this.y == y+1))
		 && ((this.x == x+2) || (this.x == x-2))
		 	) 
		  )
			return true;
		return false;
	}
			
}
