/**
 * Universidad Carlos III de Madrid (UC3M)
 * Programacion 2016-2017
 */
package bomberman.server;

import java.util.Random;
import java.util.LinkedList;
import bomberman.Constants;
import bomberman.server.enemy.Enemigo;
import bomberman.server.enemy.Enemigo1;
import bomberman.server.enemy.Enemigo2;
import bomberman.server.enemy.Enemigo3;
import bomberman.server.enemy.InterfaceEnemy;
import bomberman.server.item.RemoteControl;
import bomberman.server.item.Door;
import bomberman.server.item.InterfaceItem;
import bomberman.server.item.BonusBomb;
import bomberman.server.item.BonusSpeed;
import bomberman.server.item.BonusFire;


/**
 * El controlador del juego que interacciona con el exterior, es decir es el
 * Ãºnico punto por el que la interfaz de usuario proporcionarÃ¡ informaciÃ³n
 * (como los movimientos del ususario) y pedirÃ¡ datos ( como el plano del
 * laberinto)
 * 
 * @author sp87826
 *
 */
public class GameEngine extends Constants {

	
	//Atributos
	private static GameEngine engine;

	private String messages = "Bomberman...\n";
	private String alert = "";

	private Random r;
	private Map[] levels;
	private int currentLevel = 1;
	private Player player;
	private long contador = 0;
	private LinkedList <Detonator>colaBombas = new <Detonator> LinkedList();
	private LinkedList <Detonator>colaExtintor = new <Detonator> LinkedList();
	

	private boolean gameFinished = false;
	private boolean noclip = false;
	private boolean god = false;

	/**
	 * 
	 */
	public GameEngine() {
		engine = this;
		initGame("Bomberman");

	}

	private void initGame(String name) {
		r = new Random();

		// Start a default game
		levels = new Map[G_MAX_LEVEL];

		int initialX = 1;
		int initialY = 1;

		player = new Player();
		player.setName(name);

		changeLevel(1, initialX, initialY);

	}

	public void changeLevel(int level, int initialX, int initialY) {
		currentLevel = level;
		if (levels[currentLevel] == null) {
			levels[currentLevel] = new Map(M_WIDTH, M_HEIGHT, initialX, initialY, r, currentLevel);
		}
		player.move(initialX * 10, initialY * 10);
	}

	/**
	 * Devuelve los mensajes a mostrar por la consola del juego en la interfaz de
	 * usuario - Un solo String, con \n para separar diferentes lÃ­neas de mensaje.
	 * - El String se debe borrar cada vez que se piden los mensajes para no
	 * imprimirlos en la consola mÃ¡s de una vez
	 * 
	 * @return String con los mensajes pendientes de mostrar
	 */
	public String getMessages() {
		String retorno = messages;
		messages = "";
		return retorno;
	}

	public String getAlert() {
		String retorno = alert;
		alert = "";
		return retorno;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	/**
	 * Agrega mensajes a la lista para ser mostrados en la consola de juego 
	 * - Se deben juntar a los existentes con un \n para separar lineas 
	 * - Se pueden ademas imprimir en la consola de java para facilitar la depuración
	 * 
	 * @param newMessage
	 *            Mensaje a añadir
	 */
	public void addMessage(String newMessage) {
		messages += newMessage + "\n";
	}

	public void addAlert(String newAlert) {
		alert += newAlert + "\n";
	}

	public Player getPlayer() {
		return player;
	}

	public long getContador() {
		return contador;
	}

	public void setContador(long contador) {
		this.contador = contador;
	}

	public int getLevel() {
		return currentLevel;
	}

	public InterfaceItem[] getItems() {

		return levels[currentLevel].getItems();
	}

	public InterfaceEnemy[] getNPCs() {

		return levels[currentLevel].getNpcs();
	}

	public Square[][] getMap() {
		return levels[currentLevel].getMap();
	}

	public static GameEngine getEngine() {
		return engine;
	}

	public boolean isGameFinished() {
		return gameFinished;
	}

	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	/**
	 * Recibe la ultima accion realizada por el usuario en el interfaz 
	 * "left": se presiono la flecha hacia la izquierda. 
	 * "right": se presiono la flecha hacia la derecha. 
	 * "up": se presiono la flecha hacia la arriba. 
	 * "down": se presiono la flecha hacia abajo. 
	 * "new game <username>":se presiono el boton de nuevo juego. 
	 * "command <thecommand>":se introdujo un comando en la consola.
	 * 
	 * @param lastAction
	 *            ultima accion realizada por el usuario
	 */
	public void performAction(String lastAction) {

		if (player.getHealth() <= 0 && player.getSprite().equals("bomberman145.png")) {
			setGameFinished(true);
		}
		// NEW GAME BUTTON
		if (lastAction.startsWith("new game")) {
			gameFinished = false;
			String name = lastAction.length()>9?lastAction.substring(9):"Bomberman";
			initGame(name);
			addMessage(player.getName() + " enters in the game");

		} else if (!gameFinished) {
			// PLAYER MOVEMENT (DIRECTIONAL KEYS)
			int nuevaX = player.getX();
			int nuevaY = player.getY();
			switch (lastAction) {
			case "left":
				nuevaX = player.getX() - player.getSpeed();
				if (nuevaX < 0) {
					nuevaX = 0;
				}
				break;
			case "right":
				nuevaX = player.getX() + player.getSpeed();
				if (nuevaX >= M_WIDTH * 10) {
					nuevaX = M_WIDTH * 10 - 1;
				}
				break;
			case "up":
				nuevaY = player.getY() - player.getSpeed();
				if (nuevaY < 0) {
					nuevaY = 0;
				}
				break;
			case "down":
				nuevaY = player.getY() + player.getSpeed();
				if (nuevaY >= M_HEIGHT * 10) {
					nuevaY = M_HEIGHT * 10 - 1;
				}
				break;
			default:
				break;
			}
			
			Square[][] map = levels[currentLevel].getMap();
			//levels[currentLevel].getMap(map);
			// Calculates square of the player
			int currentSquareX = Math.min(((player.getX() + 4) / 10),M_WIDTH);
			int currentSquareY = Math.min(((player.getY() + 8) / 10),M_HEIGHT);

			// pillado por explosion
			if (map[currentSquareX][currentSquareY].getType() >= S_EXPLOSION_C) {
				// Pierde 10% vida
				if (!god) {
					player.setHealth(player.getHealth() - 10);
					if (player.getFrame() == 3 && player.getHealth()<= 0) {
						gameFinished = true;
					}
					//player.move(currentSquareX, currentSquareY);
				}
			}

			if (nuevaX == player.getX() && nuevaY == player.getY()) {
				// no se mueve

			} else {
				// Puede moverse

				int newSquareX = Math.min(((nuevaX + 4) / 10),M_WIDTH);
				int newSquareY = Math.min(((nuevaY + 8) / 10),M_HEIGHT);

				if (map[newSquareX][newSquareY].getType() == S_BOMB && map[currentSquareX][currentSquareY].getType() != S_BOMB && !noclip) {
					// Bomba, no se entra, pero se sale
					
				} else if (map[newSquareX][newSquareY].getType() != S_BOMB && map[newSquareX][newSquareY].isSolid()&& !noclip) {
					// Pared o Ladrillos, no se mueve
				} else {

					// Comprobar si hay algo en la casilla
					/*for (int i = 0; i < levels[currentLevel].getItems().length; i++) {
						if (levels[currentLevel].getItems()[i].getX() == newSquareX && levels[currentLevel].getItems()[i].getY() == newSquareY) {
							levels[currentLevel].getItems()[i].aplicarEfecto(((Player) player));
						}

					}*/
					// Comprobar si hay enemigo en la casilla
					/*for (int i = 0; i < levels[currentLevel].getNpcs().length; i++) {
						if (levels[currentLevel].getNpcs()[i].getX() == nuevaX
								&& levels[currentLevel].getNpcs()[i].getY() == nuevaY) {
							levels[currentLevel].getNpcs()[i].receiveAtack();
							if (levels[currentLevel].getNpcs()[i].isAlive()) {
								// si no ha muerto, no podemos pasar
								nuevaX = player.getX();
								nuevaY = player.getY();
								if (!god) {
									levels[currentLevel].getNpcs()[i].aplicarEfecto(((Player) player));
								}
							}
						}

					}*/
					player.move(nuevaX, nuevaY);
				}

			}

			// Bomb
			if (lastAction.equalsIgnoreCase("space")) {
				if (player.getBombs() > 0) {
					colaBombas.offer(new Detonator(currentSquareX, currentSquareY,contador, player, map));
				}

			}

			// CONSOLE COMMANDS 
			if(lastAction.startsWith("command")){
				String name = lastAction.length()>8?lastAction.substring(8):"none";
				switch (name) {
				case "god":
					god = !god;
					addMessage("God mode: " + god);
					break;
				case "noclip":
					noclip = !noclip;
					addMessage("No clipping mode: " + noclip);
					break;	
				case "detonate":
					lastAction="tab";
					break;	
				default:
					if (name.startsWith("level"))
					{
						String newLevel = lastAction.length()>13?lastAction.substring(13):"1";
						int nextLevel = 1;
						try {
							nextLevel = Integer.parseInt(newLevel);
						} catch (Exception e) {
							addMessage("Nivel no reconocido: " + newLevel);
						}
						if(nextLevel < Constants.G_MAX_LEVEL){
		                    changeLevel(nextLevel, currentSquareX, currentSquareY);
		                    addMessage("Entering level " + nextLevel);
		                }
		                else {
		                    addMessage("Maximo nivel permitido: "+Constants.G_MAX_LEVEL);
		                }
					} else {
						addMessage("Comando ["+ name+ "] no reconocido.\nPosibles: god,noclip,detonate,level<number>.");
					}
					break;
				}
				
			}

					
			
			//Cola para detonar bombas
			if (colaBombas.peek() != null) {
				if (lastAction.equalsIgnoreCase("tab")) {
					while (colaBombas.peek() != null) {
						colaBombas.peek().Detonate(map,player.getFire(),contador);
						colaExtintor.offer(colaBombas.poll());
						
					}
				}
			}	
			//Cola para apagar fuego de bombas explotadas
			if (colaBombas.peek() != null) {
				if (colaBombas.peek().getTiempo() <= contador) { 
					colaBombas.peek().Detonate(map,player.getFire(), contador);
					colaExtintor.offer(colaBombas.poll());
				}
			}
			
			//Regenerador  de bombas del jugador
			if (colaExtintor.peek() != null) {
				if (colaExtintor.peek().getTiempo() <= contador) {
					colaExtintor.poll().antiDetonate(map,player.getFire());
				player.setBombs(player.getBombs()+1);}
			}
			
			for (int i = 0; i< Constants.M_HEIGHT;i++ ) {
				for (int j = 0; j <Constants.M_HEIGHT;j++) {
					map[i][j].setTime(contador);
				}
			}
			
			
		}
	}
}
