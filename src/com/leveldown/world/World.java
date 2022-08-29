package src.com.leveldown.world;import src.com.leveldown.entity.Bullet;import src.com.leveldown.entity.Enemy;import src.com.leveldown.entity.Entity;import src.com.leveldown.entity.Lifepack;import src.com.leveldown.entity.Player;import src.com.leveldown.entity.Weapon;import src.com.leveldown.graficos.Spritesheet;import src.com.leveldown.main.Game;import java.awt.Graphics;import java.awt.image.BufferedImage;import java.io.IOException;import java.util.ArrayList;import javax.imageio.ImageIO;public class World {   public static Tile[] tiles;   public static int WIDTH;   public static int HEIGHT;   public static final int TILE_SIZE = 16;   public World(String path) {      try {         BufferedImage map = ImageIO.read(this.getClass().getResource(path));         int[] pixels = new int[map.getWidth() * map.getHeight()];         WIDTH = map.getWidth();         HEIGHT = map.getHeight();         tiles = new Tile[map.getWidth() * map.getHeight()];         map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());         for(int xx = 0; xx < map.getWidth(); ++xx) {            for(int yy = 0; yy < map.getHeight(); ++yy) {               int pixelAtual = pixels[xx + yy * map.getWidth()];               if (Game.CUR_LEVEL == 1) {                  tiles[xx + yy * WIDTH] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);               } else if (Game.CUR_LEVEL == 2) {                  tiles[xx + yy * WIDTH] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR2);               } else if (Game.CUR_LEVEL == 3) {                  tiles[xx + yy * WIDTH] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR3);               }               if (pixelAtual == -16777216) {                  if (Game.CUR_LEVEL == 1) {                     tiles[xx + yy * WIDTH] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);                  } else if (Game.CUR_LEVEL == 2) {                     tiles[xx + yy * WIDTH] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR2);                  } else if (Game.CUR_LEVEL == 3) {                     tiles[xx + yy * WIDTH] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR3);                  }               } else if (pixelAtual == -1) {                  if (Game.CUR_LEVEL == 1) {                     tiles[xx + yy * WIDTH] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);                  } else if (Game.CUR_LEVEL == 2) {                     tiles[xx + yy * WIDTH] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL2);                  } else if (Game.CUR_LEVEL == 3) {                     tiles[xx + yy * WIDTH] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL3);                  }               } else if (pixelAtual == -16767233) {                  Game.player.setX((double)(xx * 16));                  Game.player.setY((double)(xx * 16));               } else if (pixelAtual == -65536) {                  Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN);                  Game.entities.add(en);                  Game.enemies.add(en);               } else if (pixelAtual == -38400) {                  Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));               } else if (pixelAtual == -32897) {                  Lifepack pack = new Lifepack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN);                  pack.setMask(8, 8, 8, 8);                  Game.entities.add(pack);               } else if (pixelAtual == -10240) {                  Game.entities.add(new Bullet(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN));               }            }         }      } catch (IOException var8) {         var8.printStackTrace();      }   }   public static boolean isFree(int xnext, int ynext) {      int x1 = xnext / 16;      int y1 = ynext / 16;      int x2 = (xnext + 16 - 1) / 16;      int y2 = ynext / 16;      int x3 = xnext / 16;      int y3 = (ynext + 16 - 1) / 16;      int x4 = (xnext + 16 - 1) / 16;      int y4 = (ynext + 16 - 1) / 16;      return !(tiles[x1 + y1 * WIDTH] instanceof WallTile) && !(tiles[x2 + y2 * WIDTH] instanceof WallTile) && !(tiles[x3 + y3 * WIDTH] instanceof WallTile) && !(tiles[x4 + y4 * WIDTH] instanceof WallTile);   }   public static void restartGame(String level) {      boolean tinhaArma = Game.player.arma;      Game.entities = new ArrayList<Entity>();      Game.enemies = new ArrayList<Enemy>();      Game.spritesheet = new Spritesheet("/res/spritesheet.png");      Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));      if (tinhaArma) {         Game.player.arma = tinhaArma;      }      Game.entities.add(Game.player);      Game.world = new World("/res/levels/" + level);   }   public void render(Graphics g) {      int xstart = Camera.x >> 4;      int ystart = Camera.y >> 4;      int xfinal = xstart + 15;      int yfinal = ystart + 10;      for(int xx = xstart; xx <= xfinal; ++xx) {         for(int yy = ystart; yy <= yfinal; ++yy) {            if (xx >= 0 && yy >= 0 && xx < WIDTH && yy < HEIGHT) {               Tile tile = tiles[xx + yy * WIDTH];               tile.render(g);            }         }      }   }}