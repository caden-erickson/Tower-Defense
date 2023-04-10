package screen;

import java.awt.Color;
import java.awt.Graphics;

import game.*;

import java.awt.Font;

/**
 * An <code>ScreenWin</code> object contains code to draw the "You win!!!"
 * layer to the viewable window.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class ScreenWin implements Animatable
{
	// Fields
	GameState state;
	
	/** Constructor **/
	public ScreenWin(GameState state)
	{
		this.state = state;
	}
	
	/** Updates info- unused **/
	public void update(double timeElapsed)
	{
		// The layer just gets drawn. Nothing to update
	}

	/**
	 * Draws the "You won!!!" layer.<br>
	 * Draws a green rectangle, and then displays the leftover number of credits and
	 * lives.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object, unused here
	 */
	public void draw(Graphics g, GameView view)
	{
		// Green panel
		g.setColor(new Color(0.0f, 0.5f, 0.2f));
		g.fillRect(0, 0, 600, 600);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 50));
		g.drawString("You won!!!", 170, 270);
		
		g.setFont(new Font("arial", Font.PLAIN, 30));
		g.drawString("Credits left over: $"+state.getCredits(), 160, 350);
		g.drawString("Lives left: "+state.getLives(), 220, 380);		
	}
}