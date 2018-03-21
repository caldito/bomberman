package bomberman.server.item;

import bomberman.server.Player;


public class BonusGeta implements InterfaceItem {
	private int x;
	private int y;
	private int id;
	private int spawn;
	private String sprite;
	private boolean utilizado;

	
	
	
	
	public void aplicarEfecto(Player player) {
		player.setSpeed(1);
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



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getSpawn() {
		return spawn;
	}




	public void setSpawn(int spawn) {
		if (Math.random()<0.2)
			this.spawn = 1;
	}




	public String getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}


	public boolean isUtilizado() {
		return utilizado;
	}

	public void setUtilizado(boolean utilizado) {
		this.utilizado = utilizado;
	}
}
