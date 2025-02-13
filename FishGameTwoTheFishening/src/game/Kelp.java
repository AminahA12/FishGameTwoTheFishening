package game;

import java.awt.Graphics;
import java.util.Random;

/**
 * 
 * The Kelp class extends Polygon and implements Debris. It represents a Kelp
 * object that the player can eat to increase their score.
 * 
 * @author aminahasgharali
 *
 */

public class Kelp extends Polygon implements Debris {

	/**
	 * 
	 * Calls on the super contructor inherited from Polygon to create a Kelp.
	 * 
	 * @param shape
	 * @param position
	 * @param rotation
	 */
	public Kelp(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
	}
	
	/**
	 * 
	 * Paint method, which draws the Kelp itself using the 
	 * points passed into shape.
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
	 * this Kelp down by .5, and either rotates it one degree or zero degrees 
	 * to the right. Then, if this Kelp has moved below the screen, it is moved 
	 * a random position above the screen.
	 * 
	 * @param: random
	 * 
	 */
	public void floatMove(Random random) {
		for(int index = 0; index < FishGameTwoTheFishening.sharkPoints.length; index++) {
			position.y += .5;
			rotate(random.nextInt(2));
		}
		position.x = position.y > 600 ? (random.nextInt(700)) + 50: position.x;
		position.y = position.y > 600 ? -(random.nextInt(1000)) : position.y;

	}

}
