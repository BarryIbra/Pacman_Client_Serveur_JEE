package Model.Agent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Agent {

    private PositionAgent position;
    private Strategy strategy;
    private int numberOfLives = 1;

    public Agent(PositionAgent position) {
        this.position = position;
    }

    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static Agent createAgent(
            @JsonProperty("position") PositionAgent position,
            @JsonProperty("strategy") Strategy strategy,
            @JsonProperty("numberOfLives") int numberOfLives) {
        Agent agent = new Agent(position);
        agent.setStrategy(strategy);
        agent.setNumberOfLives(numberOfLives);
        return agent;
    }

    public PositionAgent getPosition() {
        return position;
    }

    public void setPosition(PositionAgent pos) {
        position = pos;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void removeOneLife() {
        numberOfLives--;
    }

    public void addOneLife() {
        numberOfLives++;
    }

    public void restartLives() {
        numberOfLives = 1;
    }

    @Override
    public String toString() {
        return "Agent [position=" + position + ", numberOfLives=" + numberOfLives + "]";
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

}
