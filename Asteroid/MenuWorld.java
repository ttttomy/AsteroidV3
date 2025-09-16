import greenfoot.*;

public class MenuWorld extends World
{
    public MenuWorld()
    {    
        super(900, 700, 1); 
        prepare();
    }

    private void prepare()
    {
        Logo logo = new Logo();
        addObject(logo, 450, 350);

        enter enter = new enter();
        addObject(enter, 450, 400);
    }

    public void started()
    {
        AudioManager.GAMEOVER_MUSIC.stop();

        if (!AudioManager.MENU_MUSIC.isPlaying()) {
            AudioManager.MENU_MUSIC.setVolume(30);
            AudioManager.MENU_MUSIC.playLoop();
        }
    }

    public void stopped()
    {
        // AudioManager.MENU_MUSIC.stop();
    }
}
