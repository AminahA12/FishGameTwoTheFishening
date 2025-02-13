package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * FishGameTwoTheFishening extends Game. It contains the implementation to draw 
 * each frame of the game, and initializes all of the Elements that are used in 
 * the game.
 * 
 * @author aminahasgharali
 *
 */
class FishGameTwoTheFishening extends Game{
	static int counter = 0;
    static Point[] fishPoints = {new Point(0, -10), new Point(10, 5), new Point(20, 15), new Point(0, 15), new Point(20, -10), new Point(10, -25)};
    static Point position = new Point(400, 300);
    static final Fish fish = new Fish(fishPoints, position, 0.0);
    static ArrayList<Shark> sharks = new ArrayList<Shark>();
    static Point[] sharkPoints = {new Point(15, -10), new Point(5, -10), new Point(-5, -17), new Point(-5, -10), new Point(-15, -10), new Point(-30, 10), new Point(-30, -10), new Point(-15, 10), new Point(15, 10), new Point(25, 0)};
    static Point[] kelpPoints = {new Point(20, 20), new Point(15, 8), new Point(7, 0), new Point(-5, -5), new Point(-10, -10), new Point(-10, 0), new Point(12, 18)};
    static Kelp kelp = new Kelp(kelpPoints, new Point(400, 0), 0.0);
    static boolean collidingShark = false;
    static boolean collidingKelp = false;
    static int collideCountShark = 0;
    static int collideCountKelp = 0;
    private static int score = 20;
    private Random random = new Random();
    
    /**
     * 
     * Calls on the super constructor to create a FishGameTwoTheFishening of size 
     * 800x600, adds KeyListener to the Fish object so that the user can control it, 
     * and runs private method sharkSetup().
     * 
     */
    public FishGameTwoTheFishening() {
      super("FishGameTwoTheFishening.",800,600);
      this.setFocusable(true);
	  this.requestFocus();
	  this.addKeyListener(fish);
	  sharkSetup();
    }
  
    /**
     * Private helper method that creates eight Sharks for the game and adds them
     * to the ArrayList sharks. They are all created at equal intervals along the x
     * axis, but a random distance above the top of the game so that they are 
     * more scattered and difficult to avoid.
     */
  	private void sharkSetup() {
  		for(int index = 0; index < 8; index++) {
  			sharks.add(new Shark(sharkPoints, new Point((index*100) + 50, -(random.nextInt(1000)) - 200), 90.0));
  		}
  	}
  	
  	/**
  	 * 
  	 * If the score falls below zero, the boolean on, inherited from the super class,
  	 * is set to false, and the game ends. </p>
  	 * Otherwise, the paint() method draws all of the elements of this 
  	 * FishGameTwoTheFishening on top of a blue background. The background color 
  	 * is chosen using RGB values. The R and G values are calculated based on the 
  	 * current number of the counter, and the B stays constant at 240, so that the
  	 * backgound is varying shades of blue.</p> 
  	 * The sharks are first moved and then drawn. The method then checks if the 
  	 * fish is colliding with any shark. If so, a collidingCounter is set to 30 + counter,
  	 * and the score decreases by two. If that collidingCounter is greater than counter,
  	 * collidingShark is set to true.</p>
  	 * Next, the fish is moved and drawn. </p>
  	 * Next, the kelp is moved and painted onto the screen. If the kelp and fish
  	 * are colliding, the kep disapears, the score is increased by four, and a 
  	 * collidingCounter is set to 10 + counter. If that collidingCounter is greater 
  	 * than the counter, collidingKelp is set to true. </p>
  	 * Finally, a small rectange is drawn in the top right, and a String is drawn
  	 * within it that displays the player's score. The counter is then incremented.
  	 * 
  	 * @param: brush
  	 */
	public void paint(Graphics brush) {
		if(score > 0) {
			brush.setColor(new Color((int) ((Math.cos(counter/100.0) + 1) * 40), (int) ((Math.cos(counter/100.0)+1)*40), 240));
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.gray);
			for(Shark s : sharks) {
				s.floatMove(random);
		    	s.paint(brush);
				if((s.collides(fish) || fish.collides(s)) && counter > collideCountShark) {
					collideCountShark = counter + 30;
					score -= 2;
				}
				else if(counter < collideCountShark) {
					collidingShark = true;
				}
				else {
					collidingShark = false;
				}
			}
			brush.setColor(Color.orange);
	    	fish.move();
	    	fish.paint(brush);  
			brush.setColor(Color.green);
			kelp.floatMove(random);
			kelp.paint(brush);
			if(kelp.collides(fish) || fish.collides(kelp)) {
				kelp.position.y = -100;
				kelp.position.x = random.nextInt(700) + 50;
				collideCountKelp = counter + 10;
				score += 4;
			}
			else if(counter < collideCountKelp) {
				collidingKelp = true;
			}
			else {
				collidingKelp = false;
			}
	    	brush.setColor(Color.black);
	    	brush.fillRect(-10, -10, 90, 40);
	    	brush.setColor(Color.white);
	    	brush.drawString("Score: " + score, 10, 20);
	    	counter++;
		}else {
			on = false;
		}
  }
	/**
	 * 
	 * Main method that creates and runs the game.
	 * 
	 * @param args
	 */
	public static void main (String[] args) {
   		FishGameTwoTheFishening a = new FishGameTwoTheFishening();
		a.repaint();
  }
}