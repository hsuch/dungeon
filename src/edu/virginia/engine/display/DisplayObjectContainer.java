package edu.virginia.engine.display;

import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject{
    ArrayList<DisplayObject> displayObjects = new ArrayList<DisplayObject>;

    public DisplayObjectContainer(String id, ArrayList<DisplayObject> displayObjects) {
        super(id);
        this.displayObjects = displayObjects;
    }

    public void addChild(DisplayObject d){
        displayObjects.add(d);
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

    public void removeChildByIndex(int i) {
        displayObjects.remove(i);
    }

    public void removeAll() {
        displayObjects.clear();
    }
}
