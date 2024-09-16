package Model.Game;

public interface Observable {
    public void enregistrerObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observer);
    public void notifierObservateurs();
}
