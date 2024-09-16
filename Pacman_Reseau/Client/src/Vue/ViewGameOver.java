package Vue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewGameOver {
    JFrame jFrameGameOver;
    
    public ViewGameOver(){
        jFrameGameOver = new JFrame("Game Over");

        // Création de la fenetre au milieu de l'écran

            jFrameGameOver.setSize(new Dimension(800, 700));

            Dimension windowSize = jFrameGameOver.getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();
            int dx = centerPoint.x - windowSize.width / 2;
            int dy = centerPoint.y - windowSize.height / 2;
            jFrameGameOver.setLocation(dx, dy);

            jFrameGameOver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création de tous les éléments

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel labelImage = new JLabel(new ImageIcon("src/icons/GameOver.gif"));
            labelImage.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JButton boutonRestart = new JButton("Recommencer");
            boutonRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
            mettreEnFormeBouton(boutonRestart);
            
            JButton boutonQuit = new JButton("Quitter");
            boutonQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
            mettreEnFormeBouton(boutonQuit);

                    boutonRestart.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {
                            jFrameGameOver.dispose();
                            ViewMenu menu = new ViewMenu();
                            menu.show();
                        }
                    });

                    boutonQuit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evenement) {
                            jFrameGameOver.dispose();
                        }
                    });

            
            panel.add(labelImage);
            panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
            panel.add(boutonRestart);
            panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible
            panel.add(boutonQuit);
            panel.add(Box.createVerticalGlue()); // Ajoutez un espace flexible


            panel.setBackground(Color.DARK_GRAY);

        jFrameGameOver.setContentPane(panel);
    }
    public void mettreEnFormeBouton(JButton bouton){
        bouton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bouton.setPreferredSize(new Dimension(400, 100));
        bouton.setBackground(new Color(249, 195, 40));
        bouton.setFont(new Font("Comfortaa", Font.BOLD, 25));
    }

    public void show(){
        jFrameGameOver.setVisible(true);
    }
}
