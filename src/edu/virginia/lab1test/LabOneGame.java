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
			{"#", "x2", "-2"},
			{"#", "+1", "#"}
	};

	String[][][] levels = {level1, level2, level3};
	int currentLevel = 0;

	Enchantment[][] enchantments =  new Enchantment[3][3];

	/* Create tile array */
	Sprite[][] tiles = new Sprite[3][3];

	/* Initialize goal */
	Sprite goal = new Sprite("goal", "goal.png", new ArrayList<DisplayObject>());

	/* Initialize sounds */
	String soundfile = ("resources" + File.separator + "sound" + File.separator + "game.wav");
	String soundfile_2 = ("resources" + File.separator + "sound" + File.separator + "jump.wav");
	String soundfile_pacman = ("resources" + File.separator + "sound" + File.separator + "pacman.wav");
	SoundManager sound = new SoundManager();


    /* Game variables */
    int speed_x = 0;
    int speed_y = 0;
    int MAX_SPEED = 5;
    int MIN_SPEED = -5;

	/* Create player */
	Player player = new Player("Player");
	DisplayObject floor = new DisplayObject("floor");

	boolean resetBoard = true;
	int playerStartingNumber = 0;
	boolean win = false;
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

		/* Initialize tiles */
		for (int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Sprite newTile = new Sprite("tile"+i+j, "tile.png", new ArrayList<DisplayObject>());
				newTile.setPosition(new Point(i * 75 + boardOffsetX, j * 75 + boardOffsetY));
				tiles[i][j] = newTile;
			}
		}

		/* Initialize goal position */
		goal.setPosition(new Point(3 * 75 + boardOffsetX, 75 + boardOffsetY));
		goal.setHitbox(20, 10, 30, 60);
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
			player.setPosition(new Point(boardOffsetX, 75+boardOffsetY));
			player.setNumber(playerStartingNumber);
			resetBoard = false;
		}
		if(player != null) player.update(pressedKeys);

		if(player.collidesWith(goal)) {
			if (currentLevel != 2) {
				if (player.getNumber() > 10) {
					currentLevel++;
					playerStartingNumber = player.getNumber();
				}
				resetBoard = true;
			}
			else {
				if (player.getNumber() > 7 && player.getNumber()%2 == 1) {
					win = true;
				}
				else {
					resetBoard = true;
				}
			}
		}

		for(Enchantment[] row : enchantments){
			for(Enchantment e : row){
				if(e != null) {
					if (player.collidesWith(e)) {
						char op = e.getOperation();
						int n = e.getNumber();
						if (op == '+') {
							player.setNumber(player.getNumber() + n);
						} else if (op == '-') {
							player.setNumber(player.getNumber() - n);
						} else if (op == 'x') {
							player.setNumber(player.getNumber() * n);
						} else {
							player.setNumber(player.getNumber() / n);
						}
						e.setPosition(new Point(500, 300));
					}
				}
			}
		}

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

		for (Sprite[] row: tiles){
			for(Sprite tile : row){
				tile.draw(g);
			}
		}

		goal.draw(g);

		for (Enchantment[] row : enchantments) {
			for (Enchantment e : row) {
				if(e != null) {
					e.draw(g);
				}
			}
		}

		/* Same, just check for null in case a frame gets thrown in before player is initialized */
		if(player != null) player.drawAnimation(g);
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
