package edu.virginia.engine.display;

import java.awt.*;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject{
    ArrayList<DisplayObject> displayObjects = new ArrayList<DisplayObject>();

    public DisplayObjectContainer(String id, String fileName, ArrayList<DisplayObject> displayObjects) {
        super(id, fileName);
        this.displayObjects = displayObjects;
    }

    public DisplayObjectContainer(String id, ArrayList<DisplayObject> displayObjects) {
        super(id);
        this.displayObjects = displayObjects;
    }

    public void addChild(DisplayObject d){
        displayObjects.add(d);
        d.setParent(this);
    }

    public void addChildAtIndex(DisplayObject d, int i){
        displayObjects.add(i, d);
    }

    public void removeChild(String id){
        for (DisplayObject d : displayObjects) {
            if (d.getId().equals(id)){
                displayObjects.remove(d);
            }
        }
    }

    public void removeChildAtIndex(int i) {
        displayObjects.remove(i);
    }

    public void removeAll() {
        displayObjects.clear();
    }

    public Boolean contains(DisplayObject object) {
        for (DisplayObject d : displayObjects) {
            if (d.equals(object)) return true;
        }
        return false;
    }

    public DisplayObject getChild(String id) {
        for (DisplayObject d: displayObjects) {
            if (d.getId().equals(id)) return d;
        }
        return null;
    }

    public DisplayObject getChild(int i) {
        return displayObjects.get(i);
    }

    public ArrayList<DisplayObject> getChildren() {
        return displayObjects;
    }

    public void draw(Graphics g) {
        super.draw(g);

        applyTransformations((Graphics2D) g);

        for (DisplayObject d: displayObjects) d.draw(g);

        reverseTransformations((Graphics2D) g);


    }
}
