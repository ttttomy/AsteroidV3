import greenfoot.*;

public class GameWorld extends World {

    private boolean isGameOver = false;

    private static final int SPAWN_SAFE_RADIUS = 150; 
    private static final int MAX_TRIES = 200;

    // Score y Vidas
    private int score = 0;
    private ScoreHUD scoreHUD;
    private LivesHUD livesHUD;
    private Nave nave;

    public GameWorld() {    
        super(900, 700, 1, false);

        // Música de fondo 
        if (!AudioManager.MENU_MUSIC.isPlaying()) {
            AudioManager.MENU_MUSIC.setVolume(30);
            AudioManager.MENU_MUSIC.play();
        }

        prepare();

        // HUDs (Head Up Display)
        scoreHUD = new ScoreHUD();   
        livesHUD = new LivesHUD();   

        addObject(scoreHUD, 90, 24);
        addObject(livesHUD, getWidth() - 110, 24);

        setPaintOrder(ScoreHUD.class, LivesHUD.class, Bala.class, Nave.class, Asteroide.class);
    }

    public void addScore(int value) { score += value; }
    public int getScore() { return score; }

    public void gameOver() {
        if (isGameOver) return;
        isGameOver = true;
        Greenfoot.setWorld(new GameOverWorld(score));
    }

    // Victoria cuando no queden asteroides
    public void checkWinCondition() {
        if (getObjects(Asteroide.class).isEmpty()) {
            AudioManager.stopAll();
            Greenfoot.setWorld(new YouWinWorld(getScore()));
        }
    }

    public void wrap(Actor a) {
        int x = a.getX();
        int y = a.getY();
        int w = getWidth();
        int h = getHeight();

        if (x < 0)        x = w - 1;
        else if (x >= w)  x = 0;

        if (y < 0)        y = h - 1;
        else if (y >= h)  y = 0;

        a.setLocation(x, y);
    }

    private void prepare() {
        int naveX = getWidth()/2;
        int naveY = getHeight()/2;

        nave = new Nave();
        addObject(nave, naveX, naveY);

        for (int i = 0; i < 3; i++) {
            int[] pos = randomSpawnAwayFrom(naveX, naveY, SPAWN_SAFE_RADIUS);
            addObject(new AsteroideGrande(), pos[0], pos[1]);
        }
    }

    private int[] randomSpawnAwayFrom(int cx, int cy, int radius) {
        int w = getWidth();
        int h = getHeight();
        int r2 = radius * radius;

        for (int i = 0; i < MAX_TRIES; i++) {
            int x = Greenfoot.getRandomNumber(w);
            int y = Greenfoot.getRandomNumber(h);
            int dx = x - cx;
            int dy = y - cy;
            if (dx*dx + dy*dy >= r2) {
                return new int[]{x, y};
            }
        }

        double ang = Math.toRadians(Greenfoot.getRandomNumber(360));
        int x = (int)Math.round(cx + Math.cos(ang) * Math.max(radius, Math.max(w, h)));
        int y = (int)Math.round(cy + Math.sin(ang) * Math.max(radius, Math.max(w, h)));
        x = Math.max(0, Math.min(w - 1, x));
        y = Math.max(0, Math.min(h - 1, y));
        return new int[]{x, y};
    }
}
