package Model.Game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import Model.Agent.PositionAgent;


public class Maze implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	/**
	 * Les differentes directions possibles pour les actions et les orientations des
	 * agents
	 */
	public static int NORTH = 0;
	public static int SOUTH = 1;
	public static int EAST = 2;
	public static int WEST = 3;
	public static int STOP = 4;

	public static String fileNameMaze = "src/layouts/originalClassic.lay"; 
	public String fileName;  

	private int sizeX;
	private int sizeY;

	/**
	 * Les elements du labyrinthe
	 */
	private boolean walls[][];
	private boolean food[][];
	private boolean capsules[][];

	/**
	 * Les positions initiales des agents
	 */
	private ArrayList<PositionAgent> pacmanStart;
	private ArrayList<PositionAgent> ghostsStart;

	public Maze(String fileName) throws Exception {
		this.fileName = fileName;
		try {
			System.out.println("Layout file is " + fileName);
			// Lecture du fichier pour determiner la taille du labyrinthe
			InputStream ips = new FileInputStream(fileName);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			int nbX = 0;
			int nbY = 0;
			while ((ligne = br.readLine()) != null) {
				ligne = ligne.trim();
				if (nbX == 0) {
					nbX = ligne.length();
				} else if (nbX != ligne.length()) {
					ips.close();
					br.close();
					throw new Exception("Wrong Input Format: all lines must have the same size");
				}
				nbY++;
			}
			br.close();
			System.out.println("### Size of maze is " + nbX + ";" + nbY);

			// Initialisation du labyrinthe
			sizeX = nbX;
			sizeY = nbY;
			walls = new boolean[sizeX][sizeY];
			food = new boolean[sizeX][sizeY];
			capsules = new boolean[sizeX][sizeY];

			pacmanStart = new ArrayList<PositionAgent>();
			ghostsStart = new ArrayList<PositionAgent>();

			// Lecture du fichier pour mettre a jour le labyrinthe
			ips = new FileInputStream(fileName);
			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			int y = 0;
			while ((ligne = br.readLine()) != null) {
				ligne = ligne.trim();

				for (int x = 0; x < ligne.length(); x++) {
					if (ligne.charAt(x) == '%')
						walls[x][y] = true;
					else
						walls[x][y] = false;
					if (ligne.charAt(x) == '.')
						food[x][y] = true;
					else
						food[x][y] = false;
					if (ligne.charAt(x) == 'o')
						capsules[x][y] = true;
					else
						capsules[x][y] = false;
					if (ligne.charAt(x) == 'P') {
						pacmanStart.add(new PositionAgent(x, y, Maze.NORTH));
					}
					if (ligne.charAt(x) == 'G') {
						ghostsStart.add(new PositionAgent(x, y, Maze.NORTH));
					}
				}
				y++;
			}
			br.close();

			if (pacmanStart.size() == 0)
				throw new Exception("Wrong input format: must specify a Pacman start");

			// On verifie que le labyrinthe est clos
			for (int x = 0; x < sizeX; x++)
				if (!walls[x][0])
					throw new Exception("Wrong input format: the maze must be closed");
			for (int x = 0; x < sizeX; x++)
				if (!walls[x][sizeY - 1])
					throw new Exception("Wrong input format: the maze must be closed");
			for (y = 0; y < sizeY; y++)
				if (!walls[0][y])
					throw new Exception("Wrong input format: the maze must be closed");
			for (y = 0; y < sizeY; y++)
				if (!walls[sizeX - 1][y])
					throw new Exception("Wrong input format: the maze must be closed");
			System.out.println("### Maze loaded.");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Probleme a la lecture du fichier: " + e.getMessage());
		}
	}

	// Méthode de fabrique statique annotée avec @JsonCreator
	@JsonCreator
	public static Maze createMaze(@JsonProperty("fileName") String fileName) throws Exception {
		return new Maze(fileName);
	}


	/**
	 * Renvoie la taille X du labyrtinhe
	 */
	public int getSizeX() {
		return (sizeX);
	}

	/**
	 * Renvoie la taille Y du labyrinthe
	 */
	public int getSizeY() {
		return (sizeY);
	}

	/**
	 * Permet de savoir si il y a un mur
	 */
	public boolean isWall(int x, int y) {
		assert ((x >= 0) && (x < sizeX));
		assert ((y >= 0) && (y < sizeY));
		return (walls[x][y]);
	}

	/**
	 * Permet de savoir si il y a de la nourriture
	 */
	public boolean isFood(int x, int y) {
		assert ((x >= 0) && (x < sizeX));
		assert ((y >= 0) && (y < sizeY));
		return (food[x][y]);
	}

	public void setFood(int x, int y, boolean b) {
		food[x][y] = b;
	}
	

	/**
	 * Permet de savoir si il y a une capsule
	 */
	public boolean isCapsule(int x, int y) {
		assert ((x >= 0) && (x < sizeX));
		assert ((y >= 0) && (y < sizeY));
		return (capsules[x][y]);
	}

	public void setCapsule(int x, int y, boolean b) {
		capsules[x][y] = b;
	}

	public ArrayList<PositionAgent> getPacmanStart() {
		return pacmanStart;
	}

	public void setPacmanStart(ArrayList<PositionAgent> pacmanStart) {
		this.pacmanStart = pacmanStart;
	}

	public ArrayList<PositionAgent> getGhostsStart() {
		return ghostsStart;
	}

	public void setGhostsStart(ArrayList<PositionAgent> ghostsStart) {
		this.ghostsStart = ghostsStart;
	}

	public String toString() {
		String s = "Maze\n";
		s += plateauToString();
		s += "\nPosition agents fantom :";
		for (PositionAgent pa : ghostsStart) {
			s += pa + " ";
		}
		s += "\nPosition agents pacman :";
		for (PositionAgent pa : pacmanStart) {
			s += pa + " ";
		}
		return s;
	}

	public String plateauToString() {
		String s = "";
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (walls[i][j])
					s += "X";
				else if (food[i][j])
					s += "f";
				else if (capsules[i][j])
					s += "c";
				else
					s += " ";
			}
			s += "\n";
		}
		return s;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		return (Maze) super.clone();
	}
	
}
