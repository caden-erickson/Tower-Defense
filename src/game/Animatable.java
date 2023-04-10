package game;

import java.awt.Graphics;

/**
 * Classes that implement the Animatable interface will contain methods to
 * update and draw objects of those classes.
 * 
 * @author Caden Erickson
 * @version November 08, 2021
 */
public interface Animatable
{
	public void update(double timeElapsed);
	public void draw(Graphics g, GameView view);
}