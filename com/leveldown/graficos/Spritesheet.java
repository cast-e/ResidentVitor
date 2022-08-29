package com.leveldown.graficos;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Spritesheet {
   private BufferedImage spritesheet;

   public Spritesheet(String path) {
      try {
         this.spritesheet = ImageIO.read(this.getClass().getResource(path));
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public BufferedImage getSprite(int x, int y, int windth, int heigth) {
      return this.spritesheet.getSubimage(x, y, windth, heigth);
   }
}