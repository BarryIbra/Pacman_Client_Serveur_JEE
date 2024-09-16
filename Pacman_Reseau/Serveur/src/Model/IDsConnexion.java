package Model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class IDsConnexion {
    private String username;
    private char[] password;


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

    
}
