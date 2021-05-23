package bomberman.server;

import bomberman.Constants;

public class Detonator {
	// Atributos
	private int casillaX;
	private int casillaY;
	private long tiempo;
	private Player player;
	private Square[][] map;

	// Constructor
	public Detonator(int casillaX, int casillaY, long tiempo, Player player, Square[][] map) {
		setCasillaX(casillaX);
		setCasillaY(casillaY);
		map[casillaX][casillaY].setType(4);
		player.setBombs(player.getBombs() - 1);
		setTiempo(tiempo + Constants.G_FPS * Constants.T_EXPLOSION);
	}

	// Métodos
	
	//Para detonar una bomba
	public void Detonate(Square[][] map,int fire, long tiempo) {
		map[casillaX][casillaY].setType(5);
		map[casillaX][casillaY].setDetonatingTime(tiempo);
		
		int i;
		
		//Explosión arriba
		for (i = 0;map[casillaX][casillaY + i +1].getType()!=Constants.S_WALL && i < fire;i++) {
			System.out.println(i);
		}
		System.out.println("i:  "+i);	
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j < i){
				map[casillaX][casillaY + j].setType(Constants.S_EXPLOSION_V);
				map[casillaX][casillaY+ j].setDetonatingTime(tiempo);
			}else if (j ==i ) {
				map[casillaX][casillaY + j].setType(Constants.S_EXPLOSION_S);
				map[casillaX][casillaY + j].setDetonatingTime(tiempo);
			}
		}
		
		//Explosión derecha
		for (i = 0;map[casillaX + i +1][casillaY].getType()!=Constants.S_WALL && i < fire;i++) {}
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j < i){
				map[casillaX + j][casillaY].setType(Constants.S_EXPLOSION_H);
				map[casillaX + j][casillaY].setDetonatingTime(tiempo);
			}else if (j ==i ) {
				map[casillaX + j][casillaY].setType(Constants.S_EXPLOSION_E);
				map[casillaX +j][casillaY].setDetonatingTime(tiempo);
			}
		}
		
		//Explosión izquierda
		for (i = 0;map[casillaX - i - 1][casillaY].getType()!=Constants.S_WALL && i < fire;i++) {}
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j < i){
				map[casillaX - j][casillaY].setType(Constants.S_EXPLOSION_H);
				map[casillaX - j][casillaY].setDetonatingTime(tiempo);
			}else if (j ==i ) {
				map[casillaX - j][casillaY].setType(Constants.S_EXPLOSION_W);
				map[casillaX - j ][casillaY].setDetonatingTime(tiempo);
			}
		}
		
		//Explosión arriba
		for (i = 0;map[casillaX][casillaY - i - 1].getType()!=Constants.S_WALL && i < fire;i++) {}
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j < i){
				map[casillaX][casillaY - j].setType(Constants.S_EXPLOSION_V);
				map[casillaX][casillaY -j].setDetonatingTime(tiempo);
			}else if (j ==i ) {
				map[casillaX][casillaY - j].setType(Constants.S_EXPLOSION_N);
				map[casillaX][casillaY - j].setDetonatingTime(tiempo);
			}
		}
		
		setTiempo(tiempo + Constants.T_FUEGO);
	
	}
	
	// Para apagar la explosión de la bomba
	public void antiDetonate(Square[][] map,int fire) {
		map[casillaX][casillaY].setType(Constants.S_NONE);
		int i;
		
		//Extintor arriba
		for (i = 0;map[casillaX][casillaY + i +1].getType()!=Constants.S_WALL && i < fire;i++) {
			System.out.println(i);
		}
		System.out.println("i:  "+i);	
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j <= i){
				map[casillaX][casillaY + j].setType(Constants.S_NONE);
			}
		}
		
		//Extintor derecha
		for (i = 0;map[casillaX + i +1][casillaY].getType()!=Constants.S_WALL && i < fire;i++) {}
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j <= i){
				map[casillaX + j][casillaY].setType(Constants.S_NONE);
			}
		}
		
		//Extintor izquierda
		for (i = 0;map[casillaX - i - 1][casillaY].getType()!=Constants.S_WALL && i < fire;i++) {}
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j <= i){
				map[casillaX - j][casillaY].setType(Constants.S_NONE);
			}
		}
		
		//Extintor arriba
		for (i = 0;map[casillaX][casillaY - i - 1].getType()!=Constants.S_WALL && i < fire;i++) {}
		for (int j = 0;j <= i;j++){
			if(j == 0) {
					
			}else if (j > 0 && j <= i){
				map[casillaX][casillaY - j].setType(Constants.S_NONE);
			}
		}
		
	}
	
	
	
	// Getters y setters
	public int getCasillaX() {
		return casillaX;
	}

	public void setCasillaX(int casillaX) {
		this.casillaX = casillaX;
	}

	public int getCasillaY() {
		return casillaY;
	}

	public void setCasillaY(int casillaY) {
		this.casillaY = casillaY;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Square[][] getMap() {
		return map;
	}

	public void setMap(Square[][] map) {
		this.map = map;
	}

	public static Detonator[] getDetonators() {
		// TODO Auto-generated method stub
		return null;
	}

}
