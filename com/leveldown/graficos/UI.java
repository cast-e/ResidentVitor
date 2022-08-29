package com.leveldown.graficos;

import com.leveldown.entity.Player;
import com.leveldown.main.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class UI {
   public void render(Graphics g) {
	  Graphics2D g2 = (Graphics2D)g;
	  g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
	    		  		  RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
      g.setColor(Color.red);
      g.fillRect(8, 4, 50, 8);
      g.setColor(Color.green);
      g.fillRect(8, 4, (int)(Game.player.life / Game.player.maxLife * 50.0D), 8);
      g.setColor(Color.black);
      g2.setFont(new Font("arial", 1, 10));
      g.drawString("+ " + (int)Game.player.life + "/" + (int)Game.player.maxLife, 11, 11);
      g.setColor(Color.red);
      g.fillRect(80, 4, 50, 8);
      g.setColor(Color.yellow);
      g.fillRect(80, 4, (int)(Player.ammo / Player.maxAmmo * 50.0D), 8);
      g.setColor(Color.black);
      g2.setFont(new Font("arial", 1, 10));
      g.drawString("⁍ " + (int)Player.ammo + "/" + (int)Player.maxAmmo, 80, 11);
      g.setColor(Color.black);
      g2.setFont(new Font("arial", 1, 10));
      g.drawString("FPS: " + Game.FPS, 160, 11);
      g.setColor(Color.white);
      g2.setFont(new Font("arial", 1, 11));
      g.drawString("FPS: " + Game.FPS, 160, 13);
      if (Game.gameState == "GAME_OVER") {
         g.setColor(Color.red);
         g2.setFont(new Font("arial", 1, 16));
         g.drawString("G a m e O v e r...", 70, 80);
         g2.setFont(new Font("arial", 1, 12));
         if (Game.showMessageGameOver) {
            g.drawString(">>> Pressione Enter para reiniciar <<<", 10, 120);
         }
      }

   }
}