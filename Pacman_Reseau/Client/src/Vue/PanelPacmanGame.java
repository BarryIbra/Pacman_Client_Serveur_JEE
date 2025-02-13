package Vue;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import Model.Agent.PositionAgent;
import Model.Game.Maze;



public class PanelPacmanGame extends JPanel {

	private static final long serialVersionUID = 1L;

	private Color wallColor = Color.BLUE;
	private Color wallColor2 = Color.CYAN;
	private Color wallColorGhost = Color.RED;
	private Color wallColorGhost2 = Color.PINK;

	private double sizePacman = 1.1;
	private Color pacmansColor = Color.yellow;

	private Color ghostsColor = Color.white;
	private Color ghostScarredColor = Color.magenta;

	private double sizeFood = 0.3;
	private Color colorFood = Color.white;

	private double sizeCapsule = 0.7;
	private Color colorCapsule = Color.red;

	private Maze m;

	private ArrayList<PositionAgent> pacmans_pos;
	private ArrayList<PositionAgent> ghosts_pos;

	private boolean ghostsScarred;
	
	private static int sa = 0;
	private static int fa = 0;

	private int playerNumber;

	public PanelPacmanGame(Maze maze, int playerNumber) {
		this.m = maze;
		pacmans_pos = this.m.getPacmanStart();
		ghosts_pos = this.m.getGhostsStart();
		ghostsScarred = false;
		this.playerNumber = playerNumber;
	}

	public void paint(Graphics g) {

		int dx = getSize().width;
		int dy = getSize().height;
		g.setColor(Color.black);
		g.fillRect(0, 0, dx, dy);

		int sx = m.getSizeX();
		int sy = m.getSizeY();
		double stepx = dx / (double) sx;
		double stepy = dy / (double) sy;
		double posx = 0;

		for (int x = 0; x < sx; x++) {
			double posy = 0;
			for (int y = 0; y < sy; y++) {
				if (m.isWall(x, y)) {
					if (playerNumber == 1) g.setColor(wallColor2);
					else g.setColor(wallColorGhost2);

					g.fillRect((int) posx, (int) posy, (int) (stepx + 1),
							(int) (stepy + 1));

					if (playerNumber == 1) g.setColor(wallColor);
					else g.setColor(wallColorGhost);
					
					double nsx = stepx * 0.5;
					double nsy = stepy * 0.5;
					double npx = (stepx - nsx) / 2.0;
					double npy = (stepy - nsy) / 2.0;
					g.fillRect((int) (npx + posx), (int) (npy + posy),
							(int) (nsx), (int) nsy);
				}
				if (m.isFood(x, y)) {
					g.setColor(colorFood);
					double nsx = stepx * sizeFood;
					double nsy = stepy * sizeFood;
					double npx = (stepx - nsx) / 2.0;
					double npy = (stepy - nsy) / 2.0;
					g.fillOval((int) (npx + posx), (int) (npy + posy),
							(int) (nsx), (int) nsy);
				}
				if (m.isCapsule(x, y)) {
					g.setColor(colorCapsule);
					double nsx = stepx * sizeCapsule;
					double nsy = stepy * sizeCapsule;
					double npx = (stepx - nsx) / 2.0;
					double npy = (stepy - nsy) / 2.0;
					g.fillOval((int) (npx + posx), (int) (npy + posy),
							(int) (nsx), (int) nsy);
				}
				posy += stepy;
			}
			posx += stepx;
		}

		for (int i = 0; i < pacmans_pos.size(); i++) {
			PositionAgent pos = pacmans_pos.get(i);
			// si plusieurs pacman présents, le premier pacman est controlé par le joueur, et est rouge, 
			if (i == 0 && pacmans_pos.size()!=1) drawPacmans(g, pos.getX(), pos.getY(), pos.getDir(), Color.red);
			else drawPacmans(g, pos.getX(), pos.getY(), pos.getDir(), pacmansColor);
		}

		for (int i = 0; i < ghosts_pos.size(); i++) {
			PositionAgent pos = ghosts_pos.get(i);
			if (ghostsScarred) {
				drawGhosts(g, pos.getX(), pos.getY(), ghostScarredColor);
			} else {
				if(i==0) drawGhosts(g, pos.getX(), pos.getY(), Color.RED);
				else drawGhosts(g, pos.getX(), pos.getY(), ghostsColor);
			}
		}
	}

	void drawPacmans(Graphics g, int px, int py, int pacmanDirection,
			Color color) {

		// si pacman est en vie
		if((px != -1) || (py != -1)){
		
			int dx = getSize().width;
			int dy = getSize().height;
	
			int sx = m.getSizeX();
			int sy = m.getSizeY();
			double stepx = dx / (double) sx;
			double stepy = dy / (double) sy;
	
			double posx = px * stepx;
			double posy = py * stepy;
	
			g.setColor(color);
			double nsx = stepx * sizePacman;
			double nsy = stepy * sizePacman;
			double npx = (stepx - nsx) / 2.0;
			double npy = (stepy - nsy) / 2.0;
			
	
			if (pacmanDirection == Maze.NORTH) {
				sa = 70;
				fa = -320;
			}
			if (pacmanDirection == Maze.SOUTH) {
				sa = 250;
				fa = -320;
			}
			if (pacmanDirection == Maze.EAST) {
				sa = 340;
				fa = -320;
			}
			if (pacmanDirection == Maze.WEST) {
				sa = 160;
				fa = -320;
			}
		
	
			g.fillArc((int) (npx + posx), (int) (npy + posy), (int) (nsx),
					(int) nsy, sa, fa);
		}

	}

	void drawGhosts(Graphics g, int px, int py, Color color) {

		if((px != -1) || (py != -1)){
			int dx = getSize().width;
			int dy = getSize().height;
	
			int sx = m.getSizeX();
			int sy = m.getSizeY();
			double stepx = dx / (double) sx;
			double stepy = dy / (double) sy;
	
			double posx = px * stepx;
			double posy = py * stepy;
	
			g.setColor(color);
	
			double nsx = stepx * sizePacman;
			double nsy = stepy * sizePacman;
			double npx = (stepx - nsx) / 2.0;
			double npy = (stepy - nsy) / 2.0;
	
			g.fillArc((int) (posx + npx), (int) (npy + posy), (int) (nsx),
					(int) (nsy), 0, 180);
			g.fillRect((int) (posx + npx), (int) (npy + posy + nsy / 2.0 - 1),
					(int) (nsx), (int) (nsy * 0.666));
			g.setColor(Color.BLACK);
			g.fillOval((int) (posx + npx + nsx / 5.0),
					(int) (npy + posy + nsy / 3.0), 4, 4);
			g.fillOval((int) (posx + npx + 3 * nsx / 5.0),
					(int) (npy + posy + nsy / 3.0), 4, 4);
	
			g.setColor(Color.black);
		}

	}

	public Maze getMaze(){
		return m;
	}
	
	public void setMaze(Maze maze){
		this.m = maze;
	}
	
	public void setGhostsScarred(boolean ghostsScarred) {
		this.ghostsScarred = ghostsScarred;
	}

	public ArrayList<PositionAgent> getPacmans_pos() {
		return pacmans_pos;
	}

	public void setPacmans_pos(ArrayList<PositionAgent> pacmans_pos) {
		this.pacmans_pos = pacmans_pos;				
	}

	public ArrayList<PositionAgent> getGhosts_pos() {
		return ghosts_pos;
	}

	public void setGhosts_pos(ArrayList<PositionAgent> ghosts_pos) {
		this.ghosts_pos = ghosts_pos;
	}
}
