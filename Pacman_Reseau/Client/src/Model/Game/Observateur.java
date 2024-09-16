package Model.Game;

import Model.Reponse;

public interface Observateur {
    public void actualiser(int turn, Reponse reponse);
}