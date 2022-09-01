package src.com.leveldown.main;import src.com.leveldown.entity.BulletShoot;import src.com.leveldown.entity.Enemy;import src.com.leveldown.entity.Entity;import src.com.leveldown.entity.Player;import src.com.leveldown.graficos.Spritesheet;import src.com.leveldown.graficos.UI;import src.com.leveldown.world.World;import java.awt.Canvas;import java.awt.Color;import java.awt.Component;import java.awt.Dimension;import java.awt.Graphics;import java.awt.Graphics2D;import java.awt.event.KeyEvent;import java.awt.event.KeyListener;import java.awt.image.BufferStrategy;import java.awt.image.BufferedImage;import java.awt.image.ImageObserver;import java.io.File;import java.util.ArrayList;import java.util.Calendar;import java.util.Date;import java.util.List;import java.util.Random;import javax.swing.JFrame;public class Game extends Canvas implements Runnable, KeyListener {   private static final long serialVersionUID = 1L;   public static JFrame frame;   private Thread thread;   private boolean isRunning = true;   public static final int WIDTH = 720;   public static final int HEIGTH = 480;   public static final int SCALE = 3;   public static int FPS = 0;   public static int CUR_LEVEL = 1;   public static int MAX_LEVEL = 3;   private BufferedImage image;   public static List<Entity> entities;   public static List<Enemy> enemies;   public static List<BulletShoot> bullets;   public static Spritesheet spritesheet;   public static World world;   public static Player player;   public static Random rand;   public static String gameState = "MENU";   public static boolean showMessageGameOver = true;   public static boolean gameSaved = false;   public static boolean gameLoaded = false;   public boolean loadGame = false;   public boolean saveGame = false;   private int framesGameOver = 0;   private boolean restartGame = false;   public Menu menu;   public UI ui;      private Date datefuturo;   public Game() {      Sound.musicBackground.loop();      rand = new Random();      this.addKeyListener(this);      this.setPreferredSize(new Dimension(WIDTH, HEIGTH));      this.initFrame();      this.ui = new UI();      this.image = new BufferedImage(240, 160, 1);      entities = new ArrayList<Entity>();      enemies = new ArrayList<Enemy>();      bullets = new ArrayList<BulletShoot>();      spritesheet = new Spritesheet("/res/spritesheet.png");      player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));      entities.add(player);      world = new World("/res/levels/level1.png");      this.menu = new Menu();   }   public Dimension initFrame() {      frame = new JFrame("Resident Vitor");      frame.add(this);      frame.setResizable(false);      frame.pack();      frame.setLocationRelativeTo((Component)null);      frame.setDefaultCloseOperation(3);      frame.setVisible(true);      return frame.getSize();   }   public synchronized void start() {      this.thread = new Thread(this);      this.isRunning = true;      this.thread.start();   }   public synchronized void stop() {      this.isRunning = false;      try {         this.thread.join();      } catch (Exception e) {         e.printStackTrace();      }   }   public static void main(String[] args) {      Game game = new Game();      game.start();   }   private void tick() {      if (gameState == "NORMAL") {    	      	  Date dateatual = new Date();    	      	  if (gameLoaded) {    		  if (dateatual.getTime() > datefuturo.getTime())    			  gameLoaded = false;    	  }    	      	  if(this.loadGame) {    		  gameLoaded = true;    		  Menu.file = new File("save.txt");        	  if(Menu.file.exists()) {        		  String saver = Menu.loadGame(10);        		  Menu.applySave(saver);        	  }    		     		  datefuturo = dateatual;     		      		  Calendar cal = Calendar.getInstance();    		  cal.setTime(datefuturo);    		  cal.add(Calendar.SECOND, 1);    		  datefuturo = cal.getTime();    		      		  this.loadGame = false;    		      	  }    	      	  if (gameSaved) {    		  if (dateatual.getTime() > datefuturo.getTime())    			  gameSaved = false;    	  }    	      	  if(this.saveGame) {    		  gameSaved = true;    		  String[] opt1 = {"level"};    		  @SuppressWarnings("static-access")    		  int[] opt2 = {this.CUR_LEVEL};    		  Menu.saveGame(opt1, opt2, 10);    		     		  datefuturo = dateatual;     		      		  Calendar cal = Calendar.getInstance();    		  cal.setTime(datefuturo);    		  cal.add(Calendar.SECOND, 1);    		  datefuturo = cal.getTime();    		      		  this.saveGame = false;    		      	  }    	      	            if(loadGame) {        	  Menu.file = new File("save.txt");        	  if(Menu.file.exists()) {        		  String saver = Menu.loadGame(10);        		  Menu.applySave(saver);        	  }        	  loadGame = false;          }			    	          this.restartGame = false;         int i;         for(i = 0; i < entities.size(); ++i) {            Entity e = (Entity)entities.get(i);            e.tick();         }         for(i = 0; i < bullets.size(); ++i) {            ((BulletShoot)bullets.get(i)).tick();         }         if (enemies.size() == 0) {            ++CUR_LEVEL;            if (CUR_LEVEL > MAX_LEVEL) {               CUR_LEVEL = 1;            }            String newWorld = "level" + CUR_LEVEL + ".png";            World.restartGame(newWorld);         }      } else if (gameState == "GAME_OVER") {         ++this.framesGameOver;         if (this.framesGameOver == 15) {            this.framesGameOver = 0;            showMessageGameOver = !showMessageGameOver;         }         if (this.restartGame) {            gameState = "NORMAL";            World.restartGame("level" + CUR_LEVEL + ".png");         }      } else if (gameState == "MENU") {         this.menu.tick();      }   }   private void render() {      BufferStrategy bs = this.getBufferStrategy();      if (bs == null) {         this.createBufferStrategy(3);      } else {         Graphics g = this.image.getGraphics();         g.setColor(new Color(0, 0, 0));         g.fillRect(0, 0, 240, 160);         world.render(g);         int i;         for(i = 0; i < entities.size(); ++i) {            Entity e = (Entity)entities.get(i);            e.render(g);         }         for(i = 0; i < bullets.size(); ++i) {            ((BulletShoot)bullets.get(i)).render(g);         }         if (gameState != "MENU") {            this.ui.render(g);         }                  if (gameState == "MENU") {            Menu.render(g);         }         g.dispose();         g = bs.getDrawGraphics();         g.drawImage(this.image, 0, 0, 720, 480, (ImageObserver)null);         if (gameState == "GAME_OVER" || gameState == "PAUSE") {            Graphics2D g2 = (Graphics2D)g;            g2.setColor(new Color(0, 0, 0, 100));            g2.fillRect(0, 0, 720, 480);         }         bs.show();      }   }   public void run() {      long lastTime = System.nanoTime();      double amountOfTicks = 60.0D;      double ns = 1.0E9D / amountOfTicks;      double delta = 0.0D;      int frames = 0;      double timer = (double)System.currentTimeMillis();      this.requestFocus();      while(this.isRunning) {         long now = System.nanoTime();         delta += (double)(now - lastTime) / ns;         lastTime = now;         if (delta >= 1.0D) {            this.tick();            this.render();            ++frames;            --delta;         }         if ((double)System.currentTimeMillis() - timer >= 1000.0D) {            FPS = frames;            frames = 0;            timer += 1000.0D;         }      }      this.stop();   }   public void keyTyped(KeyEvent e) {   }   public void keyPressed(KeyEvent e) {      if (e.getKeyCode() != KeyEvent.VK_RIGHT && e.getKeyCode() != KeyEvent.VK_D) {         if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {            player.left = true;         }      } else {         player.right = true;      }      if (e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_W) {         if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {            player.down = true;            if (gameState == "MENU") {               this.menu.down = true;            }         }      } else {         player.up = true;         if (gameState == "MENU") {            this.menu.up = true;         }      }      if (e.getKeyCode() == KeyEvent.VK_SPACE) {         player.shoot = true;      }      if (e.getKeyCode() == KeyEvent.VK_ENTER) {         this.restartGame = true;         if (gameState == "MENU") {            this.menu.enter = true;         }      }      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {         gameState = "MENU";         Menu.pause = true;      }            if (e.getKeyCode() == KeyEvent.VK_I) {    	  if(gameState == "NORMAL")    		  this.saveGame = true;      }            if (e.getKeyCode() == KeyEvent.VK_O) {    	  if(gameState == "NORMAL")    		  loadGame = true;      }   }   public void keyReleased(KeyEvent e) {	   if (e.getKeyCode() != KeyEvent.VK_RIGHT && e.getKeyCode() != KeyEvent.VK_D) {		   if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {			   player.left = false;         }      } else {         player.right = false;      }	   if (e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_W) {		   if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {			   player.down = false;         }      } else {         player.up = false;      }   }}