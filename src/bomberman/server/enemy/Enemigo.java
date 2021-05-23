package bomberman.server.enemy;

import bomberman.server.Player;

public class Enemigo implements InterfaceEnemy{
	//Atributos
	private int speed;
	private int x;
	private int y;
	private boolean alive;
	
	//Getters y setters
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
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
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void aplicarEfecto(Player player) {
		player.setHealth(player.getHealth()-10);
	}
	public void receiveAtack() {
		setAlive(false);
	}
	@Override
	public String getSprite() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	

}
