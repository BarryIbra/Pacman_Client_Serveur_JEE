package Model.Game;

import java.lang.Runnable;

public abstract class Game implements Runnable {
    public static long TIME = 300;
    private Thread thread;
    private int turn, maxTurn, score;
    private boolean isRunning, isGameOver, isGameFinished;

    public Game(int maxTurn) {
        this.turn = 0;
        this.maxTurn = maxTurn;
        this.isRunning = false;
        this.isGameOver = false;
        this.isGameFinished = false;
        this.score = 0;
    }

    public abstract void initializeGame();
    public abstract void takeTurn();
    public abstract void gameOver();
    public abstract void victory();
    public abstract boolean gameContinue();
    public abstract void changeDirectionPacman(int direction);
    public abstract void changeDirectionGhost(int direction);

    public void init() {
        turn = 0;
        initializeGame();
    }

    ///////////// Controle du Jeu \\\\\\\\\\\\\

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
        while (isRunning) {
            step();

            try {
                Thread.sleep(TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void launch() {
        isRunning = true;
        thread = new Thread(this);

        thread.start();
    }

    ///////////// Score \\\\\\\\\\\\\

    public void addScore(int point) {
        score += point;
    }

    public void resetScore() {
        score = 0;
    }

    ///////////// Getters / Setters \\\\\\\\\\\\\

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
        setGameFinished(isGameOver);
        this.isGameOver = isGameOver;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

}
