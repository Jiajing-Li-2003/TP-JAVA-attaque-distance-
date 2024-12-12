import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine{
    private ArrayList<Displayable> renderList;
    private DynamicSprite hero;

    public RenderEngine(JFrame jFrame, DynamicSprite hero) {
        this.renderList = new ArrayList<>();
        this.hero = hero;
    }
    public void addToRenderList(Displayable displayable){
        if (!renderList.contains(displayable)){
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable){
        if (!renderList.contains(displayable)){
            renderList.addAll(displayable);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Displayable renderObject : renderList) {
            renderObject.draw(g);
        }
        for (Attack attack : hero.getAttacks()) {
            attack.draw(g);
        }
    }
    @Override
    public void update(){
        this.repaint();
    }
}
