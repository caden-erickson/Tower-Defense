package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.*;

/**
 * A <code>ScreenWaveTitle</code> object contains code to draw the "Wave __"
 * layer to the viewable window.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class ScreenWaveTitle implements Animatable
{
	// Fields
	GameState state;
	int waveNumber;
	double lifeTime;
	float opacity = 0.8f;
	
	/**
	 * ScreenWaveTitle constructor. Objects of this class will have access to the
	 * GameState, and will have a variable tracking which wave they're announcing.
	 * 
	 * @param state         the current <code>GameState</code> object
	 * @param waveIndicator the integer indicating which wave is next
	 */
	public ScreenWaveTitle(GameState state, int waveIndicator)
	{
		this.state = state;
		waveNumber = waveIndicator % 10;
	}

	/**
	 * Updates the lifeTime of the banner. After 1 second, the opacity will
	 * decrease. When the opacity reaches 0, this object will remove itself.
	 * 
	 * @param timeElapsed the number of seconds since the last update
	 */
	public void update(double timeElapsed)
	{
		lifeTime += timeElapsed;
		
		if (lifeTime > 1)
		{
			opacity -= 0.04f;
		}
		
		if (opacity <= 0)
		{
			state.removeGameObject(this);
		}
	}

	/**
	 * Draws the announcement banner with the current opacity.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object, unused here
	 */
	public void draw(Graphics g, GameView view)
	{
		g.setColor(new Color(1.0f, 1.0f, 1.0f, opacity));
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("WAVE "+waveNumber, 250, 320);
	}
}
