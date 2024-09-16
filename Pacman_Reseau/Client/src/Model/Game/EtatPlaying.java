package Model.Game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Input;
import Vue.ViewCommand;

public class EtatPlaying implements Etat{

    public ViewCommand view;

    

    public EtatPlaying(ViewCommand view) {
        this.view = view;
    }
    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static EtatPlaying createEtatPlaying(@JsonProperty("view") ViewCommand view) {
        return new EtatPlaying(view);
    }

    public void play() {}

    public void restart() {
        view.controller.restart();

        view.bouton_play.setEnabled(true);
        view.bouton_next.setEnabled(true);
        view.bouton_restart.setEnabled(false);
        view.bouton_pause.setEnabled(false);

        view.changerEtat(new EtatPaused(view));
    }
    
    public void pause() throws JsonProcessingException {
        view.controller.pause();
        view.controller.game.connexion.sendInput(new Input(0,3));
        view.bouton_play.setEnabled(true);
        view.bouton_next.setEnabled(true);
        view.bouton_restart.setEnabled(true);
        view.bouton_pause.setEnabled(false);

        view.changerEtat(new EtatPaused(view));
    }

    public void step() {}
}
