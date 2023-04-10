package effect;

import java.awt.Graphics;
import java.awt.Point;

import game.*;

/**
 * CURRENTLY UNIMPLEMENTED-- EFFECT FOR SANITIZER TOWERS
 * 
 * @author Caden Erickson
 * @version December 06, 2021
 */
public class EffectSquirt extends Effect
{
	public EffectSquirt(GameState state, Point origin, Point destination)
	{
		super(state, origin);
	}

	@Override
	public void update(double timeElapsed)
	{
	}

	@Override
	public void draw(Graphics g, GameView view)
	{
	}
}
