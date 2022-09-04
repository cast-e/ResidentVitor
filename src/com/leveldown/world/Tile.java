package src.com.leveldown.world;

import src.com.leveldown.main.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Tile {
   public static BufferedImage TILE_FLOOR;
   public static BufferedImage TILE_WALL;
   public static BufferedImage TILE_FLOOR2;
   public static BufferedImage TILE_WALL2;
   public static BufferedImage TILE_FLOOR3;
   public static BufferedImage TILE_WALL3;
   public static BufferedImage TILE_FLOOR4;
   public static BufferedImage TILE_WALL4;
   public static BufferedImage TILE_FLOOR5;
   public static BufferedImage TILE_WALL5;
   public static BufferedImage TILE_FLOOR6;
   public static BufferedImage TILE_WALL6;
   private BufferedImage sprite;
   private int x;
   private int y;

   static {
      TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
      TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
      TILE_FLOOR2 = Game.spritesheet.getSprite(0, 48, 16, 16);
      TILE_WALL2 = Game.spritesheet.getSprite(16, 48, 16, 16);
      TILE_FLOOR3 = Game.spritesheet.getSprite(0, 64, 16, 16);
      TILE_WALL3 = Game.spritesheet.getSprite(16, 64, 16, 16);
      TILE_FLOOR4 = Game.spritesheet.getSprite(0, 80, 16, 16);
      TILE_WALL4 = Game.spritesheet.getSprite(16, 80, 16, 16);
      TILE_FLOOR5 = Game.spritesheet.getSprite(0, 96, 16, 16);
      TILE_WALL5 = Game.spritesheet.getSprite(16, 96, 16, 16);
      TILE_FLOOR6 = Game.spritesheet.getSprite(0, 128, 16, 16);
      TILE_WALL6 = Game.spritesheet.getSprite(16, 128, 16, 16);
   }

   public Tile(int x, int y, BufferedImage sprite) {
      this.x = x;
      this.y = y;
      this.sprite = sprite;
   }

   public void render(Graphics g) {
      g.drawImage(this.sprite, this.x - Camera.x, this.y - Camera.y, (ImageObserver)null);
   }
}