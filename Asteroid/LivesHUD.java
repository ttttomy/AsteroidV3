import greenfoot.*;
import java.util.List;

/** Muestra las VIDAS (navecitas) alineadas a la derecha del panel (esquina superior derecha). */
public class LivesHUD extends Actor {
    private final int panelWidth  = 220;
    private final int panelHeight = 36;
    private final int padding     = 8;
    private final int spacing     = 6;

    private final int lifeIconSize = 18;
    private GreenfootImage lifeIcon;

    private int lastLives = -1;

    public LivesHUD() {
        lifeIcon = new GreenfootImage("space_invader.png");
        lifeIcon.scale(lifeIconSize, lifeIconSize);

        redraw(0);
    }

    public void act() {
        GameWorld gw = (GameWorld) getWorld();
        if (gw == null) return;

        Nave n = obtenerNave();
        if (n == null) return;

        int vidas = n.getVidas();
        if (vidas != lastLives) {
            lastLives = vidas;
            redraw(vidas);
        }
    }

    private Nave obtenerNave() {
        List<Nave> naves = getWorld().getObjects(Nave.class);
        return naves.isEmpty() ? null : naves.get(0);
    }

    private void redraw(int vidas) {
        GreenfootImage img = new GreenfootImage(panelWidth, panelHeight);

        // Mostrar vidas con el sprite de la nave
        int totalW = (vidas > 0) ? (vidas * lifeIconSize + (vidas - 1) * spacing) : 0;
        int startX = panelWidth - padding - totalW;
        int y = (panelHeight - lifeIconSize) / 2;

        for (int i = 0; i < vidas; i++) {
            int x = startX + i * (lifeIconSize + spacing);
            img.drawImage(lifeIcon, x, y);
        }

        setImage(img);
    }
}
