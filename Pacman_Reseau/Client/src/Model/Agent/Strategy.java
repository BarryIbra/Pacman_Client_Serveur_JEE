package Model.Agent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Game.Maze;
import Model.Game.PacmanGame;

public interface Strategy {

    AgentAction getAction(Agent agent, Agent target, Maze labyrinthe);

    String getStrategyName();

    // Méthode de création statique pour la désérialisation
    @JsonCreator
    static Strategy createStrategy(@JsonProperty("type") String type, @JsonProperty("game") PacmanGame game) {
        switch (type) {
            case "RANDOM": return new StrategyRandom(game);
            case "SOLVER": return new StrategySolver(game);
            case "TARGET": return new StrategyTarget(game);
            case "TRAP": return new StrategyTrap(game);
            default:
                throw new IllegalArgumentException("Type de stratégie inconnu : " + type);
        }
    }
}
