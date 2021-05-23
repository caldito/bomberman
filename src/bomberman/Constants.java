/*
 * Universidad Carlos III de Madrid (UC3M)
 * Programacion 2016-2017
 */
package bomberman;

/**
 * Parameters and magic numbers to be used throughout the code.
 * @author Planning and Learning Group (PLG)
 */
public class Constants {
    ////////////////
    // PARAMETERS //
    ////////////////
    
    // Game
    public static final int G_MAX_LEVEL = 10;
    public static final int G_FPS = 60; // 30 seems good.
    
    //Map generation
    public static final int M_WIDTH = 17;
    public static final int M_HEIGHT = 17;
    public static final int M_BRICKS = 50;
    public static final int M_FIRE = 5;
    public static final int M_SPEED = 10;
    
    

    public static final int G_BONUS_BOMB = 4;//4;
    public static final int G_BONUS_FIRE = 1;//1;
    public static final int G_BONUS_SPEED = 1;//1;
    public static final int G_DOOR = 1;//1;
    
    public static final int G_ENEMY1 = 10;//10;
    public static final int G_ENEMY2 = 1;//10;
    public static final int G_ENEMY3 = 0;//10;
    
    //Tiempo de explosión
    public static final int T_EXPLOSION = 3;
    public static final int T_FUEGO = 20;
    
    
   
    
    ///////////////////
    // MAGIC NUMBERS //
    ///////////////////

    //Directions
    public static final int D_NORTH = 0;
    public static final int D_SOUTH = 1;
    public static final int D_WEST = 2;
    public static final int D_EAST = 3;
    
    //Square types
    public static final int S_NONE = 0;
    public static final int S_WALL = 1;
    public static final int S_BRICKS = 2;
    public static final int S_BRICKS_BONUS = 3;
    public static final int S_BOMB = 4;
    public static final int S_EXPLOSION_C = 5;
    public static final int S_EXPLOSION_N = 6;
    public static final int S_EXPLOSION_S = 7;
    public static final int S_EXPLOSION_E = 8;
    public static final int S_EXPLOSION_W = 9;
    public static final int S_EXPLOSION_H = 10;
    public static final int S_EXPLOSION_V = 11;
    
    
    //Item types
    public static final int I_BONUS_BOMB = 0;
    public static final int I_BONUS_FIRE = 1;
    public static final int I_BONUS_FIRE_FULL = 2;
    public static final int I_REMOTE_CONTROL = 3;
    public static final int I_BONUS_SPEED = 4;
    public static final int I_BONUS_GETA = 5;
    
    public static final int I_DOOR = 6;
    
    //NPCS types
    public static final int N_ENEMY1 = 1;
    public static final int N_ENEMY2 = 2;
    public static final int N_ENEMY3 = 3;
}
