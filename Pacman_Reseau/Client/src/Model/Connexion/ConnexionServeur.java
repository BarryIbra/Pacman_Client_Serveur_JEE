package Model.Connexion;

import java.io.*;
import java.net.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Input;
import Model.Reponse;

public class ConnexionServeur {

    private Socket socket;
    private BufferedReader entree;
    private PrintWriter sortie;

    private ObjectMapper objectMapper = new ObjectMapper();

    public ConnexionServeur(String adresse, int port) throws IOException {
        socket = new Socket(adresse, port);
        entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sortie = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("New Connexion created");
    }

    public Reponse readReponse() throws IOException {
        return objectMapper.readValue(entree.readLine(), Reponse.class);
    }

    public String readString() throws IOException {
        return entree.readLine();
    }

    public void sendInput(Input input) throws JsonProcessingException {
        String inputJson = objectMapper.writeValueAsString(input);
        sortie.println(inputJson);
    }

    public void sendString(String message) {
        sortie.println(message);
    }

    public void closeSocket() throws IOException {
        socket.close();
    }

}
