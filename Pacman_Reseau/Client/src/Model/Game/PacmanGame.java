package Model.Game;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Input;
import Model.Reponse;
import Model.Agent.*;
import Model.Connexion.Ecoute;
import Model.Connexion.Envois;
import Vue.ViewGameOver;
import Vue.ViewVictory;

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


public class PacmanGame extends Game implements Observable {

    private Maze labyrinthe;
    private List<Observateur> observateurs = new ArrayList<>();

    private List<Agent> pacmans = new ArrayList<>();
    private List<Agent> ghosts = new ArrayList<>();
    public Agent mainPacman;
    
    
    private List<PositionAgent> posFoods = new ArrayList<>();
    private List<PositionAgent> posCapsule = new ArrayList<>();
    static int timeMaxCapsule = 20;
    private boolean canKillGhosts = false;

    private int numberOfFood = 0;
    private int numberOfGhostEaten = 0;

    public int directionClavier;
    public int playerNumber = 0;
    private boolean isPacmanTeam = true;
    
    public Reponse reponse = new Reponse();
    private Ecoute ecoute = new Ecoute(this);
    private Envois envois = new Envois(this);


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
                    if(labyrinthe.isFood(x,y)) {
                        posFoods.add(new PositionAgent(x, y, 0));
                        numberOfFood++;
                    }
                    else if(labyrinthe.isCapsule(x,y)) {
                        posCapsule.add(new PositionAgent(x, y, 0));
                    }
                }
            }
        }

        // Méthode de fabrique statique annotée avec @JsonCreator
        @JsonCreator
        public static PacmanGame createPacmanGame(
            @JsonProperty("maxTurn") int maxTurn, 
            @JsonProperty("fileNameMaze") String fileNameMaze) throws Exception 
        {
            System.out.println("PacmanGame created");
            return new PacmanGame(maxTurn, Game.mazeFileName);
        }

        public void initializeGame() 
        {
            // On vide les listes s'il y a eu un redémarrage
            pacmans.clear();
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
                // ghosts.add(ghost);
            }

            // On remet toutes les capsules et foods au cas où il y a eu un redémarrage
            for (PositionAgent pos : posFoods) { labyrinthe.setFood(pos.getX(), pos.getY(), true); }
            for (PositionAgent pos : posCapsule) { labyrinthe.setCapsule(pos.getX(), pos.getY(), true); }
                
            // Le pacman controllé par le joueur sera toujours le premier dans la liste
            mainPacman = pacmans.get(0);

            // On réinitialise toutes les valeurs 
            mainPacman.restartLives();
            resetScore();

            // On envois un "INIT" au serveur pour reinitialiser le serveur s'il était déjà en cours
            // Et on recupère le numero du joueur
            try {
                connexion.sendInput(new Input(0, 0));
                Reponse rep = connexion.readReponse();
                playerNumber = rep.getPlayerNumber();
                isPacmanTeam = (playerNumber == 1) ? true : false;
                System.out.println("Reponse received : " + rep.getPlayerNumber() + "\t team Pacman ? " + isPacmanTeam);
                
            } catch (IOException e) {
                System.out.println("Aucun serveur n’est rattaché au port ");
            }
        }
    
    //############################################
    //#                MOUVEMENTS                #
    //############################################

        // Vérifie si une action est autorisée
        public Boolean isLegalMove(Agent agent, AgentAction action) 
        {
            int x_futur = agent.getPosition().getX() + action.get_vx();
            int y_futur = agent.getPosition().getY() + action.get_vy();

            return !labyrinthe.isWall(x_futur, y_futur);
        }

        /*
         * Calcule le chemin le plus court pour aller d'une position A à une position B 
         * dans le labyrinthe et retourne la distance de ce chemin
         */
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


        // Calcule la distance euclidienne entre deux positions
        public double simpleDistanceBetween(PositionAgent agent1, PositionAgent agent2) 
        {
            int dx = agent1.getX() - agent2.getX();
            int dy = agent1.getY() - agent2.getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
        
        // Déplace un agent en fonction de l'action donnée
        public void moveAgent(Agent agent, AgentAction action) 
        {
            if(isLegalMove(agent, action)) {
                int new_x = agent.getPosition().getX() + action.get_vx();
                int new_y = agent.getPosition().getY() + action.get_vy();
                agent.getPosition().setX(new_x);
                agent.getPosition().setY(new_y); 
                agent.getPosition().setDir(action.get_direction());
            }
        }

        public void changeDirection(int direction)
        {
            directionClavier = direction;
        }
        
    //############################################
    //#                ÉVÈNEMENTS                #
    //############################################
        
        // Retourne la liste de tous les agents qui sont morts
        public ArrayList<Agent> agentsMort() 
        {
            ArrayList<Agent> agentsATue = new ArrayList<Agent>();
            for (Agent pacman : pacmans) {
                for (Agent ghost : ghosts) 
                {
                    if (pacman.getPosition().equals(ghost.getPosition())) {
                        if(canKillGhosts){ // si le pacman a tué un ghost
                            agentsATue.add(ghost);
                            addScore(200 + 200 * numberOfGhostEaten);
                        }
                        else if(pacman.getLives()-1 <= 0) {
                            agentsATue.add(pacman);
                        }
                        else {
                            pacman.removeOneLife();
                            PositionAgent posStart = new PositionAgent(labyrinthe.getPacmanStart().get(0).getX(), labyrinthe.getPacmanStart().get(0).getY(), labyrinthe.getPacmanStart().get(0).getDir());
                            pacman.setPosition(posStart);
                        }
                        System.out.println("un agent est mort !");
                    }
                }
            }
            return agentsATue;
        }
        
        public void startThreads(){
            ecoute.launch();
            envois.launch();
        }

        public void takeTurn() {
            if (isGameFinished()) {
                setRunning(false);
                if (isGameOver()) {
                    if (playerNumber == 1) gameOver();
                    else victory();
                }
                else {
                    if (playerNumber == 1) victory();
                    else gameOver();
                }
                    
            } else {

                // On envois un input au serveur et on recupere la reponse
                try {
                    connexion.sendInput(new Input(directionClavier, 1));

                    reponse = connexion.readReponse();
                    setGameFinished(reponse.isGameFinished());
                    setGameOver(reponse.isGameOver());

                    notifierObservateurs();

                } catch (IOException e) {
                    System.out.println("Aucun serveur n’est rattaché au port ");
                }
            }
        }

        /*
         * On affiche la fenetre du Game Over
         */
        public void gameOver() {
            // notifierObservateurs();
            
            ViewGameOver gameOver = new ViewGameOver();
            gameOver.show();
        }
        
        /*
        * On affiche la fenetre de la victoire
         */
        public void victory(){
            // notifierObservateurs();
            
            ViewVictory victoryScreen = new ViewVictory();
            victoryScreen.show();
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

        public int getPlayerNumber() {
            return playerNumber;
        }

    //############################################
    //#               OBSERVATEURS               #
    //############################################

        public void enregistrerObservateur(Observateur observateur){
            observateurs.add(observateur);
        }
        public void supprimerObservateur(Observateur observer){
            observateurs.remove(observer);
        }
        public void notifierObservateurs(){
            for (Observateur ob : observateurs) {
                ob.actualiser(getTurn(), reponse);
            }
        }

    
    

}
