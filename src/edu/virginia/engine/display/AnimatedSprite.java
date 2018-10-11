package edu.virginia.engine.display;

import java.util.ArrayList;
import edu.virginia.engine.util.GameClock;

public class AnimatedSprite {
    private ArrayList<Animation> animations;

    private Boolean playing;

    private String fileName;

    /* Assuming images will be specified by filename */
    private ArrayList<String> frames;

    private int currentFrame;

    private int startFrame;

    private int endFrame;

    private static final int DEFAULT_ANIMATION_SPEED;

    private int animationSpeed;

    private GameClock gameClock;

    public void initGameClock(){
        if(this.gameClock == null){
            this.gameClock = new GameClock();
        }
    }

    public AnimatedSprite(String ID, String fileName, Point position) {
        this.fileName = fileName;
        this.animationSpeed = 10; // idk just a number lol
        this.gameClock = new GameClock();
    }
}
