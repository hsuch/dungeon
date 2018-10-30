package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.AlphaComposite;
import javax.imageio.ImageIO;
import java.util.concurrent.TimeUnit;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject {

	private DisplayObject parent;

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	/* Object's x y position */
	private Point position;

	/* Object's pivot point */
	private Point pivotPoint;

	/* Degrees to rotate object */
	private int rotation;

	/* True if display object is meant to be drawn */
    private Boolean visible;

    /* How transparent object is */
    private Float alpha;
    private Float oldAlpha;

    /* Scales image up or down */
    private Double scaleX;
    private Double scaleY;

    private Boolean isAnimated;

    private String transform;

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		this.setPosition(new Point());
		this.setPivotPoint(new Point());
		this.setRotation(0);
		this.setVisible(true);
		this.setAlpha(1.0f);
		this.setOldAlpha(0.0f);
		this.setScaleX(1.0);
		this.setScaleY(1.0);
		this.setIsAnimated(true);
		this.setTransform("bird");
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		this.setPosition(new Point());
		this.setPivotPoint(new Point());
		this.setRotation(0);
		this.setVisible(true);
		this.setAlpha(1.0f);
		this.setOldAlpha(0.0f);
		this.setScaleX(1.0);
		this.setScaleY(1.0);
        this.setIsAnimated(true);
        this.setTransform("bird");
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	/**
	 * Other getters and setters
	 */
	public DisplayObject getParent() { return this.parent; }

	public void setParent(DisplayObject parent) { this.parent = parent; }

	public Point getPosition() { return this.position; }

	public void setPosition(Point p) {
		this.position = p;
	}

	public Point getPivotPoint() {
		return this.pivotPoint;
	}

	public void setPivotPoint(Point p) {
		this.pivotPoint = p;
	}

	public int getRotation() {
		return this.rotation;
	}

	public void setRotation(int r) {
		this.rotation = r;
	}

	public void setVisible(Boolean v) { this.visible = v; }

	public Boolean getVisible() { return this.visible; }

	public void setAlpha(Float a) { this.alpha = a; }

	public void setOldAlpha(Float oa) { this.oldAlpha = oa; }

	public Float getAlpha() { return this.alpha; }

	public Float getOldAlpha() { return this.oldAlpha; }

	public void setScaleX(Double x) { this.scaleX = x; }

	public void setScaleY(Double y) { this.scaleY = y; }

	public Double getScaleX() { return this.scaleX; }

	public Double getScaleY() { return this.scaleY; }

	public void setTransform(String s) { this.transform = s; }

	public String getTransform() { return this.transform; }

	public void setIsAnimated(Boolean b) { this.isAnimated = b; }

	public Boolean getIsAnimated() { return this.isAnimated; }

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) {

	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {

		if (displayImage != null) {

			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			if (this.visible) {
                /* Actually draw the image, perform the pivot point translation here */
                g2d.drawImage(displayImage, 0, 0,
                        (int) (getUnscaledWidth()),
                        (int) (getUnscaledHeight()), null);

                /*
                 * undo the transformations so this doesn't affect other display
                 * objects
                 */
                reverseTransformations(g2d);

            }
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		Point globalPosition = convertToGlobal(this.position, this.getParent());
		Point globalPivot = convertToGlobal(this.pivotPoint, this.getParent());
		this.setPivotPoint(new Point());
		System.out.println(this.id + globalPivot);
		g2d.translate(globalPosition.x, globalPosition.y);
		g2d.rotate(Math.toRadians(this.rotation), globalPivot.x, globalPivot.y);
		g2d.scale(this.scaleX, this.scaleY);
		float curAlpha;
		this.oldAlpha = curAlpha = ((AlphaComposite) g2d.getComposite()).getAlpha();
		g2d.setComposite(AlphaComposite.getInstance(3, curAlpha * this.alpha));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		Point globalPosition = convertToGlobal(this.position, this.getParent());
		Point globalPivot = convertToGlobal(this.pivotPoint, this.getParent());
		g2d.setComposite(AlphaComposite.getInstance(3, this.oldAlpha));
		g2d.scale(1/this.scaleX, 1/this.scaleY);
		g2d.rotate(Math.toRadians(-this.rotation), -globalPivot.x, -globalPivot.y);
		g2d.translate(-globalPosition.x, -globalPosition.y);
	}

	protected Point convertToGlobal(Point local, DisplayObject parent){
		if (parent == null) {
			return local;
		}
		else {
			return new Point(local.x + convertToGlobal(parent.getPosition(), parent.getParent()).x,
					local.y + convertToGlobal(parent.getPosition(), parent.getParent()).y);
		}
	}

	protected Point convertToLocal(Point global, DisplayObject parent){
		if (parent == null) {
			return global;
		}
		else {
			return new Point(global.x - convertToLocal(parent.getPosition(), parent.getParent()).x,
					global.y - convertToLocal(parent.getPosition(), parent.getParent()).x);
		}
	}

}
