package Model.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Game.Maze;
import Model.Game.PacmanGame;

public class StrategyRandom implements Strategy {

    private PacmanGame game;
    private String type;

    public StrategyRandom(PacmanGame game) {
        this.game = game;
        this.type = "RANDOM";
    }

    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static StrategyRandom createStrategyRandom(@JsonProperty("game") PacmanGame game) {
        return new StrategyRandom(game);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AgentAction getAction(Agent agent, Agent target, Maze labyrinthe) {
        // Calcul des déplacements valides
        List<AgentAction> legalActions = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            AgentAction action = new AgentAction(i);
            if (game.isLegalMove(agent, action)) {
                legalActions.add(action);
            }
        }

        if (!legalActions.isEmpty()) {
            Random random = new Random();
            return legalActions.get(random.nextInt(legalActions.size()));
        }

        // Si aucune action légale n'est possible, retourner null
        return null;
    }
}
