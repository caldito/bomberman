package bomberman.server.item;

import bomberman.server.Player;

public interface InterfaceItem {
	int getX();
	int getY();
	String getSprite();
	int getId();
	void aplicarEfecto(Player player);
	boolean isUtilizado();
	//int setSpawn();
	int getSpawn();
}
