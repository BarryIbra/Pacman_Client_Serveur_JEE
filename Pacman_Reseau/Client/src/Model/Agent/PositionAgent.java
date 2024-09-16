package Model.Agent;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionAgent implements Serializable {

	private static final long serialVersionUID = 1L;

	private int x;
	private int y;
	private int dir;

	public PositionAgent(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
    // Méthode de fabrique statique annotée avec @JsonCreator
    @JsonCreator
    public static PositionAgent createPositionAgent(
            @JsonProperty("x") int x,
            @JsonProperty("y") int y,
            @JsonProperty("dir") int dir) {
        return new PositionAgent(x, y, dir);
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public boolean equals(PositionAgent other) {
		return (x == other.x) && (y == other.y);
	}

}
