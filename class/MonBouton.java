import javax.swing.*;

//Sert à donner un x et un y aux boutons pour les localiser
public class MonBouton extends JButton {
  public int x, y;

  private static final long serialVersionUID = 1000000000;
 
  public MonBouton(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  public int getMonX(){
    return this.x;
  }

  public int getMonY(){
    return this.y;
  }
}