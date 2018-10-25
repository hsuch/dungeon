package edu.virginia.engine.display;

import java.util.ArrayList;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class Sprite extends DisplayObjectContainer {

	public Sprite(String id, String imageFileName, ArrayList<DisplayObject> objects) {
		super(id, imageFileName, objects);
	}

	public Sprite(String id, ArrayList<DisplayObject> objects) {
		super(id, objects);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
	}
}
