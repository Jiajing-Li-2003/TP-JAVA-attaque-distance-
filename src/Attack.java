import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Attack implements Displayable {
    private double x, y; // Position de l'attaque
    private BufferedImage[] frames; // Images de l'animation
    private int currentFrame; // Frame actuelle de l'animation
    private long startTime; // Temps de début de l'animation
    private long duration; // Durée de l'animation
    private double length; // Longueur de l'attaque
    private double height; // Hauteur d'une frame
    Direction direction;


    public Attack(double x, double y, double length,double height, BufferedImage spritesheet,BufferedImage[] frames,Direction direction)  {
        this.direction = direction;


        this.length = length;
        this.height = height;
        this.frames = frames;

        this.duration = 1500; // Durée de l'animation en millisecondes
        this.startTime = System.currentTimeMillis();

        // attaque selon la direction du hero
        switch (direction){
            case EAST -> {
                this.x = x+30;
                this.y = y+15;
                for (int i = 0; i <5; i++) {
                    frames[i] = spritesheet.getSubimage(0, i * 133, (int) length, (int) height);
                }
            }
            case NORTH -> {
                this.y = y-346;
                this.x = x+15;
                for (int i = 0; i <5; i++) {
                    frames[i] = spritesheet.getSubimage(i*133, 0, (int) length,(int) height);
                }
            }
            case SOUTH -> {
                this.y = y;
                this.x = x+15;
                for (int i = 0; i <5; i++) {
                    frames[i] = spritesheet.getSubimage(i*133, 0, (int) length,(int) height);
                }
            }
            case WEST -> {
                this.x = x-340;
                this.y = y+15;
                for (int i = 0; i <5; i++) {
                    frames[i] = spritesheet.getSubimage(0, i * 133, (int) length, (int) height);
                }
            }

        }

    }

    public boolean isAttackPossible(ArrayList<SolidSprite> environment) {
        Rectangle2D.Double attackBounds = new Rectangle2D.Double(x, y, length, height);

        for (int i = environment.size() - 1; i >= 0; i--) { // Parcourir en ordre inverse
            SolidSprite s = environment.get(i);
            if (s.intersect(attackBounds)) {
                environment.remove(i);  // Supprime l'obstacle
                return false;           // L'attaque ne peut pas continuer
            }
        }
        return true; // L'attaque peut continuer
    }

    public void update() {
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed < duration) {
            currentFrame = (int) (elapsed / (duration / frames.length)) % frames.length;
        } else {
            currentFrame = -1; // L'attaque a fini
        }
    }

    @Override
    public void draw(Graphics g) {
        if (currentFrame != -1) {
            g.drawImage(frames[currentFrame], (int) x, (int) y, null);
        }
    }

    public boolean isActive() {
        return currentFrame != -1;
    }
}