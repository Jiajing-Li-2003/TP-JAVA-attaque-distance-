import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite{
    protected Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking =true;
    private final int spriteSheetNumberOfColumn = 10;
    private boolean isAttacking = false;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    // j'ai essayer de supprimer les obstacles lorsque qu'une attaque y passe mais ne fonctionne pas
    private boolean isAttackPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double attackBounds = new Rectangle2D.Double();

        // Définir les limites (bounds) de l'attaque selon la direction
        switch (direction) {
            case EAST:
                attackBounds.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:
                attackBounds.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:
                attackBounds.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:
                attackBounds.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }
        System.out.println(attackBounds);


        // Parcourir les obstacles pour vérifier les collisions
        for (int i = environment.size() - 1; i >= 0; i--) { // Parcourir en ordre inverse
            SolidSprite s = (SolidSprite) environment.get(i);
            System.out.println("solidsprite");
            System.out.println(s);

            if (s.intersect(attackBounds)) {
                System.out.println("intersect");

                environment.remove(i);  // Supprime l'obstacle
                return false;           // Signale que l'attaque ne peut pas continuer
            }
        }
        return true; // L'attaque peut continuer
    }

    @Override
    public void draw(Graphics g) {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);
    }



    private ArrayList<Attack> attacks = new ArrayList<>();

    public void attack(BufferedImage spritesheet, double attackLength, double attackHeight, BufferedImage[] frames) {
        if (!isAttacking) { // Ne pas attaquer si déjà en attaque
            isAttacking = true;
            attacks.add(new Attack(x, y, attackLength, attackHeight, spritesheet,frames,direction));
        }
    }
    public void updateAttacks(ArrayList<SolidSprite> environment) {
        for (int i = attacks.size() - 1; i >= 0; i--) {
            Attack attack = attacks.get(i);

            // Mise à jour de l'attaque
            attack.update();

            // Vérifie si l'attaque rencontre un obstacle
            if (!attack.isActive() || !attack.isAttackPossible(environment)) {
                System.out.println("remove");

                attacks.remove(i); // Supprimer l'attaque si elle a terminé ou rencontré un obstacle
                isAttacking = false; // Réinitialiser l'état d'attaque
            }
        }
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }
    public boolean isAttacking() {
        return isAttacking; // Retourne l'état de l'attaque
    }

    //Le perso ne peut pas bouger s'il attaque ou atteint un obstacle
    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (!isAttacking && isMovingPossible(environment)) {
            move();
        }

    }
    public void removeObstacle(ArrayList<Sprite> environment){
        if (isAttacking) {
            // Vérifiez si l'attaque peut se déplacer et supprimer les obstacles
            if (!isAttackPossible(environment)) {
                System.out.println("L'attaque a rencontré un obstacle !");
            }
        }
    }


}
