package Vue;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Model.*;
import Model.Agent.Agent;
import Model.Agent.PositionAgent;
import Model.Game.Maze;
import Model.Game.PacmanGame;

public class ViewPacmanGame {

    public JFrame jFrame;
    PanelPacmanGame panelGame;
    PacmanGame game;
    JLabel labelScore, labelLives;

    public ViewPacmanGame(PacmanGame game) {
        this.jFrame = new JFrame("Pacman Game");
        this.game = game;
        jFrame.setSize(new Dimension(game.getLabyrinthe().getSizeX()*15, game.getLabyrinthe().getSizeY()*15 + 15));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x + windowSize.height / 3 + 350;
        int dy = centerPoint.y + windowSize.height / 2 - 350;
        jFrame.setLocation(dx, dy);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BorderLayout mainLayout = new BorderLayout();
        JPanel mainPanel = new JPanel(mainLayout);

        //=============== Panel des points / vies ===============
        
            JPanel panelPoints = new JPanel();
                labelScore = new JLabel("Score : " + game.getScore() + " pts              ");
                labelLives = new JLabel("Nombre de vies : " + 00);
                // labelLives = new JLabel("Nombre de vies : " + game.getPacmans().get(0).getLives());
                labelScore.setFont(new Font("Comfortaa", Font.BOLD, 16));
                labelLives.setFont(new Font("Comfortaa", Font.BOLD, 16));


                panelPoints.add(labelScore);
                // panelPoints.add(new JSeparator(JSeparator.VERTICAL));
                panelPoints.add(labelLives);
                panelPoints.setBackground(Color.CYAN);
            
            mainPanel.add(panelPoints, BorderLayout.PAGE_START);
        
        //=============== Panel du jeu ===============

            try {
                this.panelGame = new PanelPacmanGame(game.getLabyrinthe());
                mainPanel.add(panelGame, BorderLayout.CENTER);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        //============================================
            
        jFrame.setContentPane(mainPanel);
        jFrame.setVisible(true);
    }

    public void actualiser(int turn, Reponse reponse) {

        updateFromServer(reponse);
    }

    public void updateFromServer(Reponse reponse) {

        if (reponse.isGameFinished()) {
            jFrame.dispose();
        }
        else {
            // On récupère la position des pacmans et des ghosts
                ArrayList<PositionAgent> newPositionGhosts = new ArrayList<PositionAgent>();
                ArrayList<PositionAgent> newPositionPacmans = new ArrayList<PositionAgent>();
                for (Agent ghost : reponse.getGhosts())   {    newPositionGhosts.add(ghost.getPosition());   }
                for (Agent pacman : reponse.getPacmans()) {    newPositionPacmans.add(pacman.getPosition()); }

            // On récupère le score et les vies
                if(!reponse.getPacmans().isEmpty()){
                    labelScore.setText("Score : " + reponse.getScore() + " pts              ");
                    labelLives.setText("Nombre de vies : " + 00);
                    // labelLives.setText("Nombre de vies : " + reponse.getPacmans().get(0).getLives());
                }
            
            // On met à jour les foods et capsules
                // On reinitialise à zero les foods et capsules
                    for (int i = 0; i < game.getLabyrinthe().getSizeX(); i++) {
                        for (int j = 0; j < game.getLabyrinthe().getSizeY(); j++) {
                            game.getLabyrinthe().setFood(i, j, false);
                            game.getLabyrinthe().setCapsule(i, j, false);
                        }
                    }

                    // System.out.println(reponse);

                    for (PositionAgent posFood : reponse.getPosFoods()) {
                        int x = posFood.getX();
                        int y = posFood.getY();
                        Maze lab = game.getLabyrinthe();
                        lab.setFood(x, y, true);
                        // System.out.println("x:"+x+"  y:"+y);
                        // game.getLabyrinthe().setFood(posFood.getX(), posFood.getY(), true);
                    }
                    for (PositionAgent posCapsule : reponse.getPosCapsule()) {
                        game.getLabyrinthe().setCapsule(posCapsule.getX(), posCapsule.getY(), true);
                    }
                   
    
            panelGame.setGhostsScarred(reponse.isScared());
    
            panelGame.setGhosts_pos(newPositionGhosts);
            panelGame.setPacmans_pos(newPositionPacmans);
    
            panelGame.repaint();
        }
    }

}
