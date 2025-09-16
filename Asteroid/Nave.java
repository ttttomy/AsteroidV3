import greenfoot.*;

public class Nave extends Vidas {
    private int disparoDelay = 0;
    private int disparoDelayMaximo = 30;
    private GreenfootImage imgNormal;
    private GreenfootImage imgFuego;

    // Fisica de Derrape
    private double vx = 0.0;
    private double vy = 0.0;
    private double accel = 0.25;    
    private double maxSpeed = 5.0;   
    private double fric = 0.02;      

    public Nave() {
        int ancho = 40;
        int alto  = 40;

        imgNormal = new GreenfootImage("space_invader.png");
        imgNormal.scale(ancho, alto);
        imgNormal.rotate(90);

        imgFuego = new GreenfootImage("NaveFuego.png");
        imgFuego.scale(ancho, alto);
        imgFuego.rotate(90);

        setImage(imgNormal);
    }

    public void act() {
        // cooldown de daño / inmunidad
        vidasTick();

        // Rotación 
        super.rotacion();

        // Actualiza imagen según empuje
        actualizarImagen();

        // Parpadeo de inmunidad
        if (isInvulnerable()) {
            if ((Greenfoot.getRandomNumber(10)) < 5) getImage().setTransparency(120);
            else                                      getImage().setTransparency(255);
        } else {
            getImage().setTransparency(255);
        }

        // Física aceleración/derrape + movimiento por velocidad
        movimientoInercial();

        // Disparo
        disparo();

        GameWorld mundo = (GameWorld) getWorld();
        if (mundo != null) {
            mundo.wrap(this);
        }

        // Colisión con asteroides, pierde una vida cuando no es inmune
        if (!isInvulnerable() && isTouching(Asteroide.class)) {
            if (tryLoseLife()) { // resta vida y activa inmunidad
                GameWorld gw = (GameWorld) getWorld();
                if (gw != null) {
                    if (getVidas() <= 0) {
                        gw.gameOver();
                        return;
                    }
                    // Respawn al centro
                    respawnCentro(gw);
                }
            }
        }
    }

    private void actualizarImagen() {
        if (Greenfoot.isKeyDown("w")) {
            setImage(imgFuego);
        } else {
            setImage(imgNormal);
        }
    }

    private void disparo() {
        if (disparoDelay > 0) disparoDelay--;

        if (Greenfoot.isKeyDown("space") && disparoDelay == 0) {
            Bala bala = new Bala(getRotation());
            getWorld().addObject(bala, getX(), getY());
            disparoDelay = disparoDelayMaximo;
        }
    }

    /** Movimiento con inercia: empuje con W, frena suave al soltar */
    private void movimientoInercial() {
        boolean empujando = Greenfoot.isKeyDown("w");

        if (empujando) {
            double ang = Math.toRadians(getRotation());
            vx += Math.cos(ang) * accel;
            vy += Math.sin(ang) * accel;

            // Limitar velocidad a maxSpeed
            double speed = Math.hypot(vx, vy);
            if (speed > maxSpeed) {
                double factor = maxSpeed / speed;
                vx *= factor;
                vy *= factor;
            }
        } else {
            // Aplicar fricción solo si no hay empuje
            vx *= (1.0 - fric);
            vy *= (1.0 - fric);

            // Evitar “vibración” cuando ya casi es 0
            if (Math.abs(vx) < 0.001) vx = 0;
            if (Math.abs(vy) < 0.001) vy = 0;
        }

        // Mover según la velocidad acumulada
        int nx = (int)Math.round(getX() + vx);
        int ny = (int)Math.round(getY() + vy);
        setLocation(nx, ny);
    }

    // ====== NUEVO: respawn centrado + reset de velocidad ======
    private void respawnCentro(GameWorld gw) {
        setLocation(gw.getWidth()/2, gw.getHeight()/2);
        vx = 0;
        vy = 0;
        // opcional: orientar la nave a un ángulo fijo si quieres
        // setRotation(90);
    }
}
