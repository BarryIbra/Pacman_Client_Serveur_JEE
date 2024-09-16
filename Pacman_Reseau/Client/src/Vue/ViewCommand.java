package Vue;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import Controlleur.AbstractController;
import Model.Input;
import Model.Reponse;
import Model.Game.Etat;
import Model.Game.EtatPaused;
import Model.Game.Observateur;

public class ViewCommand implements Observateur{
    
    public JFrame jFrame;
    private JLabel label_turn;
    private Etat etat;
    public JButton bouton_play, bouton_restart, bouton_pause, bouton_next;
    public AbstractController controller;
    
    public ViewCommand(AbstractController controller) {

        this.jFrame = new JFrame("Pacman Commands");
        this.label_turn = new JLabel("Turn : " + 0, JLabel.CENTER);
        this.controller = controller;
        this.etat = new EtatPaused(this);


        jFrame.setSize(new Dimension(800, 200));
        // Dimension windowSize = jFrame.getSize();
        // GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // Point centerPoint = ge.getCenterPoint();
        int dx = (controller.game.getPlayerNumber() == 1)? controller.getViewGame().jFrame.getWidth() : 0 ;
        int dy = controller.getViewGame().jFrame.getHeight();
        jFrame.setLocation(dx,dy);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //############# CRÉATION DE TOUS LES ELEMENTS #############

            GridLayout grille_verticale = new GridLayout(2, 1);
            GridLayout grille_horizontale = new GridLayout(1, 2);
            GridLayout grille_boutons = new GridLayout(1, 4);
            GridLayout grille_slide = new GridLayout(2, 1);

            Icon restartIcon = new ImageIcon("src/icons/icon_restart.png");
            Icon pauseIcon = new ImageIcon("src/icons/icon_pause.png");
            Icon runIcon = new ImageIcon("src/icons/icon_run.png");
            Icon stepIcon = new ImageIcon("src/icons/icon_step.png");

            bouton_restart = new JButton(restartIcon);
                bouton_restart.setEnabled(false);
            bouton_play = new JButton(runIcon);
            bouton_next = new JButton(stepIcon);
            bouton_pause = new JButton(pauseIcon);
                bouton_pause.setEnabled(false);
            JSlider slider = new JSlider(1, 30, 10);
            slider.setPaintTrack(true); 
            // slider.setPaintTicks(true); 
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(5); 

            JPanel mainPanel = new JPanel(grille_verticale);
            JPanel topPanel = new JPanel(grille_boutons);
            JPanel bottomPanel = new JPanel(grille_horizontale);
            JPanel slidePanel = new JPanel(grille_slide);
            
            topPanel.add(bouton_restart);
            topPanel.add(bouton_play);
            topPanel.add(bouton_next);
            topPanel.add(bouton_pause);

            slidePanel.add(new JLabel("Number of turns per second", JLabel.CENTER));
            slidePanel.add(slider);

            bottomPanel.add(slidePanel);
            bottomPanel.add(label_turn); 


            // Ajoute des composants à la deuxième ligne (vous pouvez ajouter ce que vous voulez ici)
            mainPanel.add(topPanel);
            mainPanel.add(bottomPanel); 

            Color background = (controller.game.getPlayerNumber() == 1)? Color.CYAN : Color.PINK;

            mainPanel.setBackground(background);
            topPanel.setBackground(background);
            bottomPanel.setBackground(background);
            slidePanel.setBackground(background);

            // Ajoute le JPanel principal au cadre
            jFrame.add(mainPanel);

        //############# AJOUT DE L'ECOUTEUR SUR LE SLIDER #############

            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent event){
                    JSlider source = (JSlider) event.getSource();
                    int sliderValue = source.getValue();

                    // controller.setSpeed(1000 / sliderValue);
                }
            });

        //############# AJOUT DES ECOUTEURS SUR LES BOUTONS #############

            bouton_restart.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evenement) {
                    try {
                        etat.restart();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            });
            bouton_next.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evenement) {
                    try {
                        etat.step();
                        controller.game.connexion.sendInput(new Input(0, 4));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            });
            bouton_pause.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evenement) {
                    try {
                        etat.pause();
                        controller.game.connexion.sendInput(new Input(0, 3));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            });
            bouton_play.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evenement) {
                    try {
                        etat.play();
                        controller.game.connexion.sendInput(new Input(0, 2));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            });


    }

    public void actualiser(int turn, Reponse reponse){ 
        controller.closeViewCommandIfGameFinished();
        label_turn.setText("Turn : " + turn);
    }

    public void changerEtat(Etat etat){ this.etat = etat; }
}
