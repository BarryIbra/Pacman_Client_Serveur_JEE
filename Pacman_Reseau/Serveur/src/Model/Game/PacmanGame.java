package Model.Game;

import java.util.*;

import Model.Reponse;
import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Agent.PositionAgent;
import Model.Agent.Strategy;
import Model.Agent.StrategyRandom;
import Model.Agent.StrategySolver;
import Model.Agent.StrategyTarget;
import Model.Agent.StrategyTrap;


// Utile pour calculer la distance entre un ghost et un pacman
class Node {
    int x,y;
    int distance;   // distance de départ jusqu'a ce noeud
    int heuristic;  // distance estimé jusqu'à la cible

    public Node(int x, int y, int distance, int heuristic) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.heuristic = heuristic;
    }
}


public class PacmanGame extends Game {

    private Maze labyrinthe;

    // Listes des pacmans, ghosts, foods et capsules
    private List<Agent> pacmans = new ArrayList<>();
    private List<Agent> ghosts = new ArrayList<>();
    private List<PositionAgent> posFoods = new ArrayList<>();
    private List<PositionAgent> posCapsule = new ArrayList<>();
    public Agent mainPacman;
    public Agent mainGhost;
    
    // données lorsqu'une capsule est mangée
    private static int timeMaxCapsule = 20;
    private int currentTimeCapsule = 0;
    private boolean canKillGhosts = false;

    private int numberOfFood = 0, numberOfGhostEaten = 0, numberOfLivesGiven = 0;
    private int directionClavierPacman, directionClavierGhost;

    public Reponse reponse;


    //############################################
    //#      CONSTRUCTEUR / INITIALISATION       #
    //############################################
    
        public PacmanGame(int maxTurn, String fileNameMaze) throws Exception 
        {
            super(maxTurn);
            this.labyrinthe = new Maze(fileNameMaze);

            // On récupère la position de toutes les capsules et foods
            for (int x = 0; x < labyrinthe.getSizeX(); x++) {
                for (int y = 0; y < labyrinthe.getSizeY(); y++) {

                    if (labyrinthe.isFood(x, y)) {
                        posFoods.add(new PositionAgent(x, y, 0));
                        numberOfFood++;
                    } else if (labyrinthe.isCapsule(x, y)) {
                        posCapsule.add(new PositionAgent(x, y, 0));
                    }
                }
            }
        }

        public void initializeGame() 
        {
            pacmans.clear();    // On vide les listes s'il y a eu un redémarrage
            ghosts.clear();

            // Pour chaque pacman dans le labyrinthe on leur met la stratégie Solver
            for (PositionAgent pos : labyrinthe.getPacmanStart()) { 
                Agent pacman = new Agent(new PositionAgent(pos.getX(), pos.getY(), pos.getDir()));
                pacman.setStrategy(new StrategySolver(this));
                pacmans.add(pacman);
            }

            // On récupère les positions des ghosts dans le labyrinthe
            ArrayList<PositionAgent> positionsDebut = new ArrayList<PositionAgent>(labyrinthe.getGhostsStart());
            
            // Pour chaque ghost dans le labyrinthe on leur met soit la stratégie Trap, Target ou Random 
            // Sauf le premier qui sera le joueur 2
            for (int i=0; i< positionsDebut.size(); i++) { 
                Strategy stratGhost;
                switch (i%3) {
                    case 0: stratGhost = new StrategyTrap(this); break;
                    case 1: stratGhost = new StrategyTarget(this); break;
                    case 2: stratGhost = new StrategyRandom(this); break;
                    default: stratGhost = new StrategyTrap(this); break;
                }

                Agent ghost = new Agent(new PositionAgent(positionsDebut.get(i).getX(), positionsDebut.get(i).getY(), positionsDebut.get(i).getDir()));
                ghost.setStrategy(stratGhost);
                ghosts.add(ghost);
            }

            // On remet toutes les capsules et foods au cas où il y a eu un redémarrage
            for (PositionAgent pos : posFoods) { labyrinthe.setFood(pos.getX(), pos.getY(), true); }
            for (PositionAgent pos : posCapsule) { labyrinthe.setCapsule(pos.getX(), pos.getY(), true); }
                
            // Le pacman controllé par le joueur sera toujours le premier dans la liste
            mainPacman = pacmans.get(0);
                
            // Le ghost controllé par le joueur sera toujours le premier dans la liste
            mainGhost = ghosts.get(0);

            // On réinitialise toutes les valeurs 
            mainPacman.restartLives();
            resetScore();
            setGameFinished(false);
            setGameOver(false);

            // On set les valeurs de la réponse pour le serveur
            reponse = new Reponse();
            reponse.setScore(0);
            reponse.setNumberOfLives(1);
            reponse.setNumberOfLivesGiven(numberOfLivesGiven);
            reponse.setPacmans(pacmans);
            reponse.setGhosts(ghosts);
            reponse.setPosFoods(posFoods);
            reponse.setPosCapsule(posCapsule);
            reponse.setGameFinished(isGameFinished());
            reponse.setGameOver(isGameOver());
            System.out.println("\nReponse initialised");

        }
    
    //############################################
    //#                MOUVEMENTS                #
    //############################################

        public Boolean isLegalMove(Agent agent, AgentAction action) 
        {
            int x_futur = agent.getPosition().getX() + action.get_vx();
            int y_futur = agent.getPosition().getY() + action.get_vy();
            return !labyrinthe.isWall(x_futur, y_futur);
        }

        public int calculerDistance(PositionAgent pos1, PositionAgent pos2, Maze labyrinthe)
        {
            // Utilisation d'une file de priorité pour trier les noeuds en fonction de leur distance + heuristique
            PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance + a.heuristic));
            
            // Tableau pour suivre les noeuds visités dans le labyrinthe
            boolean[][] visited = new boolean[labyrinthe.getSizeX()][labyrinthe.getSizeY()];
            
            // Coordonnées de départ et d'arrivée ainsi que la taille du labyrinthe
            int startX = pos1.getX();
            int startY = pos1.getY();
            int targetX = pos2.getX();
            int targetY = pos2.getY();
            int rows = labyrinthe.getSizeY();
            int cols = labyrinthe.getSizeX();

            // Ajout du noeud de départ avec une heuristique pour atteindre la cible
            queue.offer(new Node(startX, startY, 0, Math.abs(startX - targetX) + Math.abs(startY - targetY)));

            // Parcours de la file de priorité jusqu'à ce qu'elle soit vide
            while (!queue.isEmpty()) {
                // Récupération et suppression du noeud actuel ayant la plus petite distance + heuristique
                Node currentNode = queue.poll();
                int x = currentNode.x;
                int y = currentNode.y;

                // Vérification si le noeud actuel est la position cible
                if(x == targetX && y == targetY) return currentNode.distance;

                // Marquer le noeud actuel comme visité
                visited[x][y] = true;

                // Parcours des 4 directions possibles (haut, bas, gauche, droite)
                for (int i = 0; i < 4; i++) {
                    AgentAction action = new AgentAction(i);
                    
                    // Calcul des nouvelles coordonnées selon l'action
                    int newX = x + action.get_vx();
                    int newY = y + action.get_vy();

                    // Vérification des limites du labyrinthe et si la case n'est pas un mur ni déjà visitée
                    if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && !visited[newX][newY] && !labyrinthe.isWall(newX, newY)) {
                        int newDistance = currentNode.distance + 1;
                        int heuristic = Math.abs(newX - targetX) + Math.abs(newY - targetY);
                        queue.offer(new Node(newX, newY, newDistance, heuristic)); // Ajout du nouveau noeud
                    }
                }
            }
            // Aucun chemin trouvé
            return -1;
        }

        public double simpleDistanceBetween(PositionAgent agent1, PositionAgent agent2) 
        {
            int dx = agent1.getX() - agent2.getX();
            int dy = agent1.getY() - agent2.getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
        
        public void moveAgent(Agent agent, AgentAction action) 
        {
            if(isLegalMove(agent, action)) {
                int new_x = agent.getPosition().getX() + action.get_vx();
                int new_y = agent.getPosition().getY() + action.get_vy();
                agent.getPosition().setX(new_x);
                agent.getPosition().setY(new_y); 
                agent.getPosition().setDir(action.getdirection());
            }
        }

        public void changeDirectionPacman(int direction)
        {
            directionClavierPacman = direction;
        }

        public void changeDirectionGhost(int direction)
        {
            directionClavierGhost = direction;
        }
        
    //############################################
    //#                ÉVÈNEMENTS                #
    //############################################
        
        // Retourne la liste de tous les agents qui sont morts
        public ArrayList<Agent> agentsMort() {
            ArrayList<Agent> agentsATue = new ArrayList<Agent>();
            for (Agent pacman : pacmans) {
                for (Agent ghost : ghosts) {
                    if (pacman.getPosition().equals(ghost.getPosition())) {
                        if (canKillGhosts) { // si le pacman a tué un ghost
                            agentsATue.add(ghost);
                            addScore(200 + 200 * numberOfGhostEaten);
                        } else if (pacman.getNumberOfLives() - 1 <= 0) {
                            agentsATue.add(pacman);
                        } else {
                            pacman.removeOneLife();
                            PositionAgent posStart = new PositionAgent(labyrinthe.getPacmanStart().get(0).getX(),
                                    labyrinthe.getPacmanStart().get(0).getY(),
                                    labyrinthe.getPacmanStart().get(0).getDir());
                            pacman.setPosition(posStart);
                        }
                        System.out.println("un agent est mort !");
                    }
                }
            }
            return agentsATue;
        }
        
        public void takeTurn() 
        {
            /*
             * On déplace chaque fantome
             */
            for (Agent ghost : ghosts) {
                Agent target = null;

                // calcul de la cible la plus proche de chaque fantome
                if (ghost.getStrategy().getType() != "RANDOM") {
                    double minDistance = Double.MAX_VALUE;
                
                    for (Agent pacman : pacmans) {
                        double distance = simpleDistanceBetween(ghost.getPosition(), pacman.getPosition());
                        if (distance < minDistance) {
                            minDistance = distance;
                            target = pacman;
                        }
                    }
                }
                moveAgent(ghost, ghost.getStrategy().getAction(ghost, target, labyrinthe));
            }
            
            
            /*
             * On déplace chaque pacman
             */
            for (Agent pacman : pacmans) {
                if(pacman == mainPacman){
                    moveAgent(pacman, new AgentAction(directionClavierPacman));
                } 
                else {
                    AgentAction action = pacman.getStrategy().getAction(pacman, null, labyrinthe);
                    moveAgent(pacman, action);
                    pacman.getPosition().setDir(action.getdirection());
                }

                int pos_x = pacman.getPosition().getX();
                int pos_y = pacman.getPosition().getY();

                /*
                 * On enleve la food a la position du pacman
                 */
                if(labyrinthe.isFood(pos_x, pos_y)){
                    labyrinthe.setFood(pos_x, pos_y, false);
                    numberOfFood--;

                    // On augmente le score et éventuellement le nombre de vies du pacman 
                    addScore(10);
                    if (getScore()% (750 * (numberOfLivesGiven+1)) == 0 ) {
                        System.out.println("score % 1000 = " + getScore()%1000 + " \t number of life given : " + numberOfLivesGiven);
                        mainPacman.addOneLife();
                        numberOfLivesGiven++;
                    }
                }

                /*
                 * Si un pacman a mangé une capsule
                 */
                if(canKillGhosts){
                    System.out.println("time capsule : " + currentTimeCapsule);
                    if(currentTimeCapsule++ >= timeMaxCapsule) canKillGhosts = false;
                }
                else if(labyrinthe.isCapsule(pos_x , pos_y)){
                    canKillGhosts = true;
                    currentTimeCapsule = 0;

                    labyrinthe.setCapsule(pos_x, pos_y, false);
                    addScore(50);
                }
            }
            
            /*
             * On retire les agents morts
             */
            if(canKillGhosts)   for (Agent ghost : agentsMort()) { ghosts.remove(ghost); }
            else                for (Agent pacman : agentsMort()) { pacmans.remove(pacman); }

            // si le pacman controlé par l'utilisateur est mort et qu'il reste des pacmans, alors on lui donne le controle du pacman d'apres
            if (!pacmans.isEmpty() && pacmans.get(0) != mainPacman) mainPacman = pacmans.get(0);

            // s'il n'y a plus de pacmans, alors c'est Game Over !
            if (pacmans.isEmpty()) setGameOver(true);

            // notifierObservateurs();
        }    
    
        public void updatePosPacmans() {
            for (Agent pacman : pacmans) {
                if (pacman == mainPacman) {
                    moveAgent(pacman, new AgentAction(directionClavierPacman));
                } else {
                    AgentAction action = pacman.getStrategy().getAction(pacman, null, labyrinthe);
                    moveAgent(pacman, action);
                    pacman.getPosition().setDir(action.getdirection());
                }

                int pos_x = pacman.getPosition().getX();
                int pos_y = pacman.getPosition().getY();

                /*
                 * On enleve la food a la position du pacman
                 */
                if (labyrinthe.isFood(pos_x, pos_y)) {

                    labyrinthe.setFood(pos_x, pos_y, false);
                    numberOfFood--;

                    // On enlève la food dans la reponse
                    int foodIndexToRemove = 0;
                    for (int i = 0; i < reponse.getPosFoods().size(); i++) {
                        if (reponse.getPosFoods().get(i).getX() == pos_x &&
                                reponse.getPosFoods().get(i).getY() == pos_y)
                            foodIndexToRemove = i;
                    }
                    reponse.getPosFoods().remove(foodIndexToRemove);

                    // On augmente le score et éventuellement le nombre de vies du pacman
                    addScore(10);
                    if (getScore() % (750 * (numberOfLivesGiven + 1)) == 0) {
                        System.out.println("score % 1000 = " + getScore() % 1000 + " \t number of life given : "
                                + numberOfLivesGiven);
                        mainPacman.addOneLife();
                        numberOfLivesGiven++;
                    }
                }
                /*
                 * Si un pacman a mangé une capsule
                 */
                if (canKillGhosts) {
                    System.out.println("time capsule : " + currentTimeCapsule);
                    if (currentTimeCapsule++ >= timeMaxCapsule)
                        canKillGhosts = false;
                } else if (labyrinthe.isCapsule(pos_x, pos_y)) {
                    canKillGhosts = true;
                    currentTimeCapsule = 0;

                    // On enlève la capsule dans la reponse
                    int capsuleIndexToRemove = 0;
                    for (int i = 0; i < reponse.getPosCapsule().size(); i++) {
                        if (reponse.getPosCapsule().get(i).getX() == pos_x &&
                                reponse.getPosCapsule().get(i).getY() == pos_y)
                            capsuleIndexToRemove = i;
                    }
                    reponse.getPosCapsule().remove(capsuleIndexToRemove);

                    labyrinthe.setCapsule(pos_x, pos_y, false);
                    addScore(50);
                }
            }
        }

        public void updatePosGhosts() {
            for (Agent ghost : ghosts) {
                if (ghost == mainGhost) {
                    moveAgent(ghost, new AgentAction(directionClavierGhost));
                } else {
                    // Agent target = null;

                    // // calcul de la cible la plus proche de chaque fantome
                    // if (ghost.getStrategy().getType() != "RANDOM") {
                    //     double minDistance = Double.MAX_VALUE;

                    //     for (Agent pacman : pacmans) {
                    //         double distance = simpleDistanceBetween(ghost.getPosition(), pacman.getPosition());
                    //         if (distance < minDistance) {
                    //             minDistance = distance;
                    //             target = pacman;
                    //         }
                    //     }
                    // }
                    // moveAgent(ghost, ghost.getStrategy().getAction(ghost, target, labyrinthe));
                }
            }
        }

        public void updateDeadAgents() {
            if (canKillGhosts)
                for (Agent ghost : agentsMort()) {
                    ghosts.remove(ghost);
                }
            else
                for (Agent pacman : agentsMort()) {
                    pacmans.remove(pacman);
                }
        }

        public void updateMainPacman() {
            // si le pacman controlé par l'utilisateur est mort et qu'il reste des pacmans,
            // alors on lui donne le controle du pacman d'apres
            if (!pacmans.isEmpty() && pacmans.get(0) != mainPacman)
                mainPacman = pacmans.get(0);
        }
        public void updateMainGhost() {
            // si le ghost controlé par l'utilisateur est mort et qu'il reste des ghosts,
            // alors on lui donne le controle du ghost d'apres
            if (!ghosts.isEmpty() && ghosts.get(0) != mainGhost)
                mainGhost = ghosts.get(0);
        }

        public void updateGameState() {
            // s'il n'y a plus de pacmans, alors c'est Game Over !
            if (pacmans.isEmpty()) setGameOver(true);

            if((!gameContinue() && getTurn() > getMaxTurn()) || isGameOver()){ // quand le jeu s'arrete-t-il ?
                setRunning(false);
                setGameFinished(true);
                if (getTurn() >= getMaxTurn() || isGameOver()) 
                    setGameOver(true);
            } 
            // System.out.print(isGameFinished() + "\t");
        }

        public Reponse updateGame() {
            setTurn(getTurn() + 1); // On augmente manuellement le nombre de tour
            
            if (!isGameFinished()) {
                updatePosPacmans();
                updatePosGhosts();
                updateDeadAgents();
                updateMainPacman();
                updateMainGhost();
                updateGameState();
            }

            // Reponse reponse = new Reponse();
            reponse.setPacmans(pacmans);
            reponse.setGhosts(ghosts);
            reponse.setPosFoods(posFoods);
            reponse.setPosCapsule(posCapsule);
            reponse.setScore(getScore());
            reponse.setNumberOfLives(mainPacman.getNumberOfLives());
            reponse.setScared(canKillGhosts);
            reponse.setGameFinished(isGameFinished());
            reponse.setGameOver(isGameOver());
            reponse.setTurn(getTurn());

            // if (isGameFinished()) {
            //     System.out.println(reponse);    
            // }

            return reponse;
        }

        /*
         * On affiche la fenetre du Game Over
         */
        public void gameOver() {
            // notifierObservateurs();
        }
        
        /*
         * On affiche la fenetre de la victoire
         */
        public void victory() {
            // notifierObservateurs();
        }

        public boolean gameContinue() {
            return numberOfFood > 0 && !pacmans.isEmpty();
        }
        
    //############################################
    //#                 GETTERS                  #
    //############################################

        public Maze getLabyrinthe() {
            return labyrinthe;
        }

        public List<Agent> getPacmans() {
            return pacmans;
        }

        public List<Agent> getGhosts() {
            return ghosts;
        }

        public Boolean isScared() {
            return canKillGhosts;
        }

    //############################################
    //#               OBSERVATEURS               #
    //############################################

        // public void enregistrerObservateur(Observateur observateur) {
        //     observateurs.add(observateur);
        // }

        // public void supprimerObservateur(Observateur observer) {
        //     observateurs.remove(observer);
        // }

        // public void notifierObservateurs() {
        //     for (Observateur ob : observateurs) {
        //         ob.actualiser(getTurn());
        //     }
        // }

}
