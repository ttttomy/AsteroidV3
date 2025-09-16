import greenfoot.*;

public class Bala extends Actor {
    private int speed = 7;

    public Bala(int rotation) {
        setRotation(rotation);

        // Sonido de disparo
        GreenfootSound s = new GreenfootSound("Disparo.mp3");
        s.setVolume(40);
        s.play();
    }

    public void act() {
        if (getWorld() == null) return;

        move(speed);

        if (verificarImpacto()) return;

        World w = getWorld();
        if (w == null) return;

        if (isAtEdge()) {
            w.removeObject(this);
        }
    }

    private boolean verificarImpacto() {
        if (getWorld() == null) return true;

        Asteroide a = (Asteroide) getOneIntersectingObject(Asteroide.class);
        if (a != null) {
            a.onHit();
            World w = getWorld();
            if (w != null) {
                w.removeObject(this);
            }
            return true;
        }
        return false;
    }
}
