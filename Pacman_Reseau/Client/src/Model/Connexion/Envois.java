package Model.Connexion;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Input;
import Model.Game.PacmanGame;

public class Envois implements Runnable{
    private Thread thread;
    // private int time = 100;
    public PacmanGame game;
    int timer = 0;

    public Envois(PacmanGame game) {
        this.game = game;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        while (!game.reponse.isGameFinished()) {

            try {
                game.connexion.sendInput(new Input(game.directionClavier, 1));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            // System.out.print(timer++ + "\t");
        }
    }

    public void launch(){
        thread.start();
    }
}
