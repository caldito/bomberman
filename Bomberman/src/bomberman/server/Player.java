package bomberman.server;

import bomberman.Constants;
public class Player {
	
	//Atributos
	private String name;
	private int score = 0;
	private int bombs = 1;
	//private int bombsMax = 3;
	private int health = 100;
	private int healthMax = 100;
	private int speed = 1;
	private int fire = 1;
	private int frame = 1;
	private String portrait = "White_Bomberman_R.png";
	private String sprite = "bomberman.111";
	private int x = 1;
	private int y = 1;
	 	
	//Métodos
	public void move(int x, int y) {
		int direction = 0;
		if (this.y > y) {//
			direction = Constants.D_NORTH;
		}else if (this.y < y) {
			direction = Constants.D_SOUTH;
		}else if (this.x < x) {
			direction = Constants.D_EAST;
		}else if (this.x > x) {
			direction = Constants.D_WEST;
		}
		frame = (frame + 1) % 5;
	        if (this.health > 0 ) {

	            sprite = "bomberman1" + direction + "" + (frame + 1) + ".png";///Efecto de movimiento con varias imágenes.
	        }else {
	            sprite = "bomberman1" + "4" + "" + (frame + 1) + ".png";}
	            if (frame==3){
	            	
	            }
	        //}
		
		setX(x);
		setY(y);
	}
	
	
	
	//Constructores
	public Player(){
		
	}
	
	public Player(String name,int score,int bombs,int health,int healthMax,int speed,int fire,String portrait,String sprite,int x,int y) {
		setName(name);
		setScore(score);
		setBombs(bombs);
		setHealth(health);
		setHealthMax(healthMax);
		setSpeed(speed);
		setFire(fire);
		setPortrait(portrait);
		setSprite(sprite);
		setX(x);
		setY(y);
		
		
	}
	
	//Setters y getters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getBombs() {
		return bombs;
	}
	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	/*public int getBombsMax() {
		return bombsMax;
	}
	public void setBombsMax(int bombsMax) {
		this.bombsMax = bombsMax;
	}*/



	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getHealthMax() {
		return healthMax;
	}
	public void setHealthMax(int healthMax) {
		this.healthMax = healthMax;
	}
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getFire() {
		return fire;
	}
	public void setFire(int fire) {
		this.fire = fire;
	}
	public int getFrame() {
		return frame;
	}



	public void setFrame(int frame) {
		this.frame = frame;
	}



	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public String getSprite() {
		
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
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
	
	
	 
	
	
	
}
