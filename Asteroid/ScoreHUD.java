import greenfoot.*;

/** Muestra el SCORE en la esquina superior izquierda (score.png + número). */
public class ScoreHUD extends Actor {
    private final int panelWidth  = 200;
    private final int panelHeight = 40;
    private final int padding     = 8;

    private final int scoreIconH  = 20; 
    private GreenfootImage scoreIcon;

    private int lastScore = -1;

    public ScoreHUD() {
        
        GreenfootImage raw = new GreenfootImage("score.png");
        int sw = raw.getWidth(), sh = raw.getHeight();
        int targetW = Math.max(1, (int)Math.round((double) sw * scoreIconH / sh));
        scoreIcon = new GreenfootImage(raw);
        scoreIcon.scale(targetW, scoreIconH);

        redraw(0);
    }

    public void act() {
        GameWorld gw = (GameWorld) getWorld();
        if (gw == null) return;

        int score = gw.getScore();
        if (score != lastScore) {
            lastScore = score;
            redraw(score);
        }
    }

    private void redraw(int score) {
       
        GreenfootImage img = new GreenfootImage(panelWidth, panelHeight);
        GreenfootImage scoreText = new GreenfootImage(String.valueOf(score), 22, Color.WHITE, new Color(0,0,0,0));

        int x = padding;
        int yIcon = (panelHeight - scoreIcon.getHeight()) / 2;
        int yText = (panelHeight - scoreText.getHeight()) / 2;

        img.drawImage(scoreIcon, x, yIcon);
        img.drawImage(scoreText, x + scoreIcon.getWidth() + 6, yText);

        setImage(img);
    }
}
