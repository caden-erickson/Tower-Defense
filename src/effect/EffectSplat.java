package effect;

import java.awt.Graphics;
import java.awt.Point;

import game.*;

/**
 * An EffectPuddle object represents a splat icon drawn on the screen, generated
 * when a Pink Enemy dies.
 * 
 * Objects of this class keep track of the the GameState, their origin,
 * lifetime, and opacity. They will also have functionality to update their info
 * and draw themselves.
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class EffectSplat extends Effect
{
	// Fields
	private float opacity;
	private double lifeTime;
	
	/**
	 * EffectPuddle constructor. Objects built of this class will have functionality
	 * given by the Effect superclass, as well as values for lifeTime and opacity.
	 * 
	 * @param state
	 * @param origin
	 */
	public EffectSplat(GameState state, Point origin)
	{
		super(state, origin);
		opacity = 0.9f;
	}

	/**
	 * Updates the lifeTime and opacity variables. After having existed for 0.2
	 * seconds, the opacity will decrease, and the object will remove itself when
	 * the opacity reaches 0.
	 * 
	 * @param timeElapsed the number of seconds elapsed since the last update
	 */
	public void update(double timeElapsed)
	{
		lifeTime += timeElapsed;
		if (lifeTime > 0.2)
		{
			opacity -= 0.1f;
		}
		
		if (opacity <= 0.0f)
		{
			state.removeGameObject(this);
		}
	}

	/**
	 * Draws the splat image. This draw method makes use of the overloaded
	 * <code>drawCenteredImage</code> method that takes an alpha parameter, so the
	 * opacity of the splat image can be adjusted.
	 * 
	 * @param g    the <code>Graphics</code> context in which to paint
	 * @param view the current <code>GameView</code> object
	 */
	public void draw(Graphics g, GameView view)
	{
		view.drawCenteredImage(g, "splat.png", origin, 20, 20, opacity);
	}
}
