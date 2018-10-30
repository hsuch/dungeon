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


	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
	    super("Lab One Test Game",500, 300);
		planet1.addChild(moon1);
		sun.addChild(planet1);
		sun.addChild(planet2);
	    sun.setPosition(new Point (200, 100));
        //sun.getChild("planet1").setPosition(new Point(50, 50));
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
		sun.getChild("planet1").setRotation(sun.getChild("planet1").getRotation() + 1);
		planet1.getChild("moon1").setRotation(planet1.getChild("moon1").getRotation() + 2);
        sun.getChild("planet2").setRotation(sun.getChild("planet2").getRotation() - 1);


		/* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */

        /*
		if(mario != null) mario.update(pressedKeys);

		if (pressedKeys.contains(KeyEvent.VK_UP)){
			mario.setPosition(new Point(mario.getPosition().x,
					mario.getPosition().y - 5));
			if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}
		if (pressedKeys.contains(KeyEvent.VK_DOWN)){
			mario.setPosition(new Point(mario.getPosition().x,
					mario.getPosition().y + 5));
            if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}
		if (pressedKeys.contains(KeyEvent.VK_LEFT)){
			mario.setPosition(new Point(mario.getPosition().x - 5,
					mario.getPosition().y));
            if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}
		if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
			mario.setPosition(new Point(mario.getPosition().x + 5,
					mario.getPosition().y));
            if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}
        if(pressedKeys.contains(KeyEvent.VK_U)) {
            if(mario.getIsAnimated()) {
                mario.stopAnimation();
                mario.setIsAnimated(false);
                try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e){
                    assert false;
                }
            }
            else {
                mario.animate(mario.getTransform());
                mario.setIsAnimated(true);
                try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e){
                    assert false;
                }
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_T)) {
            if(mario.getTransform() == "bird") {
                mario.setTransform("mariospin");
                mario.animate("mariospin");
                try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e){
                    assert false;
                }
            }
            else {
                mario.setTransform("bird");
                mario.animate("bird");
                try {
                    Thread.sleep(200);

                }
                catch (InterruptedException e){
                    assert false;
                }
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_M)) {
            if(mario.getAnimationSpeed() >= 0) {
                mario.setAnimationSpeed(mario.getAnimationSpeed()-5);
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_N)) {
            if (mario.getAnimationSpeed() <= 100) {
                mario.setAnimationSpeed(mario.getAnimationSpeed() + 5);
            }
        }
		if(pressedKeys.contains(KeyEvent.VK_I)) {
			mario.setPivotPoint(new Point(mario.getPivotPoint().x - 5,
					mario.getPivotPoint().y));
            if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}
		if(pressedKeys.contains(KeyEvent.VK_K)){
			mario.setPivotPoint(new Point(mario.getPivotPoint().x + 5,
					mario.getPivotPoint().y));
            if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}
		if(pressedKeys.contains(KeyEvent.VK_J)){
			mario.setPivotPoint(new Point(mario.getPivotPoint().x,
					mario.getPivotPoint().y - 5));
            if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}
		if(pressedKeys.contains(KeyEvent.VK_L)){
			mario.setPivotPoint(new Point(mario.getPivotPoint().x,
					mario.getPivotPoint().y + 5));
            if(mario.getTransform() == "bird") {
                mario.animate("bird");
            }
            else {
                mario.animate("mariospin");
            }
		}

		if(pressedKeys.contains(KeyEvent.VK_W)) {
			mario.setRotation(mario.getRotation() + 10);
		}
		if(pressedKeys.contains(KeyEvent.VK_Q)) {
			mario.setRotation(mario.getRotation() - 10);
		}
        if (pressedKeys.contains(KeyEvent.VK_V)) {
            if (mario.getVisible()) {
                mario.setVisible(false);
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e){
					assert false;
				}
            }
            else {
                mario.setVisible(true);
                try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e){
                    assert false;
                }
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_Z)) {
            if (mario.getAlpha() < 1.0f) {
                mario.setAlpha(mario.getAlpha() + 0.1f);
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_X)) {
            if ((mario.getAlpha() - 0.1f) > 0.0f) {
                mario.setAlpha(mario.getAlpha() - 0.1f);
            }
        }
        if(pressedKeys.contains(KeyEvent.VK_A)) {
            mario.setScaleX(mario.getScaleX() + 0.1);
            mario.setScaleY(mario.getScaleY() + 0.1);
        }
        if(pressedKeys.contains(KeyEvent.VK_S)) {
            double xVar = mario.getScaleX();
            double yVar = mario.getScaleY();
            if(xVar - 0.1 >= 0 && yVar - 0.1 >= 0) {
                mario.setScaleX(mario.getScaleX() - 0.1);
                mario.setScaleY(mario.getScaleY() - 0.1);
            }
        }*/

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
