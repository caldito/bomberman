package bomberman.server;

import java.util.Random;

import bomberman.server.item.InterfaceItem;
import bomberman.server.enemy.InterfaceEnemy;
import bomberman.Constants;

public class Map {
	// Atributos
	private int width;
	private int height;
	private int initialX;
	private int initialY;
	private Random r;
	private int level;
	private InterfaceItem[] items;
	private InterfaceEnemy[] npcs;
	private Square[][] map = new Square[17][17];

	// Constructor
	public Map(int width, int height, int initialX, int initialY, Random r, int level) {
		setWidth(width);
		setHeight(height);
		setInitialX(initialX);
		setInitialY(initialY);
		setR(r);
		setLevel(level);
		setMap(map);
	}

	// Setters y getters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getInitialX() {
		return initialX;
	}

	public void setInitialX(int initialX) {
		this.initialX = initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public void setInitialY(int initialY) {
		this.initialY = initialY;
	}

	public Random getR() {
		return r;
	}

	public void setR(Random r) {
		this.r = r;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public InterfaceItem[] getItems() {
		return items;
	}

	public void setItems(InterfaceItem[] items) {
		this.items = items;
	}

	public InterfaceEnemy[] getNpcs() {
		return npcs;
	}

	public void setNpcs(InterfaceEnemy[] npcs) {
		this.npcs = npcs;
	}

	public Square[][] getMap() {
		return map;
	}

	public void setMap(Square[][] map) {
		map = new Square[Constants.M_WIDTH][Constants.M_HEIGHT];

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Square(Constants.S_NONE); // todas se hacen
															// transitables y
															// luego a las no
															// transitables se
															// les cambia el
															// valor
			}
		}

		// Para muro indestructible S_WALL que es igual a 1

		// Para ladrillo S_BRICKS que es igual a 2
		for (int i = 1; i < 16; i += 1) {
			for (int j = 1; j < 16; j += 1) {
				if (map[i][j].getType() == Constants.S_NONE) {
					map[i][j].setType(Constants.S_BRICKS);
				}
			}
		}

		for (int i = 1; i < 16; i += 1) {
			for (int j = 1; j < 16; j += 1) {
				int random = (int) Math.floor((Math.random() * 2));
				if (map[i][j].getType() == 2) {
					if (random == 0) {
						map[i][j].setType(0);
					} else {
						map[i][j].setType(2);
					}

				}
			}
			map[1][1].setType(Constants.S_NONE);
			map[1][2].setType(Constants.S_NONE);
			map[2][1].setType(Constants.S_NONE);

		}
		for (int i = 0; i < map.length; i = i + 2) {
			for (int j = 0; j < map[i].length; j = j + 2) {
				map[i][j].setType(Constants.S_WALL);
			}
		}
		for(int i = 0; i < map.length; i++){
		map[0][i].setType(Constants.S_WALL);
		map[i][0].setType(Constants.S_WALL);
		map[16][i].setType(Constants.S_WALL);
		map[i][16].setType(Constants.S_WALL);
		}
		this.map = map;
	}

}
