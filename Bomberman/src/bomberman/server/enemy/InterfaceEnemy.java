package bomberman.server.enemy;

import bomberman.server.Player;

public interface InterfaceEnemy {
	int getX();
	int getY();
	String getSprite();
	int getId();
	boolean isAlive();
	void receiveAtack();
	void aplicarEfecto(Player player);
}
