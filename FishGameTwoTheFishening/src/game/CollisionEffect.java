package game;

import java.awt.Graphics;

/**
 * 
 * Functional interface implemented by Star
 * 
 * @author aminahasgharali
 *
 */
public interface CollisionEffect {
	
	public void paint(Graphics brush, Point collision);
}
