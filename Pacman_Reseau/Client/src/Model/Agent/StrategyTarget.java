package Model.Agent;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Game.Maze;
import Model.Game.PacmanGame;

public class StrategyTarget implements Strategy {

    private PacmanGame game;
    private String name = "TARGET";

    public StrategyTarget(PacmanGame game) { 
        this.game = game;
    }
    @JsonCreator
    public static StrategyTarget createStrategyTarget(@JsonProperty("game") PacmanGame game) {
        return new StrategyTarget(game);
    }
    
    public String getStrategyName(){ return name; }

    public AgentAction getAction(Agent agent, Agent target, Maze labyrinthe) {

        // Calcul des déplacements valides 
        List<AgentAction> legalActions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AgentAction action = new AgentAction(i);
            if (game.isLegalMove(agent, action)) {
                legalActions.add(action);
            }
        }

        AgentAction meilleureAction = null;
        int distancePourFuir = 0;
        int distancePourAttaquer = 999999;

        for (AgentAction action : legalActions) {
            PositionAgent newPos = new PositionAgent(agent.getPosition().getX() + action.get_vx(), agent.getPosition().getY() + action.get_vy(), action.get_direction());
            int distanceVersPacman = game.calculerDistance(newPos, target.getPosition(), labyrinthe);

            if (distanceVersPacman < distancePourAttaquer && !game.isScared()) {
                distancePourAttaquer = distanceVersPacman;
                meilleureAction = action;
            } else if (distanceVersPacman > distancePourFuir && game.isScared()) {
                distancePourFuir = distanceVersPacman;
                meilleureAction = action;
            }
        }
        return meilleureAction;
    }
}
