package bomberman.client;

import bomberman.Constants;
import bomberman.server.GameEngine;
import bomberman.server.Player;
import bomberman.server.Square;
import bomberman.server.enemy.InterfaceEnemy;
import bomberman.server.item.InterfaceItem;
import game.GameBoardGUI;

public class ClientManager extends Constants {

	private GameBoardGUI gui;
	private GameEngine controller;

	private int currentLevel = 0;
	private int previousPlayerHealth = 0;

	public ClientManager(GameEngine theController) {

		controller = theController;
		gui = new GameBoardGUI(M_WIDTH, M_HEIGHT);

		gui.gb_setTextPointsUp("Points");
		gui.gb_setTextPointsDown("Bombs");
		gui.gb_setTextAbility1("Bomb range");
		gui.gb_setTextAbility2("Speed");

		gui.setVisible(true); // IMPORTANT INTERFACE METHOD!!!
	}

	public void updateView(String lastAction) {

		// Show console and dialogs
		updateConsole();
		if (lastAction == "jugador muerto") {
			gui.gb_clearConsole();
			gui.gb_showMessageDialog("muerto");
		}
		
		if (lastAction.startsWith("new game"))
		{
			gui.gb_clearConsole();
		}

		// update score values
		updateScore();

		// Change floor if necessary
		if (currentLevel != controller.getLevel() || lastAction.startsWith("new game")) {
			//changeLevel();
			currentLevel = controller.getLevel();
		}
		

		repaintBoard();

	}

	/**
	 * Get last action from user, if any.
	 * 
	 * @return String the last action or "" if no action exists
	 */
	public String getLastAction() {

		return gui.gb_getLastAction().trim();
	}

	/**
	 * Write messages to the console and show notifications
	 * 
	 * Clear the console if "new game"
	 */
	private void updateConsole() {
		String messages = controller.getMessages();
		if (messages.length()>0){
			gui.gb_println(messages);
		}
		String alert = controller.getAlert();
		if (alert.length()>0)
		{
			gui.gb_showMessageDialog(alert);
		}
		
		
	}

	/**
	 * Update all values of the score panel (portrait and texts)
	 */
	private void updateScore() {
		// TODO - implement
		Player player = controller.getPlayer();
		
		gui.gb_setTextPlayerName(player.getName());

		gui.gb_setValueLevel(currentLevel);

		gui.gb_setValuePointsUp(player.getScore());
		gui.gb_setValuePointsDown(player.getBombs());
		
		if (player.getHealth()<previousPlayerHealth){
			gui.gb_animateDamage();
		}
		gui.gb_setValueHealthCurrent(player.getHealth());
		previousPlayerHealth = player.getHealth();
		
		gui.gb_setValueHealthMax(player.getHealthMax());
		gui.gb_setValueAbility1(player.getFire());
		gui.gb_setValueAbility2(player.getSpeed());

		gui.gb_setPortraitPlayer(player.getPortrait());

	}

	/**
	 * Create objects to populate the new floor
	 * 
	 */
	private void changeLevel() {
		// TODO - implement
		InterfaceItem[] items = controller.getItems();
		InterfaceEnemy[] npcs = controller.getNPCs();
		Player player = controller.getPlayer();
		gui.gb_clearSprites();

		// Add all items on top of the board
		for (int i = 0; i < items.length; i++) {

			gui.gb_addSprite(items[i].getId(), items[i].getSprite(), true);
			gui.gb_moveSprite(items[i].getId(), items[i].getX(), items[i].getY());
			//gui.gb_setSpriteVisible(items[i].getId(), false);
		}

		// Add all npcs on top of the items

		for (int i = 0; i < npcs.length; i++) {

			gui.gb_addSprite(npcs[i].getId(), npcs[i].getSprite(), true);
			gui.gb_moveSprite(npcs[i].getId(), npcs[i].getX(), npcs[i].getY());
			gui.gb_setSpriteVisible(npcs[i].getId(), true);

		}

		// Add player on top of the npcs

		gui.gb_addSprite(0, player.getSprite(), true);
		gui.gb_moveSprite(0, player.getX(), player.getY());
		gui.gb_setSpriteVisible(0, true);

	}

	/**
	 * Update position and states of map and objects
	 * 
	 */
	private void repaintBoard() {

		InterfaceItem[] items = controller.getItems();
		InterfaceEnemy[] npcs = controller.getNPCs();
		Player player = controller.getPlayer();

		Square[][] map = controller.getMap();
		
		// Update squares
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				int color[] = map[i][j].getColor();
				gui.gb_setSquareColor(i, j, color[0], color[1], color[2]);
				String image = map[i][j].getSprite();
				
				if (image.length() > 0) {
					gui.gb_setSquareImage(i, j, image);
				} else {
					gui.gb_setSquareImage(i, j, null);
				}

			}
		}

		// Update items
		/*for (int i = 0; i < items.length; i++) {

			if (items[i].getUtilizado()) {
				gui.gb_setSpriteVisible(items[i].getId(), false);
			} else {
				//TODO - comentar el if para ver los elementos bajo los ladrillos
				if (!map[items[i].getX()][items[i].getY()].isSolid()) {
					gui.gb_setSpriteVisible(items[i].getId(), true);
					// ralentiza el juego, elementos fijos
					//gui.gb_setSpriteImage(items[i].getId(), items[i].getSprite());
				}
			}
		}*/
		// Update npcs

		/*for (int i = 0; i < npcs.length; i++) {
			// Cambiar el sprite si está muerto
			if (!npcs[i].isAlive()) {
				gui.gb_addSprite(npcs[i].getId(), "bones.png", false);
			}

			gui.gb_setSpriteImage(npcs[i].getId(), npcs[i].getSprite());
			// mover a su posición actual
			gui.gb_moveSpriteCoord(npcs[i].getId(), npcs[i].getX(), npcs[i].getY());

		}*/
		
		
		
		
		// Update player
		gui.gb_addSprite(0, player.getSprite(), true);
		gui.gb_setSpriteVisible(0, true);
		gui.gb_setSpriteImage(0, player.getSprite());
		gui.gb_moveSpriteCoord(0, player.getX(), player.getY());
		System.out.println("Ubicacion del jugador:("+player.getX()+","+player.getY()+")");
		System.out.println("Frame del jugador numero "+ player.getFrame());
		gui.gb_repaintBoard();
	}

}
