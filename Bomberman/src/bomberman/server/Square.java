package bomberman.server;
import bomberman.Constants;

public class Square {
	
	//Atributos
	private int type;
	
	private long detonatingTime;
	
	private long time;
	
	private long spriteTime;
	
	private long spriteBomb;
	
	private int [] color = {116, 244, 66};
	
	private String sprite;
	
	//Constructor 
	public Square(int type) {
		setType(type);
	}
	
	//Getters y setters
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public long getDetonatingTime() {
		return detonatingTime;
	}

	public void setDetonatingTime(long detonatingTime) {
		this.detonatingTime = detonatingTime;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getSpriteTime() {
		return spriteTime;
	}

	public void setSpriteTime(int spriteTime) {
		this.spriteTime = spriteTime;
	}

	public boolean isSolid() {
		boolean solid;
		if (type == Constants.S_NONE || type > Constants.S_BOMB) {
			solid = false;
		}else {
			solid = true;
		}
		return solid;
			
	}

	public int[] getColor() {
		return color;
	}

	public void setColor(int[] color) {
		this.color = color;
	}

	public String getSprite() {
		spriteTime = time - detonatingTime / 5;
		spriteTime = (spriteTime) % 4 + 1;
		spriteBomb = 0;
		switch((int)(spriteTime)) {
			case 1:
				spriteBomb = 1;
		
			case 2:
				spriteBomb = 2;
		
			case 3:
				spriteBomb = 1;
	
			case 4:
				spriteBomb = 2;
		}
		
		
		System.out.println("spriteTime: " + spriteTime);
		switch (getType()) {
		case Constants.S_NONE:
			setSprite("");
			break;
		case Constants.S_WALL:
			setSprite("wall.gif");
			break;
		case Constants.S_BRICKS:
			setSprite("bricks.gif");
			break;
		case Constants.S_BRICKS_BONUS:
			setSprite("bricks.gif");
			break;
		case Constants.S_BOMB:
			setSprite("bomb" +spriteBomb+".gif");
			break;
		case Constants.S_EXPLOSION_C:
			setSprite("explosion_C" + spriteTime + ".gif");
			break;
		case Constants.S_EXPLOSION_N:
			setSprite("explosion_N" + spriteTime + ".gif");
			break;
		case Constants.S_EXPLOSION_S:
			setSprite("explosion_S" + spriteTime + ".gif");
			break;
		case Constants.S_EXPLOSION_E:
			setSprite("explosion_E" + spriteTime + ".gif");
			break;
		case Constants.S_EXPLOSION_W:
			setSprite("explosion_W" + spriteTime + ".gif");
			break;
		case Constants.S_EXPLOSION_H:
			setSprite("explosion_H" + spriteTime + ".gif");
			break;
		case Constants.S_EXPLOSION_V:
			setSprite("explosion_V" + spriteTime + ".gif");
			break;
		}
		
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}
	
	
	
		
	
}


