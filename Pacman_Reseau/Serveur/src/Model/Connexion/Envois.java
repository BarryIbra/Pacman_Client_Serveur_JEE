package Model.Connexion;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Game.Game;

public class Envois implements Runnable {
    private Thread thread;
    // private int time = 100;
    public Serveur serveur;

    public Envois(Serveur serveur) {
        this.serveur = serveur;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        while (!serveur.pacmanGame.reponse.isGameFinished()) {
            try {
                serveur.pacmanGame.updateGame();
                serveur.vue.actualiser(serveur.pacmanGame.getTurn() , serveur.pacmanGame.reponse);

                for (Connexion player : serveur.players) {
                    player.sendReponse(serveur.pacmanGame.reponse);
                }

                // System.out.print("RS\t");

                Thread.sleep(Game.TIME);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // On envoie une dernière fois le jeu finis
        for (Connexion player : serveur.players) {
            try {
                player.sendReponse(serveur.pacmanGame.reponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void launch() {
        thread.start();
    }
}
