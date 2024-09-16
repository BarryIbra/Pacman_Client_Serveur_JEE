package Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import Model.Agent.Agent;
import Model.Agent.PositionAgent;

public class Reponse {
    private List<Agent> pacmans = new ArrayList<>();
    private List<Agent> ghosts = new ArrayList<>();
    private List<PositionAgent> posFoods = new ArrayList<>();
    private List<PositionAgent> posCapsule = new ArrayList<>();
    private int score = 0, turn = 0, numberOfLives = 1, numberOfLivesGiven = 0, playerNumber;
    private boolean isScared = false, isGameFinished = false, isGameOver = false;

    public Reponse() {
    }

    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static Reponse createReponse() {
        return new Reponse();
    }

    public List<Agent> getPacmans() {
        return pacmans;
    }

    public void setPacmans(List<Agent> pacmans) {
        this.pacmans = pacmans;
    }

    public List<Agent> getGhosts() {
        return ghosts;
    }

    public void setGhosts(List<Agent> ghosts) {
        this.ghosts = ghosts;
    }

    public List<PositionAgent> getPosFoods() {
        return posFoods;
    }

    public void setPosFoods(List<PositionAgent> posFoods) {
        this.posFoods = posFoods;
    }

    public List<PositionAgent> getPosCapsule() {
        return posCapsule;
    }

    public void setPosCapsule(List<PositionAgent> posCapsule) {
        this.posCapsule = posCapsule;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    public int getNumberOfLivesGiven() {
        return numberOfLivesGiven;
    }

    public void setNumberOfLivesGiven(int numberOfLivesGiven) {
        this.numberOfLivesGiven = numberOfLivesGiven;
    }

    public boolean isScared() {
        return isScared;
    }

    public void setScared(boolean isScared) {
        this.isScared = isScared;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    @Override
    public String toString() {
        return "Reponse [" +
                "\n\t - pacmans=" + pacmans +
                "\n\t - ghosts=" + ghosts +
                // "\n\t - posFoods=" + posFoods +
                "\n\t - posCapsule=" + posCapsule +
                "\n\t - score=" + score +
                "\n\t - numberOfLives=" + numberOfLives +
                "\n\t - isScared=" + isScared +
                "\n\t - isGameFinished=" + isGameFinished + "\n]";
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
    
}
