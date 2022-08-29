package com.leveldown.entity;

import com.leveldown.main.Game;
import com.leveldown.world.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class BulletShoot extends Entity {
   private int dx;
   private int dy;
   private double speed = 4.0D;
   private int life = 50;
   private int curLife = 0;

   public BulletShoot(int x, int y, int width, int heigth, BufferedImage sprite, int dx, int dy) {
      super(x, y, width, heigth, sprite);
      this.dx = dx;
      this.dy = dy;
   }

   public void tick() {
      this.x += (double)this.dx * this.speed;
      this.y += (double)this.dy * this.speed;
      ++this.curLife;
      if (this.curLife == this.life) {
         Game.bullets.remove(this);
      }
   }

   public void render(Graphics g) {
      g.setColor(Color.YELLOW);
      g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
   }
}