import greenfoot.*;

public class YouWinWorld extends World {

    private Actor titleActor;
    private Actor scoreActor;

    private GreenfootImage youWinImg;
    private int frameCount = 0;

    private static final int TITLE_X_OFFSET = 180;

    private GreenfootSound victory;

    public YouWinWorld(int finalScore) {
        super(900, 700, 1);

        AudioManager.stopAll();

        victory = new GreenfootSound("8 Bit Victory Non Copyright Sound Effect  NCSFX.mp3");
        victory.setVolume(30);
        victory.play();

        GreenfootImage raw;
        try {
            raw = new GreenfootImage("you_win.png");
        } catch (Exception e) {
            raw = new GreenfootImage(500, 120);
            raw.setColor(new Color(0,0,0,150));
            raw.fillRect(0,0,500,120);
            raw.setColor(Color.WHITE);
            raw.setFont(new Font(true, false, 36));
            raw.drawString("¡GANASTE!", 150, 70);
        }

        youWinImg = new GreenfootImage(raw.getWidth() + TITLE_X_OFFSET, raw.getHeight());
        youWinImg.drawImage(raw, TITLE_X_OFFSET, 0);

        titleActor = new Actor(){};
        titleActor.setImage(youWinImg);
        addObject(titleActor, getWidth()/2, getHeight()/2 - 50);

        // Imagen Score
        scoreActor = new Actor(){};

        GreenfootImage rawScore = new GreenfootImage("score.png");
        int sw = rawScore.getWidth(), sh = rawScore.getHeight();
        int targetH = 20; 
        int targetW = Math.max(1, (int)Math.round((double) sw * targetH / sh));
        GreenfootImage scoreIcon = new GreenfootImage(rawScore);
        scoreIcon.scale(targetW, targetH);

        GreenfootImage scoreText = new GreenfootImage("" + finalScore, 26, Color.WHITE, new Color(0,0,0,0));

        int cw = scoreIcon.getWidth() + 6 + scoreText.getWidth();
        int ch = Math.max(scoreIcon.getHeight(), scoreText.getHeight());
        GreenfootImage combined = new GreenfootImage(cw, ch);
        combined.drawImage(scoreIcon, 0, (ch - scoreIcon.getHeight())/2);
        combined.drawImage(scoreText, scoreIcon.getWidth() + 6, (ch - scoreText.getHeight())/2);

        scoreActor.setImage(combined);
        addObject(scoreActor, getWidth()/2, getHeight()/2 + 40);
    }

    public void act() {
        frameCount++;

        if (frameCount % 60 < 30) {
            titleActor.setImage(youWinImg);
        } else {
            titleActor.setImage((GreenfootImage) null);
        }

        if (Greenfoot.isKeyDown("enter")) {
            if (victory != null) victory.stop();
            Greenfoot.setWorld(new GameWorld());
        }
    }
}
