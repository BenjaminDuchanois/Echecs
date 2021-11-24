import java.io.IOException;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class Piece{
		protected int x;
		protected int y;
		protected boolean blanc;
		protected String nom; 
		protected Image img;
		
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

		public abstract boolean deplace(int x, int y);

}
