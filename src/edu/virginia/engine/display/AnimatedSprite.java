package edu.virginia.engine.display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.util.GameClock;

public class AnimatedSprite extends Sprite {
    private ArrayList<Animation> animations;

    private Boolean playing;

    /* Assuming images will be specified by filename */
    private ArrayList<BufferedImage> frames;

    private int currentFrame;

    private int startFrame;

    private int endFrame;

    private static final int DEFAULT_ANIMATION_SPEED = 10;

    private int animationSpeed;

    private GameClock gameClock;

    public AnimatedSprite(String id, String fileName, Point position) {
        super(id);
        this.fileName = fileName;
        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.gameClock = new GameClock();
        this.playing = true;
        this.currentFrame = 0;
        this.startFrame = 0;
        this.endFrame = 0;
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

    public void importSprites() {
        this.frames.add(super.readImage("0000.png"));
        this.frames.add(super.readImage("0001.png"));
        this.frames.add(super.readImage("0002.png"));
        this.frames.add(super.readImage("0003.png"));
        this.frames.add(super.readImage("0004.png"));
        this.frames.add(super.readImage("0005.png"));
        this.frames.add(super.readImage("0006.png"));
        this.frames.add(super.readImage("0007.png"));
        this.frames.add(super.readImage("mario1.png"));
        this.frames.add(super.readImage("mario2.png"));
        this.frames.add(super.readImage("mario3.png"));
    }

    public void animate(Animation anim) {
        this.startFrame = anim.getStartFrame();
        this.endFrame = anim.getEndFrame();
    }

    public void animate(String id) {
        Animation anim = this.getAnimation(id);
        this.animate(anim);
    }

    public void animate(int startFrame, int endFrame) {
        this.startFrame = startFrame;
        this.endFrame = endFrame;
    }

    public void stopAnimation(int frameNumber) {
        this.startFrame = frameNumber;
        this.endFrame = frameNumber;
    }

    public void stopAnimation() {
        this.stopAnimation(0);
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

