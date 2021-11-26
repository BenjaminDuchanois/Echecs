import javax.swing.*;
import java.awt.*;

public class EcranFin extends JFrame {
    Jeu parent;
    boolean blanc;

    public EcranFin(Jeu parent, boolean blanc)
    {
        this.parent = parent;
        this.blanc = blanc;

        JPanel pn = new JPanel(); 
        JPanel pn2 = new JPanel();

        //pn.setPreferredSize(new Dimension(400,300));
        pn.setLayout(new BorderLayout());

        JLabel lb = new JLabel("", SwingConstants.CENTER);

        if (blanc)
            lb.setText("Les blancs ont gagne !");
        else
            lb.setText("Les noirs ont gagne !");
        lb.setPreferredSize(new Dimension(400,150));
        pn.add(lb, BorderLayout.CENTER);

        JButton btRejoue = new JButton("Rejouer");
        JButton btQuit = new JButton("Quitter");

        pn2.setLayout(new FlowLayout());
        pn2.add(btRejoue);
        pn2.add(btQuit);

        btQuit.addActionListener(e -> {
            this.dispose();
            this.parent.plateau.dispose();
         });

         btRejoue.addActionListener(e -> {
            this.dispose();
            this.parent.plateau.dispose();
            this.parent.rejouer();
         });

        pn.add(pn2, BorderLayout.SOUTH);
        

        this.add(pn);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,350);
        this.setTitle("Fin du jeu");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
    }
}