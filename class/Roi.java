import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Roi extends Piece{

	//Constructeur de la pièce roi, gérant ses coordonnées et son image
	public Roi(int x, int y, boolean blanc)
	{
		super(x, y, blanc);
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


	//Le roi peut se déplacer d'une case dans toutes les directions, donc tant qu'il
	//ne se déplace pas de plus d'une colonne ou d'une ligne, c'est bon
	public boolean deplace(int x, int y){
		if((x-this.x > 1) || (x-this.x < -1))
			return false;
		if((y-this.y > 1) || (y-this.y < -1))
			return false;
		return true;
	}
			
}
