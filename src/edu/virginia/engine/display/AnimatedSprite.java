package edu.virginia.engine.display;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import edu.virginia.engine.util.GameClock;

public class AnimatedSprite extends Sprite {
    private ArrayList<Animation> animations;

    private Boolean playing;

    private String fileName;

    /* Assuming images will be specified by filename */
    private ArrayList<String> frames;

    private int currentFrame;

    private int startFrame;

    private int endFrame;

    private static final int DEFAULT_ANIMATION_SPEED = 10;

    private int animationSpeed;

    private GameClock gameClock;

    public AnimatedSprite(String id, String fileName, Point position) {
        super(id);
        this.fileName = fileName;
        this.animationSpeed = DEFAULT_ANIMATION_SPEED; // idk just a number lol
        this.gameClock = new GameClock();
    }

    public void initGameClock() {
        if (this.gameClock == null) {
            this.gameClock = new GameClock();
        }
    }

    public Animation getAnimation(String id) {
        for (Animation obj : this.animations) {
            if (obj.getId().equals(id)) {
                return obj;
            }
        }
    }

    public void importSprites(Animation anim) {
        String dir = anim.getFileName();
        try (Stream<Path> paths = Files.walk(Paths.get("resources"))) {
            paths.filter(Files::isRegularFile)
                    .forEach(frames)
        }
    }

    public void animate(Animation anim) {
    }

    public void animate(String id) {
        Animation anim = this.getAnimation(id);
        this.animate(anim);
    }

    public void animate(int startFrame, int endFrame) {
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public void setAnimations(ArrayList<Animation> animations) {
        this.animations = animations;
    }

    public void setAnimationSpeed(Integer animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public void draw() {
        if (this.animationSpeed >= this.gameClock.getElapsedTime()) {
            String animationId = this.frames.get(this.currentFrame);
            this.currentFrame += 1;
            this.gameClock.resetGameClock();
        }
    }
}

