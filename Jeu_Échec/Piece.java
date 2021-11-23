import javax.swing.*;
import java.awt.*;

public abstract class Piece{
		private int x;
		private int y;
		boolean blanc;
		String nom; 
		
		public Piece(int x, int y)
		{
			this.x = x;
			this.y = y;
			
			if(x == 5 || x == 6) this.blanc = false;
			else if (x == 0 || x == 1) this.blanc = true;
			
		}

}
