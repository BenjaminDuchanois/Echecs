import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.*;

public class Pion extends Piece {

	private static final long serialVersionUID = 1L;

	//Constructeur de la pièce Pion, gérant ses coordonnées et son image
	public Pion(int x, int y, boolean blanc){
		super(x, y, blanc);
		this.nom = "Pion";
	}


	public boolean deplace(int x, int y){
	//Ici on gère que le cas où un pion mange une pièce adverse, son autre type de déplacement est directement
	//géré dans le controleur.
	//On vérifie alors si c'est un pion blanc, qu'il monte d'une case et se déplace de 1 colonne, et si c'est
	//un pion noir, qu'il descende d'une case à la place, tout en se déplaçant d'une colonne.
		if (Modele.plateau[x][y] != null)
		{
			if (this.blanc){
				if (((this.y == y+1) && (this.x == x+1)) || ((this.y == y-1) && (this.x == x+1)))
					return true;}
			else
				if (((this.y == y+1) && (this.x == x-1)) || ((this.y == y-1) && (this.x == x-1)))
					return true;
		}
		//Sinon, le pion ne se déplace alors que d'une case selon sa couleur
		else
		{
			if ((this.blanc) && (this.y == y) && (this.x == x+1))
				return true;
			if ((!this.blanc) && (this.y == y) && (this.x == x-1))
				return true;
		}
		
		return false;
	}
}
