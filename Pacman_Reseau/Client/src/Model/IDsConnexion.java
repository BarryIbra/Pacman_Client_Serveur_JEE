package Model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class IDsConnexion {
    private String username;
    private char[] password;
    private int valeur; 
        /* 
             1 si c'est une connexion, 
            -1 si c'est une deconnexion
        */


    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static IDsConnexion createIDsConnexion() {
        return new IDsConnexion();
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public char[] getPassword() {
        return password;
    }
    public void setPassword(char[] password) {
        this.password = password;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    
}
