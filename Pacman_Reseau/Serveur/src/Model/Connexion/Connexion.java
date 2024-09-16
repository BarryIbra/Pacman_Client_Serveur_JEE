package Model.Connexion;

import java.io.*;
import java.net.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Input;
import Model.Reponse;

public class Connexion {

    private Socket socket;
    private BufferedReader entree;
    private PrintWriter sortie;
    private int playerNumber;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Connexion(ServerSocket ecoute, int playerNumber) throws IOException {
        socket = ecoute.accept();
        entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sortie = new PrintWriter(socket.getOutputStream(), true);
        this.playerNumber = playerNumber;

        System.out.print("New player connected.");
    }

    public Input readInput() throws IOException {
        return objectMapper.readValue(entree.readLine(), Input.class);
    }

    public void sendReponse(Reponse reponse) throws JsonProcessingException {
        String reponseJson = objectMapper.writeValueAsString(reponse);
        sortie.println(reponseJson);
    }

    public void closeSocket() throws IOException {
        socket.close();
    }

    public int getNumber(){
        return playerNumber;
    }

    public void setPlayerNumber(int number){ 
        this.playerNumber = number;
    }

}
