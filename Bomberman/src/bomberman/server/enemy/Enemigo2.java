package bomberman.server.enemy;

import bomberman.server.GameEngine;
import bomberman.server.Map;

public class Enemigo2 extends Enemigo {
	private int spawn;
	private int points;
	
	public int getSpawn() {
		return spawn;
	}
	/*public void setSpawn(int spawn) {
		int i=0;
		int j = Map.getLevel();
		for(;j>2;i++){
			j=j-4;
		}
		this.spawn = i;
	}*/
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = 250;
	}

}
