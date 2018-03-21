package bomberman.server.enemy;

public class Enemigo1 extends Enemigo{
	private int spawn;
	private int points;
	
	public int getSpawn() {
		return spawn;
	}
	public void setSpawn(int spawn) {
		this.spawn = (int) (Math.random()*10)+1;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = 100;
	}

}
