package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import edu.virginia.engine.display.*;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game{

	/* Create a sprite object for our game. We'll use mario */
	//AnimatedSprite mario = new AnimatedSprite("Mario", new Point(0,0));
    /* Lab 3 code - initialize a sun and solar system */
    Sprite moon1 = new Sprite("moon1","planets/3.png", new ArrayList<DisplayObject>());
    Sprite planet1 = new Sprite("planet1", "planets/1.png", new ArrayList<DisplayObject>());
    Sprite planet2 = new Sprite("planet2", "planets/2.png", new ArrayList<DisplayObject>());
    Sprite sun = new Sprite("sun", "planets/12.png", new ArrayList<DisplayObject>());
    Point planet1Center = new Point(60, 60);

    int rotate = 1;
    int moon_rotate = 2;

	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
	    super("Lab One Test Game",500, 300);
		planet1.addChild(moon1);
		sun.addChild(planet1);
		sun.addChild(planet2);
	    sun.setPosition(new Point(200, 100));
        planet1.setPosition(new Point(100, 100));
        sun.setPivotPoint(new Point(60, 60));
        //planet1.getChild("moon1").setPosition(new Point(25, 25));
        sun.getChild("planet1").setScaleX(.5);
        sun.getChild("planet1").setScaleY(.5);
        sun.getChild("planet2").setScaleX(.3);
        sun.getChild("planet2").setScaleY(.3);
        planet1.getChild("moon1").setScaleX(.2);
		planet1.getChild("moon1").setScaleY(.2);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<Integer> pressedKeys){
		super.update(pressedKeys);
		planet1.setRotation(planet1.getRotation() + rotate);
		planet1.setPivotPoint(new Point(-40, -40));
		moon1.setRotation(moon1.getRotation() + moon_rotate);
		moon1.setPivotPoint(planet1Center);
        planet2.setRotation(planet2.getRotation() - rotate);
		planet2.setPivotPoint(new Point(60, 60));

		if(pressedKeys.contains(KeyEvent.VK_Q)) {
			sun.setScaleX(sun.getScaleX() + 0.1);
			sun.setScaleY(sun.getScaleY() + 0.1);
		}
		if(pressedKeys.contains(KeyEvent.VK_W)) {
			sun.setScaleX(sun.getScaleX() - 0.1);
			sun.setScaleY(sun.getScaleY() - 0.1);
		}
		if(pressedKeys.contains(KeyEvent.VK_UP)) {
			sun.setPosition(new Point(sun.getPosition().x,
					sun.getPosition().y + 5));
		}
		if(pressedKeys.contains(KeyEvent.VK_DOWN)) {
			sun.setPosition(new Point(sun.getPosition().x,
					sun.getPosition().y - 5));
		}
		if(pressedKeys.contains(KeyEvent.VK_LEFT)) {
			sun.setPosition(new Point(sun.getPosition().x - 5,
					sun.getPosition().y));
		}
		if(pressedKeys.contains(KeyEvent.VK_RIGHT)) {
			sun.setPosition(new Point(sun.getPosition().x + 5,
					sun.getPosition().y));
		}
		if(pressedKeys.contains(KeyEvent.VK_A)) {
			sun.setRotation(sun.getRotation()+5);
		}
		if(pressedKeys.contains(KeyEvent.VK_S)) {
            sun.setRotation(sun.getRotation()-5);
		}

	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
		if(sun != null) sun.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		LabOneGame game = new LabOneGame();
		game.start();

	}
}
