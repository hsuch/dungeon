package edu.virginia.engine.display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.util.GameClock;

public class AnimatedSprite extends Sprite {
    private ArrayList<Animation> animations = new ArrayList<Animation>();

    private Boolean playing;

    private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();

    private int currentFrame;

    private int startFrame;

    private int endFrame;

    private static final int DEFAULT_ANIMATION_SPEED = 60;

    private int animationSpeed;

    private GameClock gameClock;

    public AnimatedSprite(String id, Point position, ArrayList<DisplayObject> objects) {
        super(id, objects);
        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.gameClock = new GameClock();
        this.playing = false;
        this.currentFrame = 0;
        this.startFrame = 0;
        this.endFrame = 0;

        this.importSprites();
        super.setImage(frames.get(0));

        // Initialize animations
        this.animations.add(new Animation("playermove", 0, 1));
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
        return null;
    }

    public void importSprites() {
        this.frames.add(super.readImage("player1.png"));
        this.frames.add(super.readImage("player2.png"));
    }

    public void animate(Animation anim) {
        this.startFrame = anim.getStartFrame();
        this.endFrame = anim.getEndFrame();
        this.playing = true;
    }

    public void animate(String id) {
        Animation anim = this.getAnimation(id);
        this.animate(anim);
        this.playing = true;
    }

    public void animate(int startFrame, int endFrame) {
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        this.playing = true;
    }

    public void stopAnimation(int frameNumber) {
        this.startFrame = frameNumber;
        this.endFrame = frameNumber;
        this.playing = false;
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

    public Integer getAnimationSpeed() {
        return this.animationSpeed;
    }

    public void drawAnimation(Graphics g) {
        if ((this.animationSpeed <= this.gameClock.getElapsedTime()) && this.playing) {
            if(this.currentFrame >= this.endFrame) {
                this.currentFrame = this.startFrame;
            }
            else {
                this.currentFrame += 1;
            }
            BufferedImage new_image = this.frames.get(this.currentFrame);
            super.setImage(new_image);
            this.gameClock.resetGameClock();
        }

        super.draw(g);
    }
}

