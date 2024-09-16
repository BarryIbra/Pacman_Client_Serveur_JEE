package Model.Connexion;

import java.io.IOException;

import Model.Input;

public class Ecoute implements Runnable{
    private Thread thread;
    // private int time = 100;
    public Serveur serveur;
    int timer = 0;

    public Ecoute(Serveur serveur) {
        this.serveur = serveur;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        while (!serveur.pacmanGame.reponse.isGameFinished()) {
            
            for (Connexion player : serveur.players) {
                try {
                    Input input = player.readInput();
                    // System.out.println(input.getAction() + " <- player " + player.getNumber());

                    switch (input.getTypeInput()) {
                        case 0: serveur.pacmanGame.initializeGame(); break; // Initialisation
                        case 1: // Input clavier
                            if (player.getNumber() == 1) serveur.pacmanGame.changeDirectionPacman(input.getAction());
                            else                         serveur.pacmanGame.changeDirectionGhost(input.getAction()); break;
                        case 2: break; // Input Play
                        case 3: break; // Input Pause
                        case 4: break; // Input Step
                        // default: serveur.pacmanGame.initializeGame(); break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void launch(){
        thread.start();
    }
}
