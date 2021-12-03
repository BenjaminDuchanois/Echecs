import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Tour extends Piece{

	//Constructeur de la pièce tour, gérant ses coordonnées et son image
	public Tour(int x, int y, boolean blanc)
	{
		super(x, y, blanc);
		this.nom = "Tour";
		File input;
		try
		{
			if(this.blanc) input = new File("../images/Tour_Blanc.png");
			else input = new File("../images/Tour_Noir.png");
			this.img = ImageIO.read(input);
		} 

		catch(IOException e) 
		{
			e.printStackTrace();
		}

	}

	//La tour se déplace en ligne, donc tant qu'elle reste soit sur la même
	//ligne ou sur la même colonne, c'est bon.
	public boolean deplace(int x, int y){
		if((this.y==y) || (this.x==x))
			return true;
		return false;
	}

}
