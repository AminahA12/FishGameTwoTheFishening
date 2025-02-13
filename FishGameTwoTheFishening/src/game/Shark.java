package game;

import java.awt.Graphics;
import java.util.Random;

/**
 * 
 * The Shark class extends Polygon and implements Debris. It represents a Shark
 * object that the player must avoid.
 * 
 * @author aminahasgharali
 *
 */
public class Shark extends Polygon implements Debris{

	private double defX;
	
	/**
	 * 
	 * Calls on the super contructor inherited from Polygon to create a Shark.
	 * Defines instance variable defX as the current x coordinate of the Shark.
	 * 
	 * @param shape
	 * @param position
	 * @param rotation
	 */
	public Shark(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
		defX = position.x;
	}
	
	/**
	 * 
	 * Paint method, which draws the Shark itself using the points passed into shape.
	 * 
	 * @param: brush
	 * 
	 */
	public void paint(Graphics brush) {
		int[] xPoints = new int[getPoints().length];
		for(int index = 0; index < xPoints.length; index++) {
			xPoints[index] = (int) getPoints()[index].x;
		}
		int[] yPoints = new int[getPoints().length];
		for(int index = 0; index < yPoints.length; index++) {
			yPoints[index] = (int) getPoints()[index].y;
		}
		brush.fillPolygon(xPoints, yPoints, xPoints.length);
	}

	/**
	 * 
	 * Implements the floatMove method from the Debris interface. floatMove moves
	 * this Shark down by .5, Then, if this Kelp has moved below the screen, 
	 * it is moved a random position above the screen. The nex x coordinate is a
	 * random number between 50 and 100, relative to defX.
	 * 
	 * @param: random
	 * 
	 */
	public void floatMove(Random random) {
		for(int index = 0; index < FishGameTwoTheFishening.sharkPoints.length; index++) {
			position.y += .5;
		}
		position.x = position.y > 600 ? defX + (random.nextInt(100)-50) : position.x;
		position.y = position.y > 600 ? -(random.nextInt(1000)) : position.y;

	}

}
