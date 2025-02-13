package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * 
 * Fish class represents the Fish object controlled by the player. 
 * It extends Polygon and implements KeyListener. It has inner classes Star and Lines,
 * which are effects associated with this Fish
 * 
 * @author: aminahasgharali
 * 
 */
public class Fish extends Polygon implements KeyListener{
	private boolean front, right, left;
	private double rotVel, xVel, yVel;
	private Star star = new Star();
	private CollisionEffect heart = new CollisionEffect() {
		public void paint(Graphics brush, Point collision) {
			brush.setColor(Color.red);
			int centerX = (int) collision.x;
			int[] xPoints = {centerX + 2, centerX + 5, centerX + 8, centerX + 9, centerX + 13, centerX + 14, centerX + 14, centerX + 11, centerX + 5, centerX - 1, centerX - 4, centerX - 4, centerX - 3, centerX + 1};
			int centerY = (int) collision.y;
			int[] yPoints = {centerY - 8, centerY - 6, centerY - 8, centerY - 9, centerY - 9, centerY - 8, centerY - 3, centerY + 2, centerY + 8, centerY + 2, centerY - 3, centerY - 8, centerY - 9, centerY - 9 };
			brush.fillPolygon(xPoints, yPoints, xPoints.length);
		}
	};
	private Lines lines = new Lines();
	
	/**
	 * 
	 * Initializes the object by calling Polygon's constructor. Defines instance 
	 * variables rotVel, xVel, and yVel, which help control the movement of this object.
	 * 
	 * @param shape
	 * @param position
	 * @param rotation
	 */
	public Fish(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
		rotVel = 0;
		xVel = 0;
		yVel = 0;
	}
	
	/**
	 * 
	 * Paint method, which first draws the Fish itself using the 
	 * points passed in to shape. Then, if applicable, a Star is drawn by 
	 * calling on the Star's paint method. Then, if applicable, a Heart is drawn 
	 * by calling on the heart's paint method. heart is an anonymus class of 
	 * CollisionEffect. Finally, Lines are drawn using Lines's paint method.
	 * 
	 * @param brush
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
		brush.drawPolygon(xPoints, yPoints, xPoints.length);
		brush.fillPolygon(xPoints, yPoints, xPoints.length);
		if(FishGameTwoTheFishening.collidingShark) {
			star.paint(brush, position);
		}
		if(star.counter == 30) {
			star.counter = 0;
		}
		if(FishGameTwoTheFishening.collidingKelp) {
			heart.paint(brush, position);
		}
		lines.paint(brush);
	}
	
	/**
	 * 
	 * The move method updates the position of this Fish according to which keys 
	 * are pressed or not pressed. To emulate real world acceleration, this Fish 
	 * speeds up when a key remains pressed and slows down even after it is released. 
	 * move() runs a series of checks on the xVel, yVel, and rotVel variables 
	 * and updates them accordingly. Then, it applies the velocity to this Fish. 
	 * Finally, if this Fish has moved too close to the bounds of the window, 
	 * the movement is restricted so that the fish does not go off screen.
	 * 
	 */
	public void move() {
		double stepSize = .04;
		if(front) {
			yVel += (stepSize * Math.cos(Math.toRadians(rotation)));
			xVel += (stepSize * Math.sin(Math.toRadians(rotation)));
			if (yVel > .8) {
				yVel = .8;
			}
			if(xVel > .8) {
				xVel = .8;
			}
		} else {
			yVel += yVel < 0 ? .02 : -.02;
			xVel += xVel < 0 ? .02 : -.02;
		}
		if(right) {
			rotVel+= .05;
		}
		if(left) {
			rotVel-= .05;
		}
		if(!right && !left && rotVel != 0) {
			rotVel += rotVel < 0? .05:-.05;
			if (Math.abs(rotVel) < 0.01) {
				rotVel = 0;
			}
		}
		if (rotVel > 1) {
			rotVel = 1;
		}
		if (rotVel < -1) {
			rotVel = -1;
		}
		if (xVel <= .02 && xVel >= -.02) {
			xVel = 0;
		}
		if(yVel <= .02 && yVel >= -.02) {
			yVel = 0;
		}
		for(int index = 0; index < FishGameTwoTheFishening.fishPoints.length; index++) {
			FishGameTwoTheFishening.position.y -= yVel;
			FishGameTwoTheFishening.position.x += xVel;
		}
		for(int index = 0; index < FishGameTwoTheFishening.fishPoints.length; index++) {
			rotation = (rotation + rotVel)%360;
		}
		position.y = position.y > 550 ? 550 : position.y;
		position.y = position.y < 20 ? 20 : position.y;
		position.x = position.x > 780 ? 780 : position.x;
		position.x = position.x < 20 ? 20 : position.x;
	}
	
	/**
	 * 
	 * This class is an inner class of Fish, and implements CollisionEffect. 
	 * A Fish has a Star that is instantiated upon creation of a Fish object.
	 * It appears in the game only when a collision with a Shark is detected.
	 * 
	 * @author aminahasgharali
	 *
	 */
	private class Star implements CollisionEffect{
		
		private int counter = 0;
		
		/**
		 * 
		 * The paint method of a Star draws a Star in relation to the Fish object 
		 * it is associated with. The Star's position is dependent on the position 
		 * of the Fish and the counter. As the counter goes up, the Star expands 
		 * in size.
		 * 
		 * @param brush
		 * @param collisionPoint
		 * 
		 */
		public void paint(Graphics brush, Point collisionPoint) {
			int c = counter%3 == 0 ? counter : counter/3;
			int centerX = (int) collisionPoint.x;
			int[] xPoints = {centerX, centerX + 3 + c, centerX + 10 + c + c, centerX + 3 + c, centerX, centerX - 3 - c, centerX - 10 - c - c, centerX - 3 - c};
			int centerY = (int) collisionPoint.y;
			int[] yPoints = {centerY + 10 + c + c, centerY + 3 + c, centerY, centerY - 3 - c, centerY - 10 - c - c, centerY - 3 - c, centerY, centerY + 3 + c};
			brush.drawPolygon(xPoints, yPoints, xPoints.length);
			counter++;
		}
	}
	
	/**
	 * 
	 * Lines is a subclass of Fish that implements the PointManipulator interface.
	 * Lines is an object associated with a Fish object, and change depending on 
	 * the position of this Fish.
	 * 
	 * @author aminahasgharali
	 *
	 */
	private class Lines implements PointManipulator{
		
		private ArrayList<Point> line1, line2, line3;
		PointManipulator man = (int index) -> {
			return new Point ((((line2.get(index).x)+(line1.get(index).x))/2), ((((line2.get(index).y)+(line1.get(index).y)))/2));
		}; 
		
		/**
		 * 
		 * Initializes the three different lines as ArrayLists containing Points, then
		 * fills them with the Points corresponding with the left, middle, and 
		 * right side of the tail of this Fish.
		 * 
		 */
		private Lines() {
			line1 = new ArrayList<Point>();
			line2 = new ArrayList<Point>();
			line3 = new ArrayList<Point>();
			for(int index = 0; index < 5; index++) {
				line1.add(getPoints()[2]);
			}
			for(int index = 0; index < 5; index++) {
				line2.add(getPoints()[3]);
			}
			for(int index = 0; index < 5; index++) {
				line3.add(manipulate(index));
			}
		}
		
		/**
		 * 
		 * The paint method draws the lines by creating four lines connected to 
		 * each other in order to emulate curved lines. First, even four frames, 
		 * the method updates the Points of the three lines, removing the first Point
		 * from each ArrayList, and adding a new Point based on the current position
		 * of the Fish. Then, it draws the three lines by creating four lines connecting
		 * the Points in the ArrayList.
		 * 
		 * @param brush
		 */
		private void paint(Graphics brush) {
			if(FishGameTwoTheFishening.counter%4 == 0) {
				line1.remove(0);
				line2.remove(0);
				line3.remove(0);
				line1.add(getPoints()[2]);
				line2.add(getPoints()[3]);
				line3.add(manipulate(4));
			}
			brush.setColor(Color.white);
			for(int index = 1; index < 5; index++) {
				brush.drawLine((int) line1.get(index).x, (int) line1.get(index).y, (int) line1.get(index-1).x, (int) line1.get(index-1).y);
				brush.drawLine((int) line2.get(index).x, (int) line2.get(index).y, (int) line2.get(index-1).x, (int) line2.get(index-1).y);
				brush.drawLine((int) line3.get(index).x, (int) line3.get(index).y, (int) line3.get(index-1).x, (int) line3.get(index-1).y);
			}
		}
		
		/**
		 * 
		 * manipulate() implements the method specified in the PointManipulator 
		 * interface. It takes in an index, then passes this index into manipulater()
		 * called on the man, which is a lambda expression. It computes the midpoint
		 * between any two Points in line1 and line2, which determine the position
		 * of line3
		 * 
		 * @param: index
		 * 
		 */
		public Point manipulate(int index) {
			return man.manipulate(index);
		}
	}
	
	/**
	 * 
	 * This method takes in a KeyEvent, and checks which key that it corresponds to
	 * If the up arrow, right arrow, or left arrow were pressed, the boolean 
	 * instance variable correspoinding to right, left, or front movement is set to true.
	 * 
	 * @param: e
	 * 
	 */
	public void keyPressed(KeyEvent e) {
		right = e.getKeyCode() == KeyEvent.VK_RIGHT ? true : right;
		left = e.getKeyCode() == KeyEvent.VK_LEFT ? true : left;
		front = e.getKeyCode() == KeyEvent.VK_UP ? true : front;
	}
	
	/**
	 * 
	 * This method takes in a KeyEvent, and checks which key that it corresponds to
	 * If the up arrow, right arrow, or left arrow were pressed, the boolean 
	 * instance variable correspoinding to right, left, or front movement is set to false.
	 * 
	 * @param: e
	 * 
	 */
	public void keyReleased(KeyEvent e) {
		right = e.getKeyCode() == KeyEvent.VK_RIGHT ? false : right;
		left = e.getKeyCode() == KeyEvent.VK_LEFT ? false : left;
		front = e.getKeyCode() == KeyEvent.VK_UP ? false : front;
	}
	
	/**
	 * Included to fulfil KeyListener's contract.
	 */
	public void keyTyped(KeyEvent e) { }
}
