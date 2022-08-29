package src.com.leveldown.main;

import java.applet.Applet;
import java.applet.AudioClip;

@SuppressWarnings("deprecation")
public class Sound {
   private AudioClip clip;
   public static final Sound musicBackground = new Sound("/res/sounds/music.wav");
   public static final Sound hurtEffect = new Sound("/res/sounds/hurt.wav");
   public static final Sound shootEffect = new Sound("/res/sounds/shoot.wav");
   public static final Sound pegarEffect = new Sound("/res/sounds/pegar.wav");

private Sound(String name) {
      try {
         this.clip = Applet.newAudioClip(Sound.class.getResource(name));
      } catch (Throwable var3) {
      }

   }

   public void play() {
      try {
         (new Thread() {
			public void run() {
               Sound.this.clip.play();
            }
         }).start();
      } catch (Throwable var2) {
      }

   }

   public void loop() {
      try {
         (new Thread() {
			public void run() {
               Sound.this.clip.loop();
            }
         }).start();
      } catch (Throwable var2) {
      }

   }
}