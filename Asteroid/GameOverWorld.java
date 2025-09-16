import greenfoot.*;

public class GameOverWorld extends World {

    private GreenfootImage gameOverImg;
    private Actor gameOverActor;
    private Actor scoreActor;
    private int frameCount = 0;

    private GreenfootSound gameOver;

    public GameOverWorld(int finalScore) {
        super(900, 700, 1);

        AudioManager.stopAll();

        gameOver = new GreenfootSound("Game Over (8-Bit Music).mp3");
        gameOver.setVolume(30);
        gameOver.play();

        // Imagen Game Over
        gameOverActor = new Actor() {};
        gameOverImg = new GreenfootImage("game_over_text.png");
        gameOverActor.setImage(gameOverImg);
        addObject(gameOverActor, getWidth() / 2, getHeight() / 2 - 50);

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
            gameOverActor.setImage(gameOverImg);
        } else {
            gameOverActor.setImage((GreenfootImage) null);
        }

        if (Greenfoot.isKeyDown("enter")) {
            if (gameOver != null) gameOver.stop();
            Greenfoot.setWorld(new GameWorld());
        }
    }
}
