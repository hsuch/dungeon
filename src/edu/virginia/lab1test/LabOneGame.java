package edu.virginia.lab1test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import edu.virginia.engine.display.*;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game{

	/* Create level arrays */
	String[][] level1 = {
			{"+2", "#", "+5"},
			{"-1", "+7", "#"},
			{"#", "#", "-12"}
	};
	String[][] level2 = {
			{"+5", "#", "+1"},
			{"#", "-10", "#"},
			{"x2", "#", "+3"}
	};
	String[][] level3 = {
			{"+5", "#", "x3"},
			{"-2", "x2", "#"},
			{"#", "+1", "#"}
	};

	String[][][] levels = {level1, level2, level3};
	int currentLevel = 0;

	Enchantment[][] enchantments =  new Enchantment[3][3];

	/* Initialize sounds */
	String soundfile = ("resources" + File.separator + "sound" + File.separator + "game.wav");
	String soundfile_2 = ("resources" + File.separator + "sound" + File.separator + "jump.wav");
	String soundfile_pacman = ("resources" + File.separator + "sound" + File.separator + "pacman.wav");
	SoundManager sound = new SoundManager();


    /* Game variables */
    int speed_x = 0;
    int speed_y = 0;
    int MAX_SPEED = 10;
    int MIN_SPEED = -10;
    int MAX_GRAV = 8;

	/* Create player */
	AnimatedSprite player = new Player("Player");
	DisplayObject floor = new DisplayObject("floor");

	boolean resetBoard = true;
	int boardOffsetX = 125;
	int boardOffsetY = 25;

	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
	    super("dunge%n",500, 300);
	    sound.LoadSoundEffect("game", soundfile);
        sound.LoadSoundEffect("jump", soundfile_2);
		sound.LoadSoundEffect("pacman", soundfile_pacman);
		sound.PlaySoundEffect("pacman");

		/*planet1.addChild(moon1);
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
		planet1.getChild("moon1").setScaleY(.2);*/
	}

	public void addSpeedX(int speedToAdd) {
		speed_x = speed_x + speedToAdd;
	}

	public void addSpeedY(int speedToAdd) {
		speed_y = speed_y + speedToAdd;
	}

	public void setSpeedY(int speed) {
		speed_y = speed;
	}

	public void bouncePlayer() {
		speed_x = -speed_x;
		speed_y = -speed_y;
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<Integer> pressedKeys){
		/* Make sure player is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(resetBoard) {
			/* initialize and replace all enchantments back where they belong */
			for (int i = 0; i < 3; i++){
				for(int j = 0; j < 3; j++){
					if(levels[currentLevel][i][j] != "#") {
						Enchantment newE = new Enchantment(levels[currentLevel][i][j]);
						newE.setPosition(new Point(i * 75 + boardOffsetX, j * 75 + boardOffsetY));
						enchantments[i][j] = newE;
					}
					else{
						enchantments[i][j] = null;
					}
				}
			}
			resetBoard = false;
		}
		if(player != null) player.update(pressedKeys);

		/*if(player.collidesWith(obstacle1)){
			bouncePlayer();
			sound.PlaySoundEffect("jump");

		if(player.collidesWith(floor)) {
			if(speed_y > 0){
				speed_y = 0;
			}
			else{
				speed_y--;
			}
		}
		*/

		if(speed_y < MIN_SPEED){
			speed_y = MIN_SPEED;
		}
		if(speed_y > MAX_SPEED){
			speed_y = MAX_SPEED;
		}
		if(speed_x < MIN_SPEED){
			speed_x = MIN_SPEED;
		}
		if(speed_x > MAX_SPEED){
			speed_x = MAX_SPEED;
		}

        player.setPosition(new Point(player.getPosition().x + speed_x, player.getPosition().y + speed_y));

		if(speed_x > 0) {
			speed_x--;
		}
		if(speed_x < 0) {
			speed_x++;
		}
		if(speed_y > 0) {
			speed_y--;
		}
		if(speed_y < 0) {
			speed_y++;
		}

		if (pressedKeys.contains(KeyEvent.VK_UP)){
			addSpeedY(-2);
			player.animate("playermove");
			return;
		}
		if (pressedKeys.contains(KeyEvent.VK_DOWN)){
			addSpeedY(2);
			player.animate("playermove");
			return;
		}
		if (pressedKeys.contains(KeyEvent.VK_LEFT)){
			addSpeedX(-2);
			player.animate("playermove");
			return;
		}
		if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
			addSpeedX(2);
			player.animate("playermove");
			return;
		}
		player.stopAnimation();


		/*if(pressedKeys.contains(KeyEvent.VK_U)) {
			if(player.getIsAnimated()) {
				player.stopAnimation();
				player.setIsAnimated(false);
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e){
					assert false;
				}
			}
			else {
				player.animate(player.getTransform());
				player.setIsAnimated(true);
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e){
					assert false;
				}
			}
		}*/


	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure player gets drawn to
	 * the screen, we need to make sure to override this method and call player's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		Graphics2D g2d = (Graphics2D) g;
		
		/* Same, just check for null in case a frame gets thrown in before player is initialized */
		if(player != null) player.drawAnimation(g);

		for (Enchantment[] row : enchantments) {
			for (Enchantment e : row) {
				if(e != null) {
					e.draw(g);
				}
			}
		}
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
