/*
 * Universidad Carlos III de Madrid (UC3M)
 * Programacion 2016-2017
 */
package bomberman;

import bomberman.client.ClientManager;
import bomberman.server.GameEngine;

/**
 * The main class of the MiniDungeon game.
 * 
 * @author Planning and Learning Group (PLG)
 */
public class Game extends Constants {

	/**
	 * The entry point of the application
	 * 
	 * @param args
	 *            the command line arguments
	 * @throws java.lang.InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Game initialization
		System.out.println("Strarting...");
		
		boolean runApplication = true;
		
		GameEngine engine= new GameEngine();
		ClientManager manager = new ClientManager(engine);
		
		String lastAction = "";
		
		///////////////
		// GAME LOOP //
		///////////////
		//long contador=0;
		while (runApplication) {
			
			// si el jugador muere se acaba el juego
			if (engine.isGameFinished()) {
				engine.setAlert("Game Over");
				runApplication = false; 
				
			}
			
			//Calculate next game state
			engine.performAction(lastAction);
			
			
			
			//if (engine.getAlert()) {
				
			//}
			//Update game screen
			manager.updateView(lastAction);
			
			//Get last action from user.
			lastAction = manager.getLastAction();
			
			// Adequate way to exit the program
			if (lastAction.equalsIgnoreCase("exit game")){
				runApplication=false;
			}
			
			try{
				// IMPORTANT THREAD METHOD
				Thread.sleep((long) (1 / (double) G_FPS * 1000));
				System.out.println(engine.getContador());
				engine.setContador(engine.getContador()+1);
			}catch(InterruptedException ie){
				System.out.println("Game Loop Error: ");
				ie.printStackTrace();
			}
		}

		System.out.println("...end of program");
		System.exit(0);
	}
}
