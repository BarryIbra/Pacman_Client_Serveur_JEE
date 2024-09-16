import Model.Connexion.Serveur;

public class App {
    public static void main(String[] args) throws Exception {
        Serveur serveur = new Serveur();

        serveur.waitForUserLoggedIn();
        serveur.waitForPlayersConnected();
        serveur.waitForPlayersReady();

        serveur.startThreads();

        while (!serveur.pacmanGame.isGameFinished()) {}

        serveur.closeAllSockets();
    }
}
