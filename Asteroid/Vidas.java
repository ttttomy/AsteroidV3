import greenfoot.*;

public class Vidas extends Player {
    // Vidas del jugador
    protected int vidas = 3;

    // Cooldown para no perder varias vidas por un mismo choque continuo (inmunidad momenatanea)
    private int hitCooldown = 0;
    private static final int HIT_COOLDOWN = 45; 

    
    protected void vidasTick() {
        if (hitCooldown > 0) hitCooldown--;
    }

    
    protected boolean isInvulnerable() {
        return hitCooldown > 0;
    }

    protected boolean tryLoseLife() {
        if (hitCooldown > 0) return false;
        vidas = Math.max(vidas - 1, 0);
        hitCooldown = HIT_COOLDOWN;
        return true;
    }

    public int getVidas() {
        return vidas;
    }

    public void resetVidas(int nuevasVidas) {
        vidas = Math.max(0, nuevasVidas);
        hitCooldown = 0;
    }
}
