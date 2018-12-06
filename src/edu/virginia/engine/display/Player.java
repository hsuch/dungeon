package edu.virginia.engine.display;

import java.awt.*;
import java.util.ArrayList;

public class Player extends AnimatedSprite {
    private int number = 0;

    public Player(String id){
        super(id, new Point(200, 200), new ArrayList<DisplayObject>());
        this.setHitbox(36, 36, 2, 2);
    }

    public int getNumber(){
        return this.number;
    }
    public void setNumber(int n) {
        this.number = n;
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
