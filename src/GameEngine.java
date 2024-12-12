import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameEngine implements Engine, KeyListener {
    DynamicSprite hero;


    public GameEngine(DynamicSprite hero) throws IOException {
        this.hero = hero;

    }

    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    BufferedImage attackSpritesheetEast = ImageIO.read(new File("./img/kamehameha_Right.png")); //Spritsheet d'attaque vers la droite
    BufferedImage attackSpritesheetNorth = ImageIO.read(new File("./img/kamehameha_UP.png")); //Spritsheet d'attaque vers le haut
    BufferedImage attackSpritesheetSouth = ImageIO.read(new File("./img/kamehameha_Down.png")); //Spritsheet d'attaque vers le bas
    BufferedImage attackSpritesheetWest = ImageIO.read(new File("./img/kamehameha_Left.png")); //Spritsheet d'attaque vers la gauche
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                if (!hero.isAttacking()) hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                if (!hero.isAttacking()) hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                if (!hero.isAttacking()) hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                if (!hero.isAttacking()) hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_SPACE: // Utiliser la barre d'espace pour attaquer
                // charger la spritsheet selon la direction du hero
                switch (hero.direction){
                    case EAST -> {
                        double attackLength = 346; // Longueur de l'attaque
                        int attackHeight = 26; // Hauteur d'une frame
                        BufferedImage[] frames = new BufferedImage[8]; // met dans le buffer un nombre de frame (assez pour l'attaque)

                        hero.attack(attackSpritesheetEast,attackLength,attackHeight,frames);

                    }
                    case NORTH -> {
                        double attackLength = 346; // Longueur de l'attaque
                        int attackHeight = 26; // Hauteur d'une frame
                        BufferedImage[] frames = new BufferedImage[8]; // met dans le buffer un nombre de frame (assez pour l'attaque)

                        hero.attack(attackSpritesheetNorth,attackHeight,attackLength,frames);
                    }
                    case SOUTH -> {
                        double attackLength = 346; // Longueur de l'attaque
                        int attackHeight = 26; // Hauteur d'une frame
                        BufferedImage[] frames = new BufferedImage[8]; // met dans le buffer un nombre de frame (assez pour l'attaque)

                        hero.attack(attackSpritesheetSouth,attackHeight,attackLength,frames);
                    }
                    case WEST -> {
                        double attackLength = 346; // Longueur de l'attaque
                        int attackHeight = 26; // Hauteur d'une frame
                        BufferedImage[] frames = new BufferedImage[8]; // met dans le buffer un nombre de frame (assez pour l'attaque)

                        hero.attack(attackSpritesheetWest,attackLength,attackHeight,frames);
                    }
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
