package Controlleur;

import java.awt.event.*;

import Model.Agent.AgentAction;
import Model.Game.PacmanGame;
import Vue.*;

public class ControllerPacmanGame extends AbstractController implements KeyListener {

    public ViewPacmanGame viewGame;
    public ViewCommand viewCommand;


    public ControllerPacmanGame(PacmanGame game) throws Exception {
        super(game);

        game.init();
        
        viewGame = new ViewPacmanGame(game);
        viewCommand = new ViewCommand(this);
        
        viewGame.jFrame.addKeyListener(this);   // on va "écouter" le clavier pour jouer
        
        game.enregistrerObservateur(viewCommand);
        game.enregistrerObservateur(viewGame);

        viewCommand.jFrame.setVisible(true);

        // envois du username au serveur pour maintenir les stats
        // game.connexion.sendString("username");
    }

    public void setFocusGame(){
        viewGame.jFrame.requestFocus();
    }

    public void closeViewCommandIfGameFinished(){
        if (game.isGameFinished()) {
            viewCommand.jFrame.dispose();
        }
    }

    //############################################
    //#             CONTROLES CLAVIER            #
    //############################################

    public void keyPressed(KeyEvent k)
    {
        int keyCode = k.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                game.changeDirection(AgentAction.WEST);
                break;
            case KeyEvent.VK_UP:
                game.changeDirection(AgentAction.NORTH);
                break;
            case KeyEvent.VK_RIGHT:
                game.changeDirection(AgentAction.EAST);
                break;
            case KeyEvent.VK_DOWN:
                game.changeDirection(AgentAction.SOUTH);
                break;

            default:
                game.changeDirection(AgentAction.STOP);
                break;
        }
    }
        
    public void keyReleased(KeyEvent k){}
    public void keyTyped(KeyEvent k){}

    public ViewPacmanGame getViewGame() {
        return viewGame;
    }
}
