package edu.virginia.engine.display;

import java.awt.*;
import java.util.ArrayList;

public class Enchantment extends Sprite {
    int number = 0;
    char operation;
    public Enchantment(String opstring) {
        super(opstring, new ArrayList<DisplayObject>());
        this.operation = opstring.charAt(0);
        this.number = Integer.parseInt(opstring.substring(1));
        if(operation == '+'){
            this.setImage("addition.png");
        }
        else if(operation == '-'){
            this.setImage("subtraction.png");
        }
        else if(operation == 'x'){
            this.setImage("multiplication.png");
        }
        else{
            this.setImage("division.png");
        }
        this.setHitbox(0, 0, 75, 75);
    }

    public void draw(Graphics g) {
        super.draw(g);

        applyTransformations((Graphics2D) g);
        g.drawString(Integer.toString(number), 37, 37);
        reverseTransformations((Graphics2D) g);
    }
}
