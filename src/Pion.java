import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

public class Pion extends Piece{

	//Constructeur de la pièce Pion, gérant ses coordonnées et son image
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

	//Ici on gère que le cas où un pion mange une pièce adverse, son autre type de déplacement est directement
	//géré dans le controleur.
	//On vérifie alors si c'est un pion blanc, qu'il monte d'une case et se déplace de 1 colonne, et si c'est
	//un pion noir, qu'il descende d'une case à la place, tout en se déplaçant d'une colonne.
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
