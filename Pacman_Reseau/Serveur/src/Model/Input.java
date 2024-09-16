package Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Input {
    private int action;
    private int typeInput;
    /*
     * 0 : initialisation
     * 1 : input clavier
     * 2 : Play
     * 3 : Pause
     * 4 : Step
     */

    public Input(int action, int typeInput) {
        this.action = action;
        this.typeInput = typeInput;
    }

    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static Input createInput(
            @JsonProperty("action") int action,
            @JsonProperty("typeInput") int typeInput) {
        return new Input(action, typeInput);
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getTypeInput() {
        return typeInput;
    }

    public void setTypeInput(int typeInput) {
        this.typeInput = typeInput;
    }

    @Override
    public String toString() {
        return "Input [action=" + action + ", typeInput=" + typeInput + "]";
    }


}
