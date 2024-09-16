package Model.Agent;

    import java.util.LinkedList;
    import java.util.Queue;
    
    import com.fasterxml.jackson.annotation.JsonCreator;
    import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Game.Maze;
import Model.Game.PacmanGame;


public class StrategySolver implements Strategy {
    
    private PacmanGame game;
    int[] dx = { 0, 1, 0, -1 }; // Déplacements selon x pour chaque direction : Nord, Est, Sud, Ouest
    int[] dy = { -1, 0, 1, 0 }; // Déplacements selon y pour chaque direction : Nord, Est, Sud, Ouest
    private String type;

    public StrategySolver(PacmanGame game) {
        this.game = game; 
        this.type  = "SOLVER"; 
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static StrategySolver createStrategySolver(@JsonProperty("game") PacmanGame game) {
        return new StrategySolver(game);
    }

    /*
     * Méthode pour trouver la nourriture la plus proche à partir de la position actuelle du pacman
     */ 
    public PositionAgent getNearestFood(Agent pacman, Maze labyrinthe) 
    {
        PositionAgent currentPos = pacman.getPosition();

        // Tableau pour suivre les noeuds visités dans le labyrinthe
        boolean[][] visited = new boolean[labyrinthe.getSizeX()][labyrinthe.getSizeY()];
        
        Queue<PositionAgent> queue = new LinkedList<>(); // File pour stocker les positions à explorer
    
        // Ajout de la position actuelle à la file et marquage comme visitée
        queue.add(currentPos);
        visited[currentPos.getX()][currentPos.getY()] = true;
    
        while (!queue.isEmpty()) {
            // Récupération et suppression de la position en tête de file
            PositionAgent pos = queue.poll();
    
            // Exploration des 4 directions possibles
            for (int dir = 0; dir < 4; dir++) {
                int newX = pos.getX() + dx[dir];
                int newY = pos.getY() + dy[dir];
    
                // Vérification si les nouvelles coordonnées sont valides et non visitées
                if (isValid(newX, newY, labyrinthe) && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    PositionAgent nextPos = new PositionAgent(newX, newY, dir);
    
                    // Vérification si la nouvelle position contient de la nourriture
                    if (labyrinthe.isFood(newX, newY)) {
                        return nextPos;
                    }
                    // Ajout de la nouvelle position à la file pour l'explorer
                    queue.add(nextPos);
                }
            }
        }
        return null; // Aucune nourriture trouvée
    }
    
    // Vérifie si les coordonnées sont valides et ne sont pas un mur
    private boolean isValid(int x, int y, Maze labyrinthe) {
        return x >= 0 && x < labyrinthe.getSizeX() && y >= 0 && y < labyrinthe.getSizeY() && !labyrinthe.isWall(x, y);
    }
        

    // Méthode pour obtenir l'action à effectuer par un agent (Pacman) pour se déplacer vers la nourriture la plus proche
    public AgentAction getAction(Agent agent, Agent target, Maze labyrinthe) {
        PositionAgent nearestFood = getNearestFood(agent, labyrinthe);

        if (nearestFood != null) {
            StrategyTarget strat = new StrategyTarget(game);
            
            return strat.getAction(agent, new Agent(nearestFood), labyrinthe);
        }
        
        // Si aucune nourriture n'est trouvée, agir de manière aléatoire
        System.out.println("Aucune nourriture trouvée");

        StrategyRandom strat = new StrategyRandom(game);
        return strat.getAction(agent, null, labyrinthe);
    }

}
