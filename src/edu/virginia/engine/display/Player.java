package edu.virginia.engine.display;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AnimatedSprite {
    private int number = 0;

    public Player(String id){
        super(id, new Point(200, 200), new ArrayList<DisplayObject>());

    }

    public void addCustomization(){
        /* TODO */
    }

    public void drawAnimation(Graphics g) {
        super.drawAnimation(g);
        applyTransformations((Graphics2D)g);
        g.drawString(Integer.toString(number), 35, 30);
        reverseTransformations((Graphics2D)g);
    }
}
