import greenfoot.*;

public abstract class Asteroide extends Actor {
    protected double vx, vy;
    protected int hp;
    protected int rotSpeed;
    protected int maxHp;

    public Asteroide(int hp, int minSpeed, int maxSpeed) {
        this.hp = hp;
        this.maxHp = hp;

        int ang = Greenfoot.getRandomNumber(360);
        double rad = Math.toRadians(ang);
        int speed = Greenfoot.getRandomNumber(maxSpeed - minSpeed + 1) + minSpeed;
        vx = speed * Math.cos(rad);
        vy = speed * Math.sin(rad);
        rotSpeed = Greenfoot.getRandomNumber(5) - 2;
        setRotation(ang);
    }

    public void act() {
        setLocation(getX() + (int)Math.round(vx), getY() + (int)Math.round(vy));
        setRotation(getRotation() + rotSpeed);
        wrap();
    }

    protected void wrap() {
        World w = getWorld();
        if (w == null) return;
        int x = getX(), y = getY();
        int W = w.getWidth(), H = w.getHeight();
        if (x < 0) x = W - 1;
        if (x >= W) x = 0;
        if (y < 0) y = H - 1;
        if (y >= H) y = 0;
        if (x != getX() || y != getY()) setLocation(x, y);
    }

    public void onHit() {
        hp--;
        if (hp <= 0) {
            World w = getWorld();
            GameWorld gw = (w instanceof GameWorld) ? (GameWorld) w : null;

            // suma de Score al ser destruido
            if (gw != null) {
                gw.addScore(getScoreValue());
            }

            // division de asteroide con un hit (children)
            spawnChildren();

            // quitar el asteroide destruido
            if (w != null) w.removeObject(this);

            
            if (gw != null) {
                gw.checkWinCondition();
            }
        }
    }

    /**
     * Puntaje por destruir este asteroide.
     * Valores por defecto:
     *   maxHp = 3 -> 20 puntos (grande)
     *   maxHp = 2 -> 50 puntos (mediano)
     *   maxHp = 1 -> 100 puntos (pequeño)
     */
    public int getScoreValue() {
        switch (maxHp) {
            case 3:  return 20;
            case 2:  return 50;
            default: return 100; 
        }
    }

    protected abstract void spawnChildren();
}
