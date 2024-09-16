package Model.Game;

import java.io.IOException;
import java.lang.Runnable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Connexion.ConnexionServeur;

public abstract class Game implements Runnable {
    public static long TIME = 300;
    private Thread thread;
    private int turn, maxTurn, score;
    private boolean isRunning, isGameOver, isGameFinished;
    public static String mazeFileName = "src/layouts/originalClassic.lay";

    // Pour communiquer avec le serveur
    public static String ADRESSEIP = "172.20.10.13"; // le serveur
    public static int PORT = 2000; // le port de connexion
    public String username;
    public ConnexionServeur connexion;

    public Game(int maxTurn) throws IOException {
        this.turn = 0;
        this.maxTurn = maxTurn;
        this.isRunning = false;
        this.isGameOver = false;
        this.isGameFinished = false;
        // this.time = 300;
        this.score = 0;
        connexion = new ConnexionServeur(ADRESSEIP, PORT);
    }

    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static Game createGame(
            @JsonProperty("maxTurn") int maxTurn,
            @JsonProperty("fileNameMaze") String fileNameMaze) throws Exception {
        return new PacmanGame(maxTurn, mazeFileName);
    }

    public abstract void initializeGame();
    public abstract void takeTurn();
    public abstract void gameOver();
    public abstract void victory();
    public abstract boolean gameContinue();
    public abstract void changeDirection(int direction);
    public abstract void startThreads();
    public abstract int getPlayerNumber();

    public void init() {
        turn = 0;
        initializeGame();
    }

    public void step() {
        turn++;

        if (gameContinue() && turn <= maxTurn) {
            takeTurn();
        } else {
            isRunning = false;
            isGameFinished = true;
            if (turn >= maxTurn || isGameOver)
                gameOver();
            else
                victory();
        }
    }

    public void pause() {
        isRunning = false;
    }

    public void run() {
        // while (isRunning) {
        //     step();

        //     try {
        //         Thread.sleep(TIME);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
    }

    public void launch() {
        isRunning = true;
        // thread = new Thread(this);

        startThreads(); // pour l'ecoute et l'envois avec le serveur

        // thread.start();
    }

    //////////// Getters - Setters \\\\\\\\\\\\

    // public long getTime() {
    //     return time;
    // }

    // public void setTime(long time) {
    //     this.time = time;
    // }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getMaxTurn() {
        return maxTurn;
    }

    public void setMaxTurn(int maxTurn) {
        this.maxTurn = maxTurn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int point) {
        score += point;
    }

    public void resetScore() {
        score = 0;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    
}
