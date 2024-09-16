package models;

public class Stats {
    private long id; // Ajout de l'attribut id
    private String username;
    private int bestscore;
    private int totalscore;
    private int gameplayed;
    
    public Stats(String username, int bestscore, int totalscore, int gameplayed) {
        this.username = username;
        this.bestscore = bestscore;
        this.totalscore = totalscore;
        this.gameplayed = gameplayed;
    }
    
    // Getters et Setters pour l'attribut id
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    // Les autres Getters et Setters restent inchangés
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBestscore() {
        return bestscore;
    }
    public void setBestscore(int bestscore) {
        this.bestscore = bestscore;
    }
    public int getTotalscore() {
        return totalscore;
    }
    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }
    public int getGameplayed() {
        return gameplayed;
    }
    public void setGameplayed(int gameplayed) {
        this.gameplayed = gameplayed;
    }
    
    public String toString() {
        return "Stats{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", bestscore=" + bestscore +
                ", totalscore=" + totalscore +
                ", gameplayed=" + gameplayed +
                '}';
    }
}
