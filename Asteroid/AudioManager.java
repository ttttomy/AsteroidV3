import greenfoot.*;

public class AudioManager {
    
    public static final GreenfootSound MENU_MUSIC = 
        new GreenfootSound("Kevin MacLeod - 8bit Dungeon Level  NO COPYRIGHT 8-bit Music.mp3");
    public static final GreenfootSound GAMEOVER_MUSIC = 
        new GreenfootSound("Game Over (8-Bit Music).mp3");

    static {
        // Volumen por defecto
        MENU_MUSIC.setVolume(30);
        GAMEOVER_MUSIC.setVolume(30);
    }

    public static void stopAll() {
        MENU_MUSIC.stop();
        GAMEOVER_MUSIC.stop();
    }
}
