package game;

/*
 * Universidad Carlos III de Madrid (UC3M)
 * Planning and Learning Group (PLG)
 * Programacion 2015-2018
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameBoardGUI extends javax.swing.JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String version = "BETA 2017.10.20";
    
    private String lastAction;
    private int boardWidth;
    private int boardHeight;    
    private JLabel [][] boardSquares;
    private JLabel damageSprite;
    
    private HashMap<String,BufferedImage> images = new HashMap<String,BufferedImage>();    
    private HashMap<String,ImageIcon> imageIcons = new HashMap<String,ImageIcon>();
    private HashMap<Integer,JLabel> sprites = new HashMap<Integer,JLabel>();
    
    private long timeStartAnimationDamage;
    private Timer timer;
        
    private void initializeGUI(int boardWidth, int boardHeight) {  
        this.setTitle("GameBoardGUI " + version + " - Universidad Carlos III de Madrid");
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        
        boardSquares = new JLabel[boardWidth][boardHeight];
        
        preloadImages();
        damageSprite = new JLabel();                
        damageSprite.setSize(141,188);
        damageSprite.setLocation(655, 37);
        damageSprite.setIcon(getImageIcon("hit.png", 141, 188));
        damageSprite.setVisible(true);
        jLayeredPane2.add(damageSprite, new Integer(6));
        
        int squareWidth = jLayeredPane1.getWidth()/boardWidth;
        int squareHeight = jLayeredPane1.getHeight()/boardHeight;
        
        for(int i = 0; i < boardWidth; i++){
            for(int j = 0; j < boardHeight; j++) {
                boardSquares[i][j] = new JLabel();                
                boardSquares[i][j].setSize(squareWidth, squareHeight);
                boardSquares[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.DARK_GRAY));
                boardSquares[i][j].setOpaque(true);
                boardSquares[i][j].setLocation(i*squareWidth, j*squareHeight);
                jLayeredPane1.add(boardSquares[i][j],new Integer(0));
            }
            
            
            KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
                public boolean dispatchKeyEvent(KeyEvent e) {   
                    return keyboardControl(e);
                }
            });
            /* Set the Nimbus look and feel */
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(GameBoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(GameBoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(GameBoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(GameBoardGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
                        
        }        
        
        
        
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                timedEvent();
            }
        };
        
        jLabel13.setBackground(new Color(255,0,0));
        jLabel12.setBackground(new Color(255,0,0));
        
        timer = new Timer(200, listener);
        timer.start();
        
        jTextField1.requestFocusInWindow();
        //testImages();
        this.repaint();
    }
    
    private void timedEvent() {
        if(System.currentTimeMillis() - timeStartAnimationDamage > 100){
            damageSprite.setVisible(false);
        }
    }
    
    /**
     * Sets the background color of a square.
     * @param x the x coordinate of the square
     * @param y the y coordinate of the square
     * @param red red channel (0 to 255)
     * @param green green channel (0 to 255)
     * @param blue blue channel (0 to 255)
     */
    public void gb_setSquareColor(int x, int y, int red, int green, int blue){
        if (red >= 0 && red < 256 && green >= 0 && green < 256 && blue >= 0 && blue < 256){
            if(x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
                System.err.println("ERROR using gb_setSquareColor: Square " +x + "x"+y+ " is out of the board limits.");
            else
                boardSquares[x][y].setBackground(new Color(red, green, blue));            
        }
        else
            System.err.println("ERROR using gb_setSquareColor: Color components (" + red + "," + green + "," + blue + ") must be between 0 and 255.");
    }
    
    /**
     * Sets a background image for a square.
     * @param x the x coordinate of the square
     * @param y the y coordinate of the square
     * @param fileName the name of the image file. Null removes the image file.
     */
    public void gb_setSquareImage(int x, int y, String fileName) {
        int squareWidth = jLayeredPane1.getWidth()/boardWidth;
        int squareHeight = jLayeredPane1.getHeight()/boardHeight;
        if(x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
            System.err.println("ERROR using gb_setSquareIcon: Square " +x + "x"+y+ " is out of the board limits.");
        else {
            if (fileName != null){
                ImageIcon i = getImageIcon(fileName,squareWidth , squareHeight);
                if (i != null)
                    boardSquares[x][y].setIcon(getImageIcon(fileName,squareWidth , squareHeight));
                else
                    System.err.println("ERROR using gb_setSquareIcon: Image " + fileName + " could not be loaded correctly.");
            }
            else
                boardSquares[x][y].setIcon(null);
        }            
    }      
    
    /**
     * Obtains the last action performed in the GUI.
     * For instance: a player's movement, a command, a new game button click
     * @return The last action codified as a String
     */
    public String gb_getLastAction() {
        String out = lastAction;
        lastAction = "";
        return out.trim();
        
    }

    /**
     * Adds a new sprite to the board.
     * @param id a unique id to identify the sprite
     * @param spriteFileName The file name of the image for the sprite
     * @param top if true, the sprite will be drawn on top of all previous ones. If false, it will be drawn at the bottom
     */
    public void gb_addSprite(int id, String spriteFileName, boolean top) {
        JLabel previous = sprites.get(id);
        if(previous != null)
            jLayeredPane1.remove(previous);
        
        int characterWidth = jLayeredPane1.getWidth()/boardWidth;
        int characterHeight = jLayeredPane1.getHeight()/boardHeight;
        ImageIcon icon = getImageIcon(spriteFileName, characterWidth, characterHeight);
        
        if (icon != null) {
            JLabel newCharacter = new JLabel();            
            newCharacter.setSize(characterWidth, characterHeight);
            newCharacter.setIcon(icon);
            newCharacter.setOpaque(false);
            newCharacter.setVisible(false);
            sprites.put(id, newCharacter);
                        
            jLayeredPane1.add(sprites.get(id));
            if(top)
                jLayeredPane1.setComponentZOrder(sprites.get(id), 0);
            else
                jLayeredPane1.setComponentZOrder(sprites.get(id), jLayeredPane1.getComponentCount()-boardWidth*boardHeight);
        }
        else
            System.err.println("ERROR using gb_addSprite: Image "+ spriteFileName + " could not be loaded correctly.");
    }
    
    /**
     * Changes the image of a sprite.
     * @param id a unique id to identify the sprite
     * @param spriteFileName The file name of the image for the sprite
     */
    public void gb_setSpriteImage(int id, String spriteFileName) {
       
        int characterWidth = jLayeredPane1.getWidth()/boardWidth;
        int characterHeight = jLayeredPane1.getHeight()/boardHeight;
        ImageIcon icon = getImageIcon(spriteFileName, characterWidth, characterHeight);
        
        JLabel sprite = sprites.get(id);
        
        if (icon != null && sprite != null) {
        	sprite.setIcon(icon);
        }
        else
            System.err.println("ERROR using gb_setSpriteImage: Image "+ spriteFileName + " could not be loaded correctly, or sprite id does not exist.");
    }
    
    /**
     * Moves a certain sprite to a square.
     * @param id the id of the sprite
     * @param x the x coordinate of the square
     * @param y the y coordinate of the square
     */
    public void gb_moveSprite(int id, int x, int y){
        int squareWidth = jLayeredPane1.getWidth()/boardWidth;
        int squareHeight = jLayeredPane1.getHeight()/boardHeight;
        JLabel l = sprites.get(id);
        if(l != null)
            l.setLocation(x*squareWidth, y*squareHeight);
        else
            System.err.println("ERROR using gb_moveSprite: Id " + id + " does not exist.");
    }
    
    /**
     * Moves a certain sprite to a coordinates (1/10 of square size).
     * @param id the id of the sprite
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void gb_moveSpriteCoord(int id, int x, int y){
    	double stepWidth = jLayeredPane1.getWidth()/(boardWidth*10.0);
    	double stepHeight = jLayeredPane1.getHeight()/(boardHeight*10.0);
        JLabel l = sprites.get(id);
        if(l != null)
            l.setLocation((int)(x*stepWidth), (int)(y*stepHeight));
        else
            System.err.println("ERROR using gb_moveSpriteCoord: Id " + id + " does not exist.");
    }
    
    /**
     * Sets the visibility of a sprite.
     * @param id the id of the sprite
     * @param visible if the sprite has to be visible or not
     */
    public void gb_setSpriteVisible(int id, boolean visible) {
        if(sprites.get(id) != null)
            sprites.get(id).setVisible(visible);
        else
            System.err.println("ERROR using gb_setSpriteVisible: id " + id + " does not exist.");
    }
    
    /**
     * Performs the damage animation over the player's portrait.
     */
    public void gb_animateDamage() {
        timeStartAnimationDamage = System.currentTimeMillis();
        damageSprite.setVisible(true);
    }
    
    /**
     * Sets the player's portrait.
     * @param portraitFileName the file name of the image of the portrait
     */
    public void gb_setPortraitPlayer(String portraitFileName){
        ImageIcon i = getImageIcon(portraitFileName,jLabel2.getWidth(), jLabel2.getHeight());
        if(i != null)
            jLabel2.setIcon(i);
        else
            System.err.println("ERROR using gb_setPortraitPlayer: Image " + portraitFileName + " could not be loaded correctly.");
    }
    
    /**
     * Opens a new message dialog.
     * @param text text to show in the dialog
     */
    public void gb_showMessageDialog(String text){
        if(text != null)
            JOptionPane.showMessageDialog(this, text,"UC3M Game Board", JOptionPane.INFORMATION_MESSAGE);    
        else
            System.err.println("ERROR using gb_showMessageDialog: The provided text is null.");
    }
    
    /**
     * Sets the player's name in the GUI.
     * @param name the player's name
     */
    public void gb_setTextPlayerName(String name){
        if(name != null)
            jLabel3.setText(name);
        else
            System.err.println("ERROR using gb_setTextPlayerName: The provided name is null.");
    }
    
    /**
     * Sets the player's PointsUp Text in the GUI.
     * @param PointsUp the PointsUp label
     */
    public void gb_setTextPointsUp(String textPointsUp) {
        jLabel6.setText(textPointsUp);
    }
    
    /**
     * Sets the player's PointsUp value in the GUI.
     * @param PointsUp the player's PointsUp
     */
    public void gb_setValuePointsUp(int pointsUp) {
        jLabel14.setText(Integer.toString(pointsUp));
    }
       
    /**
     * Sets the player's PointsDown Text in the GUI.
     * @param PointsDown the PointsUp label
     */
    public void gb_setTextPointsDown(String textPointsDown) {
        jLabel5.setText(textPointsDown);
    }
    
    /**
     * Sets the player's PointsDown value in the GUI.
     * @param PointsDown the player's PointsDown
     */
    public void gb_setValuePointsDown(int pointsDown) {
        jLabel13.setText(Integer.toString(pointsDown));
        if(pointsDown == 0)
            jLabel13.setOpaque(true);
        else
            jLabel13.setOpaque(false);
    }

    /**
     * Sets the player's current health value in the GUI.
     * @param health the player's current health
     */
    public void gb_setValueHealthCurrent(int health){
        jLabel12.setText(health + " /"+jLabel12.getText().split("/")[1]);
        if(health < Integer.parseInt(jLabel12.getText().split("/")[1].trim())*0.25f)
            jLabel12.setOpaque(true);
        else
            jLabel12.setOpaque(false);
    }

    /**
     * Sets the player's maximum health value in the GUI.
     * @param health the player's max health
     */
    public void gb_setValueHealthMax(int health){
        jLabel12.setText(jLabel12.getText().split("/")[0] + "/ "+ health);
    }    

    /**
     * Sets the player's ability text in the GUI.
     * @param textAbility1 the textAbility1 label
     */    
    public void gb_setTextAbility1(String textAbility1){
        jLabel8.setText(textAbility1);
    }
    
    /**
     * Sets the player's strength value in the GUI.
     * @param strength the player's current strength
     */    
    public void gb_setValueAbility1(int ability1){
        jLabel15.setText(Integer.toString(ability1));
    }

   /**
     * Sets the player's Ability2 value in the GUI.
     * @param Ability2 the player's current Ability2
     */    
    public void gb_setTextAbility2(String textAbility2){
        jLabel10.setText(textAbility2);
    }
    
    /**
     * Sets the player's Ability2 value in the GUI.
     * @param Ability2 the player's current Ability2
     */    
    public void gb_setValueAbility2(int ability2){
        jLabel17.setText(Integer.toString(ability2));
    }

    /**
     * Sets the Level text in the GUI.
     * @param textLevel the Level label
     */
    public void gb_setTextLevel(String textLevel){
        jLabel17.setText(textLevel);
    }
    
    /**
     * Sets the Level number in the GUI.
     * @param level the Level number
     */
    public void gb_setValueLevel(int level){
        jLabel11.setText(Integer.toString(level));
    }
    
    /**
     * Clears the console and the command bar.
     */
    public void gb_clearConsole(){
        jTextArea2.setText("");
        jTextField1.setText("");
    }
    
    /**
     * Clears the command bar.
     */
    public void gb_clearCommandBar(){
        jTextField1.setText("");
    }
    
    /**
     * Removes all sprites of the board.
     */
    public void gb_clearSprites(){  
        //TODO Si el jugador se mueve y se hace esto muy rapido, al estarlo dibujando la interfaz, no puede hacer el remove. Arreglar.
        Iterator<Integer> it = sprites.keySet().iterator();
        while (it.hasNext()) 
            jLayeredPane1.remove(sprites.get((int)it.next()));
        
        sprites.clear();
    }
    
    /**
     * Forces the board to be repainted.
     */
    public void gb_repaintBoard(){        
        jLayeredPane1.repaint();
    }
        
    /**
     * Prints a text in the console of the GUI.
     * @param text the text to be printed
     */
    public void gb_println(String text){
        if(text != null) {
            if(jTextArea2.getText().length() == 0)
                jTextArea2.setText(jTextArea2.getText()+text);
            else
                jTextArea2.setText(jTextArea2.getText()+"\n"+text);

            jTextArea2.setCaretPosition(jTextArea2.getText().length());
        }
        else
            System.err.println("ERROR using gb_println: The provided text is null.");
    }
    
    private void preloadImages() {
        try {
            File[] imgFolder = new File("images/").listFiles();
            if (imgFolder == null){
                System.err.println("ERROR: folder \"images\" not found in the root directory of the game.");
            }
            else{
                for(int i = 0; i < imgFolder.length; i++){
                    if(imgFolder[i].isFile()){
                        String ruta = "images/" + imgFolder[i].getName();
                        BufferedImage img = ImageIO.read(new File(ruta));

                        if (img == null) {                    
                            System.err.println("ERROR: A file in the images folder could not be loaded as an image: "+ ruta);  
                        }
                        else {
                        	System.out.println("Loading image: "+ imgFolder[i].getName());
                            //Image image = img.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);
                            images.put(imgFolder[i].getName(), img);
                            int spriteWidth = jLayeredPane1.getWidth()/boardWidth;
                            int spriteHeight = jLayeredPane1.getHeight()/boardHeight;
                            imageIcons.put(imgFolder[i].getName(), new ImageIcon(getScaledInstance(img, spriteWidth, spriteHeight, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true)));
                            
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private ImageIcon getImageIcon(String fileName, int width, int height){
        int spriteWidth = jLayeredPane1.getWidth()/boardWidth;
        int spriteHeight = jLayeredPane1.getHeight()/boardHeight;
        
        if(width == spriteWidth && height == spriteHeight)
            return imageIcons.get(fileName);
        else {
            BufferedImage i = getScaledInstance(images.get(fileName), width, height, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true); 
            if (i != null)
                return new ImageIcon(i);
            else
                return null;
        }
    }
    
    private boolean keyboardControl(KeyEvent e){  
        boolean consumed = false;        
        
        if(e.getID() == KeyEvent.KEY_PRESSED){
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                lastAction = "left";
                consumed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                lastAction = "right";
                consumed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP){
                lastAction = "up";
                consumed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                lastAction = "down";
                consumed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                lastAction = "space";
                consumed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_TAB) {
                lastAction = "tab";
                consumed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                lastAction = "command "+ jTextField1.getText();
                consumed = true;
            }
        }
        
        return consumed;
    }
    
    private BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        if (img == null)
            return null;
        
        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = targetWidth<img.getWidth()?img.getWidth():targetWidth;
            h = targetHeight<img.getHeight()?img.getHeight():targetHeight;
        }
        else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth && h != targetHeight);

        return ret;
    }
    
    /**
     * Creates a new MiniDungeon GUI.
     * @param boardWidth squares of width
     * @param boardHeight squares of heigth
     */
    public GameBoardGUI(int boardWidth, int boardHeight) {
        initComponents();
        initializeGUI(boardWidth, boardHeight);
        lastAction = "";
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {   
                return keyboardControl(e);
            }
        });
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLayeredPane1 = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MiniDungeon - Universidad Carlos III de Madrid");
        setResizable(false);

        jButton1.setText("New");
        jButton1.setToolTipText("New game");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Exit");
        jButton2.setToolTipText("Exit program");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        
        jLabel2.setText("Portrait");
        jLabel2.setMaximumSize(new java.awt.Dimension(100, 157));
        jLabel2.setMinimumSize(new java.awt.Dimension(100, 157));
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 157));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Health");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("PointsDown");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("PointsUp");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Ability1");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Ability2");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Level");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel11.setText("0");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("10 / 10");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("100");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("0");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("1");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("1");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(15);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jScrollPane1.setViewportView(jTextArea2);

        jLayeredPane3.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jScrollPane1)
                    .addGroup(jLayeredPane3Layout.createSequentialGroup()
                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jLayeredPane3Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jLayeredPane3Layout.createSequentialGroup()
                                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            	.addGap(84, 84, 84))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(5, 5, 5))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(89, 89, 89))))))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLayeredPane3Layout.createSequentialGroup()
                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel17))
                        .addGap(6, 6, 6))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(38, 38, 38)
                .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)       
                		.addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                		.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(650, 650));

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLayeredPane2.setLayer(jLayeredPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLayeredPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    	Object result = JOptionPane.showInputDialog(this, "New game. Enter player name:","UC3M Game Board",JOptionPane.QUESTION_MESSAGE);
    	if(result != null){
    		lastAction = "new game " + result;
    	}
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    	int result = JOptionPane.showConfirmDialog(this,"Exit game. Are you sure you want to quit?","Exit Game",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
    	if(result == JOptionPane.YES_OPTION){
    		lastAction = "exit game";
    	}
    }//GEN-LAST:event_jButton2ActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
