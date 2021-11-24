import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class Piece{
	//Classe abstraite représentant toutes les Pièces par des coordonnées
	//Un nom, une couleur et une image
	protected int x;
	protected int y;
	protected boolean blanc;
	protected String nom; 
	protected Image img;
	
	//Gère les coordonnées, et permet d'inialiser les pièces de la bonne couleur
	//Selon si elles sont sur les 2 lignes du haut ou du bas.
	public Piece(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		if(x == 5 || x == 6) this.blanc = true;
		else if (x == 0 || x == 1) this.blanc = false;
		
	}

	public Image getImage(){
		return this.img;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	//Cette fonction permettra de savoir de quelle façon chaque pièce a le droit de se déplacer.
	public abstract boolean deplace(int x, int y);

}