package Model.Connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.IDsConnexion;
import Model.Reponse;
import Model.Game.PacmanGame;
import Vue.ViewPacmanGame;

public class Serveur {
    private int port = 2000; // le port d’écoute
    public ServerSocket serverSocket;
    public ArrayList<Connexion> players = new ArrayList<>();
    public static int maxTurn = 1000, numberOfPlayersNeeded = 2;
    public static String mazeFileName = "src/layouts/originalClassic.lay";
    public PacmanGame pacmanGame;
    public ViewPacmanGame vue;
    private Ecoute ecoute = new Ecoute(this);
    private Envois envois = new Envois(this);

    public Serveur() throws Exception {
        pacmanGame = new PacmanGame(maxTurn, mazeFileName);
        pacmanGame.initializeGame();
        vue = new ViewPacmanGame(pacmanGame);

        serverSocket = new ServerSocket(port);
        System.out.println("\n[Serveur initialized] port = " + port + "\n");
    }

    public void startThreads(){
        ecoute.launch();
        envois.launch();
    }

    public void waitForUserLoggedIn(){
        int playersLoggedIn = 0;
        while (playersLoggedIn != numberOfPlayersNeeded) {
            Socket socket;
            BufferedReader entree;
            PrintWriter sortie;
            try {
                socket = serverSocket.accept();
                
                entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                sortie = new PrintWriter(socket.getOutputStream(), true);

                ObjectMapper objectMapper = new ObjectMapper();
                IDsConnexion ids = objectMapper.readValue(entree.readLine(), IDsConnexion.class);

                String reponse = (ids.getUsername().equals("admin") && String.valueOf(ids.getPassword()).equals("admin"))? "valide" : "nope";
                sortie.println(reponse);

                System.out.println(reponse + " sent");
                if (reponse.equals("valide")) playersLoggedIn++;
                // if (ids.getUsername().equals("admin") && String.valueOf(ids.getPassword()).equals("admin"))
                //     sortie.println("valide");
                // else 
                //     sortie.println("nope");

                socket.close();
            } catch (IOException e) { System.out.println("problème\n"+e); }
        }
    }

    // le serveur va attendre que suffisament de joueurs soit connectés
    public void waitForPlayersConnected() throws IOException {
        while (numberOfPlayers() != numberOfPlayersNeeded) {
            // Connexion player = new Connexion(serverSocket, 2);
            Connexion player = new Connexion(serverSocket, (numberOfPlayers() + 1));
            if (!players.contains(player)){
                addPlayer(player);

                // On notifie le client de son numéro de joueur (J1 ou J2 ou +)
                Reponse rep = new Reponse();
                // rep.setPlayerNumber(2);
                rep.setPlayerNumber(numberOfPlayers());
                player.sendReponse(rep);
                System.out.println("Number of the player sent ("+numberOfPlayers()+")");
            }
        }
        System.out.println("All players connected ! [" + numberOfPlayers() + "]\n");
    }

    // le serveur va attendre que tous les joueurs soit prêts
    public void waitForPlayersReady() throws IOException {
        int nbrJoueursPrets = 0;
        while (nbrJoueursPrets != numberOfPlayersNeeded) {
            for (Connexion player : players) {
                if (player.readInput().getTypeInput() == 2) {
                    System.out.println("Player "+player.getNumber()+" ready.");
                    nbrJoueursPrets++;
                }
            }
        }
        System.out.println("All players ready !\n");
    }

    public void closeAllSockets() throws IOException {
        for (Connexion connexion : players) {
            connexion.closeSocket();
        }
    }

    public void addPlayer(Connexion player) {
        players.add(player);
        System.out.println(" : player " + player.getNumber());
    }

    public void removePlayer(Connexion player) {
        players.remove(player);
    }

    public int numberOfPlayers() {
        return players.size();
    }
}
