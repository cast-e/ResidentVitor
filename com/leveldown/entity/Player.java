package com.leveldown.entity;

import com.leveldown.main.Game;
import com.leveldown.main.Sound;
import com.leveldown.world.Camera;
import com.leveldown.world.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Player extends Entity {
   public boolean right;
   public boolean up;
   public boolean left;
   public boolean down;
   public int right_dir = 0;
   public int left_dir = 1;
   public int dir;
   public double speed;
   private int frames;
   private int maxFrames;
   private int index;
   private int maxIndex;
   private boolean moved;
   private BufferedImage[] rightPlayer;
   private BufferedImage[] leftPlayer;
   private BufferedImage playerDamage;
   private BufferedImage playerDamageGunRight;
   private BufferedImage playerDamageGunLeft;
   private BufferedImage playerDamageRed;
   private BufferedImage playerDamageGunRightRed;
   private BufferedImage playerDamageGunLeftRed;
   public boolean arma;
   public static double ammo = 0.0D;
   public static double maxAmmo = 100.0D;
   public boolean isDamaged;
   private int damageFrame;
   public boolean shoot;
   public double life;
   public double maxLife;

   public Player(int x, int y, int width, int heigth, BufferedImage sprite) {
      super(x, y, width, heigth, sprite);
      this.dir = this.right_dir;
      this.speed = 1.4D;
      this.frames = 0;
      this.maxFrames = 5;
      this.index = 3;
      this.maxIndex = 3;
      this.moved = false;
      this.arma = false;
      this.isDamaged = false;
      this.damageFrame = 0;
      this.shoot = false;
      this.life = 100.0D;
      this.maxLife = 100.0D;
      this.rightPlayer = new BufferedImage[4];
      this.leftPlayer = new BufferedImage[4];
      this.playerDamage = Game.spritesheet.getSprite(0, 16, 16, 16);
      this.playerDamageGunRight = Game.spritesheet.getSprite(128, 32, 16, 16);
      this.playerDamageGunLeft = Game.spritesheet.getSprite(144, 32, 16, 16);
      this.playerDamageRed = Game.spritesheet.getSprite(0, 32, 16, 16);
      this.playerDamageGunRightRed = Game.spritesheet.getSprite(128, 48, 16, 16);
      this.playerDamageGunLeftRed = Game.spritesheet.getSprite(144, 48, 16, 16);

      int i;
      for(i = 0; i < 4; ++i) {
         this.rightPlayer[i] = Game.spritesheet.getSprite(32 + i * 16, 0, 16, 16);
      }

      for(i = 0; i < 4; ++i) {
         this.leftPlayer[i] = Game.spritesheet.getSprite(32 + i * 16, 16, 16, 16);
      }

   }

   public void tick() {
      this.checkMovimento();
      this.checkVida();
      this.checkCollisionLifePack();
      this.checkCollisionGun();
      this.checkCollisionAmmo();
      this.checkDamage();
      this.checkAtirar();
      this.centralizarCamera();
   }

   private void checkAtirar() {
      if (this.shoot) {
         this.shoot = false;
         if (this.arma && ammo > 0.0D) {
            --ammo;
            //int dx = false;
            //int px = false;
            int py = 7;
            byte dx;
            byte px;
            if (this.dir == this.right_dir) {
               px = 18;
               dx = 1;
            } else {
               px = -8;
               dx = -1;
            }

            BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, (BufferedImage)null, dx, 0);
            Game.bullets.add(bullet);
            Sound.shootEffect.play();
         }
      }

   }

   private void checkMovimento() {
      this.moved = false;
      if (this.right && World.isFree((int)(this.x + this.speed), this.getY())) {
         this.moved = true;
         this.dir = this.right_dir;
         this.setX(this.x + this.speed);
      } else if (this.left && World.isFree((int)(this.x - this.speed), this.getY())) {
         this.moved = true;
         this.dir = this.left_dir;
         this.setX(this.x - this.speed);
      }

      if (this.up && World.isFree(this.getX(), (int)(this.y - this.speed))) {
         this.moved = true;
         this.setY(this.y - this.speed);
      } else if (this.down && World.isFree(this.getX(), (int)(this.y + this.speed))) {
         this.moved = true;
         this.setY(this.y + this.speed);
      }

      if (this.moved) {
         ++this.frames;
         if (this.frames == this.maxFrames) {
            this.frames = 0;
            ++this.index;
            if (this.index > this.maxIndex) {
               this.index = 0;
            }
         }
      }

   }

   public void centralizarCamera() {
      Camera.x = Camera.clamp(this.getX() - 120, 0, World.WIDTH * 16 - 240);
      Camera.y = Camera.clamp(this.getY() - 80, 0, World.HEIGHT * 16 - 160);
   }

   public void checkVida() {
      if (this.life <= 0.0D) {
         this.life = 0.0D;
         Game.gameState = "GAME_OVER";
         this.arma = false;
         ammo = 0.0D;
      }

   }

   public void checkCollisionGun() {
      for(int i = 0; i < Game.entities.size(); ++i) {
         Entity e = (Entity)Game.entities.get(i);
         if (e instanceof Weapon && Entity.isColidding(this, e)) {
            this.arma = true;
            Game.entities.remove(e);
         }
      }

   }

   public void checkCollisionAmmo() {
      if (ammo < 100.0D) {
         for(int i = 0; i < Game.entities.size(); ++i) {
            Entity e = (Entity)Game.entities.get(i);
            if (e instanceof Bullet && Entity.isColidding(this, e)) {
               Sound.pegarEffect.play();
               ammo += 10.0D;
               if (ammo >= 100.0D) {
                  ammo = 100.0D;
               }

               Game.entities.remove(e);
            }
         }
      }

   }

   public void checkCollisionLifePack() {
      if (this.life < 100.0D) {
         for(int i = 0; i < Game.entities.size(); ++i) {
            Entity e = (Entity)Game.entities.get(i);
            if (e instanceof Lifepack && Entity.isColidding(this, e)) {
               Sound.pegarEffect.play();
               this.life += 10.0D;
               if (this.life >= 100.0D) {
                  this.life = 100.0D;
               }

               Game.entities.remove(i);
               return;
            }
         }
      }

   }

   public void checkDamage() {
      if (this.isDamaged) {
         ++this.damageFrame;
         if (this.damageFrame == 6) {
            this.damageFrame = 0;
            this.isDamaged = false;
         }
      }

   }

   public void render(Graphics g) {
      if (!this.isDamaged) {
         if (this.dir == this.right_dir) {
            g.drawImage(this.rightPlayer[this.index], this.getX() - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
            if (this.arma) {
               g.drawImage(Entity.GUN_RIGHT, this.getX() + 10 - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
            }
         } else if (this.dir == this.left_dir) {
            g.drawImage(this.leftPlayer[this.index], this.getX() - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
            if (this.arma) {
               g.drawImage(Entity.GUN_LEFT, this.getX() - 11 - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
            }
         }
      } else {
         if (this.damageFrame > 3) {
            g.drawImage(this.playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
         } else {
            g.drawImage(this.playerDamageRed, this.getX() - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
         }

         if (this.dir == this.right_dir) {
            if (this.arma) {
               if (this.damageFrame > 3) {
                  g.drawImage(this.playerDamageGunRight, this.getX() + 10 - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
               } else {
                  g.drawImage(this.playerDamageGunRightRed, this.getX() + 10 - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
               }
            }
         } else if (this.dir == this.left_dir && this.arma) {
            if (this.damageFrame > 3) {
               g.drawImage(this.playerDamageGunLeft, this.getX() - 11 - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
            } else {
               g.drawImage(this.playerDamageGunLeftRed, this.getX() - 11 - Camera.x, this.getY() - Camera.y, (ImageObserver)null);
            }
         }
      }

   }
}