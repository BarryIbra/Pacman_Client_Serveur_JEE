package Model.Game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Input;
import Vue.ViewCommand;

public class EtatPaused implements Etat{

    public ViewCommand view;

    public EtatPaused(ViewCommand view) {
        this.view = view;
    }
    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static EtatPaused createEtatPaused(@JsonProperty("view") ViewCommand view) {
        return new EtatPaused(view);
    }

    public void play() throws JsonProcessingException {
        view.controller.play();
        view.controller.game.connexion.sendInput(new Input(0, 2));
        view.controller.setFocusGame();

        view.bouton_play.setEnabled(false);
        view.bouton_next.setEnabled(false);
        view.bouton_restart.setEnabled(true);
        view.bouton_pause.setEnabled(true);

        view.changerEtat(new EtatPlaying(view));
    }

    public void restart() {
        view.controller.restart();
    }

    public void pause() {}

    public void step() throws JsonProcessingException {
        view.controller.step();
        view.controller.game.connexion.sendInput(new Input(0, 4));
    }

}
