package Vue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewVictory {
    JFrame jFrame;
    
    public ViewVictory(){
        jFrame = new JFrame("Victoire");

        // Création de la fenetre au milieu de l'écran

            jFrame.setSize(new Dimension(700, 400));

            Dimension windowSize = jFrame.getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();
            int dx = centerPoint.x - windowSize.width / 2;
            int dy = centerPoint.y - windowSize.height / 2;
            jFrame.setLocation(dx, dy);

            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création de tous les éléments

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel labelImage = new JLabel(new ImageIcon("src/icons/Victory.gif"));
            labelImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JButton boutonRestart = new JButton("Recommencer");
            boutonRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
            mettreEnFormeBouton(boutonRestart);
            
            JButton boutonQuit = new JButton("Quitter");
            boutonQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
            mettreEnFormeBouton(boutonQuit);

                    boutonRestart.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {
                            jFrame.dispose();
                            ViewMenu menu = new ViewMenu();
                            menu.show();
                        }
                    });

                    boutonQuit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {
                            jFrame.dispose();
                        }
                    });

            
            panel.add(labelImage);
            panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
            panel.add(boutonRestart);
            panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
            panel.add(boutonQuit);
            panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible


            panel.setBackground(Color.DARK_GRAY);

        jFrame.setContentPane(panel);
    }
    public void mettreEnFormeBouton(JButton bouton){
        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton.setPreferredSize(new Dimension(400, 100));
        bouton.setBackground(new Color(249, 195, 40));
        bouton.setFont(new Font("Comfortaa", Font.BOLD, 25));
    }

    public void show(){
        jFrame.setVisible(true);
    }
}
