package Model.Game;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Etat {

    public void play() throws JsonProcessingException;
    public void restart() throws JsonProcessingException;
    public void pause() throws JsonProcessingException;
    public void step() throws JsonProcessingException;
}
