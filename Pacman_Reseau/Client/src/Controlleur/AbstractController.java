package Controlleur;

import Model.Game.Game;
import Vue.ViewPacmanGame;

public abstract class AbstractController {
    public Game game;


    public AbstractController(Game game) {
        this.game = game;
    }

    public abstract ViewPacmanGame getViewGame();

    public void setFocusGame(){}

    public void restart(){
        System.out.println("Game restarted");
        game.pause();
        game.init();
    }
    
    public void step(){
        System.out.println("Game stepped");
        game.step();
    }
    
    public void play(){
        System.out.println("Game played");
        game.launch();
    }
    
    public void pause(){
        System.out.println("Game paused");
        game.pause();
    }
    
    // public void setSpeed(double speed){
    //     // System.out.println("Game speed set : " + speed);
    //     game.setTime((long)speed);
    // }

    public abstract void closeViewCommandIfGameFinished();

}
