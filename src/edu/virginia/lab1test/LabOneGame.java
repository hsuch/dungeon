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
	AnimatedSprite player = new AnimatedSprite("Bird", new Point(0,0), new ArrayList<DisplayObject>());
	Sprite goal = new Sprite("Goal", "planets/3.png", new ArrayList<DisplayObject>());
	Sprite obstacle1 = new Sprite("Obstacle1", "planets/9.png", new ArrayList<DisplayObject>());
	DisplayObject floor = new DisplayObject("floor");
	int health = 10;
	boolean win = false;
	boolean music = false;
	boolean resetBoard = false;

	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
	    super("Lab One Test Game",500, 300);
	    sound.LoadSoundEffect("game", soundfile);
        sound.LoadSoundEffect("jump", soundfile_2);
		sound.LoadSoundEffect("pacman", soundfile_pacman);
        goal.setHitbox(0, 0, 120, 120);
        goal.setPosition(new Point( 200, 200));
        obstacle1.setScaleX(0.5);
        obstacle1.setScaleY(0.5);
        obstacle1.setPosition(new Point(100, 200));
        obstacle1.setHitbox(0, 0, 60, 60);
	    player.setHitbox(0, 0, 20, 20);
	    floor.setHitbox(0, 0, 600, 30);
	    floor.setPosition(new Point(0, 250));
	    floor.toggleDrawHitbox();
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
			resetBoard = false;
		}
		if(player != null) player.update(pressedKeys);

		if(player.collidesWith(obstacle1)){
			bouncePlayer();
			sound.PlaySoundEffect("jump");
		}

		if(player.collidesWith(floor)) {
			if(speed_y > 0){
				speed_y = 0;
			}
			else{
				speed_y--;
			}
		}

		if(player.collidesWith(goal)) {
		    this.win = true;
		    if(music == false) {
				sound.PlayMusic("game");
				music = true;
			}
        }

		if(speed_y < MIN_SPEED){
			speed_y = MIN_SPEED;
		}
		if(speed_y > MAX_GRAV){
			speed_y = MAX_GRAV;
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
		/*if(speed_y > 0) {
			speed_y--;
		}
		if(speed_y < 0) {
			speed_y++;
		}*/
		speed_y++;

		if (pressedKeys.contains(KeyEvent.VK_UP)){
			addSpeedY(-2);
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}
		if (pressedKeys.contains(KeyEvent.VK_DOWN)){
			addSpeedY(2);
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}
		if (pressedKeys.contains(KeyEvent.VK_LEFT)){
			addSpeedX(-2);
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}
		if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
			addSpeedX(2);
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_U)) {
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
		}
		if(pressedKeys.contains(KeyEvent.VK_T)) {
			if(player.getTransform() == "bird") {
				player.setTransform("playerspin");
				player.animate("playerspin");
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e){
					assert false;
				}
			}
			else {
				player.setTransform("bird");
				player.animate("bird");
				try {
					Thread.sleep(200);

				}
				catch (InterruptedException e){
					assert false;
				}
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_M)) {
			if(player.getAnimationSpeed() >= 0) {
				player.setAnimationSpeed(player.getAnimationSpeed()-5);
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_N)) {
			if (player.getAnimationSpeed() <= 100) {
				player.setAnimationSpeed(player.getAnimationSpeed() + 5);
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_I)) {
			player.setPivotPoint(new Point(player.getPivotPoint().x - 5,
					player.getPivotPoint().y));
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_K)){
			player.setPivotPoint(new Point(player.getPivotPoint().x + 5,
					player.getPivotPoint().y));
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_J)){
			player.setPivotPoint(new Point(player.getPivotPoint().x,
					player.getPivotPoint().y - 5));
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_L)){
			player.setPivotPoint(new Point(player.getPivotPoint().x,
					player.getPivotPoint().y + 5));
			if(player.getTransform() == "bird") {
				player.animate("bird");
			}
			else {
				player.animate("playerspin");
			}
		}

		if(pressedKeys.contains(KeyEvent.VK_W)) {
			player.setRotation(player.getRotation() + 10);
		}
		if(pressedKeys.contains(KeyEvent.VK_Q)) {
			player.setRotation(player.getRotation() - 10);
		}
		if (pressedKeys.contains(KeyEvent.VK_V)) {
			if (player.getVisible()) {
				player.setVisible(false);
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e){
					assert false;
				}
			}
			else {
				player.setVisible(true);
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e){
					assert false;
				}
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_Z)) {
			if (player.getAlpha() < 1.0f) {
				player.setAlpha(player.getAlpha() + 0.1f);
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_X)) {
			if ((player.getAlpha() - 0.1f) > 0.0f) {
				player.setAlpha(player.getAlpha() - 0.1f);
			}
		}
		if(pressedKeys.contains(KeyEvent.VK_A)) {
			player.setScaleX(player.getScaleX() + 0.1);
			player.setScaleY(player.getScaleY() + 0.1);
			Rectangle hitbox = player.getHitbox();
			player.setHitbox(0, 0, hitbox.width+1, hitbox.height+1);
		}
		if(pressedKeys.contains(KeyEvent.VK_S)) {
			double xVar = player.getScaleX();
			double yVar = player.getScaleY();
			if(xVar - 0.1 >= 0 && yVar - 0.1 >= 0) {
				player.setScaleX(player.getScaleX() - 0.1);
				player.setScaleY(player.getScaleY() - 0.1);
				Rectangle hitbox = player.getHitbox();
				player.setHitbox(0, 0, hitbox.width-1, hitbox.height-1);
			}
		}

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

		obstacle1.draw(g);
		floor.draw(g);

		if(!win){
            goal.draw(g);
        }
        if(win){
        	g2d.drawString("You win!", 200, 100);
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
