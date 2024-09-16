package Vue;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Model.*;
import Model.Agent.Agent;
import Model.Agent.PositionAgent;
import Model.Game.Observateur;
import Model.Game.PacmanGame;

public class ViewPacmanGame implements Observateur{

    public JFrame jFrame;
    PanelPacmanGame panelGame;
    PacmanGame game;
    JLabel labelScore, labelLives, labelTurn;

    public ViewPacmanGame(PacmanGame game) {
        this.jFrame = new JFrame("Pacman Game");
        this.game = game;
        jFrame.setSize(new Dimension(game.getLabyrinthe().getSizeX()*30, game.getLabyrinthe().getSizeY()*30 + 30));
        Dimension windowSize = jFrame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dy = centerPoint.y - windowSize.height / 2 - 350;
        if (game.playerNumber == 1) jFrame.setLocation(jFrame.getWidth(), dy);
        else jFrame.setLocation(0, dy);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BorderLayout mainLayout = new BorderLayout();
        JPanel mainPanel = new JPanel(mainLayout);

        //=============== Panel des points / vies / tour de jeu ===============

            JPanel panelPoints = new JPanel();
                Font font = new Font("Comfortaa", Font.BOLD, 16);
                labelScore = new JLabel("Score : " + game.getScore() + " pts       ");
                labelLives = new JLabel("Nombre de vies : " + game.getPacmans().get(0).getLives() + "       ");
                labelTurn = new JLabel("Tour " + game.reponse.getTurn() + " / " + game.getMaxTurn());
                labelScore.setFont(font);
                labelLives.setFont(font);
                labelTurn.setFont(font);
                
                if (game.playerNumber == 1){
                    panelPoints.add(labelScore);
                    panelPoints.add(labelLives);
                    panelPoints.setBackground(Color.CYAN);
                }
                else panelPoints.setBackground(Color.PINK);

                panelPoints.add(labelTurn);
                
                mainPanel.add(panelPoints, BorderLayout.PAGE_START);
        
        //=============== Panel du jeu ===============

            try {
                this.panelGame = new PanelPacmanGame(game.getLabyrinthe(), game.playerNumber);
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
                    labelLives.setText("Nombre de vies : " + reponse.getPacmans().get(0).getLives());
                    labelTurn.setText("Tour " + game.reponse.getTurn() + " / " + game.getMaxTurn());
                }
            
            // On met à jour les foods et capsules
                // On reinitialise à zero les foods et capsules
                    for (int i = 0; i < game.getLabyrinthe().getSizeX(); i++) {
                        for (int j = 0; j < game.getLabyrinthe().getSizeY(); j++) {
                            game.getLabyrinthe().setFood(i, j, false);
                            game.getLabyrinthe().setCapsule(i, j, false);
                        }
                    }

                for (PositionAgent posFood : reponse.getPosFoods()) {
                    game.getLabyrinthe().setFood(posFood.getX(), posFood.getY(), true);
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
