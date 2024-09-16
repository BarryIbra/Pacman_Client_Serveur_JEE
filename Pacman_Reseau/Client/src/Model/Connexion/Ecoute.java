package Model.Connexion;

import java.io.IOException;

import Model.Game.PacmanGame;

public class Ecoute implements Runnable{
    private Thread thread;
    // private int time = 100;
    public PacmanGame game;

    public Ecoute(PacmanGame game) {
        this.game = game;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        while (!game.reponse.isGameFinished()) {

            try {
                game.reponse = game.connexion.readReponse();
            } catch (IOException e) {
                e.printStackTrace();
            }

            game.setTurn(game.reponse.getTurn());

            game.notifierObservateurs();
            // System.out.println(game.reponse);
        }

        if (game.reponse.isGameOver())
            game.gameOver();
        else
            game.victory();

    }

    public void launch(){
        thread.start();
    }
}
