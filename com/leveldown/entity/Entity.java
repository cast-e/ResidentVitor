package com.leveldown.entity;

import com.leveldown.main.Game;
import com.leveldown.world.Camera;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Entity {
   public static BufferedImage LIFEPACK_EN;
   public static BufferedImage WEAPON_EN;
   public static BufferedImage BULLET_EN;
   public static BufferedImage ENEMY_EN;
   public static BufferedImage ENEMY_EN2;
   public static BufferedImage GUN_RIGHT;
   public static BufferedImage GUN_LEFT;
   protected double x;
   protected double y;
   protected int width;
   protected int heigth;
   protected BufferedImage sprite;
   private int maskx;
   private int masky;
   private int mwidth;
   private int mheigth;

   static {
      LIFEPACK_EN = Game.spritesheet.getSprite(96, 0, 16, 16);
      WEAPON_EN = Game.spritesheet.getSprite(112, 0, 16, 16);
      BULLET_EN = Game.spritesheet.getSprite(96, 16, 16, 16);
      ENEMY_EN = Game.spritesheet.getSprite(112, 16, 16, 16);
      ENEMY_EN2 = Game.spritesheet.getSprite(112, 32, 16, 16);
      GUN_RIGHT = Game.spritesheet.getSprite(128, 0, 16, 16);
      GUN_LEFT = Game.spritesheet.getSprite(144, 0, 16, 16);
   }

   public Entity(int x, int y, int width, int heigth, BufferedImage sprite) {
      this.setX((double)x);
      this.setY((double)y);
      this.width = width;
      this.heigth = heigth;
      this.sprite = sprite;
      this.maskx = 0;
      this.masky = 0;
      this.mwidth = width;
      this.mheigth = heigth;
   }

   public void setMask(int maskx, int masky, int mwidth, int mheight) {
      this.maskx = maskx;
      this.masky = masky;
      this.mwidth = mwidth;
      this.mheigth = mheight;
   }

   public int getX() {
      return (int)this.x;
   }

   public int getY() {
      return (int)this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeigth() {
      return this.heigth;
   }

   public void tick() {
   }

   public static boolean isColidding(Entity e1, Entity e2) {
      Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheigth);
      Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheigth);
      return e1Mask.intersects(e2Mask);
   }

   public void render(Graphics g) {
      g.drawImage(this.sprite, this.getX() - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }
}