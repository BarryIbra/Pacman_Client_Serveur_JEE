package Model.Agent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Game.Maze;
import Model.Game.PacmanGame;

public class StrategyTrap implements Strategy {

    private PacmanGame game;
    private static final int MAX_DISTANCE = 5; // Modifier cette valeur selon vos besoins
    private String type;

    public StrategyTrap(PacmanGame game) {
        this.game = game;
        this.type = "TRAP";
    }

    @JsonCreator
    public static StrategyTrap createStrategyTrap(@JsonProperty("game") PacmanGame game) {
        return new StrategyTrap(game);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PositionAgent getPositionAheadPacman(Agent pacman, Maze labyrinthe) {
        PositionAgent posPacman = pacman.getPosition();
        AgentAction action = new AgentAction(posPacman.getDir());

        int vx = action.get_vx();
        int vy = action.get_vy();

        int nextX = posPacman.getX();
        int nextY = posPacman.getY();

        // Vérifier les positions jusqu'à une distance maximale devant le Pacman
        for (int i = 0; i < MAX_DISTANCE; i++) {
            nextX += vx;
            nextY += vy;

            // Vérifier si les coordonnées sont dans les limites du labyrinthe
            if (nextX >= 0 && nextX < labyrinthe.getSizeX() && nextY >= 0 && nextY < labyrinthe.getSizeY()) {

                if (labyrinthe.isWall(nextX, nextY)) {
                    // S'il y a un mur, revenir à la position précédente
                    return new PositionAgent(nextX - vx, nextY - vy, posPacman.getDir());
                }
            } else {
                // Si les coordonnées sortent du labyrinthe, revenir à la position précédente
                return new PositionAgent(nextX - vx, nextY - vy, posPacman.getDir());
            }
        }

        // Si aucune position n'est trouvée, rester sur place
        return posPacman;
    }

    public AgentAction getAction(Agent agent, Agent target, Maze labyrinthe) {
        PositionAgent positionAhead = getPositionAheadPacman(target, labyrinthe);

        AgentAction action = new AgentAction(agent.getPosition().getDir());
        action.set_vx(positionAhead.getX() - agent.getPosition().getX());
        action.set_vy(positionAhead.getY() - agent.getPosition().getY());

        StrategyTarget strat = new StrategyTarget(game);
        return strat.getAction(agent, new Agent(positionAhead), labyrinthe);
    }
}
