import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Fou extends Piece{

	//Constructeur de la pièce Fou, gérant ses coordonnées et son image
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

	//Il se déplace en diagonal, donc si l'incrémentation de x et y
	//Est de même valeur, ou de valeur inverse
	public boolean deplace(int x, int y){
		for(int i=-7; i<7; i++)
			if(((this.x==x+i) && (this.y==y+i)) || ((this.x==x+i) && (this.y==y-i)))
				return true;
		return false;
	}
			
}
